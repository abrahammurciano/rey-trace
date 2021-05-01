package math.cartesian;

import primitives.Triple;

abstract class CartesianProductPartial {

	public final double xx;
	public final double yy;
	public final double zz;
	public final double xy;
	public final double yz;
	public final double xz;

	protected CartesianProductPartial(Triple first, Triple second) {
		xx = first.x * second.x;
		yy = first.y * second.y;
		zz = first.z * second.z;
		xy = first.x * second.y;
		yz = first.y * second.z;
		xz = first.x * second.z;
	}
}
