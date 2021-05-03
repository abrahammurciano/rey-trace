package math.matrices;

import primitives.Triple;

/**
 * Fast matrix multiplication between a vector (as a 3x1 matrix) and itsef (as a 1x3 matrix).
 */
public class FastMatrixMultSelf extends FastMatrixMultPartial {

	public FastMatrixMultSelf(Triple first) {
		super(first, first);
	}

}
