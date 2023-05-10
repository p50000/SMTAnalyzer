import org.sosy_lab.java_smt.api.BooleanFormula
import org.sosy_lab.java_smt.api.FormulaManager
import org.sosy_lab.java_smt.api.Model
import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula
import org.sosy_lab.java_smt.api.SolverContext

data class ExecutionTreeNode(
    val text: String,
    val expandedOperation: Operation,
    val constraint: BoolExp?,
    val nextNodes: ArrayList<ExecutionTreeNode>
)

var cnt = 0

fun buildFullExp(expr: Expression, nodeMap: HashMap<String, Expression>): Expression {
    when (expr) {
        is Read -> {
            cnt++
            return Id("IN" + cnt)
        }
        is Id -> {
            return nodeMap[expr.id]!!
        }
        is Integer -> {
            return expr
        }
        is ExpSum -> {
            return ExpSum(buildFullExp(expr.lhs, nodeMap), buildFullExp(expr.rhs, nodeMap))
        }
        is ExpSub -> {
            return ExpSub(buildFullExp(expr.lhs, nodeMap), buildFullExp(expr.rhs, nodeMap))
        }
        is ExpMult -> {
            return ExpMult(buildFullExp(expr.lhs, nodeMap), buildFullExp(expr.rhs, nodeMap))
        }
    }
    return expr
}

fun buildFullBoolExp(bool: BoolExp, nodeMap: HashMap<String, Expression>): BoolExp {
    when (bool) {
        is ConstBool -> {
            return bool
        }
        is ExpEq -> {
            return ExpEq(buildFullExp(bool.lhs, nodeMap), buildFullExp(bool.rhs, nodeMap))
        }
        is ExpLe -> {
            return ExpLe(buildFullExp(bool.lhs, nodeMap), buildFullExp(bool.rhs, nodeMap))
        }
        is Not -> {
            return Not(buildFullBoolExp(bool.b, nodeMap))
        }
        is And -> {
            return And(buildFullBoolExp(bool.lhs, nodeMap), buildFullBoolExp(bool.rhs, nodeMap))
        }
        else -> {
            return bool
        }
    }
}

fun buildExecutionTreeAndConstraints(node: CFGNode, nodeMap: HashMap<String, Expression>, constraint: BoolExp?): ExecutionTreeNode {
    val nodevalue = node.value
    when (nodevalue) {
        is Assert -> {
            val treeNode = ExecutionTreeNode(
                node.text,
                Assert(buildFullBoolExp(nodevalue.expression, nodeMap)),
                constraint,
                ArrayList())
            val newConstraint = if (constraint == null) {
                (treeNode.expandedOperation as Assert).expression
            } else {
                And(constraint, (treeNode.expandedOperation as Assert).expression)
            }
            for (child in node.children) {
                treeNode.nextNodes.add(buildExecutionTreeAndConstraints(child, nodeMap, newConstraint))
            }
            return treeNode
        }
        is Assign -> {
            val treeNode = ExecutionTreeNode(
                node.text,
                Assign(nodevalue.value, buildFullExp(nodevalue.expression, nodeMap)),
                constraint,
                ArrayList()
            )
            nodeMap[nodevalue.value.id] = (treeNode.expandedOperation as Assign).expression
            for (child in node.children) {
                treeNode.nextNodes.add(buildExecutionTreeAndConstraints(child, nodeMap, constraint))
            }
            return treeNode
        }
        is Condition -> {
            val treeNode = ExecutionTreeNode(
                node.text,
                Condition(buildFullBoolExp(nodevalue.expression, nodeMap)),
                constraint,
                ArrayList()
            )
            val child1 = node.children[0]
            val newConstraint1 = if (constraint == null) {
                (treeNode.expandedOperation as Condition).expression
            } else {
                And(constraint, (treeNode.expandedOperation as Condition).expression)
            }
            treeNode.nextNodes.add(buildExecutionTreeAndConstraints(child1, nodeMap, newConstraint1))

            val child2 = node.children[1]
            val newConstraint2 = if (constraint == null) {
                treeNode.expandedOperation.expression
            } else {
                And(constraint, Not(treeNode.expandedOperation.expression))
            }
            treeNode.nextNodes.add(buildExecutionTreeAndConstraints(child2, nodeMap, newConstraint2))
            return treeNode
        }
        else -> {
            val treeNode = ExecutionTreeNode(
                node.text,
                nodevalue,
                constraint,
                ArrayList()
            )
            for (child in node.children) {
                treeNode.nextNodes.add(buildExecutionTreeAndConstraints(child, nodeMap, constraint))
            }
            return treeNode
        }
    }
}

fun buildIntegerFormula(formula: Expression, fmgr: FormulaManager, varMap: HashMap<String, IntegerFormula>): IntegerFormula {
    when (formula) {
        is Id -> {
            if (!varMap.containsKey(formula.id)) {
                varMap[formula.id] = fmgr.integerFormulaManager.makeVariable(formula.id)
            }
            return varMap[formula.id]!!
        }
        is Integer -> {
            return fmgr.integerFormulaManager.makeNumber(formula.exp.toLong())
        }
        is ExpSum -> {
            return fmgr.integerFormulaManager.sum(listOf(
                buildIntegerFormula(formula.lhs, fmgr, varMap),
                buildIntegerFormula(formula.rhs, fmgr, varMap)
            ))
        }
        is ExpSub -> {
            return fmgr.integerFormulaManager.subtract(
                buildIntegerFormula(formula.lhs, fmgr, varMap),
                buildIntegerFormula(formula.rhs, fmgr, varMap)
            )
        }
        is ExpMult -> {
            return fmgr.integerFormulaManager.multiply(
                buildIntegerFormula(formula.lhs, fmgr, varMap),
                buildIntegerFormula(formula.rhs, fmgr, varMap)
            )
        }
        else -> {
            throw Exception("Чего)")
        }
    }
}

fun buildBooleanFormula(formula: BoolExp,  fmgr: FormulaManager, varMap: HashMap<String, IntegerFormula>): BooleanFormula {
    when (formula) {
        is ConstBool -> {
             return fmgr.booleanFormulaManager.makeTrue()
        }
        is ExpEq -> {
            return fmgr.integerFormulaManager.equal(
                buildIntegerFormula(formula.lhs, fmgr, varMap),
                buildIntegerFormula(formula.rhs, fmgr, varMap))
        }
        is ExpLe -> {
            return fmgr.integerFormulaManager.lessOrEquals(
                buildIntegerFormula(formula.lhs, fmgr, varMap),
                buildIntegerFormula(formula.rhs, fmgr, varMap))
        }
        is Not -> {
            return fmgr.booleanFormulaManager.not(buildBooleanFormula(formula.b, fmgr, varMap))
        }
        is And -> {
            return fmgr.booleanFormulaManager.and(
                buildBooleanFormula(formula.lhs, fmgr, varMap),
                buildBooleanFormula(formula.rhs, fmgr, varMap))
        }
        else -> {
            throw Exception("Чего)")
        }
    }
}

fun solveSMTForAsserts(node: ExecutionTreeNode, context: SolverContext, fmgr: FormulaManager) {
    val operation = node.expandedOperation
    when (operation) {
        is Assert -> {
            val varMap = HashMap<String, IntegerFormula>()
            val formula = if (node.constraint != null)
                And(node.constraint, Not(operation.expression))
            else
                Not(operation.expression)

            val constraint = buildBooleanFormula(formula, fmgr, varMap)
            println()
            println("For ASSERT " + node.text)
            context.newProverEnvironment(SolverContext.ProverOptions.GENERATE_MODELS).use { prover ->
                prover.addConstraint(constraint)
                val isUnsat = prover.isUnsat
                if (!isUnsat) {
                    val model: Model = prover.model
                    println("Fails with values: ")
                    for (id in varMap.keys) {
                        print(id + " = " + varMap[id]?.let { model.evaluate(it) } + " ")
                    }
                    println()
                } else {
                    println("ASSERT NEVER FAILS")
                    println()
                }
            }
        }
    }
    for (nextNode in node.nextNodes) {
        solveSMTForAsserts(nextNode, context, fmgr)
    }
}