package algorithms.search;

import algorithms.mazeGenerators.Searchable;
import algorithms.mazeGenerators.State;

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