package boot;

import generic.Constant;
import generic.Preferences;


import java.beans.XMLDecoder;
import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


import presenter.MyPresenter;
import model.ClientModel;
import model.MyModel;

import view.MazeWindow;
import view.MyCliView;



public class Run {

	public static void main(String[] args) {


		WritePropertiesGUI guiProp=new WritePropertiesGUI();
		Display display=new Display();
		Shell shell=new Shell(display);
		guiProp.writeProperties(shell);
		MyModel model;
		ClientModel clientModel;
		Preferences preferences;
		if((preferences=readPreferences())!=null)
		{
			model=new MyModel(preferences);
			switch(preferences.getAccess()){

			case LOCAL:

				switch(preferences.getUi())
				{
				case CLI:
					model = new MyModel(preferences);
					MyCliView view=new MyCliView(new PrintWriter(System.out),new BufferedReader(new InputStreamReader(System.in)));
					MyPresenter p=new MyPresenter(view,model);
					view.addObserver(p);
					model.addObserver(p);
					view.start();
					break;
				case GUI:
					model = new MyModel(preferences);
					MazeWindow guiView=new MazeWindow(display, shell, "bobo", 1300, 700);
					MyPresenter pMaze=new MyPresenter(guiView,model);
					guiView.addObserver(pMaze);
					model.addObserver(pMaze);
					guiView.start();
					break;
				default:
					return;	
				}
				break;	
			case REMOTE_SERVER:

				switch(preferences.getUi())
				{
				case CLI:
					clientModel = new ClientModel(preferences);
					MyCliView view=new MyCliView(new PrintWriter(System.out),new BufferedReader(new InputStreamReader(System.in)));
					MyPresenter p=new MyPresenter(view,clientModel);
					view.addObserver(p);
					clientModel.addObserver(p);
					view.start();
					break;
				case GUI:
					clientModel = new ClientModel(preferences);
					MazeWindow guiView=new MazeWindow(display, shell, "bobo", 1300, 700);
					MyPresenter pMaze=new MyPresenter(guiView,clientModel);
					guiView.addObserver(pMaze);
					clientModel.addObserver(pMaze);
					guiView.start();
					break;
				default:
					return;	
				}
				break;

			default:
				return;
			}

		}
		else
			return;

	}

	public static Preferences readPreferences()
	{
		XMLDecoder d;
		Preferences p=null;
		try {
			BufferedInputStream in=new BufferedInputStream(new FileInputStream(Constant.XML_FILE_PATH));
			d=new XMLDecoder(in);
			p=(Preferences)d.readObject();
			System.out.println(p);
			d.close();
		} catch (IOException e) {
			return new Preferences();
		}
		return p;
	}
}
