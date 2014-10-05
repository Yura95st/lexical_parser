package lexical_parser.Models;

public class Location
{
	private int length;
	
	private int offset;
	
	public Location(int offset, int length)
	{
		if (offset < 0)
		{
			throw new IllegalArgumentException(
				"Offset value must be not less, than zero.");
		}
		
		if (length < 0)
		{
			throw new IllegalArgumentException(
				"Length value must be not less, than zero.");
		}
		
		this.offset = offset;
		this.length = length;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		Location other = (Location) obj;
		if (this.length != other.length)
		{
			return false;
		}
		if (this.offset != other.offset)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Gets length
	 *
	 * @return the length
	 */
	public int getLength()
	{
		return this.length;
	}
	
	/**
	 * Gets offset
	 *
	 * @return the offset
	 */
	public int getOffset()
	{
		return this.offset;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		
		int result = 1;
		
		result = prime * result + this.length;
		
		result = prime * result + this.offset;
		
		return result;
	}
}
