package lexical_parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import lexical_parser.Enums.LexemeKind;
import lexical_parser.Models.Lexeme;
import lexical_parser.Models.Location;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LexerTests
{
	private ILexer lexer;
	
	@Test
	public void getLexemes_IntegerLiterals()
	{
		String[] intNumbers = new String[] {
			"0", "01234", "1234", "1234U", "1234u", "1234L", "1234l", "1234ul",
			"1234UL", "1234Ul", "1234uL", "1234lu", "1234LU", "1234lU",
			"1234Lu", "0x0", "0x00", "0x0A", "0x0a", "0X0A", "0X0a", "0x0AU",
			"0x0Au", "0x0AL", "0x0Al", "0x0Aul", "0x0AUL", "0x0AUl", "0x0AuL",
			"0x0Alu", "0x0ALU", "0x0AlU", "0x0ALu",
		};

		for (String number : intNumbers)
		{
			this.lexer.setSource(number);
			this.lexer.parse();

			Lexeme expectedLexeme = new Lexeme(number,
				LexemeKind.IntegerLiteral, new Location(0, number.length()));

			List<Lexeme> lexemes = this.lexer.getLexemes();

			Assert.assertEquals(1, lexemes.size());

			Assert.assertEquals(expectedLexeme, lexemes.get(0));
		}
	}

	@Test
	public void getLexemes_InvalidIntegerLiterals_DividesValueIntoLexemes()
		throws Exception
	{
		HashMap<String, List<Lexeme>> hashMap = new HashMap<String, List<Lexeme>>()
		{
			{
				this.put("0x", new ArrayList<Lexeme>()
				{
					{
						this.add(new Lexeme("0", LexemeKind.IntegerLiteral,
							new Location(0, 1)));
						this.add(new Lexeme("x", LexemeKind.Identifier,
							new Location(1, 1)));
					}
				});
				this.put("x123", new ArrayList<Lexeme>()
				{
					{
						this.add(new Lexeme("x123", LexemeKind.Identifier,
							new Location(0, 4)));
					}
				});
				this.put("123uu", new ArrayList<Lexeme>()
				{
					{
						this.add(new Lexeme("123u", LexemeKind.IntegerLiteral,
							new Location(0, 4)));
						this.add(new Lexeme("u", LexemeKind.Identifier,
							new Location(4, 1)));
					}
				});
			}
		};

		for (Entry<String, List<Lexeme>> entry : hashMap.entrySet())
		{
			this.lexer.setSource(entry.getKey());
			this.lexer.parse();

			List<Lexeme> expectedLexemes = entry.getValue();

			List<Lexeme> lexemes = this.lexer.getLexemes();
			
			Assert.assertEquals(entry.getValue(), lexemes);
		}
	}

	@Test
	public void getLexemes_InvalidRealLiterals_DividesValueIntoLexemes()
		throws Exception
	{
		HashMap<String, List<Lexeme>> hashMap = new HashMap<String, List<Lexeme>>()
		{
			{
				this.put("123.45.67", new ArrayList<Lexeme>()
				{
					{
						this.add(new Lexeme("123.45", LexemeKind.RealLiteral,
							new Location(0, 6)));
						this.add(new Lexeme(".67", LexemeKind.RealLiteral,
							new Location(6, 3)));
					}
				});
				this.put("123e", new ArrayList<Lexeme>()
				{
					{
						this.add(new Lexeme("123", LexemeKind.IntegerLiteral,
							new Location(0, 3)));
						this.add(new Lexeme("e", LexemeKind.Identifier,
							new Location(3, 1)));
					}
				});
				this.put(".e+123", new ArrayList<Lexeme>()
				{
					{
						this.add(new Lexeme(".", LexemeKind.Unknown,
							new Location(0, 1)));
						this.add(new Lexeme("e", LexemeKind.Identifier,
							new Location(1, 1)));
						this.add(new Lexeme("+", LexemeKind.Unknown,
							new Location(2, 1)));
						this.add(new Lexeme("123", LexemeKind.IntegerLiteral,
							new Location(3, 3)));
					}
				});
			}
		};

		for (Entry<String, List<Lexeme>> entry : hashMap.entrySet())
		{
			this.lexer.setSource(entry.getKey());
			this.lexer.parse();

			List<Lexeme> lexemes = this.lexer.getLexemes();

			Assert.assertEquals(entry.getValue(), lexemes);
		}
	}

	@Test
	public void getLexemes_RealLiterals()
	{
		String[] realNumbers = new String[] {
			".0", "0.123", "0123.0123", ".0123e0123", ".0123E0123",
			".0123e+0123", ".0123e-0123", "0123.0123e0123", "0123d", "0123D",
			"0123f", "0123F", "0123m", "0123M", "0123.0123d", "0123.0123f",
			"0123.0123m",
		};

		for (String number : realNumbers)
		{
			this.lexer.setSource(number);
			this.lexer.parse();

			Lexeme expectedLexeme = new Lexeme(number, LexemeKind.RealLiteral,
				new Location(0, number.length()));

			List<Lexeme> lexemes = this.lexer.getLexemes();

			Assert.assertEquals(1, lexemes.size());

			Assert.assertEquals(expectedLexeme, lexemes.get(0));
		}
	}

	@Test
	public void getLexemes_SourceIsNullOrNotParsed_ReturnsEmptyList()
	{
		Assert.assertTrue(this.lexer.getLexemes().iterator().hasNext());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setSource_SourceIsNull_ThrowsIllegalArgumentException()
	{
		this.lexer.setSource(null);
	}
	
	@Before
	public void setUp() throws Exception
	{
		this.lexer = new Lexer();
	}
	
	@Test
	public void testParse()
	{
		Assert.fail("Not yet implemented");
	}
	
}
