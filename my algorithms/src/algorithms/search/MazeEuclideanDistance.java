package algorithms.search;

import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.State;

/**
 * The Class MazeEuclideanDistance.
 */
public class MazeEuclideanDistance implements Heuristic<Position> {

	/**
	 * calculate the distance between 2 positions (air distance)
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
		int temp = 2;
		double sum = Math.pow(cz - gz, temp) + Math.pow(cx - gx, temp)
				+ Math.pow(cy - gy, temp);

		return sum;
	}

}
