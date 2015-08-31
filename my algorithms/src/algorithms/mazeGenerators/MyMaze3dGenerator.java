package algorithms.mazeGenerators;

/**
 * The Class MyMaze3dGenerator.
 */
public class MyMaze3dGenerator extends Maze3dGeneratorAbs {

	/**
	 * method to generate maze
	 */
	@Override
	public Maze3d generate(int z, int x, int y) {

		this.maze = new Maze3d(z, x, y);
		this.maze.generateWalls();
		int[] start = maze.rand();
		int zs = start[0];
		int xs = start[1];
		int ys = start[2];
		int[] goal = maze.rand();
		int zg = goal[0];
		int xg = goal[1];
		int yg = goal[2];
		Position sp = new Position(zs, xs, ys);
		Position gp = new Position(zg, xg, yg);
		this.maze.setStartPosition(sp);
		this.maze.setGoalPosition(gp);
		this.maze.setCellValue(zs, xs, ys, 0);
		this.maze.setCellValue(zg, xg, yg, 0);

		while (zs != zg) {

			if (zs < zg) {

				this.maze.setCellValue(zs, xs, ys, 0);
				zs++;
			} else {

				this.maze.setCellValue(zs, xs, ys, 0);
				zs--;
			}
		}
		this.maze.setCellValue(zs, xs, ys, 0);
		while (xs != xg) {

			if (xs < xg) {

				this.maze.setCellValue(zs, xs, ys, 0);
				xs++;
			} else {

				this.maze.setCellValue(zs, xs, ys, 0);
				xs--;
			}
		}
		this.maze.setCellValue(zs, xs, ys, 0);
		while (ys != yg) {
			if (ys < yg) {

				this.maze.setCellValue(zs, xs, ys, 0);
				ys++;
			} else {

				this.maze.setCellValue(zs, xs, ys, 0);
				ys--;
			}
		}
		this.maze.setCellValue(zs, xs, ys, 0);
		return this.maze;
	}

}
