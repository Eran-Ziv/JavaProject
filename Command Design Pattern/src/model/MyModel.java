package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import view.MyView;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.Heuristic;
import algorithms.search.MazeEuclideanDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;

public class MyModel implements Model<Position> { 

	private Controller<Position> controller;
	private HashMap<String, byte[]> mazes;
	private HashMap<String, String> nameToFileName;
	
	
	@Override
	public byte[] generateModel(String name, int z, int x, int y) {
		Maze3dSearchableAdapter myAdapter = new Maze3dSearchableAdapter(z, x, y);
		
		try {
			byte[] byteArray = myAdapter.getMaze().toByteArray();
			mazes.put(name, byteArray);
			return byteArray;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("maze to byte array error");
		}
		
		return null;
	}


	@Override
	public void saveModel(String name, String fileName) {
		
		try {
			MyCompressorOutputStream myCompressor = new MyCompressorOutputStream(new FileOutputStream(fileName));
			nameToFileName.put(name, fileName);
			myCompressor.write(mazes.get(name));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error file not found exeption, save model");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error IO exeption ,get model form hash map ,save model");
		}
		
	}

	
	@Override
	public int getModelSizeInMemory(String name) {
		
		int size = mazes.get(name).length;
		return size;
	}


	@Override
	public long getModelSizeInFile(String name) {
		
		String fileName = nameToFileName.get(name);
		File myFile = new File(fileName);
		
		return myFile.length();
	}

	
	
	@Override
	public byte[] loadModel(String fileName, String name) {// wtf??
		
		try {
			MyDecompressorInputStream myDecompressor = new MyDecompressorInputStream(new FileInputStream(fileName));
		    // TODO how to get maze size??
			//byte[] byteArray = new byte[];
		    //myDecompressor.read(byteArray);
			//mazes.put(name,byteArray);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	
	@Override
	public Solution<Position> solveModel(String name, String algorithm, String heuristic) {
		
		byte[] byteArray = mazes.get(name);
		Maze3d myMaze = new Maze3d(byteArray);
		Heuristic myHeuristic;
		Maze3dSearchableAdapter myAdapter = new Maze3dSearchableAdapter(myMaze);
		
		if(algorithm.toLowerCase().equals("bfs")){
			Bfs <Position> myBfs = new Bfs<Position>();
			
			return myBfs.search(myAdapter);
		}
		else if(algorithm.toLowerCase().equals("astar")){
			
			if(heuristic.toLowerCase().equals("manhattan")){
				myHeuristic = new MazeManhattanDistance();
			}
			else{
				myHeuristic = new MazeEuclideanDistance();
			}
			
			Astar<Position> myAstar = new Astar<Position>(myHeuristic);
			
			return myAstar.search(myAdapter);
		}
		
		return null;
	}
}
