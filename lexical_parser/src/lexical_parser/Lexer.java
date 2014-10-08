package lexical_parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import lexical_parser.Enums.TokenKind;
import lexical_parser.Helpers.Guard;
import lexical_parser.Helpers.LexerHelper;
import lexical_parser.Models.Location;
import lexical_parser.Models.Token;
import lexical_parser.Models.TokenDefinition;

public class Lexer implements ILexer
{
	private Set<String> keywords;
	
	private int offset;
	
	private String source;

	private List<Character> spaceChars = new ArrayList<Character>()
	{
		{
			this.add(' ');
			this.add('\n');
			this.add('\r');
			this.add('\t');
		}
	};
	
	private List<Token> tokens;
	
	public Lexer()
	{
		this.tokens = new ArrayList<Token>();

		this.offset = 0;
		this.source = "";

		this.keywords = new HashSet<String>();
	}
	
	@Override
	public Iterable<String> getKeywords()
	{
		return this.keywords;
	}
	
	@Override
	public String getSource()
	{
		return this.source;
	}
	
	@Override
	public List<Token> getTokens()
	{
		return this.tokens;
	}
	
	private boolean isInBounds()
	{
		return this.offset < this.source.length();
	}
	
	@Override
	public void parse()
	{
		this.tokens.clear();

		while (this.isInBounds())
		{
			this.skipSpaces();

			if (!this.isInBounds())
			{
				break;
			}

			Token token = this.processToken();
			
			if (token == null)
			{
				String tokenValue = this.source.substring(this.offset,
					this.offset + 1);
				
				token = new Token(tokenValue, TokenKind.Unknown, new Location(
					this.offset, tokenValue.length()));

				this.offset += tokenValue.length();
			}

			this.tokens.add(token);
		}
	}

	private Token processToken()
	{
		for (TokenDefinition definition : LexerHelper.TokenDefinitionsList)
		{
			String matchString = this.source.substring(this.offset,
				this.source.length());
			
			Matcher matcher = definition.getRepresentation().matcher(
				matchString);
			
			if (!matcher.lookingAt())
			{
				continue;
			}
			
			Location location = new Location(this.offset, matcher.end());

			TokenKind tokenKind = definition.getKind();

			String tokenValue = matchString.substring(0, location.getLength());
			// String tokenValue = matcher.group(0);

			// Identifier can have such form: @keyword
			if (tokenKind == TokenKind.Identifier && tokenValue.startsWith("@"))
			{
				String keyword = tokenValue.substring(1, tokenValue.length());

				if (!this.keywords.contains(keyword))
				{
					continue;
				}
			}

			if (this.keywords.contains(tokenValue))
			{
				tokenKind = TokenKind.Keyword;
			}
			
			Token token = new Token(tokenValue, tokenKind, location);
			
			this.offset += matcher.end();
			
			return token;
		}

		return null;
	}
	
	@Override
	public void setKeywords(String[] keywords)
	{
		Guard.isNotNull(keywords, "keywords");

		this.keywords.clear();

		for (String keyword : keywords)
		{
			this.keywords.add(keyword);
		}
	}
	
	@Override
	public void setSource(String source)
	{
		Guard.isNotNull(source, "source");

		this.source = source;

		this.tokens.clear();
		this.offset = 0;
	}

	private void skipSpaces()
	{
		while (this.isInBounds()
				&& this.spaceChars.contains(this.source.charAt(this.offset)))
		{
			this.offset++;
		}
	}
}