package algorithm.generic;

import java.io.Serializable;

/**
 * The Class State.
 *
 * @param <T>
 *            the generic type
 */
public class State<T> implements Comparable<State<T>>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The position. */
	private T position;

	/** The cost. */
	private double cost;

	/** The came from. */
	private State<T> cameFrom;

	/**
	 * Instantiates a new state.
	 *
	 * @param position
	 *            the position
	 */
	public State(T position) {
		this.position = position;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public T getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position
	 *            the new position
	 */
	public void setPosition(T position) {
		this.position = position;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost
	 *            the new cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Gets the came from.
	 *
	 * @return the came from
	 */
	public State<T> getCameFrom() {
		return cameFrom;
	}

	/**
	 * Sets the came from.
	 *
	 * @param p
	 *            the new came from
	 */
	public void setCameFrom(State<T> p) {
		this.cameFrom = p;
	}

	@Override
	public String toString() {

		return position.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return this.position.equals(((State<T>) obj).getPosition());

	}

	@Override
	public int hashCode() {
		return position.toString().hashCode();
	}

	/**
	 * compare between 2 state<T>
	 */
	@Override
	public int compareTo(State<T> p) {
		return (int) (this.getCost() - p.getCost());

	}

}
