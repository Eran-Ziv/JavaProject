package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import algorithm.generic.Solution;
import controller.Command;

// TODO: Auto-generated Javadoc
/**
 * The Class MyView.
 */
public class MyView implements View {

	/** The my cli. */
	private CLI myCli;
	
	/** The my maze3d displayer. */
	private Maze3dDisplayerAdapter myMaze3dDisplayer;
	
	/** The my maze2d displayer. */
	private Maze2dDisplayerAdapter myMaze2dDisplayer;
	
	/** The out. */
	PrintWriter out;

	/**
	 * Instantiates a new my view.
	 *
	 * @param out the out
	 * @param in the in
	 */
	public MyView(PrintWriter out, BufferedReader in) {
		this.myCli = new CLI(out, in);
		this.myMaze2dDisplayer = new Maze2dDisplayerAdapter(out);
		this.myMaze3dDisplayer = new Maze3dDisplayerAdapter(out);
		this.out = out;
	}

	/* (non-Javadoc)
	 * @see view.View#dirCommand(java.lang.String)
	 */
	@Override
	public void dirCommand(String fileName) {
		out.flush();
		File file = new File(fileName);

		if(!file.exists()){
			out.print("No such file or directory.\n");
		}
		else{
			File[] listOfFiles = file.listFiles();
			for (File files : listOfFiles) {
				if (files.isFile()) {
					out.println(files.getName());
				}
				if(files.isDirectory()){
					out.println(files.getName());
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see view.View#setCommands(java.util.HashMap)
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {

		this.myCli.setCommands(commands);
	}

	/* (non-Javadoc)
	 * @see view.View#displayString(java.lang.String)
	 */
	@Override
	public void displayString(String toPrint) {
		out.flush();
		out.println(toPrint);
		out.flush();
	}

	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	public void start() {
		myCli.start();
	}

	/* (non-Javadoc)
	 * @see view.View#displayModel(view.Drawable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayModel(Drawable<T> draw) {
		myMaze3dDisplayer.getDisplayer((Drawable<int[][][]>) draw);
		myMaze3dDisplayer.display();
	}

	/* (non-Javadoc)
	 * @see view.View#displayCrossSectionBy(view.Drawable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayCrossSectionBy(Drawable<T> draw) {
		myMaze2dDisplayer.getDisplayer((Drawable<int[][]>) draw);
		myMaze2dDisplayer.display();
	}

	/* (non-Javadoc)
	 * @see view.View#displaySolution(algorithm.generic.Solution)
	 */
	@Override
	public <T> void displaySolution(Solution<T> solution) {
		solution.print();
	}
}
