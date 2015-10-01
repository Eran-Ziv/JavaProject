package view;

import java.util.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;

import algorithm.generic.Solution;


public abstract class CommonBoard extends Composite implements Board {

	Timer timer;

	boolean won=false;

	CommonTile[][] board;

	int boardRows; 

	int boardCols;

	CommonCharacter character=null;

	boolean checkDragged=false; 


	public CommonBoard(Composite parent, int style) {
		super(parent, style);
		addPaintListener(new PaintListener() { 

			@Override
			public void paintControl(PaintEvent arg0) {
				drawBoard(arg0);
			}
		});
		
		this.addKeyListener(new KeyListener(){	
			@Override
			public void keyPressed(KeyEvent e) { //each of those codes represents a key on the keyboard in our case up down right left arrows


				if (e.keyCode == 16777217 && hasPathUp(character.getCellX(),character.getCellY())){
					applyInputDirection((Direction.UP));
					//up
				} 
				else 
					if (e.keyCode == 16777220 && hasPathRight(character.getCellX(),character.getCellY())) {
						applyInputDirection((Direction.RIGHT));
						//right
					} 
					else 
						if (e.keyCode == 16777219 &&  hasPathLeft(character.getCellX(),character.getCellY())) {
							applyInputDirection(Direction.LEFT);
							//left
						} 
						else
							if (e.keyCode == 16777218  && hasPathDown(character.getCellX(),character.getCellY())) {
								applyInputDirection(Direction.DOWN);
								//down
							} 
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}
		});
		//allows us to set the size of window by pressing ctrl + scrolling
		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseScrolled(MouseEvent arg0) {
				if((arg0.stateMask& SWT.CTRL)!=0){ //if control is pressed
					if(arg0.count>0){ //and we scroll up
						//up zoom in										//Bonus!!
						setSize(getSize().x+30, getSize().y+30);
					}
					if(arg0.count<0){ //and we scroll down
						setSize(getSize().x-30, getSize().y-30);
						//down zoom out	
					}
				}	
			}
		});		
	}

	@Override
	public abstract void  applyInputDirection(Direction direction);

	@Override
	public abstract boolean hasPathUp(int characterRow,int characterCol);
	@Override
	public abstract boolean hasPathRight(int characterRow,int characterCol);
	@Override
	public abstract boolean hasPathDown(int characterRow,int characterCol);
	@Override
	public abstract boolean hasPathLeft(int characterRow,int characterCol);
	@Override
	public abstract boolean hasPathForward(int characterRow,int characterCol);
	@Override
	public abstract boolean hasPathBackward(int characterRow,int characterCol);
	@Override
	public abstract void displayProblem(Object o);
	@Override
	public abstract <T> void displaySolution(Solution<T> s);
	@Override
	public abstract void destructBoard();



}
