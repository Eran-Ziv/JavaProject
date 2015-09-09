package view;

import java.io.File;
import java.util.HashMap;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;

public class MyView implements View {


	private Controller controller;
	private CLI myCli;


	public MyView(Controller controller) {
		this.controller = controller;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void dirCommand(String fileName) {

		File file = new File(fileName);
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


	@Override
	public void displayModel(byte[] byteArray) {

		int height = byteArray[6];
		int length= byteArray[7];
		int width= byteArray[8];
		int size=0;

		for (int i = 0; i < height; i++) {
			System.out.println();
			for (int j = 0; j < length; j++) {
				System.out.println();
				for (int k = 0; k < width; k++) {
					System.out.println(byteArray[size++]);
					if (k + 1 == width)
						System.out.println();
				}
			}
		}
		System.out.println();

	}


	@Override
	public void displayCrossSectionBy(byte [] byteArray) {

		int size= byteArray[0];
		int length=byteArray[1];
		int k=2;
		for (int i = 0; i < size; i++) {
			System.out.println();
			for (int j = 0; j < length; j++) {
				System.out.println(byteArray[k++]);

			}

		}
		System.out.println();
	}

	@Override
	public<T> void displaySolution(Solution<T> s) {

		s.print();

	}


	@Override
	public void setCommands(HashMap<String, Command> commands) {

		this.myCli.setCommands(commands);

	}


	@Override
	public void displayError(String error) {
		System.out.println(error);

	}


}
