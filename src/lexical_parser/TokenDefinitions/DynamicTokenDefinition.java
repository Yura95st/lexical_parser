package lexical_parser.TokenDefinitions;

import java.util.regex.Pattern;

import lexical_parser.Enums.TokenKind;

public class DynamicTokenDefinition implements ITokenDefinition<Pattern>
{
	private TokenKind kind;
	
	private Pattern representation;
	
	public DynamicTokenDefinition(String representation, TokenKind kind)
	{
		this.representation = Pattern.compile(representation);
		this.kind = kind;
	}
	
	@Override
	public TokenKind getKind()
	{
		return this.kind;
	}

	@Override
	public Pattern getRepresentation()
	{
		return this.representation;
	}
	
}
