package model;

import java.io.Closeable;
import java.io.FileInputStream;
import java.util.ArrayList;

import algorithms.search.Solution;

public interface Model {

	
	public byte[] generateModel(String name, int z, int x, int y);
	public void saveModel(String name, String fileName);
	public int getModelSizeInMemory(String name);
	public long getModelSizeInFile(String name);
	public byte[] loadModel(String fileName, String name);
	public <T> Solution<T> solveModel(String name, String algorithm, String heuristic);
	public byte[]  getNameToModel(String name);
	public byte[] CrossSectionBy(String name,char dimention , int section );
	public void addThreads(Closeable c);
	public void addFiles(FileInputStream file);
	public ArrayList<Closeable> getThreads();
	public void exit();
	
}
