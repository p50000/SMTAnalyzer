import parser.WhilelangParser.AndContext
import parser.WhilelangParser.AssertContext
import parser.WhilelangParser.AttribContext
import parser.WhilelangParser.BinOpContext
import parser.WhilelangParser.BlockContext
import parser.WhilelangParser.BoolContext
import parser.WhilelangParser.BoolParenContext
import parser.WhilelangParser.BooleanContext
import parser.WhilelangParser.ExpParenContext
import parser.WhilelangParser.ExpressionContext
import parser.WhilelangParser.IdContext
import parser.WhilelangParser.IfContext
import parser.WhilelangParser.IntContext
import parser.WhilelangParser.NotContext
import parser.WhilelangParser.PrintContext
import parser.WhilelangParser.ProgramContext
import parser.WhilelangParser.ReadContext
import parser.WhilelangParser.RelOpContext
import parser.WhilelangParser.StatementContext
import parser.WhilelangParser.WriteContext

data class CFGNode(
    var value: Operation,
    var children: MutableList<CFGNode>,
    var text: String
) {
    fun addChild(value: CFGNode) {
        children.add(value)
    }
}

data class CFG(
    var nodes: MutableList<CFGNode>
) {
    fun addNode(value: CFGNode) {
        nodes.add(value)
    }
}

// All Operations
open class Operation { }

class Assert(val expression: BoolExp): Operation()
class Condition(val expression: BoolExp): Operation()
class Assign(val value: Id, val expression: Expression): Operation()
class Print(val text: String): Operation()

// All Bool expressions
open class BoolExp()

class ConstBool(val b: Boolean): BoolExp()
class ExpEq(val lhs: Expression, val rhs: Expression): BoolExp()
class ExpLe(val lhs: Expression, val rhs: Expression): BoolExp()
class Not(val b: BoolExp): BoolExp()
class And(val lhs: BoolExp, val rhs: BoolExp): BoolExp()


// All Expressions
open class Expression()

class Read(): Expression()
class Id(val id: String): Expression()
class Integer(val exp: Int): Expression()
class ExpSum(val lhs: Expression, val rhs: Expression): Expression()
class ExpSub(val lhs: Expression, val rhs: Expression): Expression()
class ExpMult(val lhs: Expression, val rhs: Expression): Expression()

class CFGBuilder {

    fun buildExp(expression: ExpressionContext): Expression {
        when (expression) {
            is ExpParenContext -> {
                return buildExp(expression.expression())
            }
            is BinOpContext -> {
                when(expression.getChild(1).text) {
                    "*" -> {
                        return ExpMult(buildExp(expression.expression(0)), buildExp(expression.expression(1)))
                    }
                    "-" -> {
                        return ExpSub(buildExp(expression.expression(0)), buildExp(expression.expression(1)))
                    }
                    "+" -> {
                        return ExpSum(buildExp(expression.expression(0)), buildExp(expression.expression(1)))
                    }
                }
            }
            is IdContext -> {
                return Id(expression.ID().text)
            }
            is IntContext -> {
                return Integer(expression.text.toInt())
            }
            is ReadContext -> {
                return Read()
            }
        }
        return Expression()
    }

    fun buildBoolExp(boolExp: BoolContext): BoolExp {
        when (boolExp) {
            is BoolParenContext -> {
                return buildBoolExp(boolExp.bool())
            }
            is AndContext -> {
                return And(buildBoolExp(boolExp.bool(0)), buildBoolExp(boolExp.bool(1)))
            }
            is BooleanContext -> {
                return ConstBool(boolExp.text == "true")
            }
            is NotContext -> {
                return Not(buildBoolExp(boolExp.bool()))
            }
            is RelOpContext -> {
                when (boolExp.getChild(1).text) {
                    "=" -> {
                        return ExpEq(buildExp(boolExp.expression(0)), buildExp(boolExp.expression(1)))
                    }
                    "<=" -> {
                        return ExpLe(buildExp(boolExp.expression(0)), buildExp(boolExp.expression(1)))
                    }
                }
            }
        }
        return BoolExp()
    }

    fun buildStatement(statement: StatementContext, graph: CFG, prevNodes: List<CFGNode>): List<CFGNode> {
        when (statement) {
            is AssertContext -> {
                val node = CFGNode(
                    Assert(buildBoolExp(statement.bool())),
                    ArrayList(),
                    statement.text
                )
                graph.addNode(node)
                for (prevNode in prevNodes) {
                    prevNode.addChild(node)
                }
                return listOf(node)
            }

            is AttribContext -> {
                val node = CFGNode(
                    Assign(
                        Id(statement.ID().text),
                        buildExp(statement.expression())
                    ),
                    ArrayList(),
                    statement.text
                )
                graph.addNode(node)
                for (prevNode in prevNodes) {
                    prevNode.addChild(node)
                }
                return listOf(node)
            }

            is BlockContext -> {
                var curPrevNodes = prevNodes
                for (state in statement.seqStatement().statement()) {
                    curPrevNodes = buildStatement(state, graph, curPrevNodes)
                }
                return curPrevNodes
            }

            is IfContext -> {
                val node = CFGNode(
                    Condition(buildBoolExp(statement.bool())),
                    ArrayList(),
                    statement.text
                )
                graph.addNode(node)
                for (prevNode in prevNodes) {
                    prevNode.addChild(node)
                }
                val nodes1 = buildStatement(statement.statement(0), graph, listOf(node))
                val nodes2 = buildStatement(statement.statement(1), graph, listOf(node))
                return nodes1 + nodes2
            }

            is PrintContext -> {
                val node = CFGNode(
                    Print(statement.text),
                    ArrayList(),
                    statement.text
                )
                graph.addNode(node)
                for (prevNode in prevNodes) {
                    prevNode.addChild(node)
                }
                return listOf(node)
            }

            is WriteContext -> {
                val node = CFGNode(
                    Print(statement.text),
                    ArrayList(),
                    statement.text
                )
                graph.addNode(node)
                for (prevNode in prevNodes) {
                    prevNode.addChild(node)
                }
                return listOf(node)
            }
        }
        return listOf()
    }

    fun visitProgram(program: ProgramContext): CFG{
        val graph = CFG(ArrayList<CFGNode>())

        var curPrevNodes = listOf<CFGNode>()
        for (statement in program.seqStatement().statement()) {
            curPrevNodes = buildStatement(statement, graph, curPrevNodes)
        }
        return graph
    }
}