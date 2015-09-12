package algorithms.mazeGenerators;

import java.util.ArrayList;

import algorithm.generic.State;

/**
 * Interface Serachable
 */
public interface Searchable<T> {
	/**
	 * The beginning state
	 * 
	 * @return Start state.
	 */
	public State<T> getStartPosition();

	/**
	 * The goal state
	 * 
	 * @return Goal state.
	 */
	public State<T> getGoalPosition();

	/**
	 * Gets an arraylist of all the possible moves from current state
	 * 
	 * @return the possible moves from current state
	 */
	public ArrayList<State<T>> getAllPossibleMoves(State<T> current);

}
