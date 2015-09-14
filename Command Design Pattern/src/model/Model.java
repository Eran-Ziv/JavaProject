package model;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import algorithms.mazeGenerators.Searchable;
import algorithms.search.Searcher;


public interface Model {

	
	public int getModelSizeInMemory(String name) throws IOException ;
	public long getModelSizeInFile(String name);
	public ArrayList<Closeable> getThreads();
	
	public void saveModel(String name, String fileName);
	public void addThreads(Closeable close);
	public void loadModel(String fileName, String name) throws IOException, FileNotFoundException;
	public void solveModel(String name, String algorithm, String heuristic);
	public  <T> void generateModel(String name, String[] params);
	
	public <T> algorithm.generic.Solution<T> getSolution(String name);
	public <T> Searchable<T>  getNameToModel(String name);
	public <T> Searchable<T> CrossSectionBy(String name,String dimention , int section );	
	
	public void exit();
	
}
