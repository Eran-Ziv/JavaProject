package algorithms.demo;

import java.util.ArrayList;

import algorithm.generic.State;
import algorithms.mazeGenerators.DfsMaze3dGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;

/**
 * The Class Maze3dSearchableAdapter.
 */
public class Maze3dSearchableAdapter implements Searchable<Position> {

	/** The maze. */
	private Maze3d maze;

	/**
	 * Instantiates a new maze3d searchable adapter.
	 *
	 * @param z
	 *            -height
	 * @param x
	 *            - length
	 * @param y
	 *            - width
	 */
	public Maze3dSearchableAdapter(int z, int x, int y) {

		DfsMaze3dGenerator mg = new DfsMaze3dGenerator();
		maze = mg.generate(z, x, y);
	}

	/**
	 * Instantiates a new maze3d searchable adapter.
	 *
	 * @param maze
	 *            the maze
	 */
	public Maze3dSearchableAdapter(Maze3d maze) {
		super();
		this.maze = maze;
	}

	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public Maze3d getMaze() {
		return maze;
	}

	/**
	 * Sets the maze.
	 *
	 * @param maze
	 *            the new maze
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	/**
	 * gets the start position
	 * 
	 * @return the StartPosition
	 */
	@Override
	public State<Position> getStartPosition() {

		Position start = maze.getStartPosition();
		State<Position> pos = new State<Position>(start);
		return pos;
	}

	/**
	 * gets the goal position
	 * 
	 * @return the GoalPosition
	 */
	@Override
	public State<Position> getGoalPosition() {
		Position goal = maze.getGoalPosition();
		State<Position> pos = new State<Position>(goal);
		return pos;
	}

	/**
	 * gets the All the Possible Moves from current state
	 * 
	 * @return the AllPossibleMoves
	 */
	@Override
	public ArrayList<State<Position>> getAllPossibleMoves(
			State<Position> current) {

		ArrayList<Position> positions = maze.getPossibleMoves(current
				.getPosition());
		ArrayList<State<Position>> states = new ArrayList<State<Position>>();

		for (Position pos : positions) {

			State<Position> state = new State<Position>(pos);
			state.setCost(state.getCost() + 1);
			states.add(state);
		}

		return states;
	}

	/**
	 * Prints the maze.
	 */
	public void print() {

		maze.print();
	}

}
