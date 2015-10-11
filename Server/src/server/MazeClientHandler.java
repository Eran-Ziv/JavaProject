package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.GZIPOutputStream;

import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;


public class MazeClientHandler extends Observable implements ClientHandler,Observer  {

	
	MazeServer server;
	
	volatile ConcurrentHashMap<String,Socket> activeConnections=new ConcurrentHashMap<String,Socket>();
	
	
	volatile ConcurrentLinkedQueue<String> messages=new ConcurrentLinkedQueue<String>();
	
	UDPMazeServerRemoteControl remote;
	
	public MazeClientHandler(MazeServer server) {
		this.server=server;
	}
	public MazeClientHandler(UDPMazeServerRemoteControl remote) {
		this.remote=remote;
	}
	
	/** The handleClient Method! it notifies what it's doing to the client throughout it's operation.
	 * 	it gets the command from the client, additional arguments and parameters. does as requested and sends.
	 */
	@Override
	public void handleClient(Socket client)
	{
		String clientIP=client.getInetAddress().getHostAddress();
		int clientPort=client.getPort();
		activeConnections.put(clientIP+","+clientPort, client);
		String message=new String(clientIP +","+ clientPort+",connected");
		messages.add(message);
		setChanged();
		notifyObservers();//check messages
		messages.remove(message);
		try {
			BufferedReader readerFromClient=new BufferedReader(new InputStreamReader(client.getInputStream()));
			String command=readerFromClient.readLine();
			ObjectOutputStream outputCompressedToClient=new ObjectOutputStream(new GZIPOutputStream(client.getOutputStream()));
			outputCompressedToClient.flush();
			if(command.contains("generate maze"))
			{
				String generator=readerFromClient.readLine();
				String arg=readerFromClient.readLine();
				String[] params=parseGenerateMazeArgument(arg);
				message=clientIP+ ","+clientPort+",generating maze";
				messages.add(message);
				setChanged();
				notifyObservers();
				outputCompressedToClient.writeObject(generateMaze(generator,params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5]),Integer.parseInt(params[6])));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				//outputToClient.writeObject(generateMaze(clientIP,clientPort,params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5]),Integer.parseInt(params[6]),"generating maze"));
				messages.remove(message);
				//outputToClient.flush();
				

			}
			else if(command.contains("solve maze"))
			{
				String solverProperties=readerFromClient.readLine();
				String arg=readerFromClient.readLine();
				String[] params=parseSolveMazeArgument(arg);
				String[] properties=parseProperties(solverProperties);
				message=clientIP+ ","+clientPort+",solving maze";
				messages.add(message);
				setChanged();
				notifyObservers();
				outputCompressedToClient.writeObject(solveMaze(properties[0],properties[1],Double.parseDouble(properties[2]),Double.parseDouble(properties[3]),params[0]));
				outputCompressedToClient.flush();
			
				//outputToClient.writeObject(solveMaze(clientIP,clientPort,params[0],"solving maze"));
				messages.remove(message);
				//outputToClient.flush();
				


			}
			
			else if(command.contains("maze exists"))
			{
				readerFromClient.readLine();//property doesn't matter
				String mazeName=readerFromClient.readLine();
				message=clientIP+ ","+clientPort+",checking maze existance";
				messages.add(message);
				setChanged();
				notifyObservers();
				if(server.generatedMazes.containsKey(mazeName))
					outputCompressedToClient.writeObject(server.generatedMazes.get(mazeName));
				else
					outputCompressedToClient.writeObject(null);
				outputCompressedToClient.flush();
				//outputToClient.writeObject(calculateHint(clientIP,clientPort,params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),"calculating hint"));
				messages.remove(message);
			}
			outputCompressedToClient.close();
			readerFromClient.close();

			client.close();
			
		} catch (Exception e1) {
			
		}
		
		activeConnections.remove(clientIP+","+clientPort);
		String last=new String(clientIP +","+ clientPort+",disconnected");
		messages.add(last);
		setChanged();
		notifyObservers();
		messages.remove(last);

	}
	
	public ConcurrentLinkedQueue<String> getMessages() {
		return messages;
	}
	
	private String[] parseProperties(String properties)
	{		
		return properties.split(" ");
	}
	
	
	private String[] parseSolveMazeArgument(Object arg) {
		String[] result=new String[1];
		result[0]=(String)arg;
		return result;
	}

	
	private String[] parseGenerateMazeArgument(Object arg) {
		String params=(String)arg;
		String[] values=params.split(" ");
		String[] additionalParams=values[1].split(",");
		String[] result=new String[1+additionalParams.length];
		result[0]=values[0];
		for(int i=1;i<additionalParams.length+1;i++)
			result[i]=additionalParams[i-1];
		return result;
	}


	
	public Maze3d generateMaze(String generator,String name,int rows, int cols, int rowSource, int colSource,
			int rowGoal, int colGoal) {
				return null;
		
		
		
	}
	
	
	public Solution<Position> solveMaze(String solver,String move,double moveCost,double moveDiagonalCost,String mazeName) {
		
		Maze3d m=server.generatedMazes.get(mazeName);
		if(m==null)
		{
			return null;
		}
		if(server.cache.containsKey(m))
		{
			ArrayList<State<Position>> Arraysolution = server.cache.get(m).getSolution();
			Solution<Position> solution = new Solution<Position>();
			solution.setSolution(Arraysolution);
			
			return solution;
		}
		Solution<Position> solution=null;
		
		
		switch(solver)
		{
		case "BFS":
			
			break;
		case "AIR_DISTANCE_ASTAR":
			
			break;
		case "MANHATTAN_DISTANCE_ASTAR":
			
			break;
			
		default:
			break;
		}
		server.cache.put(m, solution);
		return solution;
		
	}
	
	


	public MazeServer getServer() {
		return server;
	}


	public void setServer(MazeServer server) {
		this.server = server;
	}
	/** upon a disconnection request client will be sent a nice message and the server will await for his ack. then it will disconnect it.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o==remote)
			if(arg.toString().contains("disconnect"))
			{
				Socket clientToDisconnect=activeConnections.get(arg.toString().substring(0, arg.toString().length()-"disconnect".length()-1));
			
				try{
					clientToDisconnect.close();
				}catch(Exception e)
				{
					
				}

			}
	}
}
