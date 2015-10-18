package presenter;


/**
 * The Interface RemoteControlCommand.
 *  @author Eran & Ziv
 */
public interface RemoteControlCommand {
	
	
	/**
	 * Do command.
	 */
	void doCommand();
	
	/**
	 * Sets the arguments.
	 *
	 * @param args the new arguments
	 */
	public void setArguments(String args);

}

