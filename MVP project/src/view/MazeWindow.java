package view;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithm.generic.Solution;
import algorithms.mazeGenerators.Maze3d;
import boot.Run;
import boot.RunCLI;
import boot.RunGUI;
import boot.WritePropertiesGUI;
import generic.Preferences;
import presenter.Command;
import presenter.Maze3dDrawableAdapter;

public class MazeWindow extends BasicWindow implements View {
	
	protected HashMap<String, Command>  commands;
	
	protected Command LastUserCommand =null;
	
	CommonBoard boardWidget;
	
	Preferences preferences;
	
	
	String mazeName=null;

	Maze3d dataRecieved=null; 

	MazeProperties input;
	
	public MazeWindow(Display display,Shell shell,String title, int width, int height) {
		super(display,shell,title,width,height);
	}
	
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		
	}
	
	@Override
	void initWidgets() {
		shell.addListener(SWT.Close,new Listener(){

			@Override
			public void handleEvent(Event arg0) {
				closeCorrect();
				display.dispose();
				setUserCommand(commands.get("exit"));
			}
			
		});
		//sets the background image to white
		shell.setBackground(new Color(null,255,255,255));
		shell.setLayout(new GridLayout(2,false));
		shell.setText("Maze Game"); //sets the text of window
		//creates a tool bar
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
				FileDialog fd=new FileDialog(shell,SWT.OPEN); //opens a dialog box in which we can select a xml file and load it
				fd.setText("open");
				fd.setFilterPath("C:\\");
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				String filename=fd.open(); //choose the file
				if(filename!=null){
					setProperties(filename);
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							switch(preferences.getUi())
							{
								case CLI: //if the properties calls for CLI
									closeCorrect(); //dispose all data
									display.dispose(); //dispose display
									setUserCommand(commands.get("exit"));
									RunCLI demo=new RunCLI(); //call for a function that works with cli
									demo.startProgram(getPreferences());
									break;
								case GUI:
									closeCorrect();// dispose all and close timer task
									display.dispose();
									setUserCommand(commands.get("exit"));
									RunGUI demoG = new RunGUI(); //calls for a function that recreates a gui window
									demoG.start(getPreferences());
									break;
								default:
									return;	
							}
						}
					});
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
							WritePropertiesGUI guiProp=new WritePropertiesGUI();
							if(guiProp.writeProperties(shell)!=-1)
							{
								preferences=Run.readPreferences();
								switch(preferences.getUi())
								{
									case CLI:
										closeCorrect();
										display.dispose();
										LastUserCommand= commands.get("exit");
										setChanged();
										notifyObservers();
										RunCLI demo=new RunCLI();
										demo.startProgram(getPreferences());
										break;
									case GUI:
										closeCorrect();
										display.dispose();
										LastUserCommand = commands.get("exit");
										setChanged();
										notifyObservers();
										RunGUI demoG = new RunGUI();
										demoG.start(getPreferences());
										break;
									default:
										return;	
								}
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
						closeCorrect();
						shell.dispose();
						LastUserCommand= commands.get("exit");
						setChanged();
						notifyObservers();
						
						
					}
			    	
			    });
			    //a new item in the help menu prints a msg box telling the user some details about us:)
			    item = new MenuItem(HelpMenu, SWT.PUSH);
			    item.setText("About");
			    item.addSelectionListener(new SelectionListener(){
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				        messageBox.setText("Information");
				        messageBox.setMessage("This entire software was created by Tomer Cabouly and Alon Orlovsky Enjoy It!");
						messageBox.open();
						
					}
			    	
			    });
			shell.setMenuBar(menuBar);
	    
	   //buttons for generate maze
		Button generateButton=new Button(shell,SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
			//button that solves the maze
		//creates an instance of boardWidget
		   boardWidget=new MazeBoard(shell, SWT.NONE);// CommonBoard f(x)
		   boardWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,3));
		   Button clueButton=new Button(shell,SWT.PUSH);
		   clueButton.setText("Help me solve this!");
		   clueButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		   Button solveMaze = new Button (shell ,SWT.PUSH);
		   solveMaze.setText("Solve the maze I give up");
		   solveMaze.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		  
		   generateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			    dataRecieved=null;
			    ClassInputDialog dlg = new ClassInputDialog(shell,MazeProperties.class);
			    MazeProperties tempInput=(MazeProperties)dlg.open();
			    if(tempInput!=null)
			    {
			    	input=tempInput;
			    }
			    boardWidget.forceFocus();
			    if(input!=null && input.getColGoal()<input.getCols() && input.getRowGoal()<input.getRows()){
			    	MazeWindow.this.mazeName=input.getMazeName();
			    }
			    if(input!=null &&(input.getColGoal()>=input.getCols() || input.getRowGoal()>=input.getRows())){// || !(input.getColGoal()<input.getCols() && input.getRowGoal()<input.getRows())){ //if error has occureed 
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("An error has occureed");
					messageBox.open();
				}
			    else
			    {
				    LastUserCommand= commands.get("maze exists");
				    setChanged(); //check if maze already exists
					notifyObservers(" " +MazeWindow.this.mazeName); 
			    }
				
				
					
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		   
		   
		   solveMaze.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if(MazeWindow.this.mazeName!=null && !boardWidget.won){
				setUserCommand(commands.get("solve"));
				}
				else{ 
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("No maze to solve");
					messageBox.open();
				}
			}
			   
		   });	
	}
	
	protected void setProperties(String filename) {
		
		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(filename);
			d=new XMLDecoder(in);
			preferences=(Preferences)d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
				displayString("Error Loading Properties");
		}
		
	}
	
	public void closeCorrect(){
		boardWidget.destructBoard();
		boardWidget.dispose();
	}
	                                
	@Override
	public void dirCommand(String fileName) {
		// not relevant for this view
		
	}
	@Override
	public <T> void displayModel(Drawable<T> draw) {
		
		boardWidget.displayProblem((Maze3dDrawableAdapter)draw);
		
	}
	@Override
	public <T> void displayCrossSectionBy(Drawable<T> draw) {
		//not relevant for gui
		
	}
	@Override
	public <T> void displaySolution(Solution<T> solution) {
		boardWidget.displaySolution(solution);
		
	}
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;
		
	}
	@Override
	public void displayString(String toPrint) {
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
        messageBox.setText("Information");
        messageBox.setMessage(toPrint);
		messageBox.open();
		
	}
	@Override
	public Command getUserCommand() {
		
		return LastUserCommand;
	}
	@Override
	public void setUserCommand(Command command) {
		LastUserCommand= command;
		setChanged();
		notifyObservers(); 
		
	}
	@Override
	public void exit() {
		boardWidget.destructBoard();
		boardWidget.dispose();
		
	}
	@Override
	public void start() {
//		Thread myThread = new Thread(this);
//		
//		myThread.start();
		
		this.run();
		
	}
	
	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}
}
	
	


