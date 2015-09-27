package view;

import org.eclipse.swt.graphics.ImageData;

public interface Character {
	
	public void drawCharacter();
	
	public ImageData[] getCharacterImagesArray();
	
	public void setCharacterImageArray(ImageData[] image);
	
	public int getCharacterImageIndex();
	
	public void setCharacterImageIndex(int index);

}
