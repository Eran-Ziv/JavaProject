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

	// TODO- all the commands must be validated first, this is just the actions.

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
			String name = args[2];
			int height=Integer.parseInt(args[3]);
			int length=Integer.parseInt(args[4]);
			int width=Integer.parseInt(args[5]);
			model.generateModel(name, height, length, width);
		}

		@Override
		public void close() throws IOException {

			close=false;
			this.close();

		}

		@Override
		public void doCommand(String[] args) {
			//TODO- validate args
			int height=Integer.parseInt(args[3]);
			int length=Integer.parseInt(args[4]);
			int width=Integer.parseInt(args[5]);
			if(height>0 && length>0 && width>0){

				this.args = args;
				this.close = true;
				this.run();
			}
			else
				view.displayError("invalid parameters");
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
			System.out.println("ddd");
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
				
				Solution<Position> solution= model.getSolution(args[1]);
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

	public class Exit implements Command{

		@Override
		public void doCommand(String[] args) {

			model.exit();

		}

	}




}
