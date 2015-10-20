package algorithms.demo;

import java.io.Serializable;
import java.util.ArrayList;

import algorithm.generic.State;
import algorithms.mazeGenerators.Maze2d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;

public class Maze2dSearchableAdapter implements Searchable<Position>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Maze2d myMaze;
	
	public Maze2dSearchableAdapter(Maze2d myMaze) {
		this.myMaze = myMaze;
	}
	
	public Maze2d getMyMaze() {
		return myMaze;
	}

	public void setMyMaze(Maze2d myMaze) {
		this.myMaze = myMaze;
	}

	@Override
	public State<Position> getStartPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State<Position> getGoalPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<State<Position>> getAllPossibleMoves(State<Position> current) {
		// TODO Auto-generated method stub
		return null;
	}

}
