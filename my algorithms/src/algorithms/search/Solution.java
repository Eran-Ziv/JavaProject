package algorithms.search;

import java.util.ArrayList;

import algorithms.mazeGenerators.State;

/**
 * The Class Solution.
 *
 * @param <T>
 *            the generic type
 */
public class Solution<T> {

	/**
	 * The solution.
	 *
	 */
	private ArrayList<State<T>> solution = new ArrayList<State<T>>();

	/**
	 * Gets the solution.
	 *
	 * @return the solution
	 */
	public ArrayList<State<T>> getSolution() {

		return solution;
	}

	/**
	 * Sets the solution.
	 *
	 * @param solution
	 *            the new solution
	 */
	public void setSolution(ArrayList<State<T>> solution) {
		this.solution = solution;
	}

	/**
	 * Adds the state.
	 *
	 * @param state
	 *            the state
	 */
	public void addState(State<T> state) {
		this.solution.add(state);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (State<T> s : solution)
			sb.append(s + "<-");
		return sb.toString();
	}

	/**
	 * Prints the solution.
	 */
	public void print() {

		for (State<T> position : solution) {

			System.out.println(position.getPosition());

		}
	}
}