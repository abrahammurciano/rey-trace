package geometries;

import org.junit.Assert;
import org.junit.Test;

import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import util.NormalCompare;

/**
 * Tests the methods of the Polygon class.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class CylinderTests {
	public final Ray ray = new Ray(new Point(3,2,1), new Vector(6,5,4));
	public final Cylinder base = new Cylinder(ray, 5, 10);

	@Test 
	public void normal() {
		NormalizedVector calc, actual1, actual2;

		// test on round side
		calc = base.normal(new Point(30.973500981126143, 23, 13.639748528310783));
		actual1 = new NormalizedVector(2,0,-3);
		Assert.assertTrue("Normalized vectors should be equal", NormalCompare.eq(calc, actual1));

		// test on flat side side
		calc =	base.normal(new Point(7.79639313525896, 11.780511727620528, 3.517181606066203));
		actual1 = new NormalizedVector(6,5,4);
		Assert.assertTrue("Normalized vectors should be equal", NormalCompare.eq(calc, actual1));

		// test on corner
		calc = base.normal(new Point(13.920117492216907, -0.46693698629536406, 9.64090596302415));
		actual1 = new NormalizedVector(6,5,4);
		actual2 = new NormalizedVector(1,-2,1);
		// in practice always ends up being normal to the flat side
		Assert.assertTrue("Vectors should be equal", NormalCompare.eq(calc, actual1) || NormalCompare.eq(calc, actual2)); 

		// base side
		calc = base.normal(new Point(5.182178902359924, 6.364357804719848, -7.728715609439696));
		actual1 = new NormalizedVector(6,5,4);
		actual2 = new NormalizedVector(1,-2,1);
		Assert.assertTrue("Vectors should be equal", NormalCompare.eq(calc, actual1) || NormalCompare.eq(calc, actual2)); 

		// center of base
		calc = base.normal(new Point(3,2,1));
		actual1 = new NormalizedVector(6,5,4);
		Assert.assertTrue("Vectors should be equal", NormalCompare.eq(calc, actual1));

		// opposite center
		calc = base. normal(new Point(9.837634587578275,7.698028822981897,5.558423058385518));
		actual1 = new NormalizedVector(6,5,4);
		Assert.assertTrue("Vectors should be equal", NormalCompare.eq(calc, actual1));
	}
}
