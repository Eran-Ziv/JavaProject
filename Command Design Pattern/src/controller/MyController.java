package controller;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import algorithm.generic.Solution;
import algorithms.demo.Maze2dSearchableAdapter;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.Maze2d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;
import view.View;
import model.Model;


public class MyController implements Controller {

	private Model model;
	private View  view;
	HashMap<String, Command> commands;

	public MyController(View myView, Model myModel) {

		this.view = myView;
		this.model = myModel;
		view.setCommands(getCommands());

	}


	public HashMap<String, Command> getCommands() {

		this.commands = new HashMap<String, Command>();

		commands.put("dir", new DirCommand());
		commands.put("solve", new SolveModelCommand());
		commands.put("generate", new GenerateModelCommand());
		commands.put("display", new DisplayModelCommand());
		commands.put("save", new SaveModelCommand());
		commands.put("load", new LoadModelCommand());
		commands.put("file", new ModelSizeInFileCommand());//size
		commands.put("maze", new ModelSizeInMemoryCommand());//size
		commands.put("exit", new ExitCommand());

		return commands;
	}

	public void start () {

		this.view.start();
	}


	public class SolveModelCommand implements Command,  Runnable{

		String [] args;
		Thread myThread;


		@Override
		public void run() {



			String name = args[1];
			String algorithm = args[2];
			String heuristic;

			try {

				heuristic = args[3];
				model.solveModel(name, algorithm, heuristic);

			}catch (ArrayIndexOutOfBoundsException e){

				heuristic = "default";
				model.solveModel(name, algorithm, heuristic);

			}

			if(model.getSolution(name) == null){
				view.displayString("The model " + name + " does not exist." );
			}
		}






		@Override
		public void doCommand(String[] args) {

			for (int i = 0; i < args.length; i++) {
				args[i] = args[i].toLowerCase();
			}

			if(args.length >= 3){

				this.args = args;
				myThread = new Thread(this);
				myThread.start();
			}
			else
				view.displayString("invalid paramters");

		}

	}


	public class GenerateModelCommand implements Command,  Runnable{

		String [] args;
		Thread myThread;

		@Override
		public void run() {




			try {
				String name = args[3];
				String [] params = new String[3];
				params[0] = args[4];
				params[1] = args[5];
				params[2] = args[6];

				model.generateModel(name,params);

			} catch (ArrayIndexOutOfBoundsException e) {
				view.displayString("Invalid arguments");
			}

		}




		@Override
		public void doCommand(String[] args) {

			try{
				int height=Integer.parseInt(args[4]);
				int length=Integer.parseInt(args[5]);
				int width=Integer.parseInt(args[6]);

				if(height>0 && length>0 && width>0){

					this.args = args;

					myThread = new Thread(this);
					myThread.start();

				}
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

				view.displayString("invalid parameters");
			}
		}
	}

	public class DirCommand implements Command{

		@Override
		public void doCommand(String[] args) {

			try{
				if(args[1] != null){
					String fileName= args[1];
					view.dirCommand(fileName);
				}
			}
			catch (ArrayIndexOutOfBoundsException e){
				view.displayString("Error, no arguments");

			}
		}
	}


	public class DisplayModelCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			Searchable<Position> myMaze3dSearchableAdapter; 
			Searchable<Position> myMaze2dSearchableAdapter; 
			Maze3dDrawableAdapter myMaze3dDrawAdapter;
			Maze2dDrawableAdapter myMaze2dDrawAdapter;

			try{
				if(args[1] != null){
					switch (args[1]) {
					case "maze":

						myMaze3dSearchableAdapter = model.getNameToModel(args[2]);
						if(myMaze3dSearchableAdapter != null){
							Maze3d myMaze = ((Maze3dSearchableAdapter) myMaze3dSearchableAdapter).getMaze();
							myMaze3dDrawAdapter = new Maze3dDrawableAdapter(myMaze); 
							view.displayModel(myMaze3dDrawAdapter);
						}
						else{
							view.displayString("Invalid values");	
						}

						break;

					case "solution":

						if(model.getNameToModel(args[2]) == null){
							view.displayString("No record of " + args[2]+ ". Try to create it first");
						}
						else{
							Solution<Position> solution= model.getSolution(args[2]);
							if (solution!=null){
								view.displaySolution(solution);
							}
							else{
								view.displayString("No solution for " + args[2]+ ". Try to create it first");
							}

						}

						break;

					case "cross":

						String name = args[7];
						String dimention = args[4];
						int section = Integer.parseInt(args[5]);


						myMaze2dSearchableAdapter = model.CrossSectionBy(name, dimention, section);
						if(myMaze2dSearchableAdapter != null){
							Maze2d myMaze2d = ((Maze2dSearchableAdapter) myMaze2dSearchableAdapter).getMyMaze();
							myMaze2dDrawAdapter = new Maze2dDrawableAdapter(myMaze2d); 
							view.displayCrossSectionBy(myMaze2dDrawAdapter);
						}
						else{
							view.displayString("Invalid values");	
						}
						break;

					default:
						view.displayString(" invaild args.");
						break;
					}
				}
			}catch (ArrayIndexOutOfBoundsException | NullPointerException e){
				view.displayString("Invalid values");
			}
		}
	}



	public class SaveModelCommand implements Command{

		@Override
		public void doCommand(String[] args) {

			try{
				model.saveModel(args[2], args[3]);}
			catch (ArrayIndexOutOfBoundsException e){
				view.displayString("Invalid input");
			}

		}
	}

	public class LoadModelCommand implements Command{

		@Override
		public void doCommand(String[] args) {

			try {
				model.loadModel(args[2], args[3]);
			} catch (IOException e) {
				view.displayString("File not found.");


			}
		}
	}

	public class ModelSizeInMemoryCommand implements Command{

		@Override
		public void doCommand(String[] args) {

			if(model.getNameToModel(args[2]) != null){

				int size;
				try {
					size = model.getModelSizeInMemory(args[2]);
					view.displayString("Maze size in memory: " + size + " bytes");
				} catch (IOException e) {

					view.displayString("Invalid arguments");
				}
			}
			else{
				view.displayString("No such name exist");
			}

		}

	}

	public class ModelSizeInFileCommand implements Command{

		@Override
		public void doCommand(String[] args) {

			try{
				long size = model.getModelSizeInFile(args[2]);
				view.displayString("Maze size in file: " + size + " bytes");
			}catch(ArrayIndexOutOfBoundsException e){
				view.displayString("Invalid args");
			}
		}

	}

	public class ExitCommand implements Command{

		@Override
		public void doCommand(String[] args) {

			try {
				model.exit();
			} catch (IOException e) {
				view.displayString("Can't close thread");
			}


		}

	}

}









