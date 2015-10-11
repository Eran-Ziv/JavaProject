package presenter;

import generic.Constant;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import algorithm.generic.Solution;
import algorithms.demo.Maze2dSearchableAdapter;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.Maze2d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Searchable;
import view.View;
import model.Model;


/**
 * The Class MyController.
 * Specific implementation of Controller {@see Controller}
 * Uses maze3d {@see Maze3d} for model.
 */
public class MyPresenter implements Presenter {

	/** The model. */
	private Model model;

	/** The view. */
	private View  view;

	/** The commands. */
	HashMap<String, Command> commands;

	/**
	 * Instantiates a new my controller.
	 *
	 * @param myView the my view
	 * @param myModel the my model
	 */
	public MyPresenter(View myView, Model myModel) {

		this.view = myView;
		this.model = myModel;
		view.setCommands(getCommands()); 
	}

	/* (non-Javadoc)
	 * @see controller.Controller#getCommands()
	 */
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


	public class SolveModelCommand implements Command{

		/** The args. */
		String [] args;

		/** The my thread. */
		Thread myThread;



		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {


			String name = args[1];


			try {


				model.solveModel(name);

			}catch (ArrayIndexOutOfBoundsException e){

				model.solveModel(name);
			}

		}
		@Override
		public void setArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class GenerateModelCommand.
	 * Generates the maze at a diffrent thread.
	 */
	public class GenerateModelCommand implements Command{

		/** The args. */
		String [] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try{
				int height=Integer.parseInt(args[4]);
				int length=Integer.parseInt(args[5]);
				int width=Integer.parseInt(args[6]);

				if(height>0 && length>0 && width>0){

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

			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

				view.displayString("invalid parameters");
			}
		}

		@Override
		public void setArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class DirCommand.
	 * 
	 */
	public class DirCommand implements Command{

		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

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

		@Override
		public void setArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class DisplayModelCommand.
	 */
	public class DisplayModelCommand implements Command{

		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {
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

		@Override
		public void setArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class SaveModelCommand.
	 */
	public class SaveModelCommand implements Command{

		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try{
				model.saveModel(args[2], args[3]);}
			catch (ArrayIndexOutOfBoundsException e){
				view.displayString("Invalid input");
			}
		}

		@Override
		public void setArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class LoadModelCommand.
	 */
	public class LoadModelCommand implements Command{

		String[] args;
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try {
				model.loadModel(args[2], args[3]);
			} catch (IOException e) {
				view.displayString("File not found.");

			}catch(ArrayIndexOutOfBoundsException c){
				view.displayString("Invalid input");
			}
		}
		@Override
		public void setArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class ModelSizeInMemoryCommand.
	 */
	public class ModelSizeInMemoryCommand implements Command{

		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

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

		@Override
		public void setArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class ModelSizeInFileCommand.
	 */
	public class ModelSizeInFileCommand implements Command{

		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try{
				long size = model.getModelSizeInFile(args[2]);
				view.displayString("Maze size in file: " + size + " bytes");
			}catch(ArrayIndexOutOfBoundsException e){
				view.displayString("Invalid args");
			}
		}

		@Override
		public void setArguments(String[] args) {
			this.args = args;

		}
	}

	/**
	 * The Class ExitCommand.
	 */
	public class ExitCommand implements Command{

		String[] args;

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand() {

			try {
				model.exit();
				view.exit();
			} catch (IOException e) {
				view.displayString("Can't close thread");
			}
		}

		@Override
		public void setArguments(String[] args) {
			this.args = args;

		}
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof View)
		{
			
			if(arg!=null && !arg.equals(Constant.MODEL_ERROR))
			{

				Command command= view.getUserCommand();
				command.doCommand();
			}

		}
		else if(o instanceof Model){

			
			String [] args = (String[])arg;
			switch (args[0]){

			case Constant.MODEL_SAVED:

				view.displayString(args[1]+" was saved");

				break;

			case Constant.FILE_NOT_FOUND:

				view.displayString(Constant.FILE_NOT_FOUND);

				break;
			case Constant.NO_MODEL_FOUND:

				view.displayString(Constant.NO_MODEL_FOUND);

				break;
			case Constant.ERROR_CLOSING_FILE:

				view.displayString(Constant.ERROR_CLOSING_FILE);

				break;

			case Constant.MODEL_SOLVED:

				//view.displayString(Constant.MODEL_SOLVED);
				if(model.getSolution(args[1]) != null){
				view.displaySolution(model.getSolution(args[1]));
				}
				else{
					view.displayString("Solution is not ready");
				}

				break;

			case Constant.MODEL_LOADED:

				view.displayString(Constant.MODEL_LOADED);


				break;

			case Constant.PROPERTIES_ARE_NO_SET:

				view.displayString(Constant.PROPERTIES_ARE_NO_SET);

				break;

			case Constant.MODEL_GENERATED:
				Maze3dSearchableAdapter maze = new Maze3dSearchableAdapter((Maze3d) model.getNameToModel(args[1]));
				Maze3dDrawableAdapter mazeDrew = new Maze3dDrawableAdapter(maze.getMaze());
				view.displayModel(mazeDrew);

				break;

			case Constant.MODEL_EXIT:

				view.displayString(Constant.MODEL_EXIT);

				break;
			case Constant.MODEL_ERROR:

				view.displayString(Constant.MODEL_ERROR);

				break;

			}
		}
	}

	@Override
	public void start() {

		view.start();

	}
}
