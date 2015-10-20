package algorithms.mazeGenerators;

import java.io.Serializable;

public class Maze2d implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int[][] myMaze;
	int length;
	int width;
	
	public Maze2d(int[][] myMaze) {
		this.myMaze = myMaze;
		this.length = myMaze.length;
		this.width = myMaze[0].length;
	}
	
	public int[][] getMyMaze() {
		return myMaze;
	}
	public void setMyMaze(int[][] myMaze) {
		this.myMaze = myMaze;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	
}
