package util;

/**
 * Represents the resolution of an image. That is some number of pixels by some number of pixels.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Resolution {
	public final int x;
	public final int y;

	public Resolution(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Resolution(String resolution) {
		try {
			String[] split = resolution.split("x", 2);
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException __) {
			throw new IllegalArgumentException("Argument must be a string of the form \"1920x1080\".")
		}
	}

	@Override
	public String toString() {
		return "" + x + "x" + y;
	}

}
