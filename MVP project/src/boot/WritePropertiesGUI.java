package boot;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.widgets.Shell;

import generic.Constant;
import generic.Preferences;
import view.ClassInputDialog;




public class WritePropertiesGUI {
	
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
