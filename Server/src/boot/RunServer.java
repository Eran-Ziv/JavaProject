package boot;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import server.ServerProperties;
import server.UDPMazeServerRemoteControl;
import view.ClassInputDialog;


/**
 * The Class RunServer. Runing the server app.
 */
public class RunServer {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		Display display=new Display();
		Shell shell=new Shell(display);
		ClassInputDialog dlg=new ClassInputDialog(shell, ServerProperties.class);
		ServerProperties properties=(ServerProperties)dlg.open();
		if(properties!=null)
		{
			MessageBox messageBox = new MessageBox(shell,SWT.OK | SWT.Activate);
			messageBox.setText("Maze Generations");
			messageBox.setMessage("Server is Operating");
			messageBox.open();
			new UDPMazeServerRemoteControl(properties).run();
		}
	}

}
