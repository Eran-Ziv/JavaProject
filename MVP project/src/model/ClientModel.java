package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.zip.GZIPInputStream;

import algorithm.generic.Solution;
import algorithms.demo.Maze2dSearchableAdapter;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.Maze2d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;
import generic.Constant;
import generic.Preferences;
import generic.ServerConstant;


public class ClientModel extends Observable implements Model {



	Maze3d myMaze;
	Solution<Position> mySolution;



	String [] constantArgs;
	private Preferences preferences;


	public ClientModel(Preferences preferences) {

		this.preferences = preferences;
		this.constantArgs = new String[2];

	}


	

	@Override
	public int getModelSizeInMemory(String name) throws IOException {


		String data = name;

		return (int)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.GET_MODEL_SIZE_IN_MEMORY, data, "");
	}

	@Override
	public long getModelSizeInFile(String name) {

		String data = name;

		return (int)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.GET_MODEL_SIZE_IN_FILE, data, "");
	}


	@Override
	public void saveModel(String name, String fileName) {

		String data = name + " " + fileName;

		String valid = (String)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.SAVE_MAZE, data, "");
		constantArgs[0] = valid;
		constantArgs[1] = fileName;
		setChanged();
		notifyObservers(constantArgs);
	}


	@Override
	public void loadModel(String fileName, String name) throws IOException, FileNotFoundException {
		this.myMaze = null;
		String data = name + " " + fileName;
		this.myMaze = (Maze3d)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.LOAD_MAZE, data, "");

	}



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


	@SuppressWarnings("unchecked")
	@Override
	public Solution<Position> getSolution(String name) {

		return (Solution<Position>)this.mySolution;
	}





	@SuppressWarnings("unchecked")
	@Override
	public  Searchable<Position> getNameToModel(String name) {

		Maze3d myMaze = (Maze3d)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.MAZE_EXISTS, name, "");
		Maze3dSearchableAdapter myMazeAdapter = new Maze3dSearchableAdapter(myMaze);

		return myMazeAdapter;
	}





	@SuppressWarnings("unchecked")
	@Override
	public Searchable<Position> CrossSectionBy(String name, String dimention, int section) {

		Maze2d myMaze = (Maze2d)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.GET_CROSS_SECTION, name, dimention + " "  + section);
		Maze2dSearchableAdapter myMazeAdapter = new Maze2dSearchableAdapter(myMaze);

		return myMazeAdapter;
	}





	@Override
	public void exit() throws IOException {
		//Nothing to close


	}
	
	private Object queryServer(String serverIP,int serverPort,String command,String data,String property)
	{
		Object result=null;
		Socket server;			
		try {
			System.out.println("Trying to connect server, IP: " + serverIP + " " + serverPort);
			server = new Socket(serverIP,serverPort);
			PrintWriter writerToServer=new PrintWriter(new OutputStreamWriter(server.getOutputStream()));
			writerToServer.println(command);
			writerToServer.flush();
			writerToServer.println(property);
			writerToServer.flush();
			writerToServer.println(data);
			writerToServer.flush();
			ObjectInputStream inputDecompressed;
			inputDecompressed = new ObjectInputStream(new GZIPInputStream(server.getInputStream()));
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
