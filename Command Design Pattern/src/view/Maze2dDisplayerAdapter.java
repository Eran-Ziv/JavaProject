package view;

import java.io.PrintWriter;

public class Maze2dDisplayerAdapter implements Displayer<int[][]> {

	int[][] draw;
	PrintWriter out;
	
	public Maze2dDisplayerAdapter(PrintWriter out) {
		this.out = out;
	}
	
	@Override
	public void getDisplayer(Drawable<int[][]> draw) {

		this.draw = draw.getData();

	}

	@Override
	public void display() {
		out.flush();
		for (int i = 0; i < draw.length; i++) {
			for (int j = 0; j < draw[i].length; j++) {
				out.flush();

				out.print(draw[i][j]);	
			}
			
			out.print("\n");
		}
		
		out.print("\n\n");
	}


}
