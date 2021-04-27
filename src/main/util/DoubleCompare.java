package util;

/**
 * Class for comparing doubles with tolerance.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class DoubleCompare {
	private DoubleCompare() {}

	/**
	 * Check if two doubles are approximately equal.
	 *
	 * @param a A double
	 * @param b Another double
	 * @return true if the doubles are approximately equal.
	 */
	public static boolean eq(double a, double b) {
		return Math.abs(a - b) <= 1E-7;
	}


	/**
	 * Check if two doubles are not approximately equal.
	 *
	 * @param a A double
	 * @param b Another double
	 * @return true if the doubles are not approximately equal.
	 */
	public static boolean neq(double a, double b) {
		return !eq(a, b);
	}


	/**
	 * Check if a is less than b by a non-negligible amount.
	 *
	 * @param a A double
	 * @param b Another double
	 * @return true if a is less than b by a non-negligible amount.
	 */
	public static boolean lt(double a, double b) {
		return a < b && neq(a, b);
	}


	/**
	 * Check if two doubles are approximately equal or a is less than b.
	 *
	 * @param a A double
	 * @param b Another double
	 * @return true if the doubles are approximately equal or a is less than b.
	 */
	public static boolean leq(double a, double b) {
		return a < b || eq(a, b);
	}


	/**
	 * Check if a is greater than b by a non-negligible amount.
	 *
	 * @param a A double
	 * @param b Another double
	 * @return true if a is greater than b by a non-negligible amount.
	 */
	public static boolean gt(double a, double b) {
		return !leq(a, b);
	}


	/**
	 * Check if two doubles are approximately equal or a is greater than b.
	 *
	 * @param a A double
	 * @param b Another double
	 * @return true if the doubles are approximately equal or a is greater than b.
	 */
	public static boolean geq(double a, double b) {
		return !lt(a, b);
	}

	/**
	 * Compare two doubles.
	 *
	 * @param a A double
	 * @param b Another double
	 * @return 0 if equal, positive if a > b, negative if a < b.
	 */
	public static int compare(double a, double b) {
		return eq(a, b) ? 0 : Double.compare(a, b);
	}
}
