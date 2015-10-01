package view;

public class MazeProperties {
	/**
	 * Maze Name
	 */
	private String MazeName;
	/**
	 * Number of rows in maze
	 */
	private int Rows;
	/**
	 * Number of columns in maze
	 */
	private int Cols;
	/**
	 * row source in the maze
	 */
	private int rowSource;
	/**
	 * column source in the maze
	 */
	private int colSource;
	/**
	 *  row goal in maze
	 */
	private int rowGoal;
	/**
	 * col goal in maze
	 */
	private int colGoal;
	/**
	 * 
	 * getter
	 * 
	 */
	public String getMazeName() {
		return MazeName;
	}
	/**
	 * setter
	 * 
	 */
	public void setMazeName(String mazeName) {
		this.MazeName = mazeName;
	}
	/** 
	 * 
	 * getter
	 */
	public int getRows() {
		return Rows;
	}
	/**
	 * setter
	 */
	public void setRows(int rows) {
		this.Rows = rows;
	}
	/**
	 * getter
	 */
	public int getCols() {
		return Cols;
	}
	/**
	 * 
	 *setter
	 */
	public void setCols(int cols) {
		this.Cols = cols;
	}
	/**
	 * getter
	 */
	public int getRowSource() {
		return rowSource;
	}
	/**
	 * setter
	 */
	public void setRowSource(int rowSource) {
		this.rowSource = rowSource;
	}
	/**
	 * getter
	 */
	public int getColSource() {
		return colSource;
	}
	/**
	 * setter
	 */
	public void setColSource(int colSource) {
		this.colSource = colSource;
	}
	/**
	 * getter
	 */
	public int getRowGoal() {
		return rowGoal;
	}
	/**
	 * setter
	 */
	public void setRowGoal(int rowGoal) {
		this.rowGoal = rowGoal;
	}
	/**
	 * getter
	 */
	public int getColGoal() {
		return colGoal;
	}
	/**
	 * setter
	 */
	public void setColGoal(int colGoal) {
		this.colGoal = colGoal;
	}
}
