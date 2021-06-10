package geometries;

import primitives.Material;

/**
 * This abstract class serves as a base class for geometries which do not fit in a finite bounding box.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public abstract class InfiniteGeometry extends Geometry {

	/**
	 * Constructor that sets the material.
	 *
	 * @param material The material to set.
	 */
	protected InfiniteGeometry(Material material) {
		super(material);
	}

	@Override
	public BoundingBox border() {
		return BoundingBox.INFINITE;
	}
}
