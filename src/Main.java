import primitives.*;
import static java.lang.System.out;
import static primitives.Util.*;

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein
 */
public final class Main {

	/**
	 * Main program to tests initial functionality of the 1st stage
	 *
	 * @param args irrelevant here
	 */
	public static void main(String[] args) {

		try { // test zero vector
			new Vector(0, 0, 0);
			out.println("ERROR: zero vector does not throw an exception");
		} catch (Exception e) {
		}

		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);

		// test length..
		if (!isZero(v1.squareLength() - 14))
			out.println("ERROR: squareLength() wrong value");
		if (!isZero(new Vector(0, 3, 4).length() - 5))
			out.println("ERROR: length() wrong value");

		// test Dot-Product
		if (!isZero(v1.dot(v3)))
			out.println("ERROR: dot() for orthogonal vectors is not zero");
		if (!isZero(v1.dot(v2) + 28))
			out.println("ERROR: dot() wrong value");

		// test Cross-Product
		try { // test zero vector
			v1.cross(v2);
			out.println("ERROR: cross() for parallel vectors does not throw an exception");
		} catch (Exception e) {
			out.print("All's well that ends well.");
		}
		Vector vr = v1.cross(v3);
		if (!isZero(vr.length() - v1.length() * v3.length()))
			out.println("ERROR: cross() wrong result length");
		if (!isZero(vr.dot(v1)) || !isZero(vr.dot(v3)))
			out.println("ERROR: cross() result is not orthogonal to its operands");

		// test vector normalization vs vector length and cross-product
		Vector v = new Vector(1, 2, 3);
		Vector vCopyNormalize = v.normalized();
		if (!isZero(vCopyNormalize.length() - 1))
			out.println("ERROR: normalize() result is not a unit vector");
		Vector u = v.normalized();
		if (u == v)
			out.println("ERROR: normalized() function does not create a new vector");

		// Test operations with points and vectors
		Point p1 = new Point(1, 2, 3);
		if (!Point.ORIGIN.equals(p1.add(new Vector(-1, -2, -3))))
			out.println("ERROR: Point + Vector does not work correctly");
		if (!new Vector(1, 1, 1).equals(new Point(2, 3, 4).vectorTo(p1)))
			out.println("ERROR: Point - Point does not work correctly");

		out.println("If there were no any other outputs - all tests succeeded!");
	}
}
