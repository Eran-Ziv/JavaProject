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
	PrintWriter out;




	public MyView(PrintWriter out, BufferedReader in) {
		this.myCli = new CLI(out, in);
		this.myMaze2dDisplayer = new Maze2dDisplayerAdapter(out);
		this.myMaze3dDisplayer = new Maze3dDisplayerAdapter(out);
		this.out = out;
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

	@Override
	public void setCommands(HashMap<String, Command> commands) {

		this.myCli.setCommands(commands);

	}


	@Override
	public void displayString(String toPrint) {
		out.flush();
		out.println(toPrint);
		out.flush();

	}

	public void start() {
		myCli.start();

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
