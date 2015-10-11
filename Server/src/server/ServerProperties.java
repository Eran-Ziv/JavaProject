package server;

import java.io.Serializable;

public class ServerProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int port;
	private int numOfClients;
	/**	Default Properties.
	 * 
	 */
	public ServerProperties() {
		port=5400;
		numOfClients=32;
	}
	public ServerProperties(int port,int numOfClients) {
		this.port=port;
		this.numOfClients=numOfClients;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getNumOfClients() {
		return numOfClients;
	}
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
}
