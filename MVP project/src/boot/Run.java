package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import presenter.MyPresenter;
import presenter.Presenter;
import model.MyModel;
import view.MyCliView;



public class Run {

	public static void main(String[] args) {
	
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		MyModel myModel = new MyModel();
		MyCliView myView = new MyCliView(out, in);
		
		Presenter presenter = new MyPresenter(myView, myModel);
		myView.addObserver(presenter);
		myModel.addObserver(presenter);
		presenter.start();
		
		

	}

}
