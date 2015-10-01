package boot;

import generic.Preferences;
import model.MyModel;
import presenter.MyPresenter;
import view.MazeWindow;

public class RunGUI {
	
	public void start(Preferences preferences){
		MazeWindow view=new MazeWindow("Maze Generations", 600, 600);
		MyModel model;
		model=new MyModel(preferences);
		MyPresenter p=new MyPresenter(view,model);
		view.addObserver(p);
		model.addObserver(p);
		view.start();
	}

}
