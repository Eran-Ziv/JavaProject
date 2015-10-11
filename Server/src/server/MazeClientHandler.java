package server;



import generic.ServerConstant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.prefs.Preferences;
import java.util.zip.GZIPOutputStream;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.DfsMaze3dGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.MazeEuclideanDistance;
import algorithms.search.MazeManhattanDistance;


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
			String data;
			String[] params;
			BufferedReader readerFromClient=new BufferedReader(new InputStreamReader(client.getInputStream()));
			String command=readerFromClient.readLine();
			ObjectOutputStream outputCompressedToClient=new ObjectOutputStream(new GZIPOutputStream(client.getOutputStream()));
			outputCompressedToClient.flush();


			switch (command){

			case ServerConstant.GET_MODEL_SIZE_IN_FILE:

				data = readerFromClient.readLine();
				message=clientIP+ ","+clientPort+ "," +ServerConstant.GET_MODEL_SIZE_IN_FILE ;
				messages.add(message);
				setChanged();
				notifyObservers();
				readerFromClient.readLine();//empty

				outputCompressedToClient.writeObject(getMazeSizeInFile(data));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				messages.remove(message);

				break;

			case ServerConstant.GET_MODEL_SIZE_IN_MEMORY:

				break;

			case ServerConstant.GENERATE_MAZE:
				data=readerFromClient.readLine();
				String generator=readerFromClient.readLine();
				params=parseGenerateMazeArgument(data);
				message=clientIP+ ","+clientPort+",generating maze";
				messages.add(message);
				setChanged();
				notifyObservers();
				outputCompressedToClient.writeObject(generateMaze(params[0],generator,Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3])));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				//outputToClient.writeObject(generateMaze(clientIP,clientPort,params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5]),Integer.parseInt(params[6]),"generating maze"));
				messages.remove(message);
				//outputToClient.flush();
				break;

			case ServerConstant.SOLVE_MAZE:

				data =readerFromClient.readLine();
				String solverProperties=readerFromClient.readLine();

				params=parseSolveMazeArgument(data);

				message=clientIP+ ","+clientPort+",solving maze";
				messages.add(message);
				setChanged();
				notifyObservers();
				outputCompressedToClient.writeObject(solveMaze(params[0],solverProperties));
				outputCompressedToClient.flush();

				messages.remove(message);
				break;

			case ServerConstant.LOAD_MAZE:

				break;

			case ServerConstant.SAVE_MAZE:

				break;




			case ServerConstant.MAZE_EXISTS:

				readerFromClient.readLine();//property doesn't matter
				String mazeName=readerFromClient.readLine();
				message=clientIP+ ","+clientPort+",checking maze existance";
				messages.add(message);
				setChanged();
				notifyObservers();
				if(server.nameToMaze.containsKey(mazeName))
					outputCompressedToClient.writeObject(server.nameToMaze.get(mazeName));
				else
					outputCompressedToClient.writeObject(null);
				outputCompressedToClient.flush();
				//outputToClient.writeObject(calculateHint(clientIP,clientPort,params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),"calculating hint"));
				messages.remove(message);

				outputCompressedToClient.close();
				readerFromClient.close();
				break;
			}
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

	private long getMazeSizeInFile(String data) {
		// TODO Auto-generated method stub
		return 0;
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
		//{"generate", "maze", "dfs/random",input.getMazeName(), z, x, y};

		String [] params=(((String) arg).split(","));

		return params;
	}



	public Maze3d generateMaze(String name,String generator ,int z, int x, int y ) {

		if(server.nameToMaze.containsKey(name))
			return server.nameToMaze.get(name);

		Maze3d maze3d= null;
		switch(generator){

		case "DFS":
			maze3d= new DfsMaze3dGenerator().generate(z, x, y);
			break;
		case "RANDOM":

			maze3d= new MyMaze3dGenerator().generate(z, x, y);
			break;
		}

		server.nameToMaze.put(name, maze3d);

		return maze3d;

	}


	public Solution<Position> solveMaze(String mazeName, String solver) {

		Maze3d m=server.nameToMaze.get(mazeName);
		if(m==null)
		{
			return null;
		}
		if(server.mazeToSolution.containsKey(m))
		{
			ArrayList<State<Position>> Arraysolution = server.mazeToSolution.get(m).getSolution();
			Solution<Position> solution = new Solution<Position>();
			solution.setSolution(Arraysolution);

			return solution;
		}
		Solution<Position> solution=null;

		Maze3dSearchableAdapter maze3d= new  Maze3dSearchableAdapter(m);

		switch(solver)
		{
		case "BFS":

			solution= new Bfs<Position>().search(maze3d);

			break;
		case "AIR_DISTANCE_ASTAR":

			solution= new Astar<Position>(new MazeEuclideanDistance()).search(maze3d) ;

			break;
		case "MANHATTAN_DISTANCE_ASTAR":

			solution= new Astar<Position>(new MazeManhattanDistance()).search(maze3d) ;
			break;

		default:
			break;
		}
		server.mazeToSolution.put(m, solution);
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
				/*	
				//BufferedReader readerFromClient=new BufferedReader(new InputStreamReader(clientToDisconnect.getInputStream()));
				ObjectOutputStream objOut=activeConnectionsOutputStream.get(arg.toString().substring(0, arg.toString().length()-"disconnect".length()-1));
				try {
					objOut.writeObject("disconnect");
					objOut.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//readerFromClient.readLine(); //Client answers and acks.
				 */
				try{
					clientToDisconnect.close();
				}catch(Exception e)
				{

				}

			}
	}
}