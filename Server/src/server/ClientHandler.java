package server;

import java.net.Socket;



/**
 * The Interface ClientHandler. This interface is describing a general handalling of a client.
 */
public interface ClientHandler {

	/**
	 * Handle client. This function will be implemented in order to determine the behavior of a recieved client.
	 *
	 * @param client the client
	 */
	void handleClient(Socket client);
}
