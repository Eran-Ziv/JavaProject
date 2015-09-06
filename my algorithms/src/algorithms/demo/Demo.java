package algorithms.demo;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import algorithms.mazeGenerators.Maze3d;


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

		try {
			Maze3d  maze2 = new Maze3d(maze.getMaze().toByteArray());
			System.out.println("start Position: " + maze2.getStartPosition());
			System.out.println("goal Position: " + maze2.getGoalPosition());
			maze2.print();
			
			
			OutputStream out=new MyCompressorOutputStream(
					new FileOutputStream("1.txt"));
			out.write(maze2.toByteArray());
			out.flush();
			out.close();
			InputStream in=new MyDecompressorInputStream(
					new FileInputStream("1.txt"));
					byte b[]=new byte[maze2.toByteArray().length];
					in.read(b);
					in.close();
					Maze3d loaded=new Maze3d(b);
					System.out.println("start Position: " + loaded.getStartPosition());
					System.out.println("goal Position: " + loaded.getGoalPosition());
					System.out.println(loaded.equals(maze2));
					
					loaded.print();

		} catch (IOException e) {
			e.printStackTrace();
		}






		/*Bfs<Position> bfs = new Bfs<Position>();
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
		System.out.println("---------End of manhattanAstar---------");*/

	}

	public static void main(String[] args){
		Run();

	}
}