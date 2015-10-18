package view;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;


/**
 * The Class MazeCharacter.
 *  @author Eran & Ziv
 */
public class MazeCharacter extends CommonCharacter {
	
	/** The gifs. */
	ImageLoader gifs = new ImageLoader();

	/** The images. */
	ImageData [] images;
	
	/** The frame index. */
	int frameIndex = 0;
	
	/**
	 * Instantiates a new maze character.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	MazeCharacter(Composite parent, int style) {
		super(parent, style);
		this.images = gifs.load(".\\resources\\images\\mario.gif");//TODO need to paste the path here
	}

	/* (non-Javadoc)
	 * @see view.CommonCharacter#getCharacterImagesArray()
	 */
	@Override
	public ImageData[] getCharacterImagesArray() {
		
		return images;
	}

	/* (non-Javadoc)
	 * @see view.CommonCharacter#setCharacterImageArray(org.eclipse.swt.graphics.ImageData[])
	 */
	@Override
	public void setCharacterImageArray(ImageData[] image) {
		this.images = image;

	}

	/* (non-Javadoc)
	 * @see view.CommonCharacter#getCharacterImageIndex()
	 */
	@Override
	public int getCharacterImageIndex() {
		
		return frameIndex;
	}

	/* (non-Javadoc)
	 * @see view.CommonCharacter#setCharacterImageIndex(int)
	 */
	@Override
	public void setCharacterImageIndex(int index) {
		this.frameIndex = index;
	}

}
