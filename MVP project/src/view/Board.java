package view;

import org.eclipse.swt.events.PaintEvent;

import algorithm.generic.Solution;




public interface Board {

	void drawBoard(PaintEvent arg0);
	
	void applyInputDirection(Direction direction);

	 boolean hasPathUp(int characterRow,int characterCol);
	
	 boolean hasPathRight(int characterRow,int characterCol);
	
	 boolean hasPathDown(int characterRow,int characterCol);
	
	 boolean hasPathLeft(int characterRow,int characterCol);
	 
	 boolean hasPathForward(int characterRow,int characterCol);
	 
	 boolean hasPathBackward(int characterRow,int characterCol);
	 
	 void destructBoard();
	
	void displayProblem(Object o);
	
	<T> void displaySolution(Solution<T> s);
	
}
