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
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.DfsMaze3dGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.MazeEuclideanDistance;
import algorithms.search.MazeManhattanDistance;
import generic.ServerConstant;


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

				readerFromClient.readLine();//empty
				data = readerFromClient.readLine();
				message=clientIP+ ","+clientPort+ "," +ServerConstant.GET_MODEL_SIZE_IN_FILE ;
				messages.add(message);
				setChanged();
				notifyObservers();
				
				outputCompressedToClient.writeObject(getMazeSizeInFile(data));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				messages.remove(message);

				break;

			case ServerConstant.GET_MODEL_SIZE_IN_MEMORY:

				readerFromClient.readLine();//empty
				data = readerFromClient.readLine();
				message=clientIP+ ","+clientPort+ "," +ServerConstant.GET_MODEL_SIZE_IN_MEMORY ;
				messages.add(message);
				setChanged();
				notifyObservers();
				
				outputCompressedToClient.writeObject(getMazeSizeInMemory(data));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				messages.remove(message);
				break;

			case ServerConstant.GENERATE_MAZE:
				
				String generator=readerFromClient.readLine();
				data=readerFromClient.readLine();
				params=parseGenerateMazeArgument(data);
				message=clientIP+ ","+clientPort+",generating maze";
				messages.add(message);
				setChanged();
				notifyObservers();
				
				outputCompressedToClient.writeObject(generateMaze(params[0], params[1], params[2], params[3], generator));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				messages.remove(message);
				
				break;

			case ServerConstant.SOLVE_MAZE:
				
				String solverProperties=readerFromClient.readLine();
				data =readerFromClient.readLine();
				message=clientIP+ ","+clientPort+",solving maze";
				messages.add(message);
				setChanged();
				notifyObservers();
				
				outputCompressedToClient.writeObject(solveMaze(data, solverProperties));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				messages.remove(message);
				break;

			case ServerConstant.LOAD_MAZE:
				
				readerFromClient.readLine();//empty
				data = readerFromClient.readLine();
				message=clientIP+ ","+clientPort+ "," +ServerConstant.LOAD_MAZE;
				messages.add(message);
				setChanged();
				notifyObservers();
				params = ParseLoadMaze(data);
				
				outputCompressedToClient.writeObject(loadMaze(params[0], params[1]));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				messages.remove(message);

				break;

			case ServerConstant.SAVE_MAZE:
				
				readerFromClient.readLine();//empty
				data = readerFromClient.readLine();
				message=clientIP+ ","+clientPort+ "," +ServerConstant.SAVE_MAZE;
				messages.add(message);
				setChanged();
				notifyObservers();
				params = ParseSaveMaze(data);
				
				outputCompressedToClient.writeObject(SaveMaze(params[0], params[1]));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				messages.remove(message);

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
				messages.remove(message);

				outputCompressedToClient.close();
				readerFromClient.close();
				break;
				
			case ServerConstant.GET_CROSS_SECTION:
				
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

	private Object SaveMaze(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}
	private String[] ParseSaveMaze(String data) {
		// TODO Auto-generated method stub
		return null;
	}
	private Maze3d loadMaze(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}
	private String[] ParseLoadMaze(String data) {
		// TODO Auto-generated method stub
		return null;
	}
	private Object getMazeSizeInMemory(String data) {
		// TODO Auto-generated method stub
		return null;
	}
	private long getMazeSizeInFile(String data) {
		// TODO Auto-generated method stub
		return 0;
	}
	public ConcurrentLinkedQueue<String> getMessages() {
		return messages;
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



	public Maze3d generateMaze(String name, String x, String y, String z, String generator ) {

		if(server.nameToMaze.containsKey(name))
			return server.nameToMaze.get(name);

		Maze3d maze3d= null;
		try{
		switch(generator){

		case "DFS":
			maze3d= new DfsMaze3dGenerator().generate(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
			break;
		case "RANDOM":

			maze3d= new MyMaze3dGenerator().generate(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
			break;
		}

		server.nameToMaze.put(name, maze3d);

		return maze3d;
		}catch(NumberFormatException n){
			
		}
		return null;

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