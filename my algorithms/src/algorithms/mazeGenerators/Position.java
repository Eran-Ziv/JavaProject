package algorithms.mazeGenerators;

/**
 * The Class Position.
 */

public class Position {

	/** The height. */
	int z;

	/** The length */
	int x;

	/** The width. */
	int y;

	/**
	 * Instantiates a new position.
	 *
	 * @param z
	 *            the height
	 * @param x
	 *            the length
	 * @param y
	 *            the width
	 */
	public Position(int z, int x, int y) {
		this.z = z;
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Sets the height.
	 *
	 * @param z
	 *            the height
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * Gets the length
	 *
	 * @return the length
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the length.
	 *
	 * @param x
	 *            the new length
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the width.
	 *
	 * @param y
	 *            the new width
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * From position to String
	 */
	@Override
	public String toString() {

		return "{" + this.getZ() + "," + this.getX() + "," + this.getY() + "}";
	}

	/**
	 * implements the position equals
	 */
	@Override
	public boolean equals(Object obj) {

		return (((Position) obj).getZ() == this.getZ())
				&& (((Position) obj).getX() == this.getX())
				&& (((Position) obj).getY() == this.getY());
	}

	/**
	 * Split the position to int
	 *
	 * @return the int[]
	 */
	public int[] split() {
		String s = this.toString().replace("{", "");
		s = s.replace("}", "");
		String[] stringPosition = (s.split(","));
		int[] intPosition = new int[stringPosition.length];
		for (int i = 0; i < intPosition.length; i++) {
			intPosition[i] = Integer.valueOf(stringPosition[i]);
		}
		return intPosition;
	}

}
