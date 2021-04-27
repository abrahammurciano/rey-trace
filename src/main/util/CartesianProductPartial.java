package util;

import primitives.Triple;

abstract class CartesianProductPartial {

	public double xx;
	public double yy;
	public double zz;
	public double xy;
	public double yz;
	public double xz;

	protected CartesianProductPartial(Triple first, Triple second) {
		xx = first.x * second.x;
		yy = first.y * second.y;
		zz = first.z * second.z;
		xy = first.x * second.y;
		yz = first.y * second.z;
		xz = first.x * second.z;
	}
}
