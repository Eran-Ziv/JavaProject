package controller;

import algorithms.mazeGenerators.Maze2d;
import view.Drawable;

public class Maze2dDrawableAdapter implements Drawable<int[][]> {
	
	Maze2d myMaze;
	
	

	public Maze2dDrawableAdapter(Maze2d myMaze) {
		
		this.myMaze = myMaze;
	}



	@Override
	public int[][] getData() {
		
		return myMaze.getMyMaze();
	}

}
