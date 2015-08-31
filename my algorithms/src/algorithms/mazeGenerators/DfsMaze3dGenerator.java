package algorithms.mazeGenerators;

/**
 * The Class DfsMaze3dGenerator.
 */
public class DfsMaze3dGenerator extends Maze3dGeneratorAbs {

	/**
	 * generate maze
	 */

	@Override
	public Maze3d generate(int z, int x, int y) {

		this.maze = new Maze3d(z, x, y);
		maze.generateWalls();
		int[] start = maze.rand();
		int zs = start[0];
		int xs = start[1];
		int ys = start[2];
		Position sp = new Position(zs, xs, ys);
		maze.setStartPosition(sp);
		maze.setCellValue(zs, xs, ys, 0);
		Dfs(zs, xs, ys);

		return maze;
	}

	/**
	 * Dfs generate maze with walls
	 *
	 * @param z
	 *            -height
	 * @param x
	 *            - length
	 * @param y
	 *            - width
	 */
	private void Dfs(int z, int x, int y) {

		if (maze.outOfRange(z + 1, x, y) && maze.availableMove(z + 1, x, y)
				&& maze.getCellValue(z + 1, x, y) != 0) {
			maze.setCellValue(z + 1, x, y, 0);
			maze.setGoalPosition(z + 1, x, y);
			Dfs(z + 1, x, y);
		}
		if (maze.outOfRange(z - 1, x, y) && maze.availableMove(z - 1, x, y)
				&& maze.getCellValue(z - 1, x, y) != 0) {
			maze.setCellValue(z - 1, x, y, 0);
			maze.setGoalPosition(z - 1, x, y);
			Dfs(z - 1, x, y);
		}
		if (maze.outOfRange(z, x + 1, y) && maze.availableMove(z, x + 1, y)
				&& maze.getCellValue(z, x + 1, y) != 0) {
			maze.setCellValue(z, x + 1, y, 0);
			maze.setGoalPosition(z, x + 1, y);
			Dfs(z, x + 1, y);
		}
		if (maze.outOfRange(z, x - 1, y) && maze.availableMove(z, x - 1, y)
				&& maze.getCellValue(z, x - 1, y) != 0) {
			maze.setCellValue(z, x - 1, y, 0);
			maze.setGoalPosition(z, x - 1, y);
			Dfs(z, x - 1, y);
		}
		if (maze.outOfRange(z, x, y + 1) && maze.availableMove(z, x, y + 1)
				&& maze.getCellValue(z, x, y + 1) != 0) {
			maze.setCellValue(z, x, y + 1, 0);
			maze.setGoalPosition(z, x, y + 1);
			Dfs(z, x, y + 1);
		}
		if (maze.outOfRange(z, x, y - 1) && maze.availableMove(z, x, y - 1)
				&& maze.getCellValue(z, x, y - 1) != 0) {
			maze.setCellValue(z, x, y - 1, 0);
			maze.setGoalPosition(z, x, y - 1);
			Dfs(z, x, y - 1);
		}

	}

}
