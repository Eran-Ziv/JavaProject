package io;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

	private InputStream in;

	public MyDecompressorInputStream(InputStream in) {
		this.in = in;
	}

	@Override
	public int read() throws IOException {
		return this.in.read();
	}


	@Override
	public int read(byte[] a) throws IOException{

		int numRead1=0;
		int numRead2=0;
		int k=0 ;
		while (((numRead1 = in.read()) != -1) && ((numRead2 = in.read()) != -1 )) {
			for (int i = 0; i < numRead2; i++) {
				a[k++]=(byte) numRead1;
			}
            
        }
		return a.length;

	}
	

}
