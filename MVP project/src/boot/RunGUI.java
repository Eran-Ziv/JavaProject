package boot;

import generic.Preferences;
import model.ClientModel;
import model.MyModel;
import presenter.MyPresenter;
import view.MazeWindow;



/**
 * The Class RunGUI. Runs a GUI instance from the main class {@see Run}
 */
public class RunGUI {

	/**
	 * Start.
	 *
	 * @param preferences the preferences
	 */
	public void start(Preferences preferences){


		switch(preferences.getAccess()){

		case LOCAL:
			MyModel model = new MyModel(preferences);
			MazeWindow guiView=new MazeWindow("bobo", 1300, 700);
			MyPresenter pMaze=new MyPresenter(guiView,model);
			guiView.addObserver(pMaze);
			model.addObserver(pMaze);
			guiView.start();
			break;

		case REMOTE_SERVER:
			ClientModel clientModel = new ClientModel(preferences);
			guiView=new MazeWindow("bobo", 1300, 700);
			pMaze=new MyPresenter(guiView,clientModel);
			guiView.addObserver(pMaze);
			clientModel.addObserver(pMaze);
			guiView.start();
			break;

		default:
			return;	
		}

	}

}
