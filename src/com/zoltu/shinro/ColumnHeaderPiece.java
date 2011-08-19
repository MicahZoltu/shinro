
package com.zoltu.shinro;

import java.security.InvalidParameterException;

public class ColumnHeaderPiece extends HeaderPiece
{
	public ColumnHeaderPiece(Map pMap, int pColumn, int pRow)
	{
		super(pMap, pColumn, pRow);
		
		// Validate it's located in the header row.
		if (pRow != 0) throw new InvalidParameterException("Attempted to create a column header piece with a location other than ?,0.  Location: " + mLocation.x + "," + mLocation.y);
	}

	@Override protected Map.Direction GetDirection()
	{
		return Map.Direction.SOUTH;
	}
}
