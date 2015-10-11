package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.ServerProperties;


public class UDPMazeServerRemoteControl extends Observable implements Observer,Runnable{
	
	MazeClientHandler handler;
	
	InetAddress senderIP;
	
	int senderPort;

	DatagramSocket serverSocket;
	
	MazeServer clientsServer;
	
	ServerProperties properties;
	
	ExecutorService executor=Executors.newSingleThreadExecutor();
	public UDPMazeServerRemoteControl(ServerProperties properties) {
		this.properties=properties;
	}
	
	@Override
	public void run() {
		try {
			serverSocket=new DatagramSocket(properties.getPort());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		waitForStartSignal();

		initiateClientsServer();
		
		handleClientsServer();
		
	}
	
	private void waitForStartSignal()
	{
		
		byte[] receiveData=new byte[1024];
		String input=null;
		if(clientsServer==null)
		{
			do{
				DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
				try {
					serverSocket.receive(receivePacket);
					input=new String(receivePacket.getData());
					senderIP=receivePacket.getAddress();
					senderPort=receivePacket.getPort();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}while(!input.startsWith("start server"));
		}
	}
	
	private void initiateClientsServer()
	{
		byte[] receiveData=new byte[1024];
		String buffer=null,input="";
		DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
		try {
			serverSocket.receive(receivePacket);
			buffer=new String(receivePacket.getData());//expect it to be: String= numOfClients+" "+PortToServeClients
			
			for(int i=0;i<buffer.length();i++)
				if(Character.isDigit(buffer.charAt(i)) || buffer.charAt(i)==',')
						input+=buffer.charAt(i);
				else
					break;
			
			ServerProperties clientsServerProperties=new ServerProperties(Integer.parseInt(input.split(",")[1]),Integer.parseInt(input.split(",")[0]));
			handler=new MazeClientHandler(this);
			handler.addObserver(this);
			this.addObserver(handler);
			clientsServer=new MazeServer(clientsServerProperties,handler);
			handler.setServer(clientsServer);
			//executor.execute(clientsServer);
			new Thread(clientsServer).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private void handleClientsServer()
	{
		byte[] receiveData=new byte[1024];
		String input=null;
		do{
			DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
			try {
				serverSocket.receive(receivePacket);
				input=new String(receivePacket.getData());
				senderIP=receivePacket.getAddress();
				senderPort=receivePacket.getPort();
				if(input.startsWith("exit"))
					return;
				else if(input.contains("disconnect") && !input.startsWith("stop"))
				{
					String buffer=input;
					input="";
					for(int i=0;i<buffer.length();i++)
						if(Character.isDigit(buffer.charAt(i)) || buffer.charAt(i)==',' || buffer.charAt(i)=='.' || Character.isLetter(buffer.charAt(i)))
								input+=buffer.charAt(i);
						else
							break;
					setChanged();
					notifyObservers(input);
				}
				else if(input.contains("stop"))
				{
					clientsServer.stoppedServer();
					clientsServer=null;
					executor.shutdownNow();
					return;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			 * String to get bytes to sendData
			 * DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,senderIP,senderPort);
			 * serverSocket.send(sendPacket);
			 */
		}while(!input.startsWith("stop server"));
		clientsServer.stoppedServer();
		clientsServer=null;
		executor.shutdownNow();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o==handler)
		{
			String allMessages="";
			for(String message: handler.messages)
			{
				allMessages+="\n"+ message;
			}
			DatagramPacket sendPacket=new DatagramPacket(allMessages.getBytes(),allMessages.length(),senderIP,senderPort);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
