package lexical_parser.LexemeDefinitions;

import lexical_parser.Enums.LexemeKind;

public interface ILexemeDefinition<T>
{
	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public LexemeKind getKind();
	
	/**
	 * Gets the representation.
	 *
	 * @return the representation
	 */
	public T getRepresentation();
}
