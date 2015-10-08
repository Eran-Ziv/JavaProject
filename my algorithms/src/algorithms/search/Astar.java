package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;

import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.mazeGenerators.Searchable;

/**
 * The Class Astar. A* uses a best-first search and finds a least-cost path from
 * a given initial node to one goal node (out of one or more possible goals). As
 * A* traverses the graph, it builds up a tree of partial paths. The leaves of
 * this tree (called the open set or fringe) are stored in a priority queue that
 * orders the leaf nodes by a cost function, which combines a heuristic estimate
 * of the cost to reach a goal and the distance traveled from the initial node
 *
 * @param <T>
 *            the generic type
 */
public class Astar<T> extends CommonSearcher<T> {
	/**
	 * /* @param The Heuristic.
	 */
	private Heuristic<T> h;

	/** The g. */
	private HashMap<State<T>, Double> g = new HashMap<State<T>, Double>();

	/**
	 * Instantiates a new astar.
	 *
	 * @param h
	 *            the h
	 */
	public Astar(Heuristic<T> h) {
	
		this.h = h;
		
	}

	/**
	 * search the best path to goal
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {

		if(s!=null&&h!=null&&s.getGoalPosition()!=null&&s.getGoalPosition()!=null){
			State<T> start = s.getStartPosition();
			State<T> goal = s.getGoalPosition();
			openList.add(start);
			start.setCost(h.heuristical(start, goal));
			g.put(start, start.getCost());
			while (!openList.isEmpty()) {

				State<T> state = popOpenList();
				closedList.add(state);
				if (state.equals(s.getGoalPosition())) {

					return generatePathToGoal(state);
				}
				ArrayList<State<T>> successors = new ArrayList<State<T>>();
				successors = s.getAllPossibleMoves(state);

				for (State<T> position : successors) {

					double newPathPrice = state.getCost() + position.getCost()
							+ h.heuristical(position, goal);
					if (!closedList.contains(position)
							&& !openList.contains(position)) {

						position.setCameFrom(state);
						position.setCost(newPathPrice);
						openList.add(position);
						g.put(position, position.getCost());
					} else {

						double oldPathPrice = g.get(position);
						if (newPathPrice < oldPathPrice) {
							position.setCameFrom(state);
							position.setCost(newPathPrice);

							if (!openList.contains(position)) {
								openList.add(position);
								g.put(position, position.getCost());
							} else {
								openList.remove(position);
								openList.add(position);
								g.put(position, position.getCost());
							}

						}
					}
				}
			}
		}
		return null;
	}

}
