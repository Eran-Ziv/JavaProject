package algorithms.demo;

import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.MazeEuclideanDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Solution;

/**
 * The program implements maze game. we solve it with 2 algorithms: bfs & Astar
 * 
 * @author ziv bruhian & Eran Reuveni
 * @version 1.0
 * @since 24/8/2015
 */

public class Demo {

	public static  void Run() {

		/**
		 * Run. Creates a new 3D maze, and solves it using both A star and BFS.
		 * Prints the Amount of evaluated nodes for ASTAR and for BFS.
		 */
		Maze3dSearchableAdapter maze = new Maze3dSearchableAdapter(10, 10, 10);
		maze.print();
		
		System.out.println("start Position: " + maze.getStartPosition());
		System.out.println("goal Position: " + maze.getGoalPosition());
		Bfs<Position> bfs = new Bfs<Position>();
		System.out.println("Bfs:");
		Solution<Position> solution1 = bfs.search(maze);
		solution1.print();
		System.out.println(bfs.getNumberOfNodesEvaluated());
		System.out.println("---------End of Bfs---------");
		Astar<Position> euclide = new Astar<Position>(
				new MazeEuclideanDistance());
		System.out.println("Astar Euclide :");
		Solution<Position> solution2 = euclide.search(maze);
		solution2.print();
		System.out.println(euclide.getNumberOfNodesEvaluated());
		System.out.println("---------End of euclideAstar---------");
		Astar<Position> manhattan = new Astar<Position>(
				new MazeManhattanDistance());
		System.out.println("Astar Manhattan :");
		Solution<Position> solution3 = manhattan.search(maze);
		solution3.print();
		System.out.println(manhattan.getNumberOfNodesEvaluated());
		System.out.println("---------End of manhattanAstar---------");

	}

	public static void main(String[] args){
		Run();
		
	}
}