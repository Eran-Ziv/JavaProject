package io;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	
	public MyCompressorOutputStream(OutputStream out) {
		this.out = out;
	}

	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}

}
