package util;

import primitives.Triple;

public class CartesianProductSelf extends CartesianProductPartial {

	public CartesianProductSelf(Triple first) {
		super(first, first);
	}
}
