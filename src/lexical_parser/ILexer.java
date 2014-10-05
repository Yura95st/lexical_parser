package lexical_parser;

import java.util.List;

import lexical_parser.Models.Token;

public interface ILexer
{
	/**
	 * Gets the tokens.
	 *
	 * @return the tokens
	 */
	List<Token> getTokens();

	/**
	 * Parses the source.
	 */
	void parse();

	/**
	 * Sets the source.
	 *
	 * @param source
	 *            the new source
	 */
	void setSource(String source);
}
