package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
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
		load();
	}
	
	
	@Override
	public void stoppedServer() {
		
	}
	
	
	
	
	
	private void load()
	{
	
	}
	
	
	
	
	@Override
	public void run() {
		startServer();
	}
	
	
	
	
}
