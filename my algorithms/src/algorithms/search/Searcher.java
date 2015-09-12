package algorithms.search;

import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.mazeGenerators.Searchable;

/**
 * The Interface Searcher.
 *
 * @param <T>
 *            the generic type
 */
public interface Searcher<T> {

	/**
	 * Search.
	 *
	 * @param search
	 * @return the solution
	 */
	public Solution<T> search(Searchable<T> search);

	/**
	 * Generate path to goal.
	 *
	 * @param p
	 * @return the solution
	 */
	public Solution<T> generatePathToGoal(State<T> p);

	/**
	 * Gets the number of nodes evaluated.
	 *
	 * @return the number of nodes evaluated
	 */
	public int getNumberOfNodesEvaluated();

}