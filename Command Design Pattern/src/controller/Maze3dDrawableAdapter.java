package controller;

import algorithms.mazeGenerators.Maze3d;
import view.Drawable;

public class Maze3dDrawableAdapter implements Drawable<int[][][]> {

	private Maze3d myMaze;
	
	public Maze3dDrawableAdapter(Maze3d myMaze) {
		this.myMaze = myMaze;
	
	}
	
	@Override
	public int[][][] getData() {
		
		return this.myMaze.getMaze();
	}

}
