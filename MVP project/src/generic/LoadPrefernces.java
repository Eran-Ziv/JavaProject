package generic;

import java.io.Serializable;


/**
 * The Class LoadPrefernces.
 *  @author Eran & Ziv
 */
public class LoadPrefernces implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The file name. */
	public String fileName;

	/** The maze name. */
	public String mazeName;




	/**
	 * Instantiates a new load prefernces.
	 */
	public LoadPrefernces() {
		this.mazeName=null;
	}

	/**
	 * Instantiates a new load prefernces.
	 *
	 * @param mazeString the maze string
	 * @param fileName the file name
	 */
	public LoadPrefernces(String mazeString,String fileName) {

		this.mazeName=mazeString;
		this.fileName=fileName;

	}

	/**
	 * Gets the maze name.
	 *
	 * @return the maze name
	 */
	public String getMazeName() {
		return mazeName;
	}

	/**
	 * Sets the maze name.
	 *
	 * @param mazeName the new maze name
	 */
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


}
