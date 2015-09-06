package controller;

import java.util.HashMap;

import view.MyView;
import view.View;
import model.Model;
import model.MyModel;

public class MyController implements Controller {

	private Model model;
	private View view;
	
	
	
	public Model getModel() {
		return model;
	}



	public void setModel(Model model) {
		this.model = model;
	}



	public View getView() {
		return view;
	}



	public void setView(View view) {
		this.view = view;
	}



	@Override
	public HashMap<String, Command> getCommands() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
	
}
