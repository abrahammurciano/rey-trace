package geometries;

/**
 * This interface is implemented by anything which is intersectible and has a (possibly infinite) boundary. This
 * interface is separate from {@link Intersectible} in order to limit the visibility of the {@link #border()} method to
 * this package.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
interface Boundable extends Intersectible {

	/**
	 * Get the {@link BoundingBox} of this intersectible.
	 *
	 * @return The bounding box of this intersectible.
	 */
	BoundingBox border();
}
