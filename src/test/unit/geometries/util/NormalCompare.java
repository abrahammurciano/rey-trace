package unit.geometries.util;

import primitives.NormalizedVector;

/**
 * Class to compare normals.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class NormalCompare {

	private NormalCompare() {}

	/**
	 * Check if two normals are equal. They are considered equal if they are the
	 * same length and the same or opposite direction.
	 *
	 * @param calc   A vector to compare.
	 * @param actual A vector to compare.
	 * @return True if they are equal or negatives of each other, false otherwise.
	 */
	public static boolean eq(NormalizedVector calc, NormalizedVector actual) {
		return calc.equals(actual) || calc.equals(actual.reversed());
	}
}
