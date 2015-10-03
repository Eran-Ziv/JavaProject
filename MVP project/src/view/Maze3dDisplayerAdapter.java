package view;

import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze3d;

// TODO: Auto-generated Javadoc
/**
 * The Class Maze3dDisplayerAdapter.
 */
public class Maze3dDisplayerAdapter implements Displayer<Maze3d> {

	/** The draw. */
	Maze3d draw;
	
	/** The out. */
	PrintWriter out;

	/**
	 * Instantiates a new maze3d displayer adapter.
	 *
	 * @param out the out
	 */
	public Maze3dDisplayerAdapter(PrintWriter out) {
		this.out = out;
	}

	/* (non-Javadoc)
	 * @see view.Displayer#getDisplayer(view.Drawable)
	 */
	@Override
	public void getDisplayer(Drawable<Maze3d> draw) {

		this.draw = draw.getData();
	}

	/* (non-Javadoc)
	 * @see view.Displayer#display()
	 */
	@Override
	public void display() {

		draw.print();
	}
}
