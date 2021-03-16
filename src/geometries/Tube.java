package geometries;

import primitives.ZeroVectorException;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Util;
import primitives.Vector;
import primitives.Ray;

/**
 * A Tube is a 3D tube object that goes on to infinity.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Tube implements Geometry {
	private Ray axis;
	private double radius;

	/**
	 * Constructs a {@link Tube} with the source at the same source and direction as the given axis {@link Ray}.
	 *
	 * @param axis   The {@link Ray} from which to get the source and direction.
	 * @param radius The distance from the axis to the surface.
	 * @throws IllegalArgumentException if the radius is zero.
	 */
	public Tube(Ray axis, double radius) {
		if (Util.isZero(radius)) {
			throw new IllegalArgumentException("Error: Radius must be non-zero.");
		}
		this.axis = axis;
		this.radius = Math.abs(radius);
	}

	/**
	 * Gets the axis {@link Vector} of the {@link Tube}.
	 *
	 * @return The axis {@link Vector} of the {@link Tube}.
	 */
	public NormalizedVector direction() {
		return axis.direction();
	}

	/**
	 * This function returns the normal to the tube at the given point. If the point doesn't lie on the surface of the tube,
	 * the behavior is undefined.
	 *
	 * @param p The {@link Point} to get the normal at.
	 * @return The normalized normal {@link Vector}
	 * @throws ZeroVectorException if the p is equal to the source @{@link Point} of the {@link Tube}.
	 */
	@Override
	public NormalizedVector normal(Point p) {
		Vector sourceToP = axis.source().vectorTo(p);
		double dotProduct = direction().dot(sourceToP);
		if (Util.isZero(dotProduct)) { // Would throw a zero vector exception if not checked
			return sourceToP.normalized();
		}
		return sourceToP.subtract(direction().scale(dotProduct)).normalized();
	}
}
