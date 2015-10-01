package view;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParsePosition;
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

import com.google.common.collect.Table.Cell;

import algorithm.generic.Solution;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;



public class MazeBoard extends CommonBoard {
	
	
	ImageLoader gifs=new ImageLoader();
	
	ImageData[] images;
	
	int frameIndex=0;
	
	int rowSource;
	
	int rowGoal;
	
	int colGoal;
	
	int colSource;
	
	public MazeBoard(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		images=gifs.load(""); //TODO need to paste the path here
		
	}
	
	private void setBoardData(Maze3d maze)
	{
		
		MazeBoard a =this; //for our convenience
		getDisplay().syncExec(new Runnable() {
			   public void run() {
				   if(board!=null)
				   a.destructBoard(); // destory previous maze if exists 
//				   boardRows=maze.getRows(); //gets maze rows and cols
//				   boardCols=maze.getCols();
//				   rowSource=maze.getRowSource();
//				   colSource=maze.getColSource();
//				   rowGoal=maze.getRowGoal();
//				   colGoal=maze.getColGoal();
				   GridLayout layout=new GridLayout(boardCols, true); //defines the grid layout
				   layout.horizontalSpacing=0;
				   layout.verticalSpacing=0;
				   setLayout(layout); //sets the layout
				   board=new MazeTile[boardRows][boardCols]; //creates a new maze array
				   for(int i=0;i<boardRows;i++)
					   for(int j=0;j<boardCols;j++)
					   {
						   board[i][j]=new MazeTile(a,SWT.NONE);
						   board[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//						   board[i][j].setCellImage(cellImage(maze,i,j)); //creates a cell and sets the correct image
					   }
				  
				   getShell().layout();
				   
			   }
			}); 
		
	}
	
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
	 * 
	 * @param m the maze 
	 * @param i row index
	 * @param j col index
	 * @return the correct image
	 * this function calculates whicharacter image should be returned according to the walls surrounding the cell
	 */
//	private Image cellImage(Maze3d m,int i,int j)
//	{
//		Cell c=m.   getCell(i, j);
//		String temp=".\\resources\\images\\";
//		String str="";
//		if(c.getHasLeftWall())
//		{
//			if(j==0 || !m.getCell(i, j-1).getHasRightWall()) //if it has left wall image must have 1 at the begining
//				str+="1";
//			else
//				str+="0";
//		}
//		else
//			str+="0";
//		if(c.getHasTopWall())
//		{
//			if(i==0 || !m.getCell(i-1, j).getHasBottomWall()) //if it has Top wall image must have 1 at the second place
//				str+="1";
//			else
//				str+="0";
//		}	
//		else
//			str+="0";
//		if(c.getHasRightWall()) //if it has right wall image must have 1 at the third place
//			str+="1";
//		else
//			str+="0";
//		if(c.getHasBottomWall()) // if it has bottom wall image must have 1 at the fourth place
//			str+="1";
//		else
//			str+="0";
//		str+=".jpg";
//		
//		board[i][j].setImageName(str); //sets the image name
//		return new Image(getDisplay(),new ImageData(temp+str)); //returns the image
//		
//	}
	
	@Override
	public void drawBoard(PaintEvent arg0) {
		if(board==null && won==false){ //displays the photo in the begining of the program as an intro
			
		    int width=getParent().getSize().x;
			int height=getParent().getSize().y;
			ImageData data = new ImageData(""); //TODO need to paste the path here
			arg0.gc.drawImage(new Image(getDisplay(),""),0,0,data.width,data.height,0,0,width,height); //TODO need to paste the path here
		}
		else if(won==true)
		{
			
			setVisible(false);
		}
		else if(board!=null)
			for(int i=0;i<board.length;i++)
				for(int j=0;j<board[0].length;j++)
					board[i][j].redraw();
		
	}
	/**
	 * Applys the direction in the maze
	 * if right we try to go right
	 * @param direction - in which we try to go in the maze
	 */
	@Override
	public void applyInputDirection(Direction direction) {
		int dRow=0;
		int dCol =0;
		switch (direction){
		case UP:
				dRow=1; //for example if we go up we need to take 1 from the row 
			 	break;
		case RIGHT:
				dCol=-1;
				break;
		case LEFT:
				dCol=1;
				break;
		case DOWN: 
				dRow=-1;
				break;
		
		default:
			break;
			
		}	
		int row=character.getCellX();  //this function redraws the character
    	int col = character.getCellY(); //in the new place as needed
    	board[row][col].setCharacter(null);
    	character.setVisible(false);
    	character = new MazeCharacter( board[row-dRow][col-dCol],SWT.FILL);
    	character.cellX=row-dRow;
    	character.cellY=col-dCol;
		character.setCharacterImageIndex(0);
		board[row-dRow][col-dCol].setCharacter(character);
		board[character.cellX+dRow][character.cellY+dCol].redraw();
		board[character.cellX][character.cellY].redraw(); //redrawing the character
		
		if(character.cellX== this.rowGoal && character.cellY == this.colGoal && board!=null){
			 //if we have reacharactered the destination
			 won=true; 
			 redraw(); //play a sound :)
			 getShell().setBackgroundImage(new Image(getDisplay(),".\\resources\\images\\sonicwon.png"));
		 }
		
	
	}
	
//	@Override
//	public void displayProblem(Object o) {
//		Maze3d m=(Maze3d)o;
//		getDisplay().syncExec(new Runnable() {
//			   public void run() {
//				   setBoardData(m);
//				   character = new MazeCharacter(board[m.getRowSource()][m.getColSource()],SWT.FILL);
//				   character.setCurrentCellX(m.getRowSource());
//				   character.setCurrentCellY(m.getColSource());
//				   character.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,true,2,2));
//				   (board[m.getRowSource()][m.getColSource()]).setCharacter(character); //set character to the begining of the maze
//				   board[m.getRowSource()][m.getColSource()].redraw();
//				   layout(); //draw all the things needed
//				   forceFocus();
//				   
//			   }
//			});
//		
//		scheduleTimer(m);
//		
//		
//		
//	}
	private void scheduleTimer(Maze3d m)
	{
		TimerTask  timerTask = new TimerTask() {
			
			@Override
			public void run() {
				if(!isDisposed()){
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() { //this is the timer task allowing us to redraw the goal target gif and sonic's gif
						if(character!=null && !isDisposed() && m!=null){
							if(m.getRowGoal()<board.length && m.getColGoal()<board[0].length){
								 character.setCharacterImageIndex((character.getCharacterImageIndex() + 1) % character.getCharacterImagesArray().length); //next frame in gifs
								 frameIndex =(frameIndex+1) % images.length; //next frame in gifs
								 (board[rowGoal][colGoal]).setGoal(new Image(getDisplay(),images[frameIndex]));
								 board[character.cellX][character.cellY].redraw(); //redraw cell in which character now stays
								 board[rowGoal][colGoal].redraw();
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
	
	
	@Override
	public boolean hasPathUp(int characterRow, int characterCol) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasPathRight(int characterRow, int characterCol) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasPathDown(int characterRow, int characterCol) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasPathLeft(int characterRow, int characterCol) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasPathForward(int characterRow, int characterCol) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasPathBackward(int characterRow, int characterCol) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public <T> void displaySolution(Solution<T> s) {
//		String Solution = s.toString().substring(9);
//		String []path = Solution.split("	");
//		Image img = new Image(getDisplay(),".\\resources\\images\\ring.png"); //hint image
//		for(int i=0;i<path.length-1;i++){
//			String []indexes = path[i].split(",");
//			int xt=Integer.parseInt(indexes[0]);
//			int yt=Integer.parseInt(indexes[1]);	
//				(board[xt][yt]).setHint(img); //put hints all over the solutions path
//			}
//	
//			drawBoard(null);
//			forceFocus();
//		
		
	}
	
	}
	
	
		


