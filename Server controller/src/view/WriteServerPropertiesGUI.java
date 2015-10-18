package view;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import presenter.RemoteControlProperties;



/**
 * The Class WriteServerPropertiesGUI.
 *  @author Eran & Ziv
 */
public class WriteServerPropertiesGUI {
	
	/**
	 * Write properties.
	 *
	 * @param display the display
	 * @param shell the shell
	 * @return the int
	 */
	public int writeProperties(Display display, Shell shell)
	{
		XMLEncoder e;
		

	    ClassInputDialog dlg = new ClassInputDialog(shell,RemoteControlProperties.class);
	    RemoteControlProperties input = (RemoteControlProperties) dlg.open();
	    if (input != null) {
	      // User clicked OK; set the text into the label
	    	try {
				e = new XMLEncoder(new FileOutputStream(".\\resources\\config\\properties.xml"));
				e.writeObject(input);
				e.flush();
				e.close();
				return 0;
	    	} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	    }
	    return -1;
	   
	}
}
