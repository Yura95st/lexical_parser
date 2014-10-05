package lexical_parser;

import java.util.List;

import lexical_parser.Models.Lexeme;

public interface ILexer
{
	/**
	 * Gets the lexemes.
	 *
	 * @return the lexemes
	 */
	List<Lexeme> getLexemes();

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
