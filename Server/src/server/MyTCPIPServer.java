package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;


import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;


/** This class creates a generic TCP/IP Server. 
 *
 */
public class MyTCPIPServer {
	/**	Server Properties - how many clients should the server handle simultaneously and port to handle them.
	 * 
	 */
	ServerProperties serverProperties;
	/**	The client handler that will be injected to this field will change how clients will be handled.
	 * 
	 */
	ClientHandler clientHandler;
	/** a flag to know when the server was signaled to stop.
	 * 
	 */
	private volatile boolean stopped;

	/**
	 * Instantiates a new my tcpip server.
	 *
	 * @param serverProperties the server properties
	 * @param clientHandler the client handler
	 */
	public MyTCPIPServer(ServerProperties serverProperties,ClientHandler clientHandler) {
		this.serverProperties=serverProperties;
		stopped=false;
		this.clientHandler=clientHandler;

		// TODO Auto-generated constructor stub
	}
	/**	This method will start the TCP/IP Server.
	 * Please Inject your desired client handler first.
	 * 
	 */
	public void startServer()
	{
		ServerSocket server;
		try {
			server = new ServerSocket(serverProperties.getPort());
			System.out.println("Server is now listeing on port " + serverProperties.getPort());
			ListeningExecutorService threadPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(serverProperties.getNumOfClients()));
			server.setSoTimeout(500);// changed from 500 to 0 which is infinite timoute
			while(!stopped)
			{
				try {
					final Socket someClient=server.accept();
					System.out.println("New client" + " port: " + someClient.getPort() + " IP: " + someClient.getInetAddress().getHostAddress());
					threadPool.execute(new Runnable() {

						@Override
						public void run() {
							try {
								//InputStream inputFromClient=someClient.getInputStream();
								//OutputStream outputToClient=someClient.getOutputStream();
								clientHandler.handleClient(someClient);
								//inputFromClient.close();
								//outputToClient.close();
								someClient.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});

				} catch (SocketTimeoutException e) {
				}
			}
			threadPool.shutdownNow();
			server.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}


	}
	
	/**
	 * Stopped server.
	 */
	public void stoppedServer()
	{
		stopped=true;
	}
}
