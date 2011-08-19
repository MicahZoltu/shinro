
package com.zoltu.shinro;

public class TestMap extends Map
{
	public TestMap(Shinro pGame)
	{
		// Test map is just 2x2 with one bomb.
		super(pGame, 8, 8);
		
		// Create a default map.
		new ArrowPiece(this, 1, 3, Direction.EAST);
		new ArrowPiece(this, 1, 4, Direction.NORTH_EAST);
		new ArrowPiece(this, 1, 5, Direction.NORTH_EAST);
		new BombPiece(this, 2, 2);
		new BombPiece(this, 2, 3);
		new BombPiece(this, 2, 4);
		new BombPiece(this, 2, 7);
		new ArrowPiece(this, 3, 1, Direction.SOUTH);
		new BombPiece(this, 3, 2);
		new ArrowPiece(this, 3, 4, Direction.SOUTH_EAST);
		new ArrowPiece(this, 3, 5, Direction.EAST);
		new ArrowPiece(this, 4, 1, Direction.SOUTH_WEST);
		new BombPiece(this, 4, 2);
		new ArrowPiece(this, 4, 3, Direction.SOUTH_EAST);
		new ArrowPiece(this, 4, 6, Direction.NORTH);
		new ArrowPiece(this, 4, 8, Direction.NORTH_EAST);
		new ArrowPiece(this, 5, 1, Direction.SOUTH_WEST);
		new ArrowPiece(this, 5, 3, Direction.SOUTH);
		new ArrowPiece(this, 5, 6, Direction.NORTH_WEST);
		new BombPiece(this, 5, 7);
		new ArrowPiece(this, 5, 8, Direction.NORTH_EAST);
		new ArrowPiece(this, 6, 4, Direction.WEST);
		new ArrowPiece(this, 6, 5, Direction.NORTH_WEST);
		new BombPiece(this, 6, 7);
		new ArrowPiece(this, 6, 8, Direction.NORTH);
		new BombPiece(this, 7, 2);
		new BombPiece(this, 7, 5);
		new BombPiece(this, 7, 6);
		new BombPiece(this, 7, 7);
		new ArrowPiece(this, 8, 4, Direction.SOUTH_WEST);
		new ArrowPiece(this, 8, 5, Direction.SOUTH_WEST);
		new ArrowPiece(this, 8, 6, Direction.WEST);
		
		MapConstructionComplete();
	}
}
