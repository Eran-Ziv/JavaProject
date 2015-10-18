package boot;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.widgets.Shell;

import generic.Constant;
import generic.Preferences;
import view.ClassInputDialog;





/**
 * The Class WritePropertiesGUI.
 *  @author Eran & Ziv
 */
public class WritePropertiesGUI {
	
	/**
	 * Write properties.
	 *
	 * @param shell the shell
	 * @return the int
	 */
	public int writeProperties(Shell shell)
	{
		XMLEncoder e;
		

	    ClassInputDialog dlg = new ClassInputDialog(shell,Preferences.class);
	    Preferences input = (Preferences) dlg.open();
	    if (input != null) {
	     
	    	try {
				e = new XMLEncoder(new FileOutputStream(Constant.XML_FILE_PATH));
				e.writeObject(input);
				e.flush();
				e.close();
				return 0;
	    	} catch (FileNotFoundException e1) {
				
				e1.printStackTrace();
			}
			
	    }
	  
	    return -1;
	}
}
