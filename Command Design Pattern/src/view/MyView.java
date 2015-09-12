package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithm.generic.Solution;
import controller.Command;


public class MyView implements View {


	
	private CLI myCli;
	private Maze3dDisplayerAdapter myMaze3dDisplayer;
	private Maze2dDisplayerAdapter myMaze2dDisplayer;
	


	public MyView(PrintWriter out, BufferedReader in) {
		this.myCli = new CLI(out, in);
		this.myMaze2dDisplayer = new Maze2dDisplayerAdapter(out);
		this.myMaze3dDisplayer = new Maze3dDisplayerAdapter(out);
		
	}

	

	@Override
	public void dirCommand(String fileName) {

		File file = new File(fileName);
		File[] listOfFiles = file.listFiles();
		for (File files : listOfFiles) {
			if (files.isFile()) {
				System.out.println(files.getName());
			}
			if(files.isDirectory()){
				System.out.println(files.getName());
			}
		}


	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {

		this.myCli.setCommands(commands);

	}


	@Override
	public void displayError(String error) {
		System.out.println(error);

	}
	
	public void start() {
		myCli.run();
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public <T> void displayModel(Drawable<T> draw) {
		myMaze3dDisplayer.getDisplayer((Drawable<int[][][]>) draw);
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


}
