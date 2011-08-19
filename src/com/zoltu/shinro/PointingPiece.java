
package com.zoltu.shinro;

import java.util.HashSet;
import android.view.View;

/**
 * Pieces deriving from this class point at UnknownPieces and need to highlight them, change state based on their
 * states, etc.
 **/
public abstract class PointingPiece extends Piece implements UnknownPiece.Listener, Map.Listener
{
	/** A collection of all the unknown pieces in this row/column/arrow_path. **/
	protected HashSet<UnknownPiece> mUnknownPieces = new HashSet<UnknownPiece>();
	
	/** Current count of bombs pointed at. **/
	protected int mBombCount = 0;
	/** Current count of suspected bombs pointed at. **/
	protected int mSuspectedBombCount = 0;
	/** Current count of suspected spaces pointed at. **/
	protected int mSuspectedSpaceCount = 0;
	
	/**
	 * Should return the direction this piece points (columns point south, rows point east, arrows point in one of 8
	 * directions, etc.
	 * 
	 * @return The direction this piece is pointing.
	 **/
	protected abstract Map.Direction GetDirection();
	
	/**
	 * Called when the derived class should update it's drawable/highlighting due to a change in the state of the pieces
	 * being pointed at.
	 **/
	protected abstract void OnUpdate();
	
	/** Constructor. **/
	public PointingPiece(Map pMap, int pColumn, int pRow)
	{
		super(pMap, pColumn, pRow);
		
		mMap.AddListener(this);
	}
	
	public void onClick(View pView)
	{
		HighlightCollection();
		AddHighlight(mMap.GetGame().GetSelectedColor());
	}
	
	public void OnStateChanged(UnknownPiece.State pOldState, UnknownPiece.State pNewState)
	{
		CountSuspected();
		OnUpdate();
	}
	
	public void OnMapConstructionComplete()
	{
		AddUnknownsToCollection();
		ListenToCollection();
		CountBombs();
	}
	
	/** Add all UnknownPieces to our collection starting from here and going in GetDirection(). **/
	public void AddUnknownsToCollection()
	{
		switch (GetDirection())
		{
			case NORTH:
			{
				for (int i = mLocation.y; i >= 0; --i)
				{
					AddToCollectionIfUnknown(mMap.mGrid[mLocation.x][i]);
				}
				break;
			}
			case NORTH_EAST:
			{
				int i = 0;
				while ((i <= (mMap.mColumnCount - mLocation.x)) && (i <= mLocation.y))
				{
					AddToCollectionIfUnknown(mMap.mGrid[mLocation.x + i][mLocation.y - i]);
					++i;
				}
				break;
			}
			case EAST:
			{
				for (int i = mLocation.x; i <= mMap.mColumnCount; ++i)
				{
					AddToCollectionIfUnknown(mMap.mGrid[i][mLocation.y]);
				}
				break;
			}
			case SOUTH_EAST:
			{
				int i = 0;
				while ((i <= (mMap.mColumnCount - mLocation.x)) && (i <= (mMap.mRowCount - mLocation.y)))
				{
					AddToCollectionIfUnknown(mMap.mGrid[mLocation.x + i][mLocation.y + i]);
					++i;
				}
				break;
			}
			case SOUTH:
			{
				for (int i = mLocation.y; i <= mMap.mRowCount; ++i)
				{
					AddToCollectionIfUnknown(mMap.mGrid[mLocation.x][i]);
				}
				break;
			}
			case SOUTH_WEST:
			{
				int i = 0;
				while ((i <= mLocation.x) && (i <= (mMap.mRowCount - mLocation.y)))
				{
					AddToCollectionIfUnknown(mMap.mGrid[mLocation.x - i][mLocation.y + i]);
					++i;
				}
				break;
			}
			case WEST:
			{
				for (int i = mLocation.x; i >= 0; --i)
				{
					AddToCollectionIfUnknown(mMap.mGrid[i][mLocation.y]);
				}
				break;
			}
			case NORTH_WEST:
			{
				int i = 0;
				while ((i <= mLocation.x) && (i <= mLocation.y))
				{
					AddToCollectionIfUnknown(mMap.mGrid[mLocation.x - i][mLocation.y - i]);
					++i;
				}
				break;
			}
		}
	}
	
	@Override public void ClearHighlight()
	{
		super.ClearHighlight();
		
		OnUpdate();
	}
	
	/** Add the given piece to our collection if it is of type UnknownPiece. **/
	private void AddToCollectionIfUnknown(Piece pPiece)
	{
		if (pPiece instanceof UnknownPiece)
		{
			UnknownPiece lPiece = (UnknownPiece) pPiece;
			mUnknownPieces.add(lPiece);
		}
	}
	
	/** Listen to all UnknownPieces in our collection. **/
	private void ListenToCollection()
	{
		for (UnknownPiece lPiece : mUnknownPieces)
		{
			lPiece.AddListener(this);
		}
	}
	
	/** Highlight all pieces in our collection that are in the UNKNOWN state the current selected color. **/
	private void HighlightCollection()
	{
		int lSelectedColor = mMap.GetGame().GetSelectedColor();
		
		// Clear all highlighting of this color.
		mMap.ClearHighlights(lSelectedColor);
		
		for (UnknownPiece lPiece : mUnknownPieces)
		{
			if (lPiece.GetState() == UnknownPiece.State.UNKNOWN)
			{
				lPiece.AddHighlight(lSelectedColor);
			}
		}
	}
	
	private void CountBombs()
	{
		mBombCount = 0;
		
		for (UnknownPiece lPiece : mUnknownPieces)
		{
			if (lPiece instanceof BombPiece) ++mBombCount;
		}
	}
	
	private void CountSuspected()
	{
		mSuspectedBombCount = 0;
		mSuspectedSpaceCount = 0;
		
		for (UnknownPiece lPiece : mUnknownPieces)
		{
			if (lPiece.GetState() == UnknownPiece.State.SUSPECTED_BOMB) ++mSuspectedBombCount;
			if (lPiece.GetState() == UnknownPiece.State.SUSPECTED_SPACE) ++mSuspectedSpaceCount;
		}
	}
}
