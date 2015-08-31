package algorithms.mazeGenerators;

/**
 * The Class Maze3dGeneratorAbs.
 */

public abstract class Maze3dGeneratorAbs implements Maze3dGenerator {

	/** The maze. */
	protected Maze3d maze;

	/**
	 * abstract method to generate maze
	 */
	@Override
	public abstract Maze3d generate(int z, int x, int y);

	/**
	 * measure the time to generate maze
	 */
	@Override
	public String measureAlgorithmTime(int z, int x, int y) {

		long begin = System.currentTimeMillis();

		this.generate(z, x, y);

		long finish = System.currentTimeMillis();

		long total = (finish - begin);

		return ((total) + "");
	}

}
