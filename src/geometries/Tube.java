package geometries;

import primitives.Point;
import primitives.Util;
import primitives.Vector;
import primitives.Ray;

/**
 * A Tube is a 3D tube object that goes on to infinity. 
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Tube implements Geometry {
    protected Ray axisRay;
    protected double radius;

    public Tube(Ray axisRay, double radius) {
        // The following 2 if statements are repeated in Sphere ctor. Is there some way
        // to generalize this?
        if (radius < 0) {
            radius = radius * -1;
        }
        if (Util.isZero(radius)) {
            throw new IllegalArgumentException("Error: Radius must be non-zero.");
        }
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Vector axisIntersection(Point p) {
        Vector vp = axisRay.getSource().vectorTo(p); // Vector to p from point in ray
        Vector vr = axisRay.getDirection(); // Vector on Ray
        return vr.scale(vr.dot(vp));
    }

    /** 
     * This function returns the normal to the tube at the given point.
     * It is assumed that the point lies on the surface of the tube.
     * @param p If p lies on on the central axis the function will throw an exception due to Zero Vector.
     * @return normalized normal {@link Vector}
     * */
    @Override
    public Vector normal(Point p) {
        //         |  vector from RaySource to p  |  
        Vector n = axisRay.getSource().vectorTo(p).subtract(axisIntersection(p));
        return n.normalized();
    }
}
