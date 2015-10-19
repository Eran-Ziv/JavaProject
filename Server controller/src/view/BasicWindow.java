package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * The Class BasicWindow. The Class BasicWindow. This class represent a basic window with the main event loop, 
 * the abstract method, initWidget() should be implemented in order to fill the window
 */
public abstract class BasicWindow extends Observable implements Runnable{

	/** The display. */
	Display display; 
	
	/** The shell. */
	Shell shell;
	
	/**
	 * Instantiates a new basic window.
	 *
	 * @param display the display
	 * @param shell the shell
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public BasicWindow(Display display,Shell shell,String title, int width, int height) {
		this.display=display;
		this.shell=shell;
		shell.setText(title);  
		shell.setSize(width,height); 
	}
	
	/**
	 * Instantiates a new basic window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public BasicWindow(String title, int width, int height) {
		display=new Display(); 
		shell=new Shell(display);
		shell.setText(title);
		shell.setSize(width,height); 
	}

	/**
	 * Inits the widgets.
	 */
	abstract void initWidgets();

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		initWidgets();
		shell.open();

		while(!shell.isDisposed()){

			if(!display.readAndDispatch()){ 	
				display.sleep(); 			
			}

		} 

		display.dispose();

	}

}
