package geometries;

import org.junit.Assert;
import org.junit.Test;

import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class CylinderTests {
	public final Ray ray = new Ray(new Point(3,2,1), new Vector(6,5,4));
	public final Cylinder base = new Cylinder(ray, 5, 10);

	@Test 
	public void normal() {

		// test on round side
		// 30.973500981126143 23, 12.639748528310783
		NormalizedVector calc = base.normal(new Point(30.973500981126143, 23, 13.639748528310783));
		NormalizedVector actual = new NormalizedVector(4,0,-6);
		Assert.assertEquals("Normalized vectors should be equal", calc, actual);

		// test on flat side
		calc =	base.normal(new Point(7.79639313525896, 11.780511727620528, 3.517181606066203));
		actual = new NormalizedVector(6,5,4);
		Assert.assertEquals("Normalized vectors should be equal", calc, actual);
	}
}
