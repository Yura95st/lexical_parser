package lexical_parser.TokenDefinitions;

import lexical_parser.Enums.TokenKind;

public interface ITokenDefinition<T>
{
	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public TokenKind getKind();
	
	/**
	 * Gets the representation.
	 *
	 * @return the representation
	 */
	public T getRepresentation();
}
