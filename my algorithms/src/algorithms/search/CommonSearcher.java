package algorithms.search;

import java.util.HashSet;
import java.util.PriorityQueue;

import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.mazeGenerators.Searchable;

/**
 * The Class CommonSearcher.
 *
 * @param <T>
 *            the generic type
 */
public abstract class CommonSearcher<T> implements Searcher<T> {

	/**
	 * the abstract search method
	 */
	@Override
	public abstract Solution<T> search(Searchable<T> s);

	/** The open list. */
	protected PriorityQueue<State<T>> openList;

	/** The closed list. */
	protected HashSet<State<T>> closedList;

	/** The evaluated nodes. */
	private int evaluatedNodes;

	/**
	 * Instantiates a new common searcher.
	 */
	public CommonSearcher() {
		this.openList = new PriorityQueue<State<T>>();
		this.closedList = new HashSet<State<T>>();
		this.evaluatedNodes = 0;
	}

	/**
	 * Pop open list.
	 *
	 * @return the state
	 */
	protected State<T> popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}

	/**
	 * how many nodes inserted to the queue
	 */
	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}

	/**
	 * The path from start to goal
	 * 
	 */
	@Override
	public Solution<T> generatePathToGoal(State<T> goalState) {
		if(goalState.getCameFrom()==null)
			return null;
		Solution<T> solution = new Solution<T>();
		solution.addState(goalState);
		do {
			solution.addState(goalState.getCameFrom());
			goalState = goalState.getCameFrom();
		} while (goalState.getCameFrom() != null);
		return solution;
	}
}
