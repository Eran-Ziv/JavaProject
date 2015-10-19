package view;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import presenter.Maze3dDrawableAdapter;
import algorithm.generic.Solution;
import algorithm.generic.State;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;


/**
 * The Class MazeBoard. A spesific implementation for a maze board, extending from CommonBoard {@see CommonBoard} this class is taking care of the operation of a maze board.
 */
public class MazeBoard extends CommonBoard {


	/** The gifs. */
	ImageLoader gifs=new ImageLoader();

	/** The my maze. */
	Maze3dDrawableAdapter myMaze;

	/** The images. */
	ImageData[] images;

	/** The hints. */
	boolean[][][] hints;

	/** The user asked for solution. */
	volatile boolean userAskedForSolution;

	/** The frame index. */
	int frameIndex=0;

	/** The row source x. */
	int rowSourceX;

	/** The row goal x. */
	int rowGoalX;

	/** The col goal y. */
	int colGoalY;

	/** The col source y. */
	int colSourceY;

	/** The current floor z. */
	int currentFloorZ;

	/** The goal floor z. */
	int goalFloorZ;

	/** The position. */
	Position position ;
	
	/** The flag. */
	public boolean flag=true;

	/**
	 * Instantiates a new maze board.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public MazeBoard(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		images=gifs.load(".\\resources\\images\\ChaosEmerald.gif"); 


	}

	/**
	 * Sets the board data.
	 *
	 * @param maze the new board data
	 */
	private void setBoardData(Maze3dDrawableAdapter maze)
	{
		this.myMaze = maze;
		MazeBoard a =this; //for our convenience

		getDisplay().syncExec(new Runnable() {
			public void run() {
				if(board!=null)
					a.destructBoard(); // destory previous maze if exists 
				Maze3d myMaze = maze.getData();


				boardRowsX=myMaze.getLength(); //gets maze rows and cols
				boardColsY = myMaze.getWidth();
				if(flag){
					hints = new boolean[myMaze.getHeight()][myMaze.getLength()][myMaze.getWidth()];
					rowSourceX=myMaze.getStartPosition().getX();
					colSourceY=myMaze.getStartPosition().getY();
					rowGoalX=myMaze.getGoalPosition().getX();
					colGoalY=myMaze.getGoalPosition().getY();
					currentFloorZ = myMaze.getStartPosition().getZ();
					flag = false;
					position=new Position(currentFloorZ, rowSourceX, colSourceY);
					goalFloorZ = myMaze.getGoalPosition().getZ(); 
				}


				GridLayout layout=new GridLayout(boardColsY, true); //defines the grid layout
				layout.horizontalSpacing=0;
				layout.verticalSpacing=0;
				setLayout(layout); //sets the layout
				board=new MazeTile[boardRowsX][boardColsY]; //creates a new maze array

				for(int i=0;i<boardRowsX;i++)
					for(int j=0;j<boardColsY;j++)
					{
						board[i][j]=new MazeTile(a,SWT.NONE);
						board[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
						board[i][j].setCellImage(cellImage(maze,i,j)); //creates a cell and sets the correct image
						if(userAskedForSolution && hints[currentFloorZ][i][j]){

							Image img = new Image(getDisplay(),".\\resources\\images\\star.png"); //hint image
							(board[i][j]).setCellImage(img);
							(board[i][j]).setHint(img);
							(board[i][j]).redraw();
						}

					}

				getShell().layout();

			}
		}); 

	}



	/* (non-Javadoc)
	 * @see view.CommonBoard#destructBoard()
	 */
	public void destructBoard()
	{
		if(board!=null){
			for(int i=0;i<board.length;i++)
			{
				for(int j=0;j<board[0].length;j++)
				{	
					if( board[i][j].getCellImage()!=null)
						( board[i][j]).getCellImage().dispose();
					if(( board[i][j]).getGoal()!=null)
						( board[i][j]).getGoal().dispose();
					if(( board[i][j]).getCharacter()!=null)
						( board[i][j]).getCharacter().dispose();
					if(( board[i][j]).getHint()!=null)
						( board[i][j]).getHint().dispose();

					if(character!=null)
						character.dispose();

					( board[i][j]).dispose();
				}
			}
		}
		if(timer!=null)
			timer.cancel();

	}
	
	/**
	 * Cell image.
	 *
	 * @param maze the maze
	 * @param x the x
	 * @param y the y
	 * @return the correct image
	 * this function calculates which character image should be returned according to the walls surrounding the cell
	 */
	private Image cellImage(Maze3dDrawableAdapter maze,int x,int y)
	{

		Maze3d myMaze = maze.getData();
		int available =myMaze.getCellValue(currentFloorZ, x, y);
		String temp=".\\resources\\images\\";
		String str="";

		if (available == 1){
			str = "brick.png";
		}

		else if(myMaze.getCellValue(currentFloorZ+1, x, y) == 0 && myMaze.getCellValue(currentFloorZ-1, x, y) == 1){
			str = "grass_up.jpg";
		}

		else if(myMaze.getCellValue(currentFloorZ+1, x, y) == 1 && myMaze.getCellValue(currentFloorZ-1, x, y) == 0){
			str = "grass_down.png";
		}

		else if(myMaze.getCellValue(currentFloorZ+1, x, y) == 0 && myMaze.getCellValue(currentFloorZ-1, x, y) ==0 ){
			str = "grass_up_and_down.jpg";
		}

		else if(myMaze.getCellValue(currentFloorZ+1, x, y) == 1 && myMaze.getCellValue(currentFloorZ-1, x, y) == 1){
			str = "grass.jpg";
		}



		board[x][y].setImageName(str); //sets the image name
		return new Image(getDisplay(),new ImageData(temp+str)); //returns the image

	}

	/* (non-Javadoc)
	 * @see view.Board#drawBoard(org.eclipse.swt.events.PaintEvent)
	 */
	@Override
	public void drawBoard(PaintEvent arg0) {
		if(board==null && won==false){ //displays the photo in the begining of the program as an intro
			setVisible(true);
			int width=getParent().getSize().x;
			int height=getParent().getSize().y;
			ImageData data = new ImageData(".\\resources\\images\\mario_wallpaper_.jpg");  
			arg0.gc.drawImage(new Image(getDisplay(),".\\resources\\images\\mario_wallpaper_.jpg"),0,0,data.width,data.height,0,0,width,height); 
		}
		else if(won==true)
		{

			flag=true;
			setVisible(false);



		}
		else if(board!=null)
			for(int i=0;i<board.length;i++)
				for(int j=0;j<board[0].length;j++)
					board[i][j].redraw();

	}
	
	/**
	 * Applys the direction in the maze
	 * if right we try to go right.
	 *
	 * @param direction - in which we try to go in the maze
	 */
	@Override
	public void applyInputDirection(Direction direction) {
		int dRow=0;
		int dCol =0;
		switch (direction){
		case FORWARD:
			dRow=1; //for example if we go up we need to take 1 from the row 
			position.setX(position.getX()+1);
			break;
		case RIGHT:
			dCol=1;
			position.setY(position.getY()+1);
			break;
		case LEFT:
			dCol=-1;
			position.setY(position.getY()-1);
			break;
		case BACKWARD: 
			dRow=-1;
			position.setX(position.getX()-1);
			break;
		case DOWN:
			position.setZ(currentFloorZ-1);
			setFloor(getCurrentFloor()-1, this.myMaze);
			break;
		case UP:
			position.setZ(currentFloorZ+1);
			setFloor(getCurrentFloor()+1, this.myMaze);

			break;

		default:
			break;

		}	
		try{
			int row=character.getCellX();  //this function redraws the character
			int col = character.getCellY(); //in the new place as needed
			board[row][col].setCharacter(null);
			character.setVisible(false);
			character = new MazeCharacter( board[row+dRow][col+dCol],SWT.FILL);
			character.cellX=row+dRow;
			character.cellY=col+dCol;
			character.setCharacterImageIndex(0);
			board[row+dRow][col+dCol].setCharacter(character);
			board[character.cellX-dRow][character.cellY-dCol].redraw();
			board[character.cellX][character.cellY].redraw(); //redrawing the character
		}catch (ArrayIndexOutOfBoundsException e){
			//if the user is on the edge and he push further.
		}
		if(character.cellX== this.rowGoalX && character.cellY == this.colGoalY && board!=null && this.currentFloorZ == goalFloorZ ){
			//if we have reacharactered the destination
			won=true; 
			getShell().setBackgroundImage(new Image(getDisplay(),".\\resources\\images\\won.jpg"));
			drawBoard(null);
			forceFocus();
		}


	}

	/* (non-Javadoc)
	 * @see view.CommonBoard#displayProblem(java.lang.Object)
	 */
	@Override
	public void displayProblem(Object o) {
		Maze3dDrawableAdapter maze=(Maze3dDrawableAdapter)o;

		getDisplay().syncExec(new Runnable() {
			public void run() {
				setBoardData(maze);
				character = new MazeCharacter(board[position.getX()][position.getY()],SWT.FILL);
				character.setCellX(position.getX());
				character.setCellY(position.getY());
				character.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,true,2,2));
				(board[position.getX()][position.getY()]).setCharacter(character); //set character to the begining of the maze
				board[position.getX()][position.getY()].redraw();
				layout(); //draw all the things needed
				forceFocus();

			}
		});

		scheduleTimer(maze);



	}
	
	/**
	 * Schedule timer.
	 *
	 * @param maze the maze
	 */
	private void scheduleTimer(Maze3dDrawableAdapter maze)
	{
		Maze3d myMaze = maze.getData();

		TimerTask  timerTask = new TimerTask() {

			@Override
			public void run() {
				if(!isDisposed()){
					getDisplay().syncExec(new Runnable() {
						@Override
						public void run() { //this is the timer task allowing us to redraw the goal target gif and sonic's gif
							if(character!=null && !isDisposed() && maze!=null){
								if(myMaze.getGoalPosition().getX()<board.length && myMaze.getGoalPosition().getY()<board[0].length){
									character.setCharacterImageIndex((character.getCharacterImageIndex() + 1) % character.getCharacterImagesArray().length); //next frame in gifs
									frameIndex =(frameIndex+1) % images.length; //next frame in gifs
									(board[rowGoalX][colGoalY]).setGoal(new Image(getDisplay(),images[frameIndex]));
									board[character.cellX][character.cellY].redraw(); //redraw cell in which character now stays
									board[rowGoalX][colGoalY].redraw();
									//redraw the goal cell
								}
							}

						}
					});
				}
			}
		};

		this.timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 50); //ever 0.05 seconds render display
	}


	/* (non-Javadoc)
	 * @see view.CommonBoard#hasPathUp(int, int)
	 */
	@Override
	public boolean hasPathUp(int characterRow, int characterCol) {// add adapter and check with the maze func

		Maze3d maze = this.myMaze.getData();

		int availble = maze.getCellValue(currentFloorZ+1, characterRow, characterCol);


		return availble == 0; 
	}


	/* (non-Javadoc)
	 * @see view.CommonBoard#hasPathRight(int, int)
	 */
	@Override
	public boolean hasPathRight(int characterRow, int characterCol) {
		Maze3d maze = this.myMaze.getData();



		int availble = maze.getCellValue(currentFloorZ, characterRow, characterCol+1);


		return availble == 0; 
	}


	/* (non-Javadoc)
	 * @see view.CommonBoard#hasPathDown(int, int)
	 */
	@Override
	public boolean hasPathDown(int characterRow, int characterCol) {
		Maze3d maze = this.myMaze.getData();


		int availble = maze.getCellValue(currentFloorZ-1, characterRow, characterCol);


		return availble == 0; 
	}

	/* (non-Javadoc)
	 * @see view.CommonBoard#hasPathLeft(int, int)
	 */
	@Override
	public boolean hasPathLeft(int characterRow, int characterCol) {
		Maze3d maze = this.myMaze.getData();

		int availble = maze.getCellValue(currentFloorZ, characterRow, characterCol-1);


		return availble == 0; 
	}

	/* (non-Javadoc)
	 * @see view.CommonBoard#hasPathForward(int, int)
	 */
	@Override
	public boolean hasPathForward(int characterRow, int characterCol) {
		Maze3d maze = this.myMaze.getData();



		int availble = maze.getCellValue(currentFloorZ,characterRow+1, characterCol);


		return availble == 0; 
	}
	
	/* (non-Javadoc)
	 * @see view.CommonBoard#hasPathBackward(int, int)
	 */
	@Override
	public boolean hasPathBackward(int characterRow, int characterCol) {
		Maze3d maze = this.myMaze.getData();



		int availble = maze.getCellValue(currentFloorZ, characterRow-1, characterCol );


		return availble == 0; 
	}
	
	/* (non-Javadoc)
	 * @see view.CommonBoard#displaySolution(algorithm.generic.Solution)
	 */
	@Override
	public <T> void displaySolution(Solution<T> s) {
		//String Solution = s.toString();
		ArrayList<State<T>> myList = s.getSolution();

		for (State<T> state : myList) {

			Position position = (Position)state.getPosition();
			int x = position.getX();
			int y = position.getY();
			int z = position.getZ();
			hints[z][x][y] = true;
		}
		setUserAskedForSolution(true);




	}

	/**
	 * Gets the current floor.
	 *
	 * @return the current floor
	 */
	public int getCurrentFloor() {
		return currentFloorZ;
	}

	/**
	 * Sets the floor.
	 *
	 * @param floor the floor
	 * @param maze the maze
	 */
	public void setFloor(int floor, Maze3dDrawableAdapter maze) {

		this.currentFloorZ = floor;
		displayProblem(maze);

	}

	/**
	 * Checks if is user asked for solution.
	 *
	 * @return true, if is user asked for solution
	 */
	public boolean isUserAskedForSolution() {
		return userAskedForSolution;
	}

	/* (non-Javadoc)
	 * @see view.CommonBoard#setUserAskedForSolution(boolean)
	 */
	@Override
	public void setUserAskedForSolution(boolean userAskedForSolution) {
		if(userAskedForSolution){
			this.userAskedForSolution = true;
			getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {


					for (int  i= 0; i < board.length;i++) {
						for (int j = 0; j < board[i].length; j++) {
							if(userAskedForSolution && hints[currentFloorZ][i][j]){

								Image img = new Image(getDisplay(),".\\resources\\images\\star.png"); //hint image
								(board[i][j]).setCellImage(img);
								(board[i][j]).setHint(img);
								(board[i][j]).redraw();
							}
						}
					}
					layout();
					forceFocus();
				}

			});

		}

		else{
			this.userAskedForSolution = false;
			if(hints !=null)
			{
				for (int i = 0; i < hints.length; i++) {
					for (int j = 0; j < hints[i].length; j++) {
						for (int w = 0; w < hints[i][j].length; w++){
							hints[i][j][w] = false;

						}


					}

				}
			}
		}

	}


}







