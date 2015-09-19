package controller;

import java.util.HashMap;

/**
 * The Interface Controller.
 * This interface is responsible for the tranclation between the view {@see View} and the model {@see Model},
 * it uses the command interface {@see Command} to define the relation between the view and the model. 
 */
public interface Controller {

	/**
	 * Gets the commands.
	 *
	 * @return the commands
	 */
	HashMap<String, Command> getCommands();
	
	/**
	 * Start the contriller.
	 */
	void start();
	
	
}
