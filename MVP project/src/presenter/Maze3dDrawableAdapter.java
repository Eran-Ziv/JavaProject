package presenter;

import algorithms.mazeGenerators.Maze3d;
import view.Drawable;


/**
 * The Class Maze3dDrawableAdapter.
 * Object adapter for maze3d {@see Maze3d} and drawable interface{@see Drawable}
 *  @author Eran & Ziv
 */
public class Maze3dDrawableAdapter implements Drawable<Maze3d> {

	/** The my maze. */
	private Maze3d myMaze;

	/**
	 * Instantiates a new maze3d drawable adapter.
	 *
	 * @param myMaze the my maze
	 */
	public Maze3dDrawableAdapter(Maze3d myMaze) {
		this.myMaze = myMaze;

	}

	/* (non-Javadoc)
	 * @see view.Drawable#getData()
	 */
	@Override
	public Maze3d getData() {

		return this.myMaze;
	}

}
