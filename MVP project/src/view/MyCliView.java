package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import algorithm.generic.Solution;
import algorithms.mazeGenerators.Maze3d;
import presenter.Command;

public class MyCliView extends Observable implements View {

	
	private Maze3dDisplayerAdapter myMaze3dDisplayer;
	private Maze2dDisplayerAdapter myMaze2dDisplayer;
	private Command command;
	private CLI myCli;
	public  PrintWriter out;
	
	
	public MyCliView(PrintWriter out, BufferedReader in) {
		myCli = new CLI(out, in, this);
		this.out = out;
		myMaze2dDisplayer = new Maze2dDisplayerAdapter(out);
		myMaze3dDisplayer = new Maze3dDisplayerAdapter(out);
	}
	
	public boolean getRuning(){
		return myCli.isRuning();
	}
	
	public void setRuning(boolean runing){
		myCli.setRuning(runing);
	}
	
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayModel(Drawable<T> draw) {
		myMaze3dDisplayer.getDisplayer((Drawable<Maze3d>) draw);
		myMaze3dDisplayer.display();

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayCrossSectionBy(Drawable<T> draw) {
		myMaze2dDisplayer.getDisplayer((Drawable<int[][]>) draw);
		myMaze2dDisplayer.display();

	}

	@Override
	public <T> void displaySolution(Solution<T> solution) {
		solution.print();

	}

	@Override
	public void displayString(String toPrint) {
		
		out.println(toPrint);
		out.flush();

	}

	@Override
	public Command getUserCommand() {
		
		return command;
	}

	@Override
	public void start() {
		Thread thread = new Thread(myCli);
		thread.start();
	}
	
	public void setUserCommand(Command command){
		this.command = command;
		setChanged();
		notifyObservers("New command");
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		
		myCli.setCommands(commands);
		
	}

	@Override
	public void exit() {
		
		myCli.setRuning(false);		
	}

	
	
	

}
