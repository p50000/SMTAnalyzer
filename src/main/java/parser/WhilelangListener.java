package parser;// Generated from Whilelang.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link WhilelangParser}.
 */
public interface WhilelangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link WhilelangParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(WhilelangParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhilelangParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(WhilelangParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhilelangParser#seqStatement}.
	 * @param ctx the parse tree
	 */
	void enterSeqStatement(WhilelangParser.SeqStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhilelangParser#seqStatement}.
	 * @param ctx the parse tree
	 */
	void exitSeqStatement(WhilelangParser.SeqStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code attrib}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAttrib(WhilelangParser.AttribContext ctx);
	/**
	 * Exit a parse tree produced by the {@code attrib}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAttrib(WhilelangParser.AttribContext ctx);
	/**
	 * Enter a parse tree produced by the {@code if}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIf(WhilelangParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code if}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIf(WhilelangParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assert}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssert(WhilelangParser.AssertContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assert}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssert(WhilelangParser.AssertContext ctx);
	/**
	 * Enter a parse tree produced by the {@code print}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterPrint(WhilelangParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code print}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitPrint(WhilelangParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code write}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWrite(WhilelangParser.WriteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code write}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWrite(WhilelangParser.WriteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code block}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlock(WhilelangParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code block}
	 * labeled alternative in {@link WhilelangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlock(WhilelangParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code read}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRead(WhilelangParser.ReadContext ctx);
	/**
	 * Exit a parse tree produced by the {@code read}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRead(WhilelangParser.ReadContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterId(WhilelangParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitId(WhilelangParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expParen}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpParen(WhilelangParser.ExpParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expParen}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpParen(WhilelangParser.ExpParenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInt(WhilelangParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInt(WhilelangParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binOp}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinOp(WhilelangParser.BinOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binOp}
	 * labeled alternative in {@link WhilelangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinOp(WhilelangParser.BinOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code not}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterNot(WhilelangParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code not}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitNot(WhilelangParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolean}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBoolean(WhilelangParser.BooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolean}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBoolean(WhilelangParser.BooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code and}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterAnd(WhilelangParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code and}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitAnd(WhilelangParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolParen}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBoolParen(WhilelangParser.BoolParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolParen}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBoolParen(WhilelangParser.BoolParenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relOp}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterRelOp(WhilelangParser.RelOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relOp}
	 * labeled alternative in {@link WhilelangParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitRelOp(WhilelangParser.RelOpContext ctx);
}