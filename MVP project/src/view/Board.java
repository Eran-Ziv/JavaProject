package view;

import org.eclipse.swt.events.PaintEvent;

import algorithm.generic.Solution;





/**
 * The Interface Board. Represents the board widget in the Maze game, with all the functionallity needed from a board
 */
public interface Board {

	/**
	 * Draw board.
	 *
	 * @param arg0 the arg0
	 */
	void drawBoard(PaintEvent arg0);

	/**
	 * Apply input direction.
	 *
	 * @param direction the direction
	 */
	void applyInputDirection(Direction direction);

	/**
	 * Checks for path up.
	 *
	 * @param characterRow the character row
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathUp(int characterRow,int characterCol);

	/**
	 * Checks for path right.
	 *
	 * @param characterRow the character row
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathRight(int characterRow,int characterCol);

	/**
	 * Checks for path down.
	 *
	 * @param characterRow the character row
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathDown(int characterRow,int characterCol);

	/**
	 * Checks for path left.
	 *
	 * @param characterRow the character row
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathLeft(int characterRow,int characterCol);

	/**
	 * Checks for path forward.
	 *
	 * @param characterRow the character row
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathForward(int characterRow,int characterCol);

	/**
	 * Checks for path backward.
	 *
	 * @param characterRow the character row
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathBackward(int characterRow,int characterCol);

	/**
	 * Destruct board.
	 */
	void destructBoard();

	/**
	 * Display problem.
	 *
	 * @param o the o
	 */
	void displayProblem(Object o);

	/**
	 * Display solution.
	 *
	 * @param <T> the generic type
	 * @param s the s
	 */
	<T> void displaySolution(Solution<T> s);

}
