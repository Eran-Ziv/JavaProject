package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class test {

	public static void main(String[] args) {
		
		Display display = new Display();
		Shell shell = new Shell();
		
		String title = "Test";
		int width = 250;
		int height = 500;
		MazeWindow maze = new MazeWindow(display, shell, title, width, height);
		Thread t = new Thread(maze);
		
		t.start();

	}

}
