package view;

import java.util.HashMap;

import algorithm.generic.Solution;
import presenter.Command;


public interface View {

	
	public void dirCommand(String  fileName);
	
	
	public <T> void displayModel(Drawable<T> draw);
	
	
	public <T> void displayCrossSectionBy(Drawable<T> draw);
	
	
	public <T> void displaySolution(Solution<T> solution);
	
	
	public void setCommands(HashMap<String, Command>commands);
	
	
	public void displayString(String toPrint);
	
	
	public Command getUserCommand();
	
	public void setUserCommand(Command command);
	
	public void start();
	
	public void exit();
	
}
