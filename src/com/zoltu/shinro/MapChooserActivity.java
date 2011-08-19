
package com.zoltu.shinro;

import android.app.Activity;
import android.os.Bundle;

public class MapChooserActivity extends Activity
{
	private Shinro mShinro;
	
	/** Called when the activity is first created. */
	@Override public void onCreate(Bundle savedInstanceState)
	{
		// Setup the UI.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_chooser);
		
		// Get access to the main game class.
		mShinro = (Shinro)getApplication();
		
		// Set the current map for the game.
		mShinro.SetMap(new TestMap(mShinro));
		
		// Set the result of this activity as a success and return the original intent.
		setResult(RESULT_OK, getIntent());
		// Exit the activity.
		finish();
	}
}
