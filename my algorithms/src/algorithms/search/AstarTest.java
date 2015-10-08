package algorithms.search;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;

public class AstarTest {


	

	@Test
	public void testNullHeuristic() {
		Maze3d maze3D = new MyMaze3dGenerator().generate(3, 3, 3);
		Maze3dSearchableAdapter maze3d = new Maze3dSearchableAdapter(maze3D);
		Searcher<Position> astar= new Astar<Position>(null);
		maze3D.print();
		Solution<Position> astarSolution=astar.search(maze3d);
		System.out.println(" Astar sol "+astarSolution);
		assertNull(astarSolution);
		
	}

	@Test
	public void testEmptyMazeArgument() {
		Maze3d maze3D = new MyMaze3dGenerator().generate(0,0,0);
		Maze3dSearchableAdapter maze3d = new Maze3dSearchableAdapter(maze3D);
		Searcher<Position> astar= new Astar<Position>(new MazeManhattanDistance());
		Solution<Position> astarSolution=astar.search(maze3d);
		System.out.println("emptymaze" + astarSolution);
		assertNull(astarSolution);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDumbSearchableArgument() {
		@SuppressWarnings("rawtypes")
		Astar astar=new Astar(new MazeManhattanDistance());
		Searcher<Position> astarSolution=(Searcher<Position>) astar.search(new Searchable<Position>() {

			@Override
			public State<Position> getStartPosition() {
				
				return null;
			}

			@Override
			public State<Position> getGoalPosition() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ArrayList<State<Position>> getAllPossibleMoves(
					State<Position> current) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		assertNull(astarSolution);
	
	}
	
	@Test
	public void testNullSearchArgument() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Astar astar=new Astar(new MazeManhattanDistance());
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Solution sol=astar.search(null);
		
		assertNull(sol);
	}
}