package model;


/**
 * The Interface Model.
 *  @author Eran & Ziv
 */
public interface Model {
	
	
	/**
	 * Gets the status client.
	 *
	 * @param client the client
	 * @return the status client
	 */
	public void getStatusClient(String client);
	
	/**
	 * Disconnect client.
	 *
	 * @param client the client
	 */
	public void DisconnectClient(String client);
	
	/**
	 * Start server.
	 */
	public void StartServer();
	
	/**
	 * Disconnect server.
	 */
	public void DisconnectServer();
	
	/**
	 * Exit.
	 */
	public void exit();
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String[] getData();

}
