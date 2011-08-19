
package com.zoltu.shinro;

import java.util.HashSet;
import android.view.View;

public abstract class UnknownPiece extends Piece
{
	public enum State
	{
		UNKNOWN,
		SUSPECTED_SPACE,
		SUSPECTED_BOMB,
	}
	
	public interface Listener
	{
		/** Called on all listeners when an UnknownPiece changes state. **/
		public void OnStateChanged(State pOldState, State pNewState);
	}
	
	private State mState = State.UNKNOWN;
	private HashSet<Listener> mListeners = new HashSet<Listener>();
	
	public UnknownPiece(Map pMap, int pColumn, int pRow)
	{
		super(pMap, pColumn, pRow);
		
		// Enter the initial state of unknown.
		EnterStateUnknown();
	}
	
	public State GetState()
	{
		return mState;
	}
	
	public void SetState(State pNewState)
	{
		State lOldState = mState;
		
		switch (mState)
		{
			case UNKNOWN:
			{
				LeaveStateUnknown();
				break;
			}
			case SUSPECTED_SPACE:
			{
				LeaveStateSuspectedSpace();
				break;
			}
			case SUSPECTED_BOMB:
			{
				LeaveStateSuspectedBomb();
				break;
			}
		}
		
		switch (pNewState)
		{
			case UNKNOWN:
			{
				EnterStateUnknown();
				break;
			}
			case SUSPECTED_SPACE:
			{
				EnterStateSuspectedSpace();
				break;
			}
			case SUSPECTED_BOMB:
			{
				EnterStateSuspectedBomb();
				break;
			}
		}
		
		for (Listener lListener : mListeners)
		{
			lListener.OnStateChanged(lOldState, pNewState);
		}
	}
	
	public void AddListener(Listener lListener)
	{
		mListeners.add(lListener);
	}
	
	private void EnterStateUnknown()
	{
		mState = State.UNKNOWN;
		SetDrawable(R.drawable.question_mark);
	}
	
	private void EnterStateSuspectedSpace()
	{
		mState = State.SUSPECTED_SPACE;
		SetDrawable(R.drawable.safe);
	}
	
	private void EnterStateSuspectedBomb()
	{
		mState = State.SUSPECTED_BOMB;
		SetDrawable(R.drawable.bomb);
	}
	
	private void LeaveStateUnknown()
	{
		// When leaving the unknown state un-highlight the piece.
		ClearHighlight();
	}
	
	private void LeaveStateSuspectedSpace()
	{
		
	}
	
	private void LeaveStateSuspectedBomb()
	{
		
	}
	
	public void onClick(View pView)
	{
		// Rotate to the next state.
		switch (mState)
		{
			case UNKNOWN:
			{
				SetState(State.SUSPECTED_SPACE);
				break;
			}
			case SUSPECTED_SPACE:
			{
				SetState(State.SUSPECTED_BOMB);
				break;
			}
			case SUSPECTED_BOMB:
			{
				SetState(State.UNKNOWN);
				break;
			}
		}
	}
}
