package lexical_parser.Helpers;

import java.util.ArrayList;
import java.util.List;

import lexical_parser.Enums.TokenKind;
import lexical_parser.Models.TokenDefinition;

public class LexerHelper
{
	/** The Keywords. */
	public static String[] Keywords = new String[] {
		"abstract", "as", "base", "bool", "break", "byte", "case", "catch",
		"char", "checked", "class", "const", "continue", "decimal", "default",
		"delegate", "do", "double", "else", "enum", "ev", "ent", "explicit",
		"extern", "false", "finally", "fixed", "float", "for", "foreach",
		"goto", "if", "implicit", "in", "int", "interface", "internal", "is",
		"lock", "long", "namespace", "new", "null", "object", "operator",
		"out", "override", "params", "private", "protected", "public",
		"readonly", "ref", "return", "sbyte", "sealed", "short", "sizeof",
		"stackalloc", "static", "string", "struct", "switch", "this", "throw",
		"true", "try", "typeof", "uint", "ulong", "unchecked", "unsafe",
		"ushort", "using", "var", "virtual", "void", "volatile", "while"
	};
	
	/**
	 * The Token definitions list. Based on the C# language specification:
	 * http://msdn.microsoft.com/en-us/library/aa664668(v=vs.71).aspx
	 */
	public static List<TokenDefinition> TokenDefinitionsList = new ArrayList<TokenDefinition>() {
	{
			this.add(new TokenDefinition("@?[a-zA-Z_][a-zA-Z0-9_]*",
				TokenKind.Identifier));
			this.add(new TokenDefinition(
				"(([0-9]*\\.[0-9]+)([eE][-+]?[0-9]+)?[FfDdMm]?)|([0-9]+[FfDdMm]{1})",
				TokenKind.RealLiteral));
			this.add(new TokenDefinition(
				"(?:(?:0[xX][0-9a-fA-F]+)|[0-9]+)(?:UL|Ul|uL|ul|LU|Lu|lU|lu|U|u|L|l)?",
				TokenKind.IntegerLiteral));
			this.add(new TokenDefinition(
				"#\\s*(?:define|undef|if|elif|else|endif|line|error|warning|region|endregion)",
				TokenKind.PreprocessingDirective));
			this.add(new TokenDefinition(
				"(?:\"(?:\\\\[\'\"\\\\0abfnrtuUxv]|[^\\\\\"\n])*\")|(?:@\"(?:[^\"]|\"\")*\")",
				TokenKind.StringLiteral));
			this.add(new TokenDefinition(
				"(?:\'(?:[^\'\\\\\n]|(?:\\\\[\'\"\\\\0abfnrtuUxv])|(?:\\\\x[0-9a-fA-F]{1,4}))?\')",
				TokenKind.CharacterLiteral));
			this.add(new TokenDefinition(
				"(?:/\\*[^*]*\\*+(?:[^*/][^*]*\\*+)*/)|(?://.*)",
				TokenKind.Comment));
			this.add(new TokenDefinition(
				"<<=|>>=|->|\\*=|/=|%=|&=|\\|=|\\^=|\\+\\+|--|&&|\\|\\||<<|>>|==|!=|<=|>=|\\+=|-=|\\+|-|\\*|/|%|&|\\||\\^|!|~|=|<|>|\\?",
				TokenKind.Operator));
			this.add(new TokenDefinition("[\\Q{}[]().,:;\\E]",
				TokenKind.Punctuator));
		}
	};
}
