package lexical_parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import lexical_parser.Enums.TokenKind;
import lexical_parser.Helpers.TokenDefinitionHelper;
import lexical_parser.Models.Token;
import lexical_parser.Models.Location;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LexerTests
{
	private ILexer lexer;
	
	@Test
	public void getTokens_IntegerLiterals()
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

			Token expectedToken = new Token(number,
				TokenKind.IntegerLiteral, new Location(0, number.length()));

			List<Token> tokens = this.lexer.getTokens();

			Assert.assertEquals(1, tokens.size());

			Assert.assertEquals(expectedToken, tokens.get(0));
		}
	}

	@Test
	public void getTokens_InvalidIntegerLiterals_DividesValueIntoTokens()
		throws Exception
	{
		HashMap<String, List<Token>> hashMap = new HashMap<String, List<Token>>()
		{
			{
				this.put("0x", new ArrayList<Token>()
				{
					{
						this.add(new Token("0", TokenKind.IntegerLiteral,
							new Location(0, 1)));
						this.add(new Token("x", TokenKind.Identifier,
							new Location(1, 1)));
					}
				});
				this.put("x123", new ArrayList<Token>()
				{
					{
						this.add(new Token("x123", TokenKind.Identifier,
							new Location(0, 4)));
					}
				});
				this.put("123uu", new ArrayList<Token>()
				{
					{
						this.add(new Token("123u", TokenKind.IntegerLiteral,
							new Location(0, 4)));
						this.add(new Token("u", TokenKind.Identifier,
							new Location(4, 1)));
					}
				});
			}
		};

		for (Entry<String, List<Token>> entry : hashMap.entrySet())
		{
			this.lexer.setSource(entry.getKey());
			this.lexer.parse();

			List<Token> expectedTokens = entry.getValue();

			List<Token> tokens = this.lexer.getTokens();
			
			Assert.assertEquals(entry.getValue(), tokens);
		}
	}

	@Test
	public void getTokens_InvalidRealLiterals_DividesValueIntoTokens()
		throws Exception
	{
		HashMap<String, List<Token>> hashMap = new HashMap<String, List<Token>>()
		{
			{
				this.put("123.45.67", new ArrayList<Token>()
				{
					{
						this.add(new Token("123.45", TokenKind.RealLiteral,
							new Location(0, 6)));
						this.add(new Token(".67", TokenKind.RealLiteral,
							new Location(6, 3)));
					}
				});
				this.put("123e", new ArrayList<Token>()
				{
					{
						this.add(new Token("123", TokenKind.IntegerLiteral,
							new Location(0, 3)));
						this.add(new Token("e", TokenKind.Identifier,
							new Location(3, 1)));
					}
				});
				this.put(".e+123", new ArrayList<Token>()
				{
					{
						this.add(new Token(".", TokenKind.Unknown,
							new Location(0, 1)));
						this.add(new Token("e", TokenKind.Identifier,
							new Location(1, 1)));
						this.add(new Token("+", TokenKind.Unknown,
							new Location(2, 1)));
						this.add(new Token("123", TokenKind.IntegerLiteral,
							new Location(3, 3)));
					}
				});
			}
		};

		for (Entry<String, List<Token>> entry : hashMap.entrySet())
		{
			this.lexer.setSource(entry.getKey());
			this.lexer.parse();

			List<Token> tokens = this.lexer.getTokens();

			Assert.assertEquals(entry.getValue(), tokens);
		}
	}

	@Test
	public void getTokens_RealLiterals()
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

			Token expectedToken = new Token(number, TokenKind.RealLiteral,
				new Location(0, number.length()));

			List<Token> tokens = this.lexer.getTokens();

			Assert.assertEquals(1, tokens.size());

			Assert.assertEquals(expectedToken, tokens.get(0));
		}
	}

	@Test
	public void getTokens_SourceIsNullOrNotParsed_ReturnsEmptyList()
	{
		Assert.assertEquals(0, this.lexer.getTokens().size());
	}
	
	@Test
	public void getTokens_Identifiers()
	{
		String[] identifiers = new String[] {
			"identifier1", "_identifier2", "identifier_3", "@if"
		};

		for (String identifier : identifiers)
		{
			this.lexer.setSource(identifier);
			this.lexer.parse();

			Token expectedToken = new Token(identifier,
				TokenKind.Identifier, new Location(0, identifier.length()));

			List<Token> tokens = this.lexer.getTokens();

			Assert.assertEquals(1, tokens.size());

			Assert.assertEquals(expectedToken, tokens.get(0));
		}
	}
	
	@Test
	public void getTokens_InvalidIdentifiers_DividesValueIntoTokens()
		throws Exception
	{
		HashMap<String, List<Token>> hashMap = new HashMap<String, List<Token>>()
		{
			{
				this.put("@identifier", new ArrayList<Token>()
				{
					{
						this.add(new Token("@", TokenKind.Unknown,
							new Location(0, 1)));
						this.add(new Token("identifier", TokenKind.Identifier,
							new Location(1, 10)));
					}
				});
				this.put("0123identifier", new ArrayList<Token>()
					{
						{
							this.add(new Token("0123", TokenKind.IntegerLiteral,
								new Location(0, 4)));
							this.add(new Token("identifier", TokenKind.Identifier,
								new Location(4, 10)));
						}
					});
				this.put("identi-fier", new ArrayList<Token>()
					{
						{
							this.add(new Token("identi", TokenKind.Identifier,
								new Location(0, 6)));
							this.add(new Token("-", TokenKind.Unknown,
								new Location(6, 1)));
							this.add(new Token("fier", TokenKind.Identifier,
								new Location(7, 4)));
						}
					});				
			}
		};

		for (Entry<String, List<Token>> entry : hashMap.entrySet())
		{
			this.lexer.setSource(entry.getKey());
			this.lexer.parse();

			List<Token> tokens = this.lexer.getTokens();

			Assert.assertEquals(entry.getValue(), tokens);
		}
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
		
		this.lexer.setKeywords(TokenDefinitionHelper.Keywords);
	}
	
	@Test
	public void testParse()
	{
		Assert.fail("Not yet implemented");
	}
	
	@Test
	public void getKeywords_KeywordsAreNotSet_ReturnsEmptyList()
	{
		this.lexer = new Lexer();
		
		Assert.assertFalse(this.lexer.getKeywords().iterator().hasNext());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setKeywords_KeywordsIsNull_ThrowsIllegalArgumentException()
	{
		this.lexer.setKeywords(null);
	}
	
	@Test
	public void setKeywords_KeywordsAreSuccessfullySet()
	{
		String[] expectedKeywords = new String[] {
			"keyword1", "keyword2", "keyword3",
		};
		
		this.lexer.setKeywords(expectedKeywords);
		
		Iterator<String> keywords = this.lexer.getKeywords().iterator();
		
		for(int i=0, count=expectedKeywords.length; i < count; i++)
		{
			String keyword = keywords.next();
			
			Assert.assertEquals(expectedKeywords[i], keyword);
		}
		
		Assert.assertFalse(keywords.hasNext());
	}
	
	@Test
	public void setKeywords_KeywordsArrayWithDuplicates_RemoveDuplicates()
	{
		String[] expectedKeywords = new String[] {
			"duplicate_keyword", "duplicate_keyword", "duplicate_keyword",
		};
		
		Set<String> hashSet = new HashSet<String>();
		
		for(String keyword: expectedKeywords)
		{
			hashSet.add(keyword);
		}
		
		this.lexer.setKeywords(expectedKeywords);
		
		Iterator<String> keywords = this.lexer.getKeywords().iterator();
		
		Iterator<String> iterator = hashSet.iterator();
		
		while(iterator.hasNext())
		{
			String expectedKeyword = iterator.next();
			
			String keyword = keywords.next();
			
			Assert.assertEquals(expectedKeyword, keyword);
		}
		
		Assert.assertFalse(keywords.hasNext());
	}
}
