package model;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.HashMap;
import java.util.Observable;

import java.util.zip.GZIPInputStream;



import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.demo.Maze2dSearchableAdapter;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.DfsMaze3dGenerator;
import algorithms.mazeGenerators.Maze2d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.Heuristic;
import algorithms.search.MazeEuclideanDistance;
import algorithms.search.MazeManhattanDistance;
import generic.Constant;
import generic.Preferences;
import generic.ServerConstant;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class ClientModel extends Observable implements Model {



	Maze3d myMaze;
	Solution<Position> mySolution;



	String [] constantArgs;
	private Preferences preferences;


	public ClientModel(Preferences preferences) {

		this.preferences = preferences;
		this.constantArgs = new String[2];

	}


	private Object queryServer(String serverIP,int serverPort,String command,String data,String property)
	{
		Object result=null;
			Socket server;			
			try {
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
					notifyObservers("disconnect");
				}
				writerToServer.close();
				inputDecompressed.close();
				server.close();
			} catch (ClassNotFoundException e) {
			
			} catch (IOException e) {
			
			}
		
		
		return result;
		
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
		
		String valid = (String)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.GET_MODEL_SIZE_IN_FILE, data, "");
		constantArgs[0] = valid;
		constantArgs[1] = fileName;
		setChanged();
		notifyObservers(constantArgs);
	}


	@Override
	public void loadModel(String fileName, String name) throws IOException, FileNotFoundException {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void solveModel(String name) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void generateModel(String name, String[] params) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public <T> Solution<T> getSolution(String name) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public <T> Searchable<T> getNameToModel(String name) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public <T> Searchable<T> CrossSectionBy(String name, String dimention, int section) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public void exit() throws IOException {
		// TODO Auto-generated method stub
		
	}





	@Override
	public HashMap<String, Maze3d> getNameToMaze() {
		// TODO Auto-generated method stub
		return null;
	}

}
