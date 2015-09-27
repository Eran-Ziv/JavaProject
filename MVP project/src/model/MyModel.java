package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithm.generic.Solution;
import algorithm.generic.State;
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
import generic.Constant;
import generic.Preferences;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;


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

	private HashMap<Maze3d, Solution<Position>> mazeToSolution;

	/** The my compressor. */
	MyCompressorOutputStream myCompressor;

	/** The my decompressor. */
	MyDecompressorInputStream myDecompressor;



	private Preferences preferences;


	private ListeningExecutorService executor;

	/**
	 * Instantiates a new my model.
	 */
	public MyModel(Preferences preferences) {

		this.nameToMaze = new HashMap<String, Maze3d>();
		this.nameToFileName = new HashMap<String, String>();
		this.nameToSolution = new HashMap<String, Solution<Position>>();
		this.mazeToSolution = new HashMap<Maze3d, Solution<Position>>();
		this.myCompressor = null;
		this.myDecompressor = null;
		loadSolution();
		this.preferences = preferences;
		executor=MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(preferences.poolSize));
	}


	public void setNameToSolution(HashMap<String, Solution<Position>> nameToSolution) {
		this.nameToSolution = nameToSolution;
	}



	public HashMap<String, Maze3d> getNameToMaze() {
		return nameToMaze;
	}


	public void setNameToMaze(HashMap<String, Maze3d> nameToMaze) {
		this.nameToMaze = nameToMaze;
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
			setChanged();
			notifyObservers(Constant.FILE_NOT_FOUND);

		} catch (IOException e) {

			setChanged();
			notifyObservers(Constant.NO_MODEL_FOUND);

		} finally {
			try {
				myCompressor.close();
				setChanged();
				notifyObservers("Save "+fileName);
			} catch (IOException e) {
				setChanged();
				notifyObservers(Constant.ERROR_CLOSING_FILE);
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

		ListenableFuture<Solution<Position>> futureSolution = null;


		Maze3d myMaze = nameToMaze.get(name);

		if(myMaze != null){
			Solution<Position> solution = new Solution<Position>();

			if((solution = mazeToSolution.get(myMaze)) != null){
				nameToSolution.put(name, solution);
				setChanged();
				notifyObservers(name +" solved");
			}

			else if(algorithm.toLowerCase().equals("bfs")){
				Maze3dSearchableAdapter myAdapter = new Maze3dSearchableAdapter(myMaze);

				futureSolution=executor.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						Bfs <Position> myBfs = new Bfs<Position>();

						return myBfs.search(myAdapter);
					}
				});
			}

			else if(algorithm.toLowerCase().equals("astar")){

				Maze3dSearchableAdapter myAdapter = new Maze3dSearchableAdapter(myMaze);
				Heuristic<Position> myHeuristic;

				if(heuristic.toLowerCase().equals("manhattan")){
					myHeuristic = new MazeManhattanDistance();
				}
				else{
					myHeuristic = new MazeEuclideanDistance();
				}

				futureSolution=executor.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						Astar<Position> myAstar = new Astar<Position>(myHeuristic);
						return myAstar.search(myAdapter);
					}

				});


			}
		}

		if(futureSolution!=null){

			Futures.addCallback(futureSolution, new FutureCallback<Solution<Position>>() {

				@Override
				public void onFailure(Throwable arg0) {
					setChanged();
					notifyObservers(Constant.MODEL_ERROR);
				}


				@Override
				public void onSuccess(Solution<Position> arg0) {
					mazeToSolution.put(myMaze,arg0);
					nameToSolution.put(name, arg0);
					setChanged();
					notifyObservers(name +" solved");
				}		
			});
		}
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
		setChanged();
		notifyObservers(Constant.MODEL_LOADED);
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



	@Override
	public void generateModel(String name, String [] params) {

		if(this.preferences==null)
		{
			setChanged();
			notifyObservers(Constant.PROPERTIES_ARE_NO_SET);
			return;
		}

		ListenableFuture<Maze3d> futureMaze=null;

		futureMaze = executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {

				int z = Integer.parseInt(params[0]) ;
				int x = Integer.parseInt(params[1]) ;
				int y = Integer.parseInt(params[2]) ;

				DfsMaze3dGenerator myGenerator = new DfsMaze3dGenerator();
				Maze3dSearchableAdapter myAdapter = new Maze3dSearchableAdapter(myGenerator.generate(z, x, y));
				nameToMaze.put(name,myAdapter.getMaze());
				return myAdapter.getMaze();
			}	

		});

		if(futureMaze!=null)
		{			
			Futures.addCallback(futureMaze, new FutureCallback<Maze3d>() {

				@Override
				public void onFailure(Throwable arg0) {
					setChanged();
					notifyObservers(Constant.MODEL_ERROR);
				}

				@Override
				public void onSuccess(Maze3d arg0) {
					//mazeQueue.add(arg0);
					nameToMaze.put(name, arg0);
					setChanged();
					notifyObservers(name+ " generated");
				}

			});
		}
	}

	/**
	 * Gets the name to solution.
	 *
	 * @return the name to solution
	 */
	public HashMap<String, Solution<Position>> getNameToSolution() {
		return nameToSolution;
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

		saveSolution();
		executor.shutdownNow();
		setChanged();
		notifyObservers(Constant.MODEL_EXIT);
	}


	@SuppressWarnings("unchecked")
	@Override
	public State<Position> getGoalState(String name) {

		Maze3d maze = null;

		if((maze = nameToMaze.get(name)) != null){

			State<Position> state = new State<Position>(maze.getGoalPosition());
			return state;
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public State<Position> getStartState(String name) {
		Maze3d maze = null;

		if((maze = nameToMaze.get(name)) != null){

			State<Position> state = new State<Position>(maze.getStartPosition());
			return state;
		}
		return null;
	}

	private void saveSolution() throws FileNotFoundException, IOException{
		try {
			FileOutputStream fos=new FileOutputStream(Constant.FILE_PATH);
			GZIPOutputStream gzos=new GZIPOutputStream(fos);
			ObjectOutputStream out=new ObjectOutputStream(gzos);
			out.writeObject(mazeToSolution);
			out.flush();
			out.close();
		}
		catch (IOException e) {
			e.getStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void loadSolution() {

		try {
			FileInputStream fos=new FileInputStream(Constant.FILE_PATH);
			GZIPInputStream gzos=new GZIPInputStream(fos);
			ObjectInputStream out=new ObjectInputStream(gzos);
			mazeToSolution = (HashMap<Maze3d, Solution<Position>>) out.readObject();
			out.close();
		}
		catch (  IOException e) {
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}


