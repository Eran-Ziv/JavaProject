package server;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithm.generic.Solution;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import generic.Constant;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;





public class MazeServer extends MyTCPIPServer implements Runnable {

	public ConcurrentHashMap<String, Maze3d> nameToMaze;

	public ConcurrentHashMap<String, String> nameToFileName;

	public ConcurrentHashMap<String, Solution<Position>>nameToSolution;

	public ConcurrentHashMap<Maze3d, Solution<Position>> mazeToSolution;

	MyCompressorOutputStream myCompressor;

	MyDecompressorInputStream myDecompressor;
	
	public MazeServer(ServerProperties serverProperties, MazeClientHandler clientHandler) {
		super(serverProperties, clientHandler);
		this.nameToFileName = new ConcurrentHashMap<String, String>();
		this.nameToMaze = new ConcurrentHashMap<String, Maze3d>();
		this.mazeToSolution = new ConcurrentHashMap<Maze3d, Solution<Position>>();
		this.nameToSolution = new ConcurrentHashMap<String, Solution<Position>>();
		load();
		if(mazeToSolution == null){
			mazeToSolution = new ConcurrentHashMap<Maze3d, Solution<Position>>();
		}
	}
	
	
	@Override
	public void stoppedServer() {
		
		try {
			FileOutputStream fos=new FileOutputStream(Constant.FILE_PATH);
			GZIPOutputStream gzos=new GZIPOutputStream(fos);
			ObjectOutputStream out=new ObjectOutputStream(gzos);
			out.writeObject(mazeToSolution);
			out.flush();
			out.close();
		}
		catch (IOException e) {
			e.getStackTrace();
		}
		
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	private void load()
	{
		try {
			FileInputStream fos=new FileInputStream(Constant.FILE_PATH);
			GZIPInputStream gzos=new GZIPInputStream(fos);
			ObjectInputStream out=new ObjectInputStream(gzos);
			mazeToSolution = (ConcurrentHashMap<Maze3d, Solution<Position>>) out.readObject();
			out.close();
		}
		catch (  IOException e) {
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	
	@Override
	public void run() {
		startServer();
	}
	
	
	
	
}
