package view;

import java.util.HashMap;

import algorithm.generic.Solution;
import controller.Command;


/**
 * The Interface View.
 *  @author Eran & Ziv
 */
public interface View {

	/**
	 * Dir command.
	 *
	 * @param fileName the file name
	 */
	public void dirCommand(String  fileName);
	
	/**
	 * Display model.
	 *
	 * @param <T> the generic type
	 * @param draw the draw
	 */
	public <T> void displayModel(Drawable<T> draw);
	
	/**
	 * Display cross section by.
	 *
	 * @param <T> the generic type
	 * @param draw the draw
	 */
	public <T> void displayCrossSectionBy(Drawable<T> draw);
	
	/**
	 * Display solution.
	 *
	 * @param <T> the generic type
	 * @param solution the solution
	 */
	public <T> void displaySolution(Solution<T> solution);
	
	/**
	 * Sets the commands.
	 *
	 * @param commands the commands
	 */
	public void setCommands(HashMap<String, Command>commands);
	
	/**
	 * Display string.
	 *
	 * @param toPrint the to print
	 */
	public void displayString(String toPrint);
	
	/**
	 * Start.
	 */
	public void start();
	
}
