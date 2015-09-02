package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI  implements Runnable{
	
	private PrintWriter out;
	private BufferedReader in;
	
	
	
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


	@Override
	synchronized public void run() {
		
		Scanner in = new Scanner(System.in);
		// TODO Auto-generated method stub
		
	}
	
	

}
