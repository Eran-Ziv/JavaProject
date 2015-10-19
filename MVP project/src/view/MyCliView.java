package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import algorithm.generic.Solution;
import algorithms.mazeGenerators.Maze3d;
import presenter.Command;


/**
 * The Class MyCliView. A spesific implementation of the View {@see View} interface, implementing a CLI view.
 */
public class MyCliView extends Observable implements View {


	/** The my maze3d displayer. */
	private Maze3dDisplayerAdapter myMaze3dDisplayer;
	
	/** The my maze2d displayer. */
	private Maze2dDisplayerAdapter myMaze2dDisplayer;
	
	/** The command. */
	private Command command;
	
	/** The my cli. */
	private CLI myCli;
	
	/** The out. */
	public  PrintWriter out;


	/**
	 * Instantiates a new my cli view.
	 *
	 * @param out the out
	 * @param in the in
	 */
	public MyCliView(PrintWriter out, BufferedReader in) {
		myCli = new CLI(out, in, this);
		this.out = out;
		myMaze2dDisplayer = new Maze2dDisplayerAdapter(out);
		myMaze3dDisplayer = new Maze3dDisplayerAdapter(out);
	}

	/**
	 * Gets the runing.
	 *
	 * @return the runing
	 */
	public boolean getRuning(){
		return myCli.isRuning();
	}

	/**
	 * Sets the runing.
	 *
	 * @param runing the new runing
	 */
	public void setRuning(boolean runing){
		myCli.setRuning(runing);
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
	 * @see view.View#displayModel(view.Drawable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayModel(Drawable<T> draw) {
		myMaze3dDisplayer.getDisplayer((Drawable<Maze3d>) draw);
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

	/* (non-Javadoc)
	 * @see view.View#displayString(java.lang.String)
	 */
	@Override
	public void displayString(String toPrint) {

		out.println(toPrint);
		out.flush();

	}

	/* (non-Javadoc)
	 * @see view.View#getUserCommand()
	 */
	@Override
	public Command getUserCommand() {

		return command;
	}

	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		Thread thread = new Thread(myCli);
		thread.start();
	}

	/* (non-Javadoc)
	 * @see view.View#setUserCommand(presenter.Command)
	 */
	public void setUserCommand(Command command){
		this.command = command;
		setChanged();
		notifyObservers("New command");
	}

	/* (non-Javadoc)
	 * @see view.View#setCommands(java.util.HashMap)
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {

		myCli.setCommands(commands);

	}

	/* (non-Javadoc)
	 * @see view.View#exit()
	 */
	@Override
	public void exit() {

		myCli.setRuning(false);		
	}





}
