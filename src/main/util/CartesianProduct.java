package util;

import primitives.Triple;

public class CartesianProduct extends CartesianProductPartial {

	public double yx;
	public double zy;
	public double zx;

	public CartesianProduct(Triple first, Triple second) {
		super(first, second);
		yx = first.y * second.x;
		zy = first.z * second.y;
		zx = first.z * second.x;
	}
}
