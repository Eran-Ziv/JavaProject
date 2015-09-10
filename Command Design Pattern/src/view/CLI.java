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
		String line;
		Command command;
		String [] args;
		System.out.println("Insert command");
		try {
			while ((line= in.readLine())!="exit"){
				System.out.println("aaaaaa");
				args= line.split(" ");
				if(commands.containsKey(args[0])/*to do*/){
					command= commands.get(args[0]);
					
					command.doCommand(args);
					System.out.println("Enter command:");
				}
				else{
					System.out.println("invalid args");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	



}
