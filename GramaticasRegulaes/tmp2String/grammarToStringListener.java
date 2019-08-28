// Generated from grammarToString.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link grammarToStringParser}.
 */
public interface grammarToStringListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link grammarToStringParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(grammarToStringParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link grammarToStringParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(grammarToStringParser.StartContext ctx);
}