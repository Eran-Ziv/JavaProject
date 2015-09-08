package view;

import java.util.HashMap;

import controller.Command;
import algorithms.search.Solution;

public interface View {

	public void dirCommand(String  fileName);
	public void displayModel(byte [] byteArray);
	public void displayCrossSectionBy(byte [] byteArray, char axis, int section);
	public <T> void displaySolution(Solution<T> solution);
	public void setCommands(HashMap<String, Command>commands);
	public void displayError(String error);
	
}
