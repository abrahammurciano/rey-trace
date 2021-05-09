package rendering.camera;

/**
 * This class represents the row and column of a pixel.
 */
public class Coordinates {
	/** The row index. */
	public final int row;
	/** The column index */
	public final int col;

	/**
	 * Constructor for coordinates.
	 *
	 * @param row The row index.
	 * @param col The column index.
	 */
	public Coordinates(int row, int col) {
		this.row = row;
		this.col = col;
	}


	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinates)) {
			return false;
		}
		Coordinates coordinates = (Coordinates) o;
		return row == coordinates.row && col == coordinates.col;
	}

	@Override
	public int hashCode() {
		return row * 5246663 + col;
	}

}
