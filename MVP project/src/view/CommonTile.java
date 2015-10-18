package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


/**
 * The Class CommonTile.
 *  @author Eran & Ziv
 */
public abstract class CommonTile extends Canvas implements Tile{
	
	
	/** The cell image. */
	Image cellImage; 
	
	/** The image name. */
	String imageName;
	
	/** The Hint. */
	Image Hint=null; 
	
	/** The ch. */
	CommonCharacter ch = null;  
	
	/** The goal. */
	Image goal =null; 
	
	/**
	 * Instantiates a new common tile.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public CommonTile(Composite parent, int style) {
		super(parent, style);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {   
				drawTile(e);
		}
	});
	}
	
	/* (non-Javadoc)
	 * @see view.Tile#drawTile(org.eclipse.swt.events.PaintEvent)
	 */
	@Override
	public abstract void drawTile(PaintEvent e);
	
	
	 
	/**
	 * Sets the goal.
	 *
	 * @param img the new goal
	 */
	public  void setGoal(Image img){
		this.goal=img;
	}
	
	/**
	 * Gets the goal.
	 *
	 * @return the goal
	 */
	public  Image getGoal(){
		return this.goal;
	}
	
	/**
	 * Sets the cell image.
	 *
	 * @param img the new cell image
	 */
	public void setCellImage(Image img){
		this.cellImage=img;
	}
	
	/**
	 * Sets the character.
	 *
	 * @param character the new character
	 */
	public  void setCharacter(CommonCharacter character){
		this.ch=character;
	}
	
	/**
	 * Gets the image name.
	 *
	 * @return the image name
	 */
	public  String getImageName()
	{
		return this.imageName;
	}
	
	/**
	 * Sets the image name.
	 *
	 * @param name the new image name
	 */
	public void setImageName(String name)
	{
		this.imageName=name;
	}
	
	/**
	 * Gets the cell image.
	 *
	 * @return the cell image
	 */
	public  Image getCellImage(){
		return this.cellImage;
	}
	
	/**
	 * Gets the character.
	 *
	 * @return the character
	 */
	public  CommonCharacter getCharacter(){
		return this.ch;
	}

	/**
	 * Gets the hint.
	 *
	 * @return the hint
	 */
	public Image getHint() {
		return Hint;
	}

	/**
	 * Sets the hint.
	 *
	 * @param hint the new hint
	 */
	public void setHint(Image hint) {
		Hint = hint;
	}

	

}
