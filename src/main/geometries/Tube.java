package geometries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.VectorBase;
import primitives.ZeroVectorException;
import math.compare.DoubleCompare;
import math.equations.Linear;
import math.equations.Polynomial;
import math.equations.Quadratic;
import math.matrices.FastMatrixMult;
import math.matrices.FastMatrixMultSelf;

/**
 * A Tube is a 3D tube object that goes on to infinity.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Tube implements Geometry {
	/** The axis {@link Ray} of the tube. */
	public final Ray axis;
	/** The radius of the tube. */
	public final double radius;

	private VectorBase toOrigin = null, fromOrigin = null;

	/**
	 * Constructs a {@link Tube} with the source at the same source and direction as
	 * the given axis {@link Ray}.
	 *
	 * @param axis   The {@link Ray} from which to get the source and direction.
	 * @param radius The distance from the axis to the surface.
	 * @throws IllegalArgumentException if the radius is zero.
	 */
	public Tube(Ray axis, double radius) {
		if (DoubleCompare.eq(radius, 0)) {
			throw new IllegalArgumentException("Error: Radius must be non-zero.");
		}
		this.axis = axis;
		this.radius = Math.abs(radius);

		toOrigin = axis.source.vectorBaseTo(Point.ORIGIN);
		fromOrigin = toOrigin.reversed();
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
	 * This function returns the normal to the tube at the given point. If the point
	 * doesn't lie on the surface of the tube, the behavior is undefined.
	 *
	 * @param p The {@link Point} to get the normal at.
	 * @return The normalized normal {@link Vector}
	 * @throws ZeroVectorException if the p is equal to the source @{@link Point} of
	 *                             the {@link Tube}.
	 */
	@Override
	public NormalizedVector normal(Point p) {
		Vector sourceToP = axis.source.vectorTo(p);
		double dotProduct = direction().dot(sourceToP);
		return sourceToP.subtract(direction().scale(dotProduct, VectorBase::new)).normalized();
	}

	/**
	 * This function will find intersection points (possibly none) between a
	 * {@link Ray} and an {@link Tube}.
	 *
	 * @param r The {@link Ray} to intersect
	 * @return a list (possibly empty) of intersection points
	 */
	@Override
	public List<Point> intersect(Ray r) {
		Point source;
		if (!axis.source.equals(Point.ORIGIN)) {
			source = r.source.add(toOrigin);
		} else {
			source = r.source;
		}
		FastMatrixMultSelf a = new FastMatrixMultSelf(axis.direction);
		FastMatrixMultSelf p = new FastMatrixMultSelf(source); // p for point
		FastMatrixMultSelf v = new FastMatrixMultSelf(r.direction); // v for vector
		FastMatrixMult pv = new FastMatrixMult(source, r.direction); // pv for...
		// @formatter:off
		double A =
			(a.yy * v.xx) - 2*(a.xy * v.xy) + (a.xx * v.yy) +
			(a.zz * v.yy) - 2*(a.yz * v.yz) + (a.yy * v.zz) +
			(a.xx * v.zz) - 2*(a.xz * v.xz) + (a.zz * v.xx);
		double B = 2 * (
			(a.yy * pv.xx) - a.xy*(pv.yx + pv.xy) + (a.xx * pv.yy) +
			(a.zz * pv.yy) - a.yz*(pv.zy + pv.yz) + (a.yy * pv.zz) +
			(a.xx * pv.zz) - a.xz*(pv.xz + pv.zx) + (a.zz * pv.xx));
		double C =
			(a.yy * p.xx) - 2*(a.xy * p.xy) + (a.xx * p.yy) +
			(a.zz * p.yy) - 2*(a.yz * p.yz) + (a.yy * p.zz) +
			(a.xx * p.zz) - 2*(a.xz * p.xz) + (a.zz * p.xx) -
			(radius * radius);
		// @formatter:on
		Polynomial equation;
		if (A == 0) {
			if (DoubleCompare.eq(B, 0)) {
				return Collections.emptyList();
			}
			equation = new Linear(B, C);
		} else {
			Quadratic quadratic = new Quadratic(A, B, C);
			if (DoubleCompare.leq(quadratic.discriminant, 0)) {
				return Collections.emptyList();
			}
			equation = quadratic;
		}
		List<Point> intersections = new ArrayList<>(2);
		Ray shiftedRay = new Ray(source, r.direction);
		for (double t : equation.solutions()) {
			fillList(intersections, shiftedRay, t);
		}
		return intersections;
	}

	// helper function for intersection
	private void fillList(List<Point> intersections, Ray r, double t) {
		if (DoubleCompare.leq(t, 0)) {
			return;
		}
		Point p = r.travel(t);
		p = p.add(fromOrigin);
		intersections.add(p);
	}

}
