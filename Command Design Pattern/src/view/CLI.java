package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import controller.Command;

public class CLI extends Thread {

	private PrintWriter out;
	private BufferedReader in;
	private HashMap<String,Command> commands;

	public CLI(PrintWriter out, BufferedReader in, HashMap<String,Command> commands) {

		this.out = out;
		this.in = in;
		this.commands= commands;
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



	synchronized public void run()  {
		String line;
		Command command;
		try {
			while ((line= in.readLine())!="exit"){
				if(commands.containsKey(line)/*to do*/){
					command= commands.get(line);
					this.runInThread(command);
				}
				else{
					out.write("Invalid command");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void runInThread (Command c) {
		new Thread(new Runnable() {
			public void run() {
				c.doCommand();
			}
		}).start();
	}



}
