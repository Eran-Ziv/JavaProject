package view;

import java.io.PrintWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class Maze3dDisplayerAdapter.
 */
public class Maze3dDisplayerAdapter implements Displayer<int[][][]> {

	/** The draw. */
	int[][][] draw;
	
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
	public void getDisplayer(Drawable<int[][][]> draw) {

		this.draw = draw.getData();
	}

	/* (non-Javadoc)
	 * @see view.Displayer#display()
	 */
	@Override
	public void display() {

		for (int i = 0; i < draw.length; i++) {
			for (int j = 0; j < draw[i].length; j++) {
				for (int w = 0; w < draw[i][j].length; w++) {

					out.print(draw[i][j][w]);
					out.flush();
				}
				out.print("\n");
				out.flush();
			}
			out.print("\n\n");
			out.flush();
		}
	}
}
