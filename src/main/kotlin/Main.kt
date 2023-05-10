import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.sosy_lab.common.ShutdownNotifier
import org.sosy_lab.common.configuration.Configuration
import org.sosy_lab.common.log.LogManager
import org.sosy_lab.java_smt.SolverContextFactory
import org.sosy_lab.java_smt.api.SolverContext
import parser.WhilelangLexer
import parser.WhilelangParser
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import kotlin.system.exitProcess


fun main(args: Array<String> = emptyArray()) {
    val l: WhilelangLexer
    var p: WhilelangParser

    try {
        val input: Reader = if (args.isEmpty()) {
            InputStreamReader(System.`in`)
        } else  {
            FileReader(args[0])
        }
        l = WhilelangLexer(ANTLRInputStream(input))
    } catch (e: IOException) {
        System.err.println("Error: File not found: " + args[0])
        exitProcess(1)
    }
    p = WhilelangParser(CommonTokenStream(l))
    val graph = CFGBuilder().visitProgram(p.program())
    val tree = buildExecutionTreeAndConstraints(graph.nodes[0], HashMap(), null)
    val context: SolverContext =
        SolverContextFactory.createSolverContext(
            Configuration.defaultConfiguration(),
            LogManager.createNullLogManager(),
            ShutdownNotifier.createDummy(),
            SolverContextFactory.Solvers.SMTINTERPOL)
    solveSMTForAsserts(tree, context, context.formulaManager)
}