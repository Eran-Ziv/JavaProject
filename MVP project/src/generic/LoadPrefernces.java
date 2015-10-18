package generic;

import java.io.Serializable;

public class LoadPrefernces implements Serializable {

	private static final long serialVersionUID = 1L;

	public String fileName;

	public String mazeName;




	public LoadPrefernces() {
		this.mazeName=null;
	}

	public LoadPrefernces(String mazeString,String fileName) {

		this.mazeName=mazeString;
		this.fileName=fileName;

	}

	public String getMazeName() {
		return mazeName;
	}

	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


}
