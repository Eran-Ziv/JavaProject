package algorithms.search;

import algorithm.generic.State;
import algorithms.mazeGenerators.Position;

/**
 * The Class MazeManhattanDistance.
 */
public class MazeManhattanDistance implements Heuristic<Position> {

	/**
	 * calculate the distance between 2 positions
	 */
	@Override
	public double heuristical(State<Position> current, State<Position> goal) {

		Position pc = current.getPosition();
		int[] tempc = pc.split();
		int cz = tempc[0];
		int cx = tempc[1];
		int cy = tempc[2];
		Position pg = goal.getPosition();
		int[] tempg = pg.split();
		int gz = tempg[0];
		int gx = tempg[1];
		int gy = tempg[2];

		double sum = Math.abs(cz - gz) + Math.abs(cx - gx) + Math.abs(cy - gy);
		return sum;
	}

}
