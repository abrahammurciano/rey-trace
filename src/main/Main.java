import primitives.Vector;
import primitives.ZeroVectorException;
import static java.lang.System.out;
import util.DoubleCompare;
import primitives.Point;

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

		try { // test zero vector
			new Vector(0, 0, 0);
			throw new AssertionError("Error: zero vector does not throw an exception");
		} catch (ZeroVectorException e) {
			out.println("Zero vector throws an exception: Passed");
		}

		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);

		// test length..
		if (DoubleCompare.neq(v1.squareLength(), 14)) {
			throw new AssertionError("Error: squareLength() wrong value");
		}
		if (DoubleCompare.neq(new Vector(0, 3, 4).length(), 5)) {
			throw new AssertionError("Error: length() wrong value");
		}
		out.println("Vector length check: Passed");

		// test Dot-Product
		if (DoubleCompare.neq(v1.dot(v3), 0)) {
			throw new AssertionError("Error: dot() for orthogonal vectors is not zero");
		}
		if (DoubleCompare.neq(v1.dot(v2), -28)) {
			throw new AssertionError("Error: dot() wrong value");
		}
		out.println("Dot product check: Passed");

		// test Cross-Product
		try { // test zero vector
			v1.cross(v2);
			throw new AssertionError(
					"Error: cross() for parallel vectors does not throw an exception");
		} catch (ZeroVectorException e) {
			out.println("Cross product throws zero vector exception: Passed");
		}
		Vector vr = v1.cross(v3);
		if (DoubleCompare.neq(vr.length(), v1.length() * v3.length())) {
			throw new AssertionError("Error: cross() wrong result length");
		}
		if (DoubleCompare.neq(vr.dot(v1), 0) || DoubleCompare.neq(vr.dot(v3), 0)) {
			throw new AssertionError("Error: cross() result is not orthogonal to its operands");
		}
		out.println("Cross product checks: Passed");

		// test vector normalization vs vector length and cross-product
		Vector v = new Vector(1, 2, 3);
		Vector vCopyNormalize = v.normalized();
		if (DoubleCompare.neq(vCopyNormalize.length(), 1)) {
			throw new AssertionError("Error: normalize() result is not a unit vector");
		}
		Vector u = v.normalized();
		if (u == v) {
			throw new AssertionError("Error: normalized() function does not create a new vector");
		}
		out.println("Normalization check: Passed");

		// Test operations with points and vectors
		Point p1 = new Point(1, 2, 3);
		if (!Point.ORIGIN.equals(p1.add(new Vector(-1, -2, -3)))) {
			throw new AssertionError("Error: Point + Vector does not work correctly");
		}
		if (!new Vector(1, 1, 1).equals(new Point(2, 3, 4).vectorFrom(p1))) {
			throw new AssertionError("Error: Point - Point does not work correctly");
		}
		out.println("Point and vector operations check: Passed");

		out.println("All tests succeeded!");
	}
}
