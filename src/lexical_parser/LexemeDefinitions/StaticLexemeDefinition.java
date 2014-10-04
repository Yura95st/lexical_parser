package lexical_parser.LexemeDefinitions;

import lexical_parser.Enums.LexemeKind;

public class StaticLexemeDefinition implements ILexemeDefinition<String>
{
	private boolean isKeyword;
	
	private LexemeKind kind;
	
	private String representation;
	
	public StaticLexemeDefinition(String representation, LexemeKind kind,
		boolean isKeyword)
	{
		this.representation = representation;
		
		this.kind = kind;
		
		this.isKeyword = isKeyword;
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

	/**
	 * Checks if is keyword.
	 *
	 * @return true, if is keyword
	 */
	public boolean isKeyword()
	{
		return this.isKeyword;
	}
}
