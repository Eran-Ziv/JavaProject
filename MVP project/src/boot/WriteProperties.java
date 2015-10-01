package boot;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;

import generic.Preferences;


/** This class main has to run prior to running main Run. it will set the Default Properties.
 * @author Tomer
 *
 */
public class WriteProperties {

	public static void main(String[] args) {
		
		XMLEncoder e;
		Preferences prop=new Preferences();//fine with the defaults I have defined
		try {
			e = new XMLEncoder(new FileOutputStream("properties.xml"));
			e.writeObject(prop);
			e.flush();
			e.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		

		
	}

}
