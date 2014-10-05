package lexical_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import lexical_parser.Enums.LexemeKind;
import lexical_parser.Helpers.LexemeDefinitionHelper;
import lexical_parser.LexemeDefinitions.DynamicLexemeDefinition;
import lexical_parser.LexemeDefinitions.StaticLexemeDefinition;
import lexical_parser.Models.Lexeme;
import lexical_parser.Models.Location;

public class Lexer implements ILexer
{
	private List<Lexeme> lexemes;

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

	public Lexer()
	{
		this.lexemes = new ArrayList<Lexeme>();
		this.offset = 0;
		this.source = "";
	}

	@Override
	public List<Lexeme> getLexemes()
	{
		return this.lexemes;
	}

	private boolean inBounds()
	{
		return this.offset < this.source.length();
	}

	@Override
	public void parse()
	{
		this.lexemes.clear();
		
		while (this.inBounds())
		{
			this.skipSpaces();
			
			if (!this.inBounds())
			{
				break;
			}
			
			Lexeme lexeme = this.processStatic();

			if (lexeme == null)
			{
				lexeme = this.processDynamic();
			}

			if (lexeme == null)
			{
				lexeme = new Lexeme(this.source.substring(this.offset, this.offset+1), LexemeKind.Unknown, new Location(this.offset, 1));
				
				this.offset++;
			}
			
			this.lexemes.add(lexeme);
		}
	}

	private Lexeme processDynamic()
	{
		for (DynamicLexemeDefinition definition : LexemeDefinitionHelper.DynamicLexemeDefinitionsList)
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
			
			String lexemeValue = matchString.substring(0, location.getLength());
//			String lexemeValue = matcher.group(0);

			Lexeme lexeme = new Lexeme(lexemeValue, definition.getKind(),
				location);

			this.offset += matcher.end();

			return lexeme;
		}
		
		return null;
	}

	private Lexeme processStatic()
	{
		for (StaticLexemeDefinition definition : LexemeDefinitionHelper.StaticLexemeDefinitionsList)
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
//				&& definition.getKind().equals(LexemeKind.Keyword))
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

			String lexemeValue = this.source.substring(location.getOffset(),
				location.getOffset() + location.getLength());

			Lexeme lexeme = new Lexeme(lexemeValue, definition.getKind(),
				location);

			this.offset += length;

			return lexeme;
		}
		
		return null;
	}

	@Override
	public void setSource(String source)
	{
		this.source = source;
		
		this.lexemes.clear();
		
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