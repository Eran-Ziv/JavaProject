package presenter;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import model.Constants;
import model.Model;
import view.View;


/**
 * The Class Presenter.
 *  @author Eran & Ziv
 */
public class Presenter implements Observer {


	/** The my model. */
	Model myModel;
	
	/** The my view. */
	View myView;

	/**
	 * The Class CheckConnectionStatus.
	 */
	public class CheckConnectionStatus implements RemoteControlCommand {

		/** The params. */
		String params;

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {
			myModel.getStatusClient(params);

		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */
		@Override
		public void setArguments(String args) {
			this.params = args;

		}

	}

	/**
	 * The Class DisconnectUser.
	 */
	public class DisconnectUser implements RemoteControlCommand{

		/** The params. */
		String params;

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {
			myModel.DisconnectClient(params);

		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */
		@Override
		public void setArguments(String args) {
			this.params = args;

		}

	}

	/**
	 * The Class StartServer.
	 */
	public class StartServer implements RemoteControlCommand{

		/** The params. */
		String params;

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {

			myModel.StartServer();
		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */
		@Override
		public void setArguments(String args) {
			this.params = args;

		}

	}

	/**
	 * The Class StopServer.
	 */
	public class StopServer implements RemoteControlCommand{

		/** The params. */
		String params;

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {
			myModel.DisconnectServer();

		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */
		@Override
		public void setArguments(String args) {
			this.params = args;

		}

	}

	/**
	 * The Class exit.
	 */
	public class exit implements RemoteControlCommand{

		/** The params. */
		String params;

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {
			myModel.exit();
		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */
		@Override
		public void setArguments(String args) {
			this.params = args;


		}

	}

	/**
	 * Instantiates a new presenter.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public Presenter(Model model, View view)
	{
		this.myModel=model;
		this.myView=view;
		ConcurrentHashMap<String, RemoteControlCommand> commandMap=new ConcurrentHashMap<String, RemoteControlCommand>(); 
		commandMap.put("connection status", new CheckConnectionStatus());
		commandMap.put("disconnect user", new DisconnectUser());
		commandMap.put("start server",new StartServer());
		commandMap.put("stop server", new StopServer());
		commandMap.put("exit", new exit());

		myView.setCommands(commandMap);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {

		
		if(o instanceof View){

			myView.getCommand().doCommand();
		}

		if(o instanceof Model ){

			String [] args = myModel.getData();

			if(args != null){
				switch(args[0]){

				case Constants.CANNOT_REMOVE_CLIENT:
					
					if(args[1] == null)
						myView.Display(args[0]);
					else
					myView.Display(args[1]);
					break;

				case Constants.CANNOT_START_SERVER:

					if(args[1] == null)
						myView.Display(args[0]);
					else
					myView.Display(args[1]);
					break;

				case Constants.SERVER_START:
					if(args[1] == null)
						myView.Display(args[0]);
					else
					myView.DisplayStatus(args[0] + " " +args[1]);
					break;

				case Constants.CANNOT_DISCONNECT_SERVER:

					if(args[1] == null)
						myView.Display(args[0]);
					else
					myView.Display(args[1]);
					break;

				case Constants.DISCONNECT_SERVER:

					
						myView.Display(args[0]);
					break;
				case Constants.CLIENT_STATUS:

					if(args[1] != null)
						myView.DisplayStatus(args[0]+ " " + args[1]);
					else
					myView.Display(args[0]);
					break;

				case Constants.CLIENT_ADDED:

					myView.addClient(args[1]); 
					break;

				case Constants.CLIENT_REMOVED:

					myView.removeClient(args[1]);
					break;
					
				case Constants.CLIENT_UPDATE:
					if(args[1] == null)
						myView.DisplayStatus(args[0]);
					else
						myView.DisplayStatus(args[0] + "\n" + args[1]);
					
					break;
					

				case Constants.EXIT:

					if(args[1] == null)
						myView.Display(args[0]);
					else
					myView.Display(args[1]);
					break;

				}
			}
		}


	}


}
