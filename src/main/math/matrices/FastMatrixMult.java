package math.matrices;

import primitives.Triple;

/**
 * Fast matrix multiplication between a 3x1 matrix and a 1x3 matrix.
 */
public class FastMatrixMult extends FastMatrixMultPartial {

	public final double yx;
	public final double zy;
	public final double zx;

	public FastMatrixMult(Triple first, Triple second) {
		super(first, second);
		yx = first.y * second.x;
		zy = first.z * second.y;
		zx = first.z * second.x;
	}
}
