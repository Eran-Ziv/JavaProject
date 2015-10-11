package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;





public class MazeServer extends MyTCPIPServer implements Runnable {

	
	ConcurrentHashMap<Maze3d,Solution<Position>> cache=new ConcurrentHashMap<Maze3d,Solution<Position>>();
	
	
	ConcurrentLinkedQueue<String> databaseNames=new ConcurrentLinkedQueue<String>();
	
	
	ConcurrentHashMap<String,Maze3d> generatedMazes=new ConcurrentHashMap<String,Maze3d>();
	
	
	public MazeServer(ServerProperties serverProperties, MazeClientHandler clientHandler) {
		super(serverProperties, clientHandler);
		loadFromDatabase();
	}
	@Override
	public void stoppedServer() {
		
	}
	
	
	
	
	
	private void loadFromDatabase()
	{
	
	}
	
	
	
	
	
	private ConcurrentLinkedQueue<String> namesToWriteToDB()
	{
		ConcurrentLinkedQueue<String> names=new ConcurrentLinkedQueue<String>(); 
		for(String str : generatedMazes.keySet())
		{
			if(!databaseNames.contains(str))
				names.add(str);
		}
		return names;
	}
	/** start server as Runnable.
	 */
	@Override
	public void run() {
		startServer();
	}
	
	
	
	
}
