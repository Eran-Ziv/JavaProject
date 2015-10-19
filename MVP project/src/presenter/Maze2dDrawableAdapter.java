package presenter;

import algorithms.mazeGenerators.Maze2d;
import view.Drawable;

/**
 * The Class Maze2dDrawableAdapter.
 * Object adapter for maze2d {@see Maze2d} and drawable interface{@see Drawable}
 */
public class Maze2dDrawableAdapter implements Drawable<int[][]> {

	/** The my maze. */
	Maze2d myMaze;

	/**
	 * Instantiates a new maze2d drawable adapter.
	 *
	 * @param myMaze the my maze
	 */
	public Maze2dDrawableAdapter(Maze2d myMaze) {

		this.myMaze = myMaze;
	}

	/* (non-Javadoc)
	 * @see view.Drawable#getData()
	 */
	@Override
	public int[][] getData() {

		return myMaze.getMyMaze();
	}

}
