package lexical_parser.Helpers;

import java.util.ArrayList;
import java.util.List;

import lexical_parser.Enums.LexemeKind;
import lexical_parser.LexemeDefinitions.DynamicLexemeDefinition;
import lexical_parser.LexemeDefinitions.StaticLexemeDefinition;

public class LexemeDefinitionHelper
{
	public static List<DynamicLexemeDefinition> DynamicLexemeDefinitionsList = new ArrayList<DynamicLexemeDefinition>()
	{
		{
			this.add(new DynamicLexemeDefinition("[a-zA-Z_][a-zA-Z0-9_]*",
				LexemeKind.Identifier));
			this.add(new DynamicLexemeDefinition(
				"(([0-9]*\\.[0-9]+)([eE][-+]?[0-9]+)?(F|f|D|d|M|m)?)|([0-9]+(F|f|D|d|M|m){1})",
				LexemeKind.RealLiteral));
			this.add(new DynamicLexemeDefinition(
				"((0[xX][0-9a-fA-F]+)|[0-9]+)(UL|Ul|uL|ul|LU|Lu|lU|lu|U|u|L|l)?",
				LexemeKind.IntegerLiteral));
		}
	};
	
	public static List<StaticLexemeDefinition> StaticLexemeDefinitionsList = new ArrayList<StaticLexemeDefinition>();
	
}
