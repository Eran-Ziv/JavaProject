package presenter;

import java.io.Serializable;

public class RemoteControlProperties implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private int PortForServerClients;
	private int portOnWhichServerListens;
	
	private int numOfClients;
	private int RemoteControlPortListener;
	
	private String IP;
	
	public int getPortOnWhichServerListens() {
		return portOnWhichServerListens;
	}
	
	public void setPortOnWhichServerListens(int portOnWhichServerListens) {
		this.portOnWhichServerListens = portOnWhichServerListens;
	}
	
	
	public RemoteControlProperties() {
		PortForServerClients=5400;
		numOfClients=32;
		RemoteControlPortListener = 1234;
	}
	public RemoteControlProperties(int port,int numOfClients,int RemoteControlPortListener) {
		this.PortForServerClients=port;
		this.numOfClients=numOfClients;
		this.RemoteControlPortListener=RemoteControlPortListener;
	}
	
	public int getRemoteControlPortListener() {
		return RemoteControlPortListener;
	}
	
	public void setRemoteControlPortListener(int RemoteControlPortListener) {
		this.RemoteControlPortListener = RemoteControlPortListener;
	}
	
	public int getPortServerClients() {
		return PortForServerClients;
	}
	
	public void setPortServerClients(int port) {
		this.PortForServerClients = port;
	}
	
	public int getNumOfClients() {
		return numOfClients;
	}
	
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}
}
