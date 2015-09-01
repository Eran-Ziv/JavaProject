package io;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	
	public MyCompressorOutputStream(OutputStream out) {
		this.out = out;
	}

	@Override
	public void write(int a) throws IOException {

		out.write(a);
	}
	@Override
	public void write(byte[] a) throws IOException {

		int counter=1;
		int temp=a[0];
		for (int i = 1; i < a.length; i++) {
			if(a[i]==temp){
				counter++;
			}
			else{
				this.out.write(temp);
				this.out.write(counter);
				temp=a[i];
				counter=1;	
			}
		}

	}

}
