
package com.zoltu.shinro;

import java.security.InvalidParameterException;
import android.view.View;

public class CornerPiece extends Piece
{
	public CornerPiece(Map pMap, int pColumn, int pRow)
	{
		super(pMap, 0, 0);
		
		// Validate it is in the corner.
		if (pRow != 0 || pColumn != 0) throw new InvalidParameterException("Attempted to create a corner piece with a location other than 0,0.  Location: " + mLocation.x + "," + mLocation.y);
		
		SetDrawable(R.drawable.blank);
		setFocusable(false);
	}
	
	public void onClick(View pView)
	{
		// Corner piece has no functionality when clicked.
	}
}
