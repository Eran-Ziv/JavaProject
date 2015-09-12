package view;

import java.io.PrintWriter;

public class Maze3dDisplayerAdapter implements Displayer<int[][][]> {

	int[][][] draw;
	PrintWriter out;
	
	public Maze3dDisplayerAdapter(PrintWriter out) {
		this.out = out;
	}
	
	
	@Override
	public void getDisplayer(Drawable<int[][][]> draw) {
		
		this.draw = draw.getData();
		
	}

	@Override
	public void display() {
		
		for (int i = 0; i < draw.length; i++) {
			for (int j = 0; j < draw[i].length; j++) {
				for (int w = 0; w < draw[i][j].length; w++) {
					
					out.print(draw[i][j][w]);
					out.flush();
				}
				out.print("\n");
				out.flush();
			}
			out.print("\n\n");
			out.flush();
		}
		
	}

	
	
}
