package lexical_parser.Helpers;

import java.util.ArrayList;
import java.util.List;

import lexical_parser.Enums.TokenKind;
import lexical_parser.TokenDefinitions.DynamicTokenDefinition;
import lexical_parser.TokenDefinitions.StaticTokenDefinition;

public class TokenDefinitionHelper
{
	public static List<DynamicTokenDefinition> DynamicTokenDefinitionsList = new ArrayList<DynamicTokenDefinition>()
	{
		{
			this.add(new DynamicTokenDefinition("[a-zA-Z_][a-zA-Z0-9_]*",
				TokenKind.Identifier));
			this.add(new DynamicTokenDefinition(
				"(([0-9]*\\.[0-9]+)([eE][-+]?[0-9]+)?(F|f|D|d|M|m)?)|([0-9]+(F|f|D|d|M|m){1})",
				TokenKind.RealLiteral));
			this.add(new DynamicTokenDefinition(
				"((0[xX][0-9a-fA-F]+)|[0-9]+)(UL|Ul|uL|ul|LU|Lu|lU|lu|U|u|L|l)?",
				TokenKind.IntegerLiteral));
		}
	};
	
	public static List<StaticTokenDefinition> StaticTokenDefinitionsList = new ArrayList<StaticTokenDefinition>();
	
}
