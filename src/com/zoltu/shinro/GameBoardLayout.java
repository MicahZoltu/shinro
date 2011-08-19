
package com.zoltu.shinro;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class GameBoardLayout extends ViewGroup
{
	View[][] mGrid;
	int mColumnCount = 0;
	int mRowCount = 0;
	int mMargin = 0;
	
	public GameBoardLayout(Context pContext)
	{
		super(pContext);
	}
	
	public GameBoardLayout(Context pContext, AttributeSet pAttributes)
	{
		super(pContext, pAttributes);
		
		ParseAttributes(pAttributes);
	}
	
	public GameBoardLayout(Context pContext, AttributeSet pAttributes, int pStyle)
	{
		super(pContext, pAttributes, pStyle);
		
		ParseAttributes(pAttributes);
	}
	
	private void ParseAttributes(AttributeSet pAttributes)
	{
		TypedArray lStyledAttributes = getContext().obtainStyledAttributes(pAttributes, R.styleable.GameBoardLayout);
		mMargin = lStyledAttributes.getInt(R.styleable.GameBoardLayout_children_margin, 0);
	}
	
	public void SetGrid(View[][] pGrid, int pColumnCount, int pRowCount)
	{
		mGrid = pGrid;
		mColumnCount = pColumnCount;
		mRowCount = pRowCount;
		
		// Add the Views supplied by this grid to this ViewGroup.
		for (int x = 0; x < mColumnCount; ++x)
		{
			for (int y = 0; y < mRowCount; ++y)
			{
				addView(mGrid[x][y]);
			}
		}
		
		requestLayout();
	}
	
	@Override protected void onLayout(boolean pChanged, int pLeft, int pTop, int pRight, int pBottom)
	{
		if (mGrid == null) return;
		
		int lWidth = pRight - pLeft;
		int lHeight = pBottom - pTop;
		int lCellSize = Math.min(lWidth / mColumnCount, lHeight / mRowCount);
		
		for (int x = 0; x < mColumnCount; ++x)
		{
			for (int y = 0; y < mRowCount; ++y)
			{
				int lLeft = x * lCellSize + (mMargin / 2);
				int lTop = y * lCellSize + (mMargin / 2);
				int lRight = lLeft + lCellSize - (mMargin / 2);
				int lBottom = lTop + lCellSize - (mMargin / 2);
				
				mGrid[x][y].layout(lLeft, lTop, lRight, lBottom);
			}
		}
	}
	
	@Override protected void onMeasure(int pWidthMeasureSpec, int pHeightMeasureSpec)
	{
		int lWidth = View.MeasureSpec.getSize(pWidthMeasureSpec);
		int lHeight = View.MeasureSpec.getSize(pHeightMeasureSpec);
		
		if (mGrid == null)
		{
			setMeasuredDimension(lWidth, lHeight);
			return;
		}
		
		int lMaxCellWidth = lWidth / mColumnCount;
		int lMaxCellHeight = lHeight / mRowCount;
		int lCellSize = Math.min(lMaxCellWidth, lMaxCellHeight);
		
		setMeasuredDimension(lCellSize * mColumnCount, lCellSize * mRowCount);
	}
}
