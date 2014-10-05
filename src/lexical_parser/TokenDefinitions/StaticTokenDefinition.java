package lexical_parser.TokenDefinitions;

import lexical_parser.Enums.TokenKind;

public class StaticTokenDefinition implements ITokenDefinition<String>
{
	private TokenKind kind;

	private String representation;

	public StaticTokenDefinition(String representation, TokenKind kind)
	{
		this.representation = representation;

		this.kind = kind;
	}

	@Override
	public TokenKind getKind()
	{
		return this.kind;
	}

	@Override
	public String getRepresentation()
	{
		return this.representation;
	}
}
