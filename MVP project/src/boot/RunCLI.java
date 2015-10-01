package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import generic.Preferences;
import model.MyModel;
import presenter.MyPresenter;
import view.MyCliView;

public class RunCLI {
	

	public void startProgram(Preferences preferences ) {
		MyCliView view=new MyCliView(new PrintWriter(System.out),new BufferedReader(new InputStreamReader(System.in)));
		MyModel model;
		model=new MyModel(preferences );
		MyPresenter p=new MyPresenter(view,model);
		view.addObserver(p);
		model.addObserver(p);
		view.start();
	}

}
