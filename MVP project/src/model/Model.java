package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Searchable;

// TODO: Auto-generated Javadoc
/**
 * The Interface Model.
 */
public interface Model {

	/**
	 * Gets the model size in memory.
	 *
	 * @param name the name
	 * @return the model size in memory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public int getModelSizeInMemory(String name) throws IOException ;
	
	/**
	 * Gets the model size in file.
	 *
	 * @param name the name
	 * @return the model size in file
	 */
	public long getModelSizeInFile(String name);
	
	/**
	 * Save model.
	 *
	 * @param name the name
	 * @param fileName the file name
	 */
	public void saveModel(String name, String fileName);
	
	/**
	 * Load model.
	 *
	 * @param fileName the file name
	 * @param name the name
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException the file not found exception
	 */
	public void loadModel(String fileName, String name) throws IOException, FileNotFoundException;
	
	/**
	 * Solve model.
	 *
	 * @param name the name
	 * @param algorithm the algorithm
	 * @param heuristic the heuristic
	 */
	public void solveModel(String name, String algorithm, String heuristic);
	
	/**
	 * Generate model.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @param params the params
	 */
	public  void generateModel(String name, String[] params);
	
	/**
	 * Gets the solution.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @return the solution
	 */
	public <T> Solution<T> getSolution(String name);
	
	/**
	 * Gets the name to model.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @return the name to model
	 */
	public <T> Searchable<T>  getNameToModel(String name);
	
	/**
	 * Cross section by.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @param dimention the dimention
	 * @param section the section
	 * @return the searchable
	 */
	public <T> Searchable<T> CrossSectionBy(String name,String dimention , int section );	
	
	/**
	 * Exit.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void exit()  throws IOException;
	
	public HashMap<String, Maze3d> getNameToMaze() ;
	
	public <T> State<T> getGoalState(String name);
	public <T> State<T> getStartState(String name);

}
