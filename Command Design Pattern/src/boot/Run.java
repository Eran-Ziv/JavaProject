package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import controller.Controller;
import controller.MyController;
import model.Model;
import model.MyModel;
import view.MyView;
import view.View;


/**
 * The Class Run.
 *  @author Eran & Ziv
 */
public class Run {

	/**
	 * The test method.
	 *
	 * @param args the arguments
	 */
	public void test() {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		Model myModel = new MyModel();
		View myView = new MyView(out, in);

		Controller myController = new MyController(myView, myModel);
		myController.start();



	}

}
