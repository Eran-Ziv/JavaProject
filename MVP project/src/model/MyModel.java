package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import algorithm.generic.Solution;
import algorithms.demo.Maze2dSearchableAdapter;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.DfsMaze3dGenerator;
import algorithms.mazeGenerators.Maze2d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.Heuristic;
import algorithms.search.MazeEuclideanDistance;
import algorithms.search.MazeManhattanDistance;

// TODO: Auto-generated Javadoc
/**
 * The Class MyModel.
 */
public class MyModel extends Observable implements Model  { 

	/** The name to maze. */
	private HashMap<String, Maze3d> nameToMaze;
	
	/** The name to file name. */
	private HashMap<String, String> nameToFileName;
	
	/** The name to solution. */
	private HashMap<String, Solution<Position>>nameToSolution;
	
	/** The my compressor. */
	MyCompressorOutputStream myCompressor;
	
	/** The my decompressor. */
	MyDecompressorInputStream myDecompressor;

	/**
	 * Instantiates a new my model.
	 */
	public MyModel() {

		this.nameToMaze = new HashMap<>();
		this.nameToFileName = new HashMap<>();
		this.nameToSolution = new HashMap<>();
		this.myCompressor = null;
		this.myDecompressor = null;
	}

	/* (non-Javadoc)
	 * @see model.Model#saveModel(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveModel(String name, String fileName) {

		try {
			this.myCompressor = new MyCompressorOutputStream(new FileOutputStream(fileName));
			nameToFileName.put(name, fileName);
			this.myCompressor.write(nameToMaze.get(name).toByteArray());
			myCompressor.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error file not found exeption, save model");

		} catch (IOException e) {
			System.out.println("Error IO exeption ,get model form hash map ,save model");

		} finally {
			try {
				myCompressor.close();
			} catch (IOException e) {
				System.out.println("Error closing file");
			}
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public  Solution<Position> getSolution(String name){

		if(nameToSolution.get(name) != null){
			return nameToSolution.get(name); 
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInMemory(java.lang.String)
	 */
	@Override
	public int getModelSizeInMemory(String name) throws IOException {

		int size;
		size = nameToMaze.get(name).toByteArray().length;
		return size;

	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInFile(java.lang.String)
	 */
	@Override
	public long getModelSizeInFile(String name) {

		if(name != null){
			File myFile = new File(name);
			return myFile.length();
		}
		else
			return 0;
	}

	/* (non-Javadoc)
	 * @see model.Model#solveModel(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void solveModel(String name, String algorithm, String heuristic) {

		Maze3d myMaze = nameToMaze.get(name);
		Heuristic<Position> myHeuristic;

		if(myMaze != null){
			Maze3dSearchableAdapter myAdapter = new Maze3dSearchableAdapter(myMaze);

			if(algorithm.toLowerCase().equals("bfs")){
				Bfs <Position> myBfs = new Bfs<Position>();
				nameToSolution.put(name, myBfs.search(myAdapter) );
			}
			else if(algorithm.toLowerCase().equals("astar")){

				if(heuristic.toLowerCase().equals("manhattan")){
					myHeuristic = new MazeManhattanDistance();
				}
				else{
					myHeuristic = new MazeEuclideanDistance();
				}

				Astar<Position> myAstar = new Astar<Position>(myHeuristic);
				nameToSolution.put(name, myAstar.search(myAdapter) );
			}
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#exit()
	 */
	@Override
	public void exit() throws IOException {

		if(myCompressor!=null)
			myCompressor.close();
		if(myDecompressor!=null)
			myDecompressor.close();
	}

	/* (non-Javadoc)
	 * @see model.Model#loadModel(java.lang.String, java.lang.String)
	 */
	@Override
	public void loadModel(String fileName, String name) throws IOException, FileNotFoundException{

		ArrayList<Byte> myStream = new ArrayList<Byte>();
		byte [] byteArray = new byte[1024];

		MyDecompressorInputStream myDecompressor = new MyDecompressorInputStream(new FileInputStream(fileName));
		while(myDecompressor.read(byteArray) > 0){

			for (byte b : byteArray) {
				myStream.add(b);
			}
		}
		myDecompressor.close();
		byte[] data = new byte[myStream.size()];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) myStream.get(i);
		}

		Maze3d myMaze = new Maze3d(data);
		nameToMaze.put(name, myMaze);
	}

	/* (non-Javadoc)
	 * @see model.Model#CrossSectionBy(java.lang.String, java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  Searchable<Position> CrossSectionBy(String name, String dimention, int section) {

		Maze3d maze3d=nameToMaze.get(name);

		if(maze3d == null){
			return null;
		}
		try{
			Maze2d maze2d = null;

			switch (dimention) {

			case "z":
				if(section>0&&section>maze3d.getHeight())
					maze2d= new Maze2d(maze3d.getCrossSectionByZ(section));
				break;

			case "x":
				if(section > 0 && section < maze3d.getLength())
					maze2d= new Maze2d(maze3d.getCrossSectionByX(section));

				break;

			case "y":
				if(section>0&&section>maze3d.getWidth())
					maze2d= new Maze2d(maze3d.getCrossSectionByY(section));

				break;

			default:
				return null;

			}

			Maze2dSearchableAdapter myMazeAdapter = new Maze2dSearchableAdapter(maze2d);
			return myMazeAdapter;

		}catch (ArrayIndexOutOfBoundsException | NullPointerException a){
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getNameToModel(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Searchable<Position> getNameToModel(String name) {

		Maze3d maze = nameToMaze.get(name);
		if(maze != null){
			Maze3dSearchableAdapter myMaze = new Maze3dSearchableAdapter(maze);
			return  myMaze;
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see model.Model#generateModel(java.lang.String, java.lang.String[])
	 */
	@Override
	public void generateModel(String name, String [] params) {

		int z = Integer.parseInt(params[0]) ;
		int x = Integer.parseInt(params[1]) ;
		int y = Integer.parseInt(params[2]) ;

		DfsMaze3dGenerator myGenerator = new DfsMaze3dGenerator();
		Maze3dSearchableAdapter myAdapter = new Maze3dSearchableAdapter(myGenerator.generate(z, x, y));
		this.nameToMaze.put(name, myAdapter.getMaze());
	}

	/**
	 * Gets the name to solution.
	 *
	 * @return the name to solution
	 */
	public HashMap<String, Solution<Position>> getNameToSolution() {
		return nameToSolution;
	}
	
	

}
