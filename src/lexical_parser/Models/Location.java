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
}
