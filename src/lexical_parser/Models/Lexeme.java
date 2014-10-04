package lexical_parser.Models;

import lexical_parser.Enums.LexemeKind;

public class Lexeme
{
	private LexemeKind kind;
	private Location location;
	private String value;

	public Lexeme(String value, LexemeKind kind, Location location)
	{
		this.value = value;
		this.kind = kind;
		this.location = location;
	}
	
	/**
	 * Gets kind
	 * 
	 * @return the kind
	 */
	public LexemeKind getKind()
	{
		return this.kind;
	}

	/**
	 * Gets location
	 * 
	 * @return the location
	 */
	public Location getLocation()
	{
		return this.location;
	}

	/**
	 * Gets value
	 * 
	 * @return the value
	 */
	public String getValue()
	{
		return this.value;
	}
}
