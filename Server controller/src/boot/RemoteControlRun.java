package boot;


import model.RemoteControlModel;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import presenter.Presenter;
import view.ServerWindow;
import view.WriteServerPropertiesGUI;


/**
 * The Class RemoteControlRun.
 *  @author Eran & Ziv
 */
public class RemoteControlRun {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		WriteServerPropertiesGUI sp = new WriteServerPropertiesGUI(); 
		Display display= new Display(); 
		Shell shell=new Shell(display); 
		sp.writeProperties(display, shell); 
		ServerWindow SE= new ServerWindow("Remote maze client",500,500,display,shell);
		RemoteControlModel m = new RemoteControlModel(ServerWindow.readProperties()); 
		Presenter p = new Presenter(m, SE); 
		m.addObserver(p); 
		SE.addObserver(p);
		SE.run();

	}
}

