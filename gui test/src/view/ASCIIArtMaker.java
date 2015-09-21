package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

public class ASCIIArtMaker extends BasicWindow {

	String fileName;
	
	public ASCIIArtMaker(int width, int height) {
		super(width, height);
				
	}

	@Override
	public void initWidget() {
		super.shell.setLayout(new org.eclipse.swt.layout.GridLayout(2, false));
		
		Button openButton = new Button(super.shell, SWT.PUSH);
		openButton.setText("Open image file");
		openButton.setLayoutData(new GridData(SWT.FILL , SWT.None, false, false, 1, 1));
		
		openButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.jpg", "*.gif", "*.png"};
				fd.setFilterExtensions(filterExt);
				fileName = fd.open();

			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Text text = new Text(super.shell, SWT.MULTI | SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true,true,1,2));
		
		Button convert = new Button(super.shell, SWT.PUSH);
		convert.setText("Convert to ASCII art");
		convert.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
	}

}
