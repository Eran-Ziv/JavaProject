package server;

import java.net.Socket;



/**
 * The Interface ClientHandler.
 *  @author Eran & Ziv
 */
public interface ClientHandler {
	
	/**
	 * Handle client.
	 *
	 * @param client the client
	 */
	void handleClient(Socket client);
}
