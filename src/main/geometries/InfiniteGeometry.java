package geometries;

import primitives.Material;

public abstract class InfiniteGeometry extends Geometry {

	protected InfiniteGeometry(Material material) {
		super(material);
	}

	@Override
	public BoundingBox border() {
		return BoundingBox.INFINITE;
	}
}
