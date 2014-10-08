package lexical_parser.Models;

import java.util.regex.Pattern;

import lexical_parser.Enums.TokenKind;

public class TokenDefinition
{
	private TokenKind kind;

	private Pattern representation;

	public TokenDefinition(String representation, TokenKind kind)
	{
		this.representation = Pattern.compile(representation);
		this.kind = kind;
	}

	public TokenKind getKind()
	{
		return this.kind;
	}
	
	public Pattern getRepresentation()
	{
		return this.representation;
	}

}
