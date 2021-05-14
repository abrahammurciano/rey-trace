package math.matrices;

import primitives.Triple;

/**
 * A base class for fast matrix multiplication between a 3x1 matrix (a {@link Triple}) and a single column vector (a
 * {@link Triple}).
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
abstract class FastMatrixMultPartial {

	/** Matrix entry at position (0, 0) (starting from top left). */
	public final double xx;
	/** Matrix entry at position (1, 1) (starting from top left). */
	public final double yy;
	/** Matrix entry at position (2, 2) (starting from top left). */
	public final double zz;
	/** Matrix entry at position (0, 1) (starting from top left). */
	public final double xy;
	/** Matrix entry at position (1, 2) (starting from top left). */
	public final double yz;
	/** Matrix entry at position (0, 2) (starting from top left). */
	public final double xz;

	/**
	 * Multiply the two given vectors as if they were matrices.
	 *
	 * @param first  The first vector to multiply, as a 3x1 matrix.
	 * @param second The second vector to multiply, as a 1x3 matrix.
	 */
	protected FastMatrixMultPartial(Triple first, Triple second) {
		xx = first.x * second.x;
		yy = first.y * second.y;
		zz = first.z * second.z;
		xy = first.x * second.y;
		yz = first.y * second.z;
		xz = first.x * second.z;
	}
}
