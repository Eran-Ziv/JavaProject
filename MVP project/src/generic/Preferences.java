package generic;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;


public class Preferences implements Serializable{


	private static final long serialVersionUID = 1L;

	public enum MazeGenerator{
		DFS,RANDOM
	}

	public enum UI{
		CLI,GUI
	}

	public enum MazeSolver{
		MANHATTAN_ASTAR,EUCLIDIAN_ASTAR,BFS
	}

	public int poolSize;

	public String serverIP;

	public int serverPort;

	public MazeGenerator generator;

	public UI ui;

	public MazeSolver solver;
	
	XMLDecoder myXMLDecoder;
	
	XMLEncoder myXMLEncoder;

	public Preferences(boolean load) {
		
		Preferences preferences = loadPreferences();
		
		this.poolSize = preferences.poolSize;
		this.ui = preferences.ui;
		this.generator = preferences.generator;
		this.solver = preferences.solver;
		this.serverIP = preferences.serverIP;
		this.serverPort = preferences.serverPort;
		
	}
	
	public Preferences() {
		
		this.poolSize = Constant.DEFAULT_POOL_SIZE;
		this.ui = UI.CLI;
		this.generator = MazeGenerator.DFS;
		this.solver = MazeSolver.BFS;
	}
	
	

	public Preferences(int poolSize, String serverIP, int serverPort,
			MazeGenerator generator, UI ui, MazeSolver solver) {
		 
		this.poolSize = poolSize;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.generator = generator;
		this.ui = ui;
		this.solver = solver;
		savePreferences();
	}



	@Override
	public String toString() {
		return "Preferences [poolSize=" + poolSize + ", serverIP=" + serverIP
				+ ", serverPort=" + serverPort + ", generator=" + generator
				+ ", ui=" + ui + ", solver=" + solver + "]";
	}


	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public MazeGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(MazeGenerator generator) {
		this.generator = generator;
	}

	public UI getUi() {
		return ui;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

	public MazeSolver getSolver() {
		return solver;
	}

	public void setSolver(MazeSolver solver) {
		this.solver = solver;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}


	private void savePreferences(){
		
		try {
			myXMLEncoder = new XMLEncoder(new FileOutputStream("Preferences.xml"));
			myXMLEncoder.writeObject(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Preferences loadPreferences(){
		
		try {
			myXMLDecoder = new XMLDecoder(new FileInputStream(Constant.XML_FILE_PATH));
			myXMLDecoder.readObject();
			Preferences preferences =(Preferences)myXMLDecoder.readObject(); 
			return preferences;
			
		} catch (FileNotFoundException e) {
			return new Preferences();
		}
	}



}
