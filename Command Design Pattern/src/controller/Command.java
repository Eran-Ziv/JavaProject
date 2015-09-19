package controller;


/**
 * The Interface Command.
 * This interface define the behavior of a command.
 * The command should be the tranclator between the view {@see View} and the model {@see Model},
 * the command is responsible for requesting a specific command from the model and send the output to the view.
 * Some commands should be writen in the view and will be displayed there.
 */
public interface Command {

	/**
	 * Do command.
	 * This method gets <args> parse them and makes to relevant tranclation.
	 *
	 * @param args the args
	 */
	public void doCommand(String [] args);


}
