package lexical_parser.LexemeDefinitions;

import lexical_parser.Enums.LexemeKind;

public class StaticLexemeDefinition implements ILexemeDefinition<String>
{
	private LexemeKind kind;

	private String representation;

	public StaticLexemeDefinition(String representation, LexemeKind kind)
	{
		this.representation = representation;

		this.kind = kind;
	}

	@Override
	public LexemeKind getKind()
	{
		return this.kind;
	}

	@Override
	public String getRepresentation()
	{
		return this.representation;
	}
}
