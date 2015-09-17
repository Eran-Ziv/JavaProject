package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


import controller.Command;

public class CLI extends Thread {

	private PrintWriter out;
	private BufferedReader in;
	private HashMap<String,Command> commands;

	public CLI(PrintWriter out, BufferedReader in) {

		this.out = out;
		this.in = in;
		
	}


	public PrintWriter getOut() {
		return out;
	}


	public void setOut(PrintWriter out) {
		this.out = out;
	}


	public BufferedReader getIn() {
		return in;
	}


	public void setIn(BufferedReader in) {
		this.in = in;
	}



	 public HashMap<String, Command> getCommands() {
		return commands;
	}


	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}


	public void run()  {
		out.flush();
		String line=null;
		Command command;
		String [] args=null;
		String [] args1={"exit","Default"};
		out.println("Insert command");
		out.flush();
		
		
		try {
			
			while (!(line= in.readLine()).equals("exit")){
				
				args= line.split(" ");
				if(commands.containsKey(args[0])){
					command= commands.get(args[0]);
					
					command.doCommand(args);
					
					out.println("Enter command:");
					out.flush();
				}
				else{
					
					out.print("invalid args ");
					out.println("re-enter command:");
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
