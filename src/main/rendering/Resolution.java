package rendering;

/**
 * Represents the resolution of an image. That is some number of pixels by some
 * number of pixels.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Resolution {
	/** The number of pixels in the width of the resolution. */
	public final int columns;
	/** The number of pixels in the height of the resolution. */
	public final int rows;

	/**
	 * Construct a resolution {@code x} by {@code y} pixels.
	 *
	 * @param columns The number of pixels in the resolution's width.
	 * @param rows    The number of pixels in the resolution's height.
	 */
	public Resolution(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
	}

	/**
	 * Construct a resolution from a string, such as "1920x1080".
	 *
	 * @param resolution The string representation of the resolution.
	 */
	public Resolution(String resolution) {
		try {
			String[] split = resolution.split("x", 2);
			columns = Integer.parseInt(split[0]);
			rows = Integer.parseInt(split[1]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException __) {
			throw new IllegalArgumentException("Argument must be a string of the form \"1920x1080\".");
		}
	}

	@Override
	public String toString() {
		return "" + columns + "x" + rows;
	}

}
