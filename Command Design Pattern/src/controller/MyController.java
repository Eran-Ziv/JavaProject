package controller;

import java.io.File;

import view.View;
import model.Model;

public class MyController implements Controller {

	private Model myModel;
	private View myView;
	
	public MyController(Model myModel, View myView) {
		super();
		this.myModel = myModel;
		this.myView = myView;
	}


	private class Dir implements Command{

		@Override
		public void doCommand(String[] args) {
			
			File file = new File(args[1]);
			File[] listOfFiles = file.listFiles();
			for (File files : listOfFiles) {
			    if (files.isFile()) {
			        System.out.println(files.getName());
			    }
			    if(files.isDirectory()){
			    	System.out.println(files.getName());
			    }
			}
			
			
		}
		
	}
	
	private class generate3dmaze implements Command{

		private String name;
		
		
		@Override
		public void doCommand(String[] args) {
			
			
		}
		
		
		
		
	}
	
	public Model getMyModel() {
		return myModel;
	}

	public void setMyModel(Model myModel) {
		this.myModel = myModel;
	}

	public View getMyView() {
		return myView;
	}

	public void setMyView(View myView) {
		this.myView = myView;
	}

	
	
	
}
