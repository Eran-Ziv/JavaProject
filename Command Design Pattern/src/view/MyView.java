package view;

import controller.Command;
import controller.Controller;
import controller.MyController;

public class MyView implements View {

	
	private MyController myController;
	
	
	public MyView(MyController myController) {
		
		
	}

	public class Display implements Command{

		@Override
		public void doCommand(String[] args) {
			
		myController.getMyModel().getMazes();
			
			
		}
		
		
		
	}
}
