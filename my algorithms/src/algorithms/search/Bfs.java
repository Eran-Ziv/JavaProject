package algorithms.search;

import java.util.ArrayList;

import algorithms.mazeGenerators.Searchable;
import algorithms.mazeGenerators.State;

/**
 * The Class Bfs. It starts at the tree root (or some arbitrary node of a graph,
 * sometimes referred to as a `search key') and explores the neighbor nodes
 * first, before moving to the next level neighbors. Compare BFS with the
 * equivalent, but more memory-efficient iterative deepening depth-first search
 * and contrast with depth-first search.
 * 
 * @param <T>
 *            the generic type
 */
public class Bfs<T> extends CommonSearcher<T> {

	/**
	 * search the best path to goal
	 * 
	 * @return the solution
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {

		openList.add(s.getStartPosition());
		while (!openList.isEmpty()) {
			State<T> p = popOpenList();
			closedList.add(p);
			if (p.equals(s.getGoalPosition())) {

				Solution<T> path = generatePathToGoal(p);
				return path;
			}

			ArrayList<State<T>> pos = s.getAllPossibleMoves(p);

			for (State<T> position : pos) {

				double newPathPrice = p.getCost() + position.getCost();
				if (!closedList.contains(position)
						&& !openList.contains(position)) {

					position.setCameFrom(p);
					position.setCost(newPathPrice);
					openList.add(position);
				} else {
					if (newPathPrice < position.getCost()) {
						position.setCameFrom(p);
						position.setCost(newPathPrice);
						if (!openList.contains(position))
							openList.add(position);
						else {
							openList.remove(position);
							openList.add(position);
						}
					}
				}

			}
		}
		return null;
	}

}
