package math.matrices;

import primitives.Triple;

/**
 * Fast matrix multiplication between a vector (as a 3x1 matrix) and itsef (as a 1x3 matrix).
 */
public class FastMatrixMultSelf extends FastMatrixMultPartial {

	/**
	 * Multiply the given vector with itself as if they were matrices.
	 *
	 * @param first The vector to multiply, as a 3x1 matrix and then as a 1x3 matrix.
	 */
	public FastMatrixMultSelf(Triple first) {
		super(first, first);
	}

}
