package view;

import java.io.File;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;

public class MyView implements View {


	private Controller controller;


	public MyView(Controller controller) {
		this.controller = controller;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void dirCommand(String[] args) {

		File file = new File(args[1]);
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
	public void displayMaze(byte[] b) {

		System.out.println(b.toString());

	}

	@Override
	public void displayCrossSectionBy(Maze3d maze, char axis, int section ) {

		if(axis=='z'){
			maze.getCrossSectionByZ(section);
		}
		else if(axis=='x'){
			maze.getCrossSectionByX(section);
		}
		else if (axis=='y'){
			maze.getCrossSectionByY(section);
		}
		else {
			System.out.println("invalid axis");
		}

	}

	@Override
	public void displaySolution(Solution<Position> s) {

		s.print();

	}


	@Override
	public void displayError() {

		System.out.println("Error");

	}
}
