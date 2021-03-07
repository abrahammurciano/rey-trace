package geometries;

import primitives.Point;
import primitives.Util;
import primitives.Vector;
import primitives.Ray;

/**
 * This class represents a cylinder, which is just a 3D tube with a certain
 * height
 * 
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Cylinder extends Tube {
    private double height;

    /**
     * This contructs a Cylinder.
     * 
     * @param axisRay The ray that makes up the center of the Cylinder.
     * @param radius  A postive double that represents the radius.
     * @param height  A postive double that represents the height of the Cylinder.
     *                Even if passed as a negative number it is stored as a
     *                positive number.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if (height < 0) {
            height = height * -1;
        }
        if (Util.isZero(height)) {
            // this is a disk
            throw new IllegalArgumentException("Error: Height must be a non-zero number.");
        }
        this.height = height;
    }

    /** This function returns the normal vector relative to the {@link Point} p.
     * p is assumed to be on the surface of the Cylinder. Anything else is undefined behavior.
     *
     * @param p The point at which to find the normal vector.
     * */
    @Override
    public Vector normal(Point p) {
        Vector v = super.axisIntersection(p);
        Vector axis = axisRay.getDirection();
        // check if intersection point is beyond the height of the cylinder or less than
        // the base
        // is there a nicer way to do this?
        double d = v.length() - axis.scale(height).length();
        if (Util.isZero(d) || d > 0) { // check magnitude
            return axis;
        } else if (v.dot(axis) < 0) { // check direction
            return axis.scale(-1);
        }
        return axisRay.getSource().vectorTo(p).subtract(v).normalized();
    }
}
