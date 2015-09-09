package controller;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import view.View;
import model.Model;


public class MyController implements Controller {

	private Model model;
	private View  view;
	HashMap<String, Command> commands;

	public MyController() {

		view.setCommands(getCommands());
	}


	public HashMap<String, Command> getCommands() {

		this.commands = new HashMap<String, Command>();

		commands.put("dir", new DirCommand());
		commands.put("solve", new SolveModelCommand());
		commands.put("generate", new GenerateModelCommand());
		commands.put("display maze", new DisplayModelCommand());
		commands.put("display solution", new DisplayModelCommand());
		commands.put("display cross section by", new DisplayModelCommand());
		commands.put("save", new SaveModelCommand());
		commands.put("load", new LoadModelCommand());
		commands.put("size in file", new ModelSizeInFileCommand());
		commands.put("size in memory", new ModelSizeInMemoryCommand());

		return commands;
	}

	// TODO- all the commands must be validated first, this is just the actions.

	public class SolveModelCommand implements Command, Closeable, Runnable{

		String [] args;
		boolean close;

		@Override
		public void run() {

			String name = args[1];
			String algorithm = args[2];
			String heuristic = args[3] ;



			model.solveModel(name, algorithm, heuristic);
		}

		@Override
		public void close() throws IOException {

			this.close();
		}

		@Override
		public void doCommand(String[] args) {
			//TODO- validate args.
			args[2]=args[2].toLowerCase();
			if(model.getNameToModel(args[1]) != null&& (args[2].equals("bfs")||args[2].equals("astar"))){
				this.args = args;
				this.close = true;
				this.run();
			}

		}

	}


	public class GenerateModelCommand implements Command, Closeable, Runnable{

		String [] args;
		Boolean close;

		@Override
		public void run() {


		}

		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void doCommand(String[] args) {
			//TODO- validate args
			this.args = args;
			this.close = true;
			this.run();

		}
	}

	public class DirCommand implements Command{

		@Override
		public void doCommand(String[] args) {

			String fileName= args[1];
			view.dirCommand(fileName);


		}
	}

	public class DisplayModelCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			byte [] byteArray =null;
			switch (args[1]) {
			case "maze":
				byteArray = model.getNameToModel(args[2]);
				if(byteArray!=null)
					view.displayModel(byteArray);
				else
					view.displayError(" invaild name. ");
				break;

			case "solution":
				Solution<Position> solution= model.solveModel(args[1], args[2], args[3]);
				if (solution!=null)
					view.displaySolution(solution);
				else 
					view.displayError(" invaild args.");
				break;

			case "cross":

				byteArray = model.CrossSectionBy(args[7], args[4].charAt(0),Integer.parseInt(args[5]));
				if(byteArray!=null)
					view.displayCrossSectionBy(byteArray);
				else
					view.displayError(" invaild args.");
				break;

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




}
