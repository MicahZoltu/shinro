
package com.zoltu.shinro;

import java.util.HashSet;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.view.View;

public abstract class Piece extends ImageButton implements View.OnClickListener
{
	protected Map mMap;
	protected Point mLocation;
	protected HashSet<Integer> mHighlights = new HashSet<Integer>();
	protected int mHighlight;
	private int mResourceId;
	
	public Piece(Map pMap, int pColumn, int pRow)
	{
		super(pMap.GetGame());
		mMap = pMap;
		mLocation = new Point(pColumn, pRow);
		mMap.mGrid[mLocation.x][mLocation.y] = this; 
		setScaleType(ScaleType.FIT_CENTER);
		setPadding(0, 0, 0, 0);
		setBackgroundResource(0);
		setAdjustViewBounds(true);
		setOnClickListener(this);
	}
	
	protected void SetDrawable(int pResourceId)
	{
		mResourceId = pResourceId;
		
		// Get a mutated drawable from the resource.
		Drawable lDrawable = mMap.GetGame().getResources().getDrawable(mResourceId).mutate();
		// Set the drawable for this piece.
		setImageDrawable(lDrawable);
	}
	
	public void AddHighlight(int pColor)
	{
		mHighlights.add(pColor);
		SetHighlight();
	}
	
	public void RemoveHighlight(int pColor)
	{
		mHighlights.remove(pColor);
		SetHighlight();
	}
	
	public void ClearHighlight()
	{
		mHighlights.clear();
		SetHighlight();
	}
	
	protected void SetHighlight()
	{
		// If there are no highlights then have a transparent highlight.
		if (mHighlights.size() == 0)
		{
			mHighlight = Color.TRANSPARENT;
			setColorFilter(mHighlight, Mode.SRC_ATOP);
			return;
		}
		
		int lRedTotal = 0;
		int lGreenTotal = 0;
		int lBlueTotal = 0;
		
		// Total the individual colors.
		for (Integer lColor : mHighlights)
		{
			lRedTotal += Color.red(lColor);
			lGreenTotal += Color.green(lColor);
			lBlueTotal += Color.blue(lColor);
		}
		
		// Create a new highlight color from the average of all the highlights.
		mHighlight = Color.argb(127, lRedTotal / mHighlights.size(), lGreenTotal / mHighlights.size(), lBlueTotal / mHighlights.size());
		
		// Highlight the piece.
		setColorFilter(mHighlight, Mode.SRC_ATOP);
	}
}
