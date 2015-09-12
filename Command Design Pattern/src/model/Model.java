package model;

import java.io.Closeable;
import java.util.ArrayList;

import algorithms.mazeGenerators.Searchable;
import algorithms.search.Searcher;


public interface Model {

	
	public int getModelSizeInMemory(String name);
	public long getModelSizeInFile(String name);
	public ArrayList<Closeable> getThreads();
	
	public void saveModel(String name, String fileName);
	public void addThreads(Closeable close);
	public void loadModel(String fileName, String name);
	public void solveModel(String name, String algorithm, String heuristic);
	public  <T> void generateModel(String name, String[] params) throws Exception;
	
	public <T> algorithm.generic.Solution<T> getSolution(String name);
	public <T> Searchable<T>  getNameToModel(String name);
	public <T> Searchable<T> CrossSectionBy(String name,String dimention , int section );	
	
	public void exit();
	
}
