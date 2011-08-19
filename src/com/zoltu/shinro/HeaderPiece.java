
package com.zoltu.shinro;

import android.graphics.Color;

public abstract class HeaderPiece extends PointingPiece
{
	public HeaderPiece(Map pMap, int pColumn, int pRow)
	{
		super(pMap, pColumn, pRow);
	}
	
	@Override public void OnMapConstructionComplete()
	{
		super.OnMapConstructionComplete();
		
		UpdateDrawable();
		UpdateHighlight();
	}
	
	@Override protected void OnUpdate()
	{
		UpdateHighlight();
	}
	
	private boolean HasTooManySuspectedBombs()
	{
		if (mSuspectedBombCount > mBombCount) return true;
		else return false;
	}
	
	private boolean HasTooManySuspectedNonBombs()
	{
		if (mSuspectedSpaceCount > (mUnknownPieces.size() - mBombCount)) return true;
		else return false;
	}
	
	private void UpdateDrawable()
	{
		// Get the resource identifier for the correct number.
		int lResourceId = mMap.GetGame().getResources().getIdentifier("number_" + Integer.toString(mBombCount), "drawable", mMap.GetGame().getPackageName());
		SetDrawable(lResourceId);
	}
	
	private void UpdateHighlight()
	{
		if (HasTooManySuspectedBombs() || HasTooManySuspectedNonBombs()) AddHighlight(Color.RED);
		else RemoveHighlight(Color.RED);
		
		if (mBombCount == mSuspectedBombCount) AddHighlight(Color.WHITE);
		else RemoveHighlight(Color.WHITE);
	}
}
