package view;


/**
 * The Interface Displayer.
 * The displayer gets a drawable object {@see Drawable} and extract the data.
 * Both Displayer and drawable interface should be implemented with an overlap,
 * Both must be with the same data type within the specific implementation.
 *  @author Eran & Ziv
 *
 * @param <T> the generic type
 */
public interface Displayer<T> {

	/**
	 * Gets the displayer.
	 *
	 * @param draw the draw
	 * @return the displayer
	 */
	void getDisplayer(Drawable<T> draw);
	
	/**
	 * Display.
	 */
	void display();
}
