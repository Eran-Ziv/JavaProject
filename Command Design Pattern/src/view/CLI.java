package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import controller.Command;

// TODO: Auto-generated Javadoc
/**
 * The Class CLI.
 */
public class CLI extends Thread {

	/** The out. */
	private PrintWriter out;
	
	/** The in. */
	private BufferedReader in;
	
	/** The commands. */
	private HashMap<String,Command> commands;

	/**
	 * Instantiates a new cli.
	 *
	 * @param out the out
	 * @param in the in
	 */
	public CLI(PrintWriter out, BufferedReader in) {

		this.out = out;
		this.in = in;
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

	/**
	 * Gets the commands.
	 *
	 * @return the commands
	 */
	public HashMap<String, Command> getCommands() {
		return commands;
	}

	/**
	 * Prints the commands.
	 */
	public void printCommands(){

		out.flush();

		out.println("Display maze by axis...............display cross section by <axis> <index> for <name>");
		out.println("Solve the maze.................................solve <name> <algorithm> <? heuristic>");
		out.println("Generate maze.....................................generate maze 3d <name> <z> <x> <y>");
		out.println("Save the maze to a file..................................save maze <name> <file name>");
		out.println("Load the maze from a file................................load maze <file name> <name>");
		out.println("Display the solution..........................................display solution <name>");
		out.println("Display the maze..................................................display maze <name>");
		out.println("Show maze size in file..........................................file size <file name>");
		out.println("Show maze size in memory.............................................maze size <name>");
		out.println("Show files in directory...............................................dir <file name>");
		out.println("Exit.............................................................................exit");
	}

	/**
	 * Sets the commands.
	 *
	 * @param commands the commands
	 */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run()  {
		out.flush();
		String line=null;
		Command command;
		String [] args=null;
		String [] args1={"exit","Default"};
		printCommands();
		out.println("Enter command");
		out.flush();

		try {	
			while (!(line= in.readLine()).equals("exit")){

				args= line.split(" ");
				if(commands.containsKey(args[0])){
					command= commands.get(args[0]);		
					command.doCommand(args);

					out.println("Enter command: ");
					out.flush();
				}
				else{
					out.println("Invalid parameters ");
					out.println("Retry command: ");
					out.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			command= commands.get("exit");
			command.doCommand(args1);
		}
	}
}