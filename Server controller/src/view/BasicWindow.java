package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow extends Observable implements Runnable{

	Display display; 
	Shell shell;
	public BasicWindow(Display display,Shell shell,String title, int width, int height) {
		this.display=display;
		this.shell=shell;
		shell.setText(title);  
		shell.setSize(width,height); 
	}
	public BasicWindow(String title, int width, int height) {
		display=new Display(); 
		shell=new Shell(display);
		shell.setText(title);
		shell.setSize(width,height); 
	}
	
	abstract void initWidgets();
	
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
