package view;


/**
 * The Interface Drawable.
 * This interface define the behavior of an object that can be displayed.
 * Using getData() method the displayer interface {@see Displayer} can extract the data using a specific implementation of
 * both the displayer and the drawable object.
 * <p>
 * The getData() method will return a generic value for the displayer to use.
 *
 * @param <T> the generic type
 *  @author Eran & Ziv
 */
public interface Drawable<T> {

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	T getData();
	
}
