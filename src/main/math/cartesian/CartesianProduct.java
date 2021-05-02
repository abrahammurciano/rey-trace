package math.cartesian;

import primitives.Triple;

public class CartesianProduct extends CartesianProductPartial {

	public final double yx;
	public final double zy;
	public final double zx;

	public CartesianProduct(Triple first, Triple second) {
		super(first, second);
		yx = first.y * second.x;
		zy = first.z * second.y;
		zx = first.z * second.x;
	}
}