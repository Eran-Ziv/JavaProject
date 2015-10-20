package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import algorithm.generic.Solution;
import algorithms.demo.Maze2dSearchableAdapter;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;
import generic.Constant;
import generic.Preferences;
import generic.ServerConstant;



/**
 * The Class ClientModel. Implementing the Model {@see Model} in a remote server, all requests are directed to a remote server and responded with an answer.
 * All the Model functunallity is in the server, for **every** request, this model will sent a request to the server.
 */
public class ClientModel extends Observable implements Model {

	/** The my maze. */
	Maze3d myMaze;
	
	/** The my solution. */
	Solution<Position> mySolution;

	/** The constant args. */
	String [] constantArgs;
	
	/** The preferences. */
	private Preferences preferences;

	/**
	 * Instantiates a new client model.
	 *
	 * @param preferences the preferences
	 */
	public ClientModel(Preferences preferences) {

		this.preferences = preferences;
		this.constantArgs = new String[2];
	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInMemory(java.lang.String)
	 */
	@Override
	public int getModelSizeInMemory(String name) throws IOException {

		String data = name;

		return (int)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.GET_MODEL_SIZE_IN_MEMORY, data, "");
	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInFile(java.lang.String)
	 */
	@Override
	public long getModelSizeInFile(String name) {

		String data = name;

		return (int)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.GET_MODEL_SIZE_IN_FILE, data, "");
	}

	/* (non-Javadoc)
	 * @see model.Model#saveModel(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveModel(String name, String fileName) {

		String data = name + " " + fileName;

		String [] valid = (String[])queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.SAVE_MAZE, data, "");
		constantArgs[0] = valid[0];
		constantArgs[1] = fileName;
		setChanged();
		notifyObservers(constantArgs);
	}

	/* (non-Javadoc)
	 * @see model.Model#loadModel(java.lang.String, java.lang.String)
	 */
	@Override
	public Maze3d loadModel(String fileName, String name) throws IOException, FileNotFoundException {
		this.myMaze = null;
		String data = name + " " + fileName;
		this.myMaze = (Maze3d)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.LOAD_MAZE, data, "");
		constantArgs[0] = Constant.MODEL_LOADED;
		constantArgs[1]= name;
		setChanged();
		notifyObservers(constantArgs);
		return this.myMaze;

	}

	/* (non-Javadoc)
	 * @see model.Model#solveModel(java.lang.String)
	 */
	@Override
	public void solveModel(String name) {

		String property=null;
		switch(preferences.getSolver())
		{
		case BFS:
			property="BFS";
			break;
		case MANHATTAN_ASTAR:
			property="MANHATTAN_ASTAR";
			break;
		case EUCLIDIAN_ASTAR:
			property="EUCLIDIAN_ASTAR";
			break;
		default:
			return;
		}

		@SuppressWarnings("unchecked")
		Solution<Position> solution=(Solution<Position>)queryServer(preferences.getServerIP(),preferences.getServerPort(),ServerConstant.SOLVE_MAZE,name,property);
		if(solution==null)
		{
			constantArgs[0] = ServerConstant.DISCONNECT;
			constantArgs[1] = name;
			setChanged();
			notifyObservers(constantArgs);
			return;
		}

		System.out.println(solution);
		this.mySolution = solution;

		constantArgs[0] = Constant.MODEL_SOLVED;
		constantArgs[1] = name;
		setChanged();
		notifyObservers(constantArgs);

	}

	/* (non-Javadoc)
	 * @see model.Model#generateModel(java.lang.String, java.lang.String[])
	 */
	@Override
	public void generateModel(String name, String[] params) {

		String property=null;
		switch(preferences.getGenerator())
		{
		case DFS:
			property="DFS";
			break;
		case RANDOM:
			property="RANDOM";
			break;
		default:
			return;
		}

		String z = params[0];
		String x = params[1];
		String y = params[2];


		Maze3d myMaze=(Maze3d)queryServer(preferences.getServerIP(),preferences.getServerPort(),ServerConstant.GENERATE_MAZE,name+","+z+","+x+","+y ,property);
		if(myMaze==null)
		{
			constantArgs[0] = ServerConstant.DISCONNECT;
			constantArgs[1] = name;
			setChanged();
			notifyObservers(constantArgs);
			return;
		}
		this.myMaze = myMaze;

		constantArgs[0] = Constant.MODEL_GENERATED;
		constantArgs[1] = name;
		setChanged();
		notifyObservers(constantArgs);

	}


	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Solution<Position> getSolution(String name) {

		return (Solution<Position>)this.mySolution;
	}

	/* (non-Javadoc)
	 * @see model.Model#getNameToModel(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  Searchable<Position> getNameToModel(String name) {


		Maze3dSearchableAdapter myMazeAdapter = new Maze3dSearchableAdapter(myMaze);

		return myMazeAdapter;
	}

	/* (non-Javadoc)
	 * @see model.Model#CrossSectionBy(java.lang.String, java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Searchable<Position> CrossSectionBy(String name, String dimention, int section) {

		//Maze2d myMaze = (Maze2d)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.GET_CROSS_SECTION, name, dimention + " "  + section);
		Maze2dSearchableAdapter myMazeAdapter = (Maze2dSearchableAdapter)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.GET_CROSS_SECTION, name, dimention + " "  + section);
		constantArgs[0] = Constant.MODEL_GENERATED;
		constantArgs[1] = name;
		setChanged();
		notifyObservers(constantArgs);

		return myMazeAdapter;
	}

	/* (non-Javadoc)
	 * @see model.Model#exit()
	 */
	@Override
	public void exit() throws IOException {
		//Nothing to close
	}

	/**
	 * Query server.
	 *
	 * @param serverIP the server ip
	 * @param serverPort the server port
	 * @param command the command
	 * @param data the data
	 * @param property the property
	 * @return the object
	 */
	private Object queryServer(String serverIP,int serverPort,String command,String data,String property)
	{
		Object result=null;
		Socket server;			
		try {
			System.out.println("Trying to connect server, IP: " + serverIP + " " + serverPort);
			server = new Socket(serverIP,serverPort);
			PrintWriter writerToServer=new PrintWriter((new OutputStreamWriter(server.getOutputStream())));
			writerToServer.println(command);
			writerToServer.flush();
			writerToServer.println(property);
			writerToServer.flush();
			writerToServer.println(data);
			writerToServer.flush();
			ObjectInputStream inputDecompressed;
			inputDecompressed = new ObjectInputStream(server.getInputStream());
			result=inputDecompressed.readObject();
			if(result.toString().contains("disconnect"))
			{
				setChanged();
				notifyObservers(ServerConstant.DISCONNECT);
			}
			writerToServer.close();
			inputDecompressed.close();
			server.close();
		} catch (ClassNotFoundException | IOException  e) {
			e.printStackTrace();

		}

		return result;

	}

}
