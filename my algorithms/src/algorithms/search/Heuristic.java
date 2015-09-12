package algorithms.search;

import algorithm.generic.State;

/**
 * The Interface Heuristic.
 *
 * @param <T>
 *            the generic type
 */
public interface Heuristic<T> {

	/**
	 * Heuristical.
	 *
	 * @param current
	 *            State<T>
	 * @param goal
	 *            State<T>
	 * @return the cost
	 */
	double heuristical(State<T> current, State<T> goal);

}
