package model;

public interface Model {

	void generateModel(String [] args);
	void saveModel(String [] args);
	void getModelSizeInMemory(String [] args);
	void getModelSizeInFile(String [] args);
	void loadModel(String[] args);
	void solveModel(String[] args);
	
}
