package controller;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
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
		commands.put("disply maze", new DisplayCommand());
		commands.put("display solution", new DisplayCommand());
		commands.put("display croos section by", new DisplayCommand());
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
			
			String name;
			String algorithm;
			String heuristic ;
			
			
	
			model.solveModel(name, algorithm, heuristic);
		}

		@Override
		public void close() throws IOException {
			
			
		}

		@Override
		public void doCommand(String[] args) {
			//TODO- validate args.
			this.args = args;
			this.close = true;
			this.run();
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
			//TODO- validate args
			
			
		}
	}
	
	public class DisplayCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			
			
			
			
			
		}
	}
	
	
	public class SaveModelCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class LoadModelCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class ModelSizeInMemoryCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class ModelSizeInFileCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}
