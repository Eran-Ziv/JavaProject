package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.Heuristic;
import algorithms.search.MazeEuclideanDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Solution;
import controller.Controller;

public class MyModel implements Model { 

	private Controller controller;
	private HashMap<String, byte[]> nameToMaze;
	private HashMap<String, String> nameToFileName;
	private HashMap<String, Solution<Position>>nameToSolution;
	private ArrayList<Closeable> threads ;
	private ArrayList<FileInputStream> files ;

	public Controller getController() {
		return controller;
	}


	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void addThreads(Closeable c) {
		threads.add(c);
	}

	
	public void addFiles(FileInputStream file) {
		files.add(file);
	}

	public ArrayList<Closeable> getThreads() {
		return threads;
	}


	public void setThreads(ArrayList<Closeable> threads) {
		this.threads = threads;
	}


	@Override
	public byte[] generateModel(String name, int z, int x, int y) {
		Maze3dSearchableAdapter myAdapter = new Maze3dSearchableAdapter(z, x, y);

		try {
			byte[] byteArray = myAdapter.getMaze().toByteArray();
			nameToMaze.put(name, byteArray);
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
			myCompressor.write(nameToMaze.get(name));
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

		int size = nameToMaze.get(name).length;
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

		if(nameToSolution.get(name) != null){
			return nameToSolution.get(name); 
		}


		byte[] byteArray = nameToMaze.get(name);
		Maze3d myMaze = new Maze3d(byteArray);
		Heuristic myHeuristic;
		Maze3dSearchableAdapter myAdapter = new Maze3dSearchableAdapter(myMaze);

		if(algorithm.toLowerCase().equals("bfs")){
			Bfs <Position> myBfs = new Bfs<Position>();
			nameToSolution.put(name, myBfs.search(myAdapter) );
			return nameToSolution.get(name);
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


	@Override
	public byte[] getNameToModel(String name) {

		byte [] byteArray=nameToMaze.get(name);
		return byteArray;
	}


	@Override
	public byte[] CrossSectionBy(String name, char dimention, int section) {

		byte [] byteArray=nameToMaze.get(name);
		Maze3d maze3d=null;
		int[][] maze2d=null;
		if(byteArray!=null)
			maze3d = new Maze3d(byteArray);	
		byteArray=null;
		switch (dimention) {
		
		case 'z':
			if(section>0&&section>maze3d.getHeight())
				maze2d=maze3d.getCrossSectionByZ(section);
			break;
			
		case 'x':
			if(section>0&&section>maze3d.getLength())
				maze2d=maze3d.getCrossSectionByX(section);

			break;
			
		case 'y':
			if(section>0&&section>maze3d.getWidth())
				maze2d=maze3d.getCrossSectionByY(section);

			break;
			
		default:
			
			break;
		}

		byteArray=intToByte(maze2d);

		return byteArray;
	}
	
	private byte[] intToByte(int[][] array){
		
		int size=array.length;
		int length=array[0].length;
		byte [] byteArray=new byte[size*length+2];
		int k=0;
		byteArray[k++]=(byte) size;
		byteArray[k++]=(byte) length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < length; j++) {
				byteArray[k++]=(byte)array[i][j];
			}
			
		}
		
		return byteArray;
	}


	@Override
	public void exit() {
		
		for (FileInputStream file : files) {
			
			try {
				
				file.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}
