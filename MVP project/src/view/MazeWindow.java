package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MazeWindow extends BasicWindow {

	public MazeWindow(Display display, Shell shell, String title, int width, int height) {
		super(display, shell, title, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(1, false));
		   MazeDisplay maze=new MazeDisplay(shell, SWT.BORDER);
		   maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true));


	}

}
