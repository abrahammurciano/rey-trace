package geometries;

import static java.lang.System.out;
import primitives.ZeroVectorException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import primitives.Matrix3x3;
import primitives.NormalizedVector;
import primitives.Point;
import util.DoubleCompare;
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
	private Matrix3x3 rotation;
	private Matrix3x3 rotationInv;

	/**
	 * Constructs a {@link Tube} with the source at the same source and direction as the given axis {@link Ray}.
	 *
	 * @param axis The {@link Ray} from which to get the source and direction.
	 * @param radius The distance from the axis to the surface.
	 * @throws IllegalArgumentException if the radius is zero.
	 */
	public Tube(Ray axis, double radius) {
		if (DoubleCompare.eq(radius, 0)) {
			throw new IllegalArgumentException("Error: Radius must be non-zero.");
		}
		this.axis = axis;
		this.radius = Math.abs(radius);
		// Get rotation matrix from tube axis to the z-axis
		if (direction().equals(new NormalizedVector(0, 0, 1))) {
			rotation = rotationInv = Matrix3x3.IDENTITY;
		} else {
			rotation = Matrix3x3.getRotation(direction(), new NormalizedVector(0, 0, 1));
			rotationInv = rotation.inverseRotMat();
		}
	}

	/**
	 * Gets the axis {@link Vector} of the {@link Tube}.
	 *
	 * @return The axis {@link Vector} of the {@link Tube}.
	 */
	public NormalizedVector direction() {
		return axis.direction;
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
		Vector sourceToP = axis.source.vectorTo(p);
		double dotProduct = direction().dot(sourceToP);
		if (DoubleCompare.eq(dotProduct, 0)) { // Would throw a zero vector exception if not checked
			return sourceToP.normalized();
		}
		return sourceToP.subtract(direction().scale(dotProduct)).normalized();
	}


	/**
	 * This function will find intersection points between a {@link Ray} and an {@link Cylinder}. The general steps are as
	 * follows: 1) Rotate and shift space such that the cylinders center lies on the origin 2) Project the {@link Ray} r
	 * onto the the xy plane. 3) Find 2 dimensional intersections with with circle about the origin with radius of the
	 * {@link Cylinder}. 4) Bring those intersections back into 3 dimensions 5) Undo the rotation and the shift done at the
	 * beginning.
	 *
	 * @param r The {@link Ray} to intersect
	 * @return a list (possibly empty) of intersection points
	 */
	// @Override
	public List<Point> intersect(Ray r) {
		double x1, y1, x2, y2;
		Vector toCenter = null;
		Vector fromCenter = null;
		Point A, B;
		try {
			toCenter = new Vector(axis.source); // could throw
			fromCenter = toCenter.reversed();
			A = rotation.multiply(r.source.add(fromCenter));
			B = rotation.multiply(r.source.add(r.direction).add(fromCenter));
		} catch (ZeroVectorException e) {
			A = rotation.multiply(r.source);
			B = rotation.multiply(r.source.add(r.direction));
		}
		Vector newDirection = A.vectorTo(B);
		if (DoubleCompare.eq(A.x, B.x)) {
			// y^2 = r^2 - A.x^2
			x1 = x2 = A.x;
			double ySquared = A.x * A.x - radius * radius;
			if (DoubleCompare.leq(ySquared, 0)) {
				return Collections.emptyList();
			}
			y1 = Math.sqrt(ySquared);
			y2 = -y1;
		} else {
			double slope = (A.y - B.y) / (A.x - B.x);
			double yIntercept = (-1 * slope * A.x) + A.y;
			// intersection of line and circle will give a quadratic
			double a = 1 + (slope * slope);
			double b = 2 * slope * (yIntercept);
			double c = (yIntercept * yIntercept) - (radius * radius);
			double det = b * b - 4 * a * c;
			if (DoubleCompare.leq(det, 0.0)) {
				return Collections.emptyList();
			}
			x1 = (-b + Math.sqrt(det)) / (2 * a);
			y1 = (slope * x1) + yIntercept;
			x2 = (-b - Math.sqrt(det)) / (2 * a);
			y2 = (slope * x2) + yIntercept;
		}
		double div;
		if (DoubleCompare.eq(newDirection.head.x, 0)) {
			div = newDirection.head.y;
		} else {
			div = newDirection.head.x;
		}
		if (DoubleCompare.eq(div, 0)) {
			return Collections.emptyList();
		}
		double t1 = (x1 - A.x) / div;
		double t2 = (x2 - A.x) / div;
		List<Point> l = new ArrayList<Point>();
		if (DoubleCompare.gt(t1, 0.0)) {
			double z = A.z + (t1 * newDirection.head.z);
			Point intersection;
			if (toCenter == null) {
				intersection = rotationInv.multiply(new Point(x1, y1, z));
			} else {
				intersection = rotationInv.multiply(new Point(x1, y1, z)).add(toCenter);
			}
			l.add(intersection);
		}
		if (DoubleCompare.gt(t2, 0.0)) {
			double z = A.z + (t2 * newDirection.head.z);
			Point intersection;
			if (toCenter == null) {
				intersection = rotationInv.multiply(new Point(x2, y2, z));
			} else {
				intersection = rotationInv.multiply(new Point(x2, y2, z)).add(toCenter);
			}
			l.add(intersection);
		}
		return l;
	}



	public List<Point> intersectBetter(Ray r) {
		// shift everything so that center is on origin
		// Axis of Tube now is origin alligned and going through (x_a, y_a, z_a) with distance of 1 from origin
		// Ray is represesented by Point(x_p, y_p, z_p) and NormalizedVector(x_v, y_v, z_v)
		double x_a2 = axis.direction.length

	}



}
