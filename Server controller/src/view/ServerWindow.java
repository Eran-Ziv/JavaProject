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

public class ServerWindow extends BasicWindow implements View {
	
	String [] clients;
	
	Text status;
	
	RemoteControlProperties serverProperties;
	
	ConcurrentHashMap<String, RemoteControlCommand> commandMap=new ConcurrentHashMap<String, RemoteControlCommand>();
	
	RemoteControlCommand lastCommand =null;
	
	String DataFromModel=null;
	
	List list;
	
	public ServerWindow(String title, int width, int height) {
		super(title, width, height);
		shell.setBackgroundImage(new Image(display,".\\resources\\images\\matrix1.jpg")); //setting the image and some music:)
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
	}
	
	public ServerWindow(String title, int width, int height,Display display,Shell shell) {
		super(display,shell,title, width, height);
		shell.setBackgroundImage(new Image(display,".\\resources\\images\\matrix1.jpg")); //setting the image and some music:)
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
	}
	
	
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
		
		list.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_CYAN));
		
		list.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));

		//setting status of selected clients
	    status = new Text(shell, SWT.CENTER | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	    status.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_WHITE));
	    status.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,true,2,1));
	  
	    list.addSelectionListener(new SelectionListener() { //on selection show status
	      public void widgetSelected(SelectionEvent event) {
	        String[] selectedClients = list.getSelection(); //get all clients selcted
	        String outString = "";
	        
	        if(selectedClients.length>1)
	        {// for each selected client
	        	for (int loopIndex = 0; loopIndex < selectedClients.length; loopIndex++)
	        	{
	        		
		        	
		        	RemoteControlCommand command = commandMap.get("connection status");
		        	command.setArguments(selectedClients[loopIndex]);
		        	setUserCommand(command);
		        	
	        		if(loopIndex!=selectedClients.length-1)
	        			outString += selectedClients[loopIndex].split(" ")[2] +" " + DataFromModel +", "; //Data from model is the data about the client
	        		else
	        			outString+=selectedClients[loopIndex].split(" ")[2] +" " + DataFromModel;
	        	}
	        }
	        else if(selectedClients.length==1){
	        	RemoteControlCommand command = commandMap.get("connection status");
	        	command.setArguments(selectedClients[0]);
	        	setUserCommand(command);
	        	outString=selectedClients[0].split(" ")[2]+" " +DataFromModel;
	        }
	        if(selectedClients.length!=0)
	        	status.setText("Selected Clients: " + outString);
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
	
	@Override
	public RemoteControlCommand getCommand() {
		return this.lastCommand;
	}
	
	@Override
	public void setCommands(ConcurrentHashMap<String, RemoteControlCommand> commandMap) {
		this.commandMap=commandMap;
		
	}
	
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
	
	@Override
	public void saveData(String data) {
		this.DataFromModel=data;
		
	}
	
	@Override
	public void addClient(String Client) {
		display.asyncExec(new Runnable(){

			@Override
			public void run() {
				list.add("Client IP: " + Client.split(",")[0]+" Port: "+ Client.split(",")[1]);
				
			}
			
		});
		
		
	}
	
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

	@Override
	public void setUserCommand(RemoteControlCommand userCommand) {
		
		this.lastCommand = userCommand;
		setChanged(); 
		notifyObservers();
		
	}
	
}
