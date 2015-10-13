package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import presenter.RemoteControlCommand;



/**
 * The Class CLI.
 */
public class CLI implements Runnable {

	boolean runing = true;
	
	/** The out. */
	private PrintWriter out;

	/** The in. */
	private BufferedReader in;

	private HashMap<String,RemoteControlCommand> commands;

	RemoteControlCommand userCommand;

	View view;

	/**
	 * Instantiates a new cli.
	 *
	 * @param out the out
	 * @param in the in
	 */
	public CLI(PrintWriter out, BufferedReader in, View view) {

		this.out = out;
		this.in = in;
		this.view = view;
	}

	/**
	 * Gets the out.
	 *
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}

	/**
	 * Sets the out.
	 *
	 * @param out the new out
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}

	/**
	 * Gets the in.
	 *
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * Sets the in.
	 *
	 * @param in the new in
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public void setCommands(HashMap<String, RemoteControlCommand> commands) {

		this.commands = commands; 
	}


	public RemoteControlCommand getUserCommand() {
		return userCommand;
	}

	public void setUserCommand(RemoteControlCommand userCommand) {
		this.userCommand = userCommand;
	}

	/**
	 * Prints the commands.
	 */
	public void printCommands(){

		out.flush();

		out.println();
		//TODO generate print
	}




	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run()  {
		out.flush();
		String line=null;
		String [] args=null;
		
		printCommands();
		

		try {	
			while (runing){
				out.println("Enter command: ");
				out.flush();
				
				line= in.readLine();
				args= line.split(" ");
				if(commands.containsKey(args[0])){

					userCommand= commands.get(args[0]);		
					userCommand.setArguments(line);
					view.setUserCommand(userCommand);

				}
				else{
					out.println("Invalid parameters, retry command.");
					out.println();
					out.flush();
				}
			}
		} catch (IOException e) {
			out.println("Oops something wrong happend");
		}
		
		out.println("Good bye.");

	}

	public boolean isRuning() {
		return runing;
	}

	public void setRuning(boolean runing) {
		this.runing = runing;
	}
}