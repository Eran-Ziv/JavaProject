package model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import algorithms.demo.Maze3dSearchableAdapter;
import controller.Command;
import controller.Controller;

public class MyModel implements Model {

	private Controller myController;
	private HashMap<String, byte[]> mazes;

	public MyModel(Controller myController,HashMap<String, byte[]> mazes) {
		this.myController = myController;
		this.mazes=mazes;
	}


	public HashMap<String, byte[]> getMazes() {
		return mazes;
	}

	public void setMazes(HashMap<String, byte[]> mazes) {
		this.mazes = mazes;
	}


	public class Dir implements Command{

		@Override
		public synchronized void doCommand(String[] args) {

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

	public class Generate3dmaze implements Command{

		private String name;

		@Override
		public synchronized void  doCommand(String[] args) {

			byte [] b;
			name= args[1];
			Maze3dSearchableAdapter maze = new Maze3dSearchableAdapter(10, 10, 10);
			try {
				b= maze.getMaze().toByteArray();
				mazes.put(name, b);
			} catch (IOException e) {
				e.printStackTrace();

			}



		}




	}

}
