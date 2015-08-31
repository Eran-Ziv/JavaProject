package algorithms.mazeGenerators;

/**
 * The Interface Maze3dGenerator.
 */
public interface Maze3dGenerator {

	/**
	 * Generate maze
	 *
	 * @param z
	 *            -height
	 * @param x
	 *            - length
	 * @param y
	 *            - width
	 * @return the maze3d
	 */
	public Maze3d generate(int z, int x, int y);

	/**
	 * Measure algorithm time of generate maze
	 *
	 * @param z
	 *            -height
	 * @param x
	 *            - length
	 * @param y
	 *            - width
	 * @return the time
	 */
	public String measureAlgorithmTime(int z, int x, int y);

}
