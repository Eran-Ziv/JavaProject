package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View {

	public void dirCommand(String [] args);
	public void display(Maze3d maze);
	public void displayCrossSectionBy(Maze3d maze, char axis, int section);
	public void displaySolution(Solution<Position> s);
	
}
