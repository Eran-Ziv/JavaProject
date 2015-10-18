package view;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import presenter.RemoteControlCommand;
import presenter.RemoteControlProperties;
import boot.RunGui;


/**
 * The Class ServerWindow.
 *  @author Eran & Ziv
 */
public class ServerWindow extends BasicWindow implements View {

	/** The clients. */
	String [] clients;

	/** The status. */
	Text status;

	/** The server properties. */
	RemoteControlProperties serverProperties;

	/** The command map. */
	ConcurrentHashMap<String, RemoteControlCommand> commandMap=new ConcurrentHashMap<String, RemoteControlCommand>();

	/** The last command. */
	RemoteControlCommand lastCommand =null;

	/** The Data from model. */
	String DataFromModel=null;

	/** The list. */
	List list;

	/**
	 * Instantiates a new server window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public ServerWindow(String title, int width, int height) {
		super(title, width, height);
		shell.setBackgroundImage(new Image(display,".\\resources\\images\\matrix1.jpg")); //setting the image and some music:)
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	/**
	 * Instantiates a new server window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 * @param display the display
	 * @param shell the shell
	 */
	public ServerWindow(String title, int width, int height,Display display,Shell shell) {
		super(display,shell,title, width, height);
		shell.setBackgroundImage(new Image(display,".\\resources\\images\\matrix.gif")); //setting the image and some music:)
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
	}


	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {

		shell.addListener(SWT.Close,new Listener(){ 

			@Override
			public void handleEvent(Event arg0) {

				setUserCommand(commandMap.get("exit"));
				display.dispose();				 
			}

		});

		initMenu();
		shell.setLayout(new GridLayout(2,false)); 



		initMenu();


		//Start server buttons
		Button startServerButton=new Button(shell,SWT.PUSH);
		startServerButton.setText("Start Server");
		startServerButton.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));
		startServerButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				setUserCommand(commandMap.get("start server"));

			}

		});

		//Stop server buttons
		Button stopServerButton=new Button(shell,SWT.PUSH);
		stopServerButton.setText("Stop Server");
		stopServerButton.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1));
		stopServerButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				setUserCommand(commandMap.get("stop server"));

			}

		});

		//Designing a bit of the window
		final Label listLabel=new Label(shell,SWT.CENTER);

		listLabel.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_WHITE)); //setting color
		listLabel.setText("Connected Clients");

		FontData fontData = listLabel.getFont().getFontData()[0];
		fontData.setHeight(20);
		Font font = new Font(display, new FontData(fontData.getName(), fontData
				.getHeight(), SWT.BOLD)); 

		listLabel.setFont(font);
		listLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1)); //setting layout

		list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL); //setting the list of connected clients

		list.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_YELLOW));

		list.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));

		//setting status of selected clients
		status = new Text(shell, SWT.CENTER | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		status.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
		status.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,true,2,1));

		list.addSelectionListener(new SelectionListener() { //on selection show status
			public void widgetSelected(SelectionEvent event) {
				String[] selectedClients = list.getSelection(); //get all clients selcted
				@SuppressWarnings("unused")
				String outString = "";

				if(selectedClients.length>1)
				{// for each selected client
					for (int loopIndex = 0; loopIndex < selectedClients.length; loopIndex++)
					{

						RemoteControlCommand command = commandMap.get("connection status");
						command.setArguments(selectedClients[loopIndex]);
						setUserCommand(command);

						if(loopIndex!=selectedClients.length-1)
							outString += selectedClients[loopIndex]+ "\n"; 

					}
				}
				else if(selectedClients.length==1){
					RemoteControlCommand command = commandMap.get("connection status");
					command.setArguments(selectedClients[0]);
					setUserCommand(command);
					outString=selectedClients[0] + "\n";
				}
				else
					status.setText("");
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				int[] selectedClients = list.getSelectionIndices();
				String outString = "";
				for (int loopIndex = 0; loopIndex < selectedClients.length; loopIndex++){
					outString += selectedClients[loopIndex] + " ";


				}//displaying the data we collected
				System.out.println("Selected Clients: " + outString);
			}
		});

		// a button for disconnecting selected clients
		Button disconnectClientButton=new Button(shell,SWT.PUSH | SWT.CENTER);
		disconnectClientButton.setText("Disconnect Clients");
		disconnectClientButton.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,false,false,2,1));
		disconnectClientButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String[] unparsedClients = list.getSelection(); //getting all selected clients
				for(int i=0;i<unparsedClients.length;i++){

					RemoteControlCommand command = commandMap.get("disconnect user");
					command.setArguments(unparsedClients[i]);
					setUserCommand(command);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});


	}


	/**
	 * Inits the menu.
	 */
	private void initMenu() {
		Menu menuBar = new Menu(shell, SWT.BAR);
		//creates a file category in toolbar
		MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeFileMenu.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeFileMenu.setMenu(fileMenu);


		//creates a help category in toolbar
		MenuItem cascadeHelpMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeHelpMenu.setText("&Help");
		Menu HelpMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeHelpMenu.setMenu(HelpMenu);


		//add item to file menu open properties
		MenuItem item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Open Properties");
		item.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {


			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN); 
				fd.setText("open");
				fd.setFilterPath("C:\\");
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				String filename=fd.open();
				if(filename!=null){
					setProperties(filename);
					shell.close();
					new RunGui().loadWindow(serverProperties); //runs a new programm with new properties from user

				}
			}

		});
		//new item to file menu
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Write Properties");
		item.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {



			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				display.asyncExec(new Runnable() {

					@Override
					public void run() {//this function works on the same basis as open Properties the only difference is the source of the Properties data here we recieve it directly from the user
						WriteServerPropertiesGUI guiProp=new WriteServerPropertiesGUI();
						if(guiProp.writeProperties(display,shell)!=-1)
						{
							shell.close();
							new RunGui().loadWindow(readProperties()); //runs a new programm with new properties from user
						}

					}

				});
			}

		});
		//new item for file menu
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Exit");
		item.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {


			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				setUserCommand(commandMap.get("exit"));
				display.dispose();		
			}

		});

		shell.setMenuBar(menuBar);

	}

	/**
	 * Sets the properties.
	 *
	 * @param filename the new properties
	 */
	private void setProperties(String filename) {//sets properties from a certain file

		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(filename);
			d=new XMLDecoder(in);
			serverProperties=(RemoteControlProperties)d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Read properties.
	 *
	 * @return the remote control properties
	 */
	public static RemoteControlProperties readProperties()
	{
		XMLDecoder d;
		RemoteControlProperties p=null;
		try {
			FileInputStream in=new FileInputStream("properties.xml");
			d=new XMLDecoder(in);
			p=(RemoteControlProperties)d.readObject();
			System.out.println(p);
			d.close();
		} catch (IOException e) {
			return new RemoteControlProperties();
		}
		return p;
	}

	/* (non-Javadoc)
	 * @see view.View#getCommand()
	 */
	@Override
	public RemoteControlCommand getCommand() {
		return this.lastCommand;
	}

	/* (non-Javadoc)
	 * @see view.View#setCommands(java.util.concurrent.ConcurrentHashMap)
	 */
	@Override
	public void setCommands(ConcurrentHashMap<String, RemoteControlCommand> commandMap) {
		this.commandMap=commandMap;

	}

	/* (non-Javadoc)
	 * @see view.View#Display(java.lang.String)
	 */
	@Override
	public void Display(String msg) {
		display.asyncExec(new Runnable(){

			@Override
			public void run() {
				if(!shell.isDisposed()){
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					messageBox.setText("Remote maze client");
					messageBox.setMessage(msg);
					messageBox.open(); //message to user
				}
			}

		});

	}

	/* (non-Javadoc)
	 * @see view.View#DisplayStatus(java.lang.String)
	 */
	@Override
	public void DisplayStatus(String msg) {
		display.asyncExec(new Runnable(){

			@Override
			public void run() {
				if(!shell.isDisposed() && msg != null){
					status.insert(msg);
					
				}
			}	
		});

	}

	/* (non-Javadoc)
	 * @see view.View#saveData(java.lang.String)
	 */
	@Override
	public void saveData(String data) {
		this.DataFromModel=data;

	}

	/* (non-Javadoc)
	 * @see view.View#addClient(java.lang.String)
	 */
	@Override
	public void addClient(String Client) {
		display.asyncExec(new Runnable(){

			@Override
			public void run() {
				list.add(Client);

			}

		});


	}

	/* (non-Javadoc)
	 * @see view.View#removeClient(java.lang.String)
	 */
	@Override
	public void removeClient(String Client) {
		display.asyncExec(new Runnable(){

			@Override
			public void run() {
				try{list.remove(Client);}
				catch(Exception e)
				{

				}
				status.setText("");
			}

		});
	}

	/* (non-Javadoc)
	 * @see view.View#setUserCommand(presenter.RemoteControlCommand)
	 */
	@Override
	public void setUserCommand(RemoteControlCommand userCommand) {

		this.lastCommand = userCommand;
		setChanged(); 
		notifyObservers();

	}

}
