package model;


/**
 * The Interface Model. This is an interface of the model later to be implemented by other classes
 *it is part of the MVP Architecture
 *this model will be able to later communicate directly with the SERVER
 *activate him disconnect him and manage a list of connections  
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
	 * Gets the data. For the Observers.
	 *
	 * @return the data
	 */
	public String[] getData();

}
