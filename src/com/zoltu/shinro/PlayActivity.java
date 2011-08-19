
package com.zoltu.shinro;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class PlayActivity extends Activity
{
	public static final String INTENT_EXTRA_MAP = "Map";
	
	private GameBoardLayout mGameBoard;
	private Shinro mGame;
	private Map mMap;
	
	/** Called when the activity is first created. */
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mGame = (Shinro)getApplication();
		mMap = mGame.GetMap();
		BuildLayout();
		
		// Hook up the highlight radio buttons to their listeners.
		RadioButton lCyanRadio = (RadioButton)findViewById(R.id.cyan_highlight_radio);
		lCyanRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked)
			{
				OnCyanCheckedChanged(pButtonView, pIsChecked);
			}
		});
		RadioButton lMagentaRadio = (RadioButton)findViewById(R.id.magenta_highlight_radio);
		lMagentaRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked)
			{
				OnMagentaCheckedChanged(pButtonView, pIsChecked);
			}
		});
		RadioButton lYellowRadio = (RadioButton)findViewById(R.id.yellow_highlight_radio);
		lYellowRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked)
			{
				OnYellowCheckedChanged(pButtonView, pIsChecked);
			}
		});
		
		// Default cyan to selected.
		lCyanRadio.setChecked(true);
		
		// Hook up the buttons.
		Button lResetButton = (Button)findViewById(R.id.reset_board_button);
		lResetButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View pView)
			{
				OnResetButtonClicked(pView);
			}
		});
		Button lClearHighlightButton = (Button)findViewById(R.id.clear_highlight_button);
		lClearHighlightButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View pView)
			{
				OnClearHighlightButtonClicked(pView);
			}
		});
	}
	
	private void OnCyanCheckedChanged(CompoundButton pButtonView, boolean pIsChecked)
	{
		if (pIsChecked) mGame.SetSelectedColor(Color.CYAN);
	}
	
	private void OnMagentaCheckedChanged(CompoundButton pButtonView, boolean pIsChecked)
	{
		if (pIsChecked) mGame.SetSelectedColor(Color.MAGENTA);
	}
	
	private void OnYellowCheckedChanged(CompoundButton pButtonView, boolean pIsChecked)
	{
		if (pIsChecked) mGame.SetSelectedColor(Color.YELLOW);
	}
	
	private void OnResetButtonClicked(View pView)
	{
		mMap.Reset();
	}
	
	private void OnClearHighlightButtonClicked(View pView)
	{
		mMap.ClearAllHighlights();
	}
	
	/** Build the layout for this activity. **/
	private void BuildLayout()
	{
		setContentView(R.layout.play);
		
		// Get a handle to the game board.
		mGameBoard = (GameBoardLayout)findViewById(R.id.game_board);
		
		// Give the grid of pieces to the game board for layout.
		mGameBoard.SetGrid(mMap.GetPieces(), mMap.mColumnCount + 1, mMap.mRowCount + 1);
	}
}
