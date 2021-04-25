// TODO: DELETE THIS FILE
import primitives.Vector;
import primitives.ZeroVectorException;
import static java.lang.System.out;

import java.util.List;

import util.DoubleCompare;
import primitives.Matrix3x3;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import geometries.Tube;

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein
 * @author Abraham Murciano
 */
public final class Main {

	/**
	 * Main program to tests initial functionality of the 1st stage
	 *
	 * @param args irrelevant here
	 */
	public static void main(String[] args) {
		Ray axis = new Ray(new Point(2,-5,9), new Vector(-5,9,14));
		Tube tube = new Tube(axis, 4.1);
		Ray zoink = new Ray(new Point(4,3,3), new Vector(-2,-2,10));
		List<Point> l = tube.intersect(zoink);
		out.println(l.toString());
	}
}
