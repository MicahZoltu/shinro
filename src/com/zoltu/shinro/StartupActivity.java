
package com.zoltu.shinro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartupActivity extends Activity
{
	private static enum ActivityResult
	{
		MAP_CHOOSER,
		PLAY;
		
		public static ActivityResult FromOrdinal(int pOrdinal)
		{
			return values()[pOrdinal];
		}
	}
	
	/** Called when the activity is first created. */
	@Override public void onCreate(Bundle pSavedInstanceState)
	{
		super.onCreate(pSavedInstanceState);
		setContentView(R.layout.main);
		Button lButton = (Button)findViewById(R.id.tutorial_game_button);
		lButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View pView)
			{
				OnClickTutorialButton(pView);
			}
		});
	}
	
	private void OnClickTutorialButton(View pView)
	{
		Intent lMapChooserIntent = new Intent(this, MapChooserActivity.class);
		startActivityForResult(lMapChooserIntent, ActivityResult.MAP_CHOOSER.ordinal());
	}
	
	@Override protected void onPause()
	{
		super.onPause();
		// TODO: Save the state.
	}
	
	@Override protected void onActivityResult(int pRequestCode, int pResultCode, Intent pData)
	{
		super.onActivityResult(pRequestCode, pResultCode, pData);
		
		switch (ActivityResult.FromOrdinal(pRequestCode))
		{
			case MAP_CHOOSER:
			{
				onMapChooserResult(pResultCode, pData);
				break;
			}
			case PLAY:
			{
				onPlayResult(pResultCode, pData);
				break;
			}
		}
	}
	
	/**
	 * Called when the {@link MapChooserActivity} is finished.
	 * 
	 * @param pResultCode
	 *            The integer result code returned by the child activity through its setResult().
	 * @param pData
	 *            An Intent, which can return result data to the caller (various data can be attached to Intent
	 *            "extras").
	 * **/
	private void onMapChooserResult(int pResultCode, Intent pData)
	{
		// Once the map is selected start the game.
		Intent lPlayIntent = new Intent(this, PlayActivity.class);
		startActivityForResult(lPlayIntent, ActivityResult.PLAY.ordinal());
	}
	
	/**
	 * Called when the {@link PlayActivity} is finished.
	 * 
	 * @param pResultCode
	 *            The integer result code returned by the child activity through its setResult().
	 * @param pData
	 *            An Intent, which can return result data to the caller (various data can be attached to Intent
	 *            "extras").
	 * **/
	private void onPlayResult(int pResultCode, Intent pData)
	{
		// Place holder.
	}
}
