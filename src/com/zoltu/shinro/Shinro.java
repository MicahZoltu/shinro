
package com.zoltu.shinro;

import android.app.Application;
import android.graphics.Color;

public class Shinro extends Application
{
	private Map mMap;
	private int mSelectedColor = Color.CYAN;
	
	public void SetMap(Map pMap)
	{
		mMap = pMap;
	}
	
	public Map GetMap()
	{
		return mMap;
	}
	
	public void SetSelectedColor(int pColor)
	{
		mSelectedColor = pColor;
	}
	
	public int GetSelectedColor()
	{
		return mSelectedColor;
	}
}
