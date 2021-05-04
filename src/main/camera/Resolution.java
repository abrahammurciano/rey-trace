package camera;

/**
 * Represents the resolution of an image. That is some number of pixels by some
 * number of pixels.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Resolution {
	/** The number of pixels in the width of the resolution. */
	public final int x;
	/** The number of pixels in the height of the resolution. */
	public final int y;

	/**
	 * Construct a resolution {@code x} by {@code y} pixels.
	 *
	 * @param x The number of pixels in the resolution's width.
	 * @param y The number of pixels in the resolution's height.
	 */
	public Resolution(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Construct a resolution from a string, such as "1920x1080".
	 *
	 * @param resolution The string representation of the resolution.
	 */
	public Resolution(String resolution) {
		try {
			String[] split = resolution.split("x", 2);
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException __) {
			throw new IllegalArgumentException("Argument must be a string of the form \"1920x1080\".");
		}
	}

	@Override
	public String toString() {
		return "" + x + "x" + y;
	}

}
