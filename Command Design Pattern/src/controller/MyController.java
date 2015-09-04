package controller;

import view.MyView;
import view.View;
import model.Model;
import model.MyModel;

public class MyController implements Controller {

	private MyModel myModel;
	private MyView myView;
	public MyModel getMyModel() {
		return myModel;
	}
	public void setMyModel(MyModel myModel) {
		this.myModel = myModel;
	}
	public MyView getMyView() {
		return myView;
	}
	public void setMyView(MyView myView) {
		this.myView = myView;
	}
	
	

	
	
	
}
