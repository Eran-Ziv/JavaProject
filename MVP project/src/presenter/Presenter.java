package presenter;

import java.util.HashMap;
import java.util.Observer;

/**
 * The Interface Controller.
 * This interface is responsible for the tranclation between the view {@see View} and the model {@see Model},
 * it uses the command interface {@see Command} to define the relation between the view and the model. 
 */
public interface Presenter extends Observer {

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
