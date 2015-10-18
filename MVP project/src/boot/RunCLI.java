package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import generic.Preferences;
import model.ClientModel;
import model.MyModel;
import presenter.MyPresenter;

import view.MyCliView;

/**
 * The Class RunCLI.
 * @author Eran & Ziv
 */
public class RunCLI {
	

	/**
	 * Start program.
	 *
	 * @param preferences the preferences
	 */
	public void startProgram(Preferences preferences ) {

		switch(preferences.getAccess()){
	case LOCAL:

			MyModel model = new MyModel(preferences);
			MyCliView view=new MyCliView(new PrintWriter(System.out),new BufferedReader(new InputStreamReader(System.in)));
			MyPresenter p=new MyPresenter(view,model);
			view.addObserver(p);
			model.addObserver(p);
			view.start();
			break;
		
	case REMOTE_SERVER:

		
			ClientModel clientModel = new ClientModel(preferences);
			view=new MyCliView(new PrintWriter(System.out),new BufferedReader(new InputStreamReader(System.in)));
			 p=new MyPresenter(view,clientModel);
			view.addObserver(p);
			clientModel.addObserver(p);
			view.start();
			break;
		
		default:
			return;	
		}
	
	}

}
