package geometries;

import primitives.Point;
import primitives.Util;
import primitives.Vector;

/**
 * This represents a sphere. A sphere is described by a center {@link Point} and
 * a positive radius.
 * 
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Sphere implements Geometry {
    private Point center;
    private double radius;

    /**
     * Constructs a sphere from a given center point and a radius.
     * 
     * @param center A {@link Point} representing the center of the circle.
     * @param radius A positive number representing the radius. If given a negative
     *               number will be assumed to be positive.
     */
    public Sphere(Point center, double radius) {
        if (radius < 0) {
            radius = radius * -1;
        }
        if (Util.isZero(radius)) {
            throw new IllegalArgumentException("Error: Radius must not be zero.");
        }
        this.center = center;
        this.radius = radius;
    }

    /**
     * Checks if given point is on surface of the sphere.
     * 
     * @param p The {@link Point} to check.
     * @return True if point is on surface of Sphere, false otherwise.
     */
    public boolean isOnSurface(Point p) {
        return Util.isZero(center.distance(p) - radius);
    }

    /**
     * Returns normal to the sphere at given point.
     * 
     * @param p The point to get normal at. This point is assumed to be on the
     *          surface of the circle. If it's not the it is undefined behavior.
     *          If p equals the center then zero Vector exception is thrown.
     */
    @Override
    public Vector normal(Point p) {
        return center.vectorTo(p).normalized();
    }
}
