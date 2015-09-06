package controller;

import java.util.HashMap;

import view.View;
import model.Model;


public class MyController implements Controller {

	private Model model;
	private View view;

	public MyController(Model model, View view) {
		this.model = model;
		this.view = view;
	}

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



	public class DirCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			if(args[1]!=null)
				view.dirCommand(args);

		}

	}

	public class DisplayMaze implements Command{

		@Override
		public void doCommand(String[] args) {

			model.loadModel(args);

		}






	}
}
