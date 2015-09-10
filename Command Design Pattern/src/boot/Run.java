package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import controller.Controller;
import controller.MyController;
import model.Model;
import model.MyModel;
import view.CLI;
import view.MyView;
import view.View;

public class Run {

	public static void main(String[] args) {
		
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter print = new PrintWriter(System.out);
		Model myModel = new MyModel();
		CLI myCli = new CLI(print,buf);
		View myView = new MyView(myCli);
		
		Controller myController = new MyController(myView, myModel);
		myController.start();
		
		

	}

}
