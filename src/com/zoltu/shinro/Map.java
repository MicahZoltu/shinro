
package com.zoltu.shinro;

import java.util.HashSet;
import android.widget.Toast;

/** <b>Important for derived classes:</b> You must call MapConstructionComplete at the end of construction. **/
public abstract class Map implements UnknownPiece.Listener
{
	/** One of the 8 directions an arrow (or header) can point. **/
	enum Direction
	{
		NORTH,
		NORTH_EAST,
		EAST,
		SOUTH_EAST,
		SOUTH,
		SOUTH_WEST,
		WEST,
		NORTH_WEST,
	}
	
	/** Implement this interface and call Map.AddListener if you want to be alerted of significant map events. **/
	public interface Listener
	{
		/** Called when the map construction is finished and all pieces are placed on the board in their final location. **/
		public void OnMapConstructionComplete();
	}
	
	/** Access to the game for both Context access and game variables such as selected color. **/
	private Shinro mGame;
	
	/** The number of playable columns on the board (not counting the row header column. **/
	protected int mColumnCount;
	/** The number of playable rows on the board (not counting the column header row. **/
	protected int mRowCount;
	/** The game grid. It is mColumnCount+1 x mRowCount+1, the +1 is to account for the headers. **/
	protected Piece mGrid[][];
	
	/** The default size of a map if specific dimensions are not provided to the constructor. **/
	private static final int DEFAULT_GRID_SIZE = 8;
	
	// Piece collections of various types. Populated in CollectPieces when MapConstructionComplete is called.
	/** Set of all Pieces in this map. **/
	private HashSet<Piece> mPieces = new HashSet<Piece>();
	/** Set of all UnknownPieces in this map. **/
	private HashSet<UnknownPiece> mUnknowns = new HashSet<UnknownPiece>();
	/** Set of all SpacePieces in this map. **/
	private HashSet<SpacePiece> mSpaces = new HashSet<SpacePiece>();
	/** Set of BombPieces in this map. **/
	private HashSet<BombPiece> mBombs = new HashSet<BombPiece>();
	
	/** Set of objects that want to be alerted when significant events occur on the map. **/
	private HashSet<Listener> mListeners = new HashSet<Listener>();
	
	/** Construct an empty square map of DEFAULT_GRID_SIZE. **/
	protected Map(Shinro pGame)
	{
		this(pGame, DEFAULT_GRID_SIZE, DEFAULT_GRID_SIZE);
	}
	
	/** Create an empty map of the specified size. **/
	protected Map(Shinro pGame, int pNumColumns, int pNumRows)
	{
		mGame = pGame;
		mColumnCount = pNumColumns;
		mRowCount = pNumRows;
		
		// Create the grid.
		mGrid = new Piece[mColumnCount + 1][mRowCount + 1];
		
		// Create the top left corner piece.
		new CornerPiece(this, 0, 0);
		
		// Create all of the column header pieces.
		for (int i = 1; i <= mColumnCount; ++i)
		{
			new ColumnHeaderPiece(this, i, 0);
		}
		
		// Create all of the row header pieces.
		for (int i = 1; i <= mRowCount; ++i)
		{
			new RowHeaderPiece(this, 0, i);
		}
		
		// Create all of the board pieces and add them to the appropriate collections.
		for (int i = 1; i <= mColumnCount; ++i)
		{
			for (int j = 1; j <= mRowCount; ++j)
			{
				new SpacePiece(this, i, j);
			}
		}
	}
	
	/** Called by any object that wants to be alerted of significant events. **/
	public void AddListener(Listener pListener)
	{
		mListeners.add(pListener);
	}
	
	/** Shinro getter. **/
	public Shinro GetGame()
	{
		return mGame;
	}
	
	/** Column count getter (needed by PlayActivity for layout). **/
	public int GetColumnCount()
	{
		return mColumnCount;
	}
	
	/** Row count getter (needed by PlayActivity for layout). **/
	public int GetRowCount()
	{
		return mRowCount;
	}
	
	/** Grid getter (needed by PlayActivity for layout). **/
	public Piece[][] GetPieces()
	{
		return mGrid;
	}
	
	/** Clear all highlighting on the map. **/
	public void ClearAllHighlights()
	{
		for (Piece lPiece : mPieces)
		{
			lPiece.ClearHighlight();
		}
	}
	
	/** Reset all unknown pieces on the map to the UNKNOWN state. **/
	public void Reset()
	{
		// Reset the bombs first so we don't get accidentally trigger a game over.
		for (BombPiece lBomb : mBombs)
		{
			lBomb.SetState(UnknownPiece.State.UNKNOWN);
		}
		
		// Reset the rest of the unknowns (spaces).
		for (SpacePiece lSpace : mSpaces)
		{
			lSpace.SetState(UnknownPiece.State.UNKNOWN);
		}
		
		ClearAllHighlights();
	}
	
	/** Clear all highlights of the given color. **/
	public void ClearHighlights(int lColor)
	{
		// Clear highlights of the currently selected color.
		for (Piece lPiece : mPieces)
		{
			lPiece.RemoveHighlight(lColor);
		}
	}
	
	/** An unknown piece has changed states, check if game over conditions are met. **/
	public void OnStateChanged(UnknownPiece.State pOldState, UnknownPiece.State pNewState)
	{
		if (AllBombsMarked() && AllSpacesNotSuspectedBombs())
		{
			GameOver();
		}
	}
	
	/** Must be called by derived classes when map construction is complete. **/
	protected void MapConstructionComplete()
	{
		CollectPieces();
		ListenToUnknowns();
		
		// Tell all listeners that construction is complete.
		for (Listener lListener : mListeners)
		{
			lListener.OnMapConstructionComplete();
		}
	}
	
	/** Check to see if all the bombs in the map are marked as SUSPECTED_BOMB. **/
	private boolean AllBombsMarked()
	{
		for (BombPiece lBomb : mBombs)
		{
			if (lBomb.GetState() != UnknownPiece.State.SUSPECTED_BOMB) return false;
		}
		
		return true;
	}
	
	/** Check to see if all spaces in the map are not marked as SUSPECTED_BOMB. **/
	private boolean AllSpacesNotSuspectedBombs()
	{
		for (SpacePiece lSpace : mSpaces)
		{
			if (lSpace.GetState() == UnknownPiece.State.SUSPECTED_BOMB) return false;
		}
		
		return true;
	}
	
	/** Called when it has been determined that the map is solved. Comence fireworks. **/
	private void GameOver()
	{
		Toast.makeText(mGame, R.string.game_over, Toast.LENGTH_LONG).show();
	}
	
	/** Add all the pieces on the board to the appropriate collections. **/
	private void CollectPieces()
	{
		for (int x = 0; x <= mColumnCount; ++x)
		{
			for (int y = 0; y <= mRowCount; ++y)
			{
				Piece lPiece = mGrid[x][y];
				mPieces.add(lPiece);
				
				if (lPiece instanceof UnknownPiece)
				{
					mUnknowns.add((UnknownPiece) lPiece);
					if (lPiece instanceof SpacePiece) mSpaces.add((SpacePiece) lPiece);
					if (lPiece instanceof BombPiece) mBombs.add((BombPiece) lPiece);
				}
			}
		}
	}
	
	/** Add this as a listener to all of our unknown pieces. **/
	private void ListenToUnknowns()
	{
		for (UnknownPiece lPiece : mUnknowns)
		{
			lPiece.AddListener(this);
		}
	}
}
