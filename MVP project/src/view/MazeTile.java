package view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;

public class MazeTile extends CommonTile{


	public MazeTile(Composite parent, int style) {//wanna get it from the outside
		super(parent, style | SWT.DOUBLE_BUFFERED);


	}

	public void setImage(Image image) 		//getters and setters of images
	{
		if(this.cellImage!=null)
			this.cellImage.dispose();
		this.cellImage=image;
		
		redraw();
	}

	@Override
	public void drawTile(PaintEvent e) {
		int width=getSize().x; //get width of window
		int height=getSize().y; //get height of window

		if(cellImage!=null&& Hint!=null){
			Hint=null;
		}

		if(cellImage!=null){ //display image of the tile
			ImageData data = cellImage.getImageData();
			e.gc.drawImage(cellImage,0,0,data.width,data.height,0,0,width,height);
		}

		if(ch!=null){ //if a character is on the tile display it 
			Image img= new Image(getDisplay(),ch.getCharacterImagesArray()[ch.getCharacterImageIndex()]);
			ImageData data3= img.getImageData();
			e.gc.drawImage(img,0,0,data3.width,data3.height,0,0,getSize().x,getSize().y);
		}
		if(Hint!=null){ //display hint if it has been given
	    	   ImageData data2=Hint.getImageData();
	    	   e.gc.drawImage(Hint,0,0,data2.width,data2.height,0,0,width,height);
	       } 
		
		if(goal!=null){ // draw the goal if it is on the tile

			ImageData data4= goal.getImageData();

			e.gc.drawImage(goal,0,0,data4.width,data4.height,0,0,width,height);
		}


	}

}





