package generic;


import java.io.Serializable;



/**
 * The Class Preferences. Represent the client Preferences.
 */
public class Preferences implements Serializable{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;



	/**
	 * The Enum MazeGenerator.
	 */
	public enum MazeGenerator{

		/** The dfs. */
		DFS,
		/** The random. */
		RANDOM
	}

	/**
	 * The Enum UI.
	 */
	public enum UI{

		/** The cli. */
		CLI,
		/** The gui. */
		GUI
	}

	/**
	 * The Enum MazeSolver.
	 */
	public enum MazeSolver{

		/** The manhattan astar. */
		MANHATTAN_ASTAR,
		/** The euclidian astar. */
		EUCLIDIAN_ASTAR,
		/** The bfs. */
		BFS
	}

	/**
	 * The Enum Access.
	 */
	public enum Access{

		/** The remote server. */
		REMOTE_SERVER, 
		/** The local. */
		LOCAL
	}

	/** The pool size. */
	public int poolSize;

	/** The server ip. */
	public String serverIP;

	/** The server port. */
	public int serverPort;

	/** The generator. */
	public MazeGenerator generator;

	/** The ui. */
	public UI ui;

	/** The solver. */
	public MazeSolver solver;

	/** The access. */
	public Access access;



	/**
	 * Instantiates a new preferences.
	 */
	public Preferences() {

		this.poolSize = Constant.DEFAULT_POOL_SIZE;
		this.ui = UI.CLI;
		this.generator = MazeGenerator.DFS;
		this.solver = MazeSolver.BFS;
		this.access = Access.LOCAL;
	}



	/**
	 * Instantiates a new preferences.
	 *
	 * @param poolSize the pool size
	 * @param serverIP the server ip
	 * @param serverPort the server port
	 * @param generator the generator
	 * @param ui the ui
	 * @param solver the solver
	 */
	public Preferences(int poolSize, String serverIP, int serverPort,
			MazeGenerator generator, UI ui, MazeSolver solver) {

		this.poolSize = poolSize;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.generator = generator;
		this.ui = ui;
		this.solver = solver;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Preferences [poolSize=" + poolSize + ", serverIP=" + serverIP
				+ ", serverPort=" + serverPort + ", generator=" + generator
				+ ", ui=" + ui + ", solver=" + solver + "]";
	}


	/**
	 * Gets the server ip.
	 *
	 * @return the server ip
	 */
	public String getServerIP() {
		return serverIP;
	}

	/**
	 * Sets the server ip.
	 *
	 * @param serverIP the new server ip
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	/**
	 * Gets the server port.
	 *
	 * @return the server port
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * Sets the server port.
	 *
	 * @param serverPort the new server port
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * Gets the generator.
	 *
	 * @return the generator
	 */
	public MazeGenerator getGenerator() {
		return generator;
	}

	/**
	 * Sets the generator.
	 *
	 * @param generator the new generator
	 */
	public void setGenerator(MazeGenerator generator) {
		this.generator = generator;
	}

	/**
	 * Gets the ui.
	 *
	 * @return the ui
	 */
	public UI getUi() {
		return ui;
	}

	/**
	 * Sets the ui.
	 *
	 * @param ui the new ui
	 */
	public void setUi(UI ui) {
		this.ui = ui;
	}

	/**
	 * Gets the solver.
	 *
	 * @return the solver
	 */
	public MazeSolver getSolver() {
		return solver;
	}

	/**
	 * Sets the solver.
	 *
	 * @param solver the new solver
	 */
	public void setSolver(MazeSolver solver) {
		this.solver = solver;
	}

	/**
	 * Gets the pool size.
	 *
	 * @return the pool size
	 */
	public int getPoolSize() {
		return poolSize;
	}

	/**
	 * Sets the pool size.
	 *
	 * @param poolSize the new pool size
	 */
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}



	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	/**
	 * Gets the access.
	 *
	 * @return the access
	 */
	public Access getAccess() {
		return access;
	}



	/**
	 * Sets the access.
	 *
	 * @param access the new access
	 */
	public void setAccess(Access access) {
		this.access = access;
	}

}
