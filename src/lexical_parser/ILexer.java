package lexical_parser;

import lexical_parser.Models.Lexeme;

public interface ILexer
{
	/**
	 * Gets the lexemes.
	 *
	 * @return the lexemes
	 */
	Iterable<Lexeme> getLexemes();
	
	/**
	 * Parses the source.
	 *
	 * @throws Exception
	 *             the exception
	 */
	void parse() throws Exception;
	
	/**
	 * Sets the source.
	 *
	 * @param source
	 *            the new source
	 */
	void setSource(String source);
}
