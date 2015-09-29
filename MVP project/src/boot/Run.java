package boot;

import generic.Constant;
import generic.Preferences;
import generic.Preferences.MazeGenerator;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
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
		Preferences myPref = readPreferences();
		
		MyModel myModel = new MyModel(myPref);
		MyCliView myView = new MyCliView(out, in);
		
		Presenter presenter = new MyPresenter(myView, myModel);
		myView.addObserver(presenter);
		myModel.addObserver(presenter);
		presenter.start();
		
		

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
