package model;

import algorithms.search.Solution;

public interface Model<T> {

	
	byte[] generateModel(String name, int z, int x, int y);
	void saveModel(String name, String fileName);
	int getModelSizeInMemory(String name);
	long getModelSizeInFile(String name);
	byte[] loadModel(String fileName, String name);
	Solution<T> solveModel(String name, String algorithm, String heuristic);
	
}
