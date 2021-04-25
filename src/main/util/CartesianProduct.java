package util;

import primitives.Triple;

public class CartesianProduct extends CartesianProductPartial {

	public double xy;
	public double yz;
	public double zx;

	public CartesianProduct(Triple first, Triple second) {
		super(first, second);
		xy = first.x * second.y;
		yz = first.y * second.z;
		zx = first.z * second.x;
	}
}
