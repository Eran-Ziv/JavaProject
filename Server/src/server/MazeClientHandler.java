package server;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.demo.Maze2dSearchableAdapter;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.DfsMaze3dGenerator;
import algorithms.mazeGenerators.Maze2d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.MazeEuclideanDistance;
import algorithms.search.MazeManhattanDistance;
import generic.Constant;
import generic.ServerConstant;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;



/**
 * The Class MazeClientHandler. A spesific implementation of a client handler.
 */
public class MazeClientHandler extends Observable implements ClientHandler,Observer  {


	/** The server. */
	MazeServer server;

	/** The active connections. */
	volatile ConcurrentHashMap<String,Socket> activeConnections=new ConcurrentHashMap<String,Socket>();


	/** The messages. */
	volatile ConcurrentLinkedQueue<String> messages=new ConcurrentLinkedQueue<String>();

	/** The remote. */
	UDPMazeServerRemoteControl remote;

	/**
	 * Instantiates a new maze client handler.
	 *
	 * @param server the server
	 */
	public MazeClientHandler(MazeServer server) {
		this.server=server;
	}
	
	/**
	 * Instantiates a new maze client handler.
	 *
	 * @param remote the remote
	 */
	public MazeClientHandler(UDPMazeServerRemoteControl remote) {
		this.remote=remote;
	}

	/**
	 *  The handleClient Method! it notifies what it's doing to the client throughout it's operation.
	 * 	it gets the command from the client, additional arguments and parameters. does as requested and sends.
	 *
	 * @param client the client
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
			ObjectOutputStream outputCompressedToClient=new ObjectOutputStream( client.getOutputStream());
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
				System.out.println(message);
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

				outputCompressedToClient.writeObject(loadMaze(params[1], params[0]));
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

				String data1=readerFromClient.readLine();//empty
				data = readerFromClient.readLine();
				
				
				message=clientIP+ ","+clientPort+ "," +ServerConstant.GET_CROSS_SECTION;
				messages.add(message);
				setChanged();
				notifyObservers();
				params = ParseCroosMaze(data);
				String []args= ParseCroosMaze(data1);

				outputCompressedToClient.writeObject(getCrossSection(params[0], args[0],Integer.parseInt(args[1])));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				messages.remove(message);

				break;

			default:
				message=clientIP+ ","+clientPort+ "," +"Invalid command";
				messages.add(message);
				setChanged();
				notifyObservers();
				outputCompressedToClient.writeObject(null);
				outputCompressedToClient.flush();

			}


		} catch (Exception e1) {

		}

		activeConnections.remove(clientIP+","+clientPort);
		String last=new String(clientIP +","+ clientPort+",disconnected");
		System.out.println();
		messages.add(last);
		setChanged();
		notifyObservers();
		messages.remove(last);

	}

	/**
	 * Parses the croos maze.
	 *
	 * @param data the data
	 * @return the string[]
	 */
	private String[] ParseCroosMaze(String data) {

		return data.split(" ");
	}
	
	/**
	 * Gets the cross section.
	 *
	 * @param name the name
	 * @param dimention the dimention
	 * @param section the section
	 * @return the cross section
	 */
	private Object getCrossSection(String name, String dimention, int section) {

		Maze3d maze3d=server.nameToMaze.get(name);

		if(maze3d == null){

			return null;
		}
		try{
			Maze2d maze2d = null;

			switch (dimention) {

			case "z":
				if(section>0&&section>maze3d.getHeight())
					maze2d= new Maze2d(maze3d.getCrossSectionByZ(section));
				break;

			case "x":
				if(section > 0 && section < maze3d.getLength())
					maze2d= new Maze2d(maze3d.getCrossSectionByX(section));

				break;

			case "y":
				if(section>0&&section>maze3d.getWidth())
					maze2d= new Maze2d(maze3d.getCrossSectionByY(section));

				break;

			default:

				return null;

			}

			Maze2dSearchableAdapter myMazeAdapter = new Maze2dSearchableAdapter(maze2d);
			
			return myMazeAdapter;

		}catch (ArrayIndexOutOfBoundsException | NullPointerException a){

			return null;
		}
	}

	/**
	 * Save maze.
	 *
	 * @param name the name
	 * @param fileName the file name
	 * @return the string[]
	 */
	private String [] SaveMaze(String name, String fileName) {


		String path = ".\\resources\\mazes\\";
		String []args= new String[2];
		try {
			server.myCompressor = new MyCompressorOutputStream(new FileOutputStream(path+fileName));
			server.nameToFileName.put(name, fileName);
			server.myCompressor.write(server.nameToMaze.get(name).toByteArray());

		} catch (FileNotFoundException e) {
			args[0]=Constant.FILE_NOT_FOUND;
			return args;

		} catch (IOException e) {
			args[0]=Constant.NO_MODEL_FOUND;
			return args;
		}
		finally {
			try {
				server.myCompressor.close();
				args[0] = Constant.MODEL_SAVED;
				args[1] = fileName;
				return args;
			} catch (IOException e) {
				args[0] = Constant.ERROR_CLOSING_FILE;

			}
		}

		return null;
	}

	/**
	 * Parses the save maze.
	 *
	 * @param data the data
	 * @return the string[]
	 */
	private String[] ParseSaveMaze(String data) {
		return data.split(" ");
	}

	/**
	 * Load maze.
	 *
	 * @param fileName the file name
	 * @param name the name
	 * @return the maze3d
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Maze3d loadMaze(String fileName, String name) throws IOException {

		String path = ".\\resources\\mazes\\";
		ArrayList<Byte> myStream = new ArrayList<Byte>();
		byte [] byteArray = new byte[1024];

		MyDecompressorInputStream myDecompressor = new MyDecompressorInputStream(new FileInputStream(path+fileName));

		while(myDecompressor.read(byteArray) > 0){

			for (byte b : byteArray) {
				myStream.add(b);
			}
		}
		myDecompressor.close();
		byte[] data = new byte[myStream.size()];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) myStream.get(i);
		}
		Maze3d myMaze = new Maze3d(data);
		server.nameToMaze.put(name, myMaze);
		return myMaze; 
	}

	/**
	 * Parses the load maze.
	 *
	 * @param data the data
	 * @return the string[]
	 */
	private String[] ParseLoadMaze(String data) {
		return data.split(" ");

	}

	/**
	 * Gets the maze size in memory.
	 *
	 * @param data the data
	 * @return the maze size in memory
	 */
	private Object getMazeSizeInMemory(String data) {

		int size;
		try {
			size = server.nameToMaze.get(data).toByteArray().length;
			return size;
		} catch (IOException e) {
			return 0;
		}


	}

	/**
	 * Gets the maze size in file.
	 *
	 * @param data the data
	 * @return the maze size in file
	 */
	private long getMazeSizeInFile(String data) {

		if(data != null){
			File myFile = new File(data);
			return myFile.length();
		}
		else
			return 0;
	}

	/**
	 * Gets the messages.
	 *
	 * @return the messages
	 */
	public ConcurrentLinkedQueue<String> getMessages() {
		return messages;
	}

	/**
	 * Parses the generate maze argument.
	 *
	 * @param arg the arg
	 * @return the string[]
	 */
	private String[] parseGenerateMazeArgument(Object arg) {

		String [] params=(((String) arg).split(","));
		return params;
	}



	/**
	 * Generate maze.
	 *
	 * @param name the name
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param generator the generator
	 * @return the maze3d
	 */
	public Maze3d generateMaze(String name, String x, String y, String z, String generator ) {
		try{

			if(server.nameToMaze.containsKey(name))
				return server.nameToMaze.get(name);

			Maze3d maze3d= null;

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

		}catch(NumberFormatException | NullPointerException n){

		}
		return null;

	}


	/**
	 * Solve maze.
	 *
	 * @param mazeName the maze name
	 * @param solver the solver
	 * @return the solution
	 */
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
		case "EUCLIDIAN_ASTAR":

			solution= new Astar<Position>(new MazeEuclideanDistance()).search(maze3d) ;

			break;
		case "MANHATTAN_ASTAR":

			solution= new Astar<Position>(new MazeManhattanDistance()).search(maze3d) ;
			break;

		default:
			break;
		}
		server.mazeToSolution.put(m, solution);
		return solution;

	}


	/**
	 * Gets the server.
	 *
	 * @return the server
	 */
	public MazeServer getServer() {
		return server;
	}


	/**
	 * Sets the server.
	 *
	 * @param server the new server
	 */
	public void setServer(MazeServer server) {
		this.server = server;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
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