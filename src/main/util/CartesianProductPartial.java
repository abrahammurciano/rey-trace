package util;

import primitives.Triple;

abstract class CartesianProductPartial {

	public double xx;
	public double yy;
	public double zz;
	public double yx;
	public double zy;
	public double xz;

	protected CartesianProductPartial(Triple first, Triple second) {
		xx = first.x * second.x;
		yy = first.y * second.y;
		zz = first.z * second.z;
		yx = first.y * second.x;
		zy = first.z * second.y;
		xz = first.x * second.z;
	}
}
