package lexical_parser;

import java.util.List;

import lexical_parser.Models.Token;

public interface ILexer
{
	/**
	 * Gets the keywords.
	 *
	 * @return the keywords
	 */
	Iterable<String> getKeywords();

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
	 * Sets the keywords.
	 *
	 * @param keywords
	 *            the new keywords
	 */
	void setKeywords(String[] keywords);
	
	/**
	 * Sets the source.
	 *
	 * @param source
	 *            the new source
	 */
	void setSource(String source);
}
