package lexical_parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import lexical_parser.Enums.TokenKind;
import lexical_parser.Helpers.Guard;
import lexical_parser.Helpers.TokenDefinitionHelper;
import lexical_parser.Models.Location;
import lexical_parser.Models.Token;
import lexical_parser.TokenDefinitions.DynamicTokenDefinition;
import lexical_parser.TokenDefinitions.StaticTokenDefinition;

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
	public List<Token> getTokens()
	{
		return this.tokens;
	}

	private boolean inBounds()
	{
		return this.offset < this.source.length();
	}

	@Override
	public void parse()
	{
		this.tokens.clear();
		
		while (this.inBounds())
		{
			this.skipSpaces();
			
			if (!this.inBounds())
			{
				break;
			}
			
			Token token = this.processStatic();

			if (token == null)
			{
				token = this.processDynamic();
			}

			if (token == null)
			{
				token = new Token(this.source.substring(this.offset,
					this.offset + 1), TokenKind.Unknown, new Location(
						this.offset, 1));
				
				this.offset++;
			}
			
			this.tokens.add(token);
		}
	}

	private Token processDynamic()
	{
		for (DynamicTokenDefinition definition : TokenDefinitionHelper.DynamicTokenDefinitionsList)
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
			//			String tokenValue = matcher.group(0);
			
			// Identifier can have such form: @keyword
			if (tokenValue.startsWith("@"))
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

	private Token processStatic()
	{
		for (StaticTokenDefinition definition : TokenDefinitionHelper.StaticTokenDefinitionsList)
		{
			String representation = definition.getRepresentation();
			
			int length = representation.length();
			
			if (this.offset + length > this.source.length()
				|| !this.source.substring(this.offset, this.offset + length)
						.equals(representation))
			{
				continue;
			}

			//			if (this.offset + length < this.source.length()
			//				&& definition.getKind().equals(TokenKind.Keyword))
			//			{
			//				char nextChar = this.source.charAt(this.offset + length);
			//
			//				if (nextChar == '_' || Character.isDigit(nextChar)
			//					|| Character.isLetter(nextChar))
			//				{
			//					continue;
			//				}
			//			}

			Location location = new Location(this.offset, length);

			String tokenValue = this.source.substring(location.getOffset(),
				location.getOffset() + location.getLength());

			Token token = new Token(tokenValue, definition.getKind(), location);

			this.offset += length;

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
		while (this.inBounds()
			&& this.spaceChars.contains(this.source.charAt(this.offset)))
		{
			this.offset++;
		}
	}
}