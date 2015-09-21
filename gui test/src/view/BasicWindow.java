package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow extends Observable implements Runnable {

	Display display;
	Shell shell;

	public BasicWindow(int width, int height) {
		this.display = new Display();
		this.shell = new Shell(this.display);
		shell.setSize(width,  height);
	}

	public abstract void initWidget();

	@Override
	public void run() {
		initWidget();
		shell.open();

		while(!shell.isDisposed()){

			if(!display.readAndDispatch()){
				display.sleep();
			}
		}

		display.dispose();

	}

}
