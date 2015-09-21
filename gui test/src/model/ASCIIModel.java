package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ASCIIModel {

	ASCIIMaker myMaker = new ASCIIMaker();
	
	public void convert(InputStream in, OutputStream out ){
		
		myMaker.convertToAscii(in, out);
		
	}
}
