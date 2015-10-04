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
	private int Columns;
	/**
	 * row source in the maze
	 */
	private int Floors;
	
	
	public MazeProperties(String mazeName, int rows, int columns, int floors) {
		super();
		MazeName = mazeName;
		Rows = rows;
		Columns = columns;
		Floors = floors;
	}
	
	public MazeProperties() {
		MazeName = "Deafult";
		Rows = 5;
		Columns = 5;
		Floors = 5;
	}

	public String getMazeName() {
		return MazeName;
	}

	public void setMazeName(String mazeName) {
		MazeName = mazeName;
	}

	public int getRows() {
		return Rows;
	}

	public void setRows(int rows) {
		Rows = rows;
	}

	public int getColumns() {
		return Columns;
	}

	public void setColumns(int columns) {
		Columns = columns;
	}

	public int getFloors() {
		return Floors;
	}

	public void setFloors(int floors) {
		Floors = floors;
	}
	
	
}
