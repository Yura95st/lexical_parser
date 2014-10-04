package lexical_parser.LexemeDefinitions;

import java.util.regex.Pattern;

import lexical_parser.Enums.LexemeKind;

public class DynamicLexemeDefinition implements ILexemeDefinition<Pattern>
{
	private LexemeKind kind;
	
	private Pattern representation;
	
	public DynamicLexemeDefinition(String representation, LexemeKind kind)
	{
		this.representation = Pattern.compile(representation);
		this.kind = kind;
	}
	
	@Override
	public LexemeKind getKind()
	{
		return this.kind;
	}

	@Override
	public Pattern getRepresentation()
	{
		return this.representation;
	}
	
}
