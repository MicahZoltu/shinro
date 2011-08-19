
package com.zoltu.shinro;

import android.graphics.Color;

public class ArrowPiece extends PointingPiece implements Map.Listener
{
	protected Map.Direction mDirection;
	
	public ArrowPiece(Map pMap, int pColumn, int pRow, Map.Direction pDirection)
	{
		super(pMap, pColumn, pRow);
		
		mDirection = pDirection;
		
		switch (pDirection)
		{
			case NORTH:
			{
				SetDrawable(R.drawable.arrow_nn);
				break;
			}
			case NORTH_EAST:
			{
				SetDrawable(R.drawable.arrow_ne);
				break;
			}
			case EAST:
			{
				SetDrawable(R.drawable.arrow_ee);
				break;
			}
			case SOUTH_EAST:
			{
				SetDrawable(R.drawable.arrow_se);
				break;
			}
			case SOUTH:
			{
				SetDrawable(R.drawable.arrow_ss);
				break;
			}
			case SOUTH_WEST:
			{
				SetDrawable(R.drawable.arrow_sw);
				break;
			}
			case WEST:
			{
				SetDrawable(R.drawable.arrow_ww);
				break;
			}
			case NORTH_WEST:
			{
				SetDrawable(R.drawable.arrow_nw);
				break;
			}
		}
	}
	
	@Override protected Map.Direction GetDirection()
	{
		return mDirection;
	}
	
	@Override protected void OnUpdate()
	{
		if (mSuspectedBombCount > 0) AddHighlight(Color.WHITE);
		else RemoveHighlight(Color.WHITE);
		
		if (mSuspectedSpaceCount >= mUnknownPieces.size()) AddHighlight(Color.RED);
		else RemoveHighlight(Color.RED);
	}
}
