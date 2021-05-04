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

	public final double xx;
	public final double yy;
	public final double zz;
	public final double xy;
	public final double yz;
	public final double xz;

	protected FastMatrixMultPartial(Triple first, Triple second) {
		xx = first.x * second.x;
		yy = first.y * second.y;
		zz = first.z * second.z;
		xy = first.x * second.y;
		yz = first.y * second.z;
		xz = first.x * second.z;
	}
}
