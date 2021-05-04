package math.matrices;

import primitives.Triple;

/**
 * Fast matrix multiplication between a 3x1 matrix and a 1x3 matrix.
 */
public class FastMatrixMult extends FastMatrixMultPartial {
	/** Matrix entry at location (1, 0) (starting from top left). */
	public final double yx;
	/** Matrix entry at location (2, 0) (starting from top left). */
	public final double zx;
	/** Matrix entry at location (2, 1) (starting from top left). */
	public final double zy;

	/**
	 * Multiply the two given vectors as if they were matrices.
	 *
	 * @param first  The first vector to multiply, as a 3x1 matrix.
	 * @param second The second vector to multiply, as a 1x3 matrix.
	 */
	public FastMatrixMult(Triple first, Triple second) {
		super(first, second);
		yx = first.y * second.x;
		zy = first.z * second.y;
		zx = first.z * second.x;
	}
}
