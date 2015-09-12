package controller;

import java.io.Closeable;
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
		commands.put("size in file", new ModelSizeInFileCommand());
		commands.put("size in memory", new ModelSizeInMemoryCommand());

		return commands;
	}

	public void start () {

		this.view.start();
	}

	
	public class SolveModelCommand implements Command, Closeable, Runnable{

		String [] args;
		boolean close;


		@Override
		public void run() {

			model.addThreads(this);
			String name = args[1];
			String algorithm = args[2];
			String heuristic;
			if(args[3] != null){
				heuristic = args[3];
			}
			else{

				heuristic = "default";
			}
			model.solveModel(name, algorithm, heuristic);
		}

		@Override
		public void close() throws IOException {

			close=false;
			this.close();
		}

		@Override
		public void doCommand(String[] args) {

			for (int i = 0; i < args.length; i++) {
				args[i] = args[i].toLowerCase();
			}

			if(model.getNameToModel(args[1]) != null&& (args[2].equals("bfs")||args[2].equals("astar"))/*heuristic valid*/){
				this.close = true;
				this.args = args;
				this.run();
			}
			else
				view.displayError("invalid paramters");

		}

	}


	public class GenerateModelCommand implements Command, Closeable, Runnable{

		String [] args;
		Boolean close;

		@Override
		public void run() {

			model.addThreads(this);
			String name = args[3];
			String [] params = new String[3];
			params[0] = args[4];
			params[1] = args[5];
			params[2] = args[6];

			try {
				model.generateModel(name,params);
			} catch (Exception e) {
				view.displayError("Invalid arguments");
			}
		}

		@Override
		public void close() throws IOException {

			close=false;
			this.close();

		}
	

		@Override
		public void doCommand(String[] args) {

			try{
				int height=Integer.parseInt(args[4]);
				int length=Integer.parseInt(args[5]);
				int width=Integer.parseInt(args[6]);
				if(height>0 && length>0 && width>0){

					this.args = args;
					this.close = true;
					this.run();
				}
			} catch (NumberFormatException e) {
			
				view.displayError("invalid parameters");
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
					view.displayError("Error, no arguments");

				}


			}
		}

		public class DisplayModelCommand implements Command{

			@Override
			public void doCommand(String[] args) {
				Searchable<Position> myMazeSearchableAdapter; 
				Maze3dDrawableAdapter myMaze3dDrawAdapter;
				Maze2dDrawableAdapter myMaze2dDrawAdapter;

				switch (args[1]) {
				case "maze":

					myMazeSearchableAdapter = model.getNameToModel(args[2]);
					if(myMazeSearchableAdapter != null){
						Maze3d myMaze = ((Maze3dSearchableAdapter) myMazeSearchableAdapter).getMaze();
						myMaze3dDrawAdapter = new Maze3dDrawableAdapter(myMaze); 
						view.displayModel(myMaze3dDrawAdapter);
					}
					else{
						view.displayError("Invalid values");	
					}

					break;

				case "solution":

					Solution<Position> solution= model.getSolution(args[2]);
					if (solution!=null)
						view.displaySolution(solution);
					else 
						view.displayError(" invaild args.");
					break;

				case "cross":

					String name = args[7];
					String dimention = args[4];
					int section = Integer.parseInt(args[5]);

					myMazeSearchableAdapter = model.CrossSectionBy(name, dimention, section);
					if(myMazeSearchableAdapter != null){
						Maze2d myMaze2d = ((Maze2dSearchableAdapter) myMazeSearchableAdapter).getMyMaze();
						myMaze2dDrawAdapter = new Maze2dDrawableAdapter(myMaze2d); 
						view.displayModel(myMaze2dDrawAdapter);
					}
					else{
						view.displayError("Invalid values");	
					}

				default:
					view.displayError(" invaild args.");
					break;
				}
			}
		}



		public class SaveModelCommand implements Command{

			@Override
			public void doCommand(String[] args) {


				model.saveModel(args[2], args[3]);

			}
		}

		public class LoadModelCommand implements Command{

			@Override
			public void doCommand(String[] args) {

				model.loadModel(args[2], args[3]);

			}
		}

		public class ModelSizeInMemoryCommand implements Command{

			@Override
			public void doCommand(String[] args) {

				model.getModelSizeInMemory(args[1]);

			}

		}

		public class ModelSizeInFileCommand implements Command{

			@Override
			public void doCommand(String[] args) {

				model.getModelSizeInFile(args[1]);

			}

		}

		public class Exit implements Command{

			@Override
			public void doCommand(String[] args) {

				model.exit();
				//view.exit();

			}

		}
		
	}




	

