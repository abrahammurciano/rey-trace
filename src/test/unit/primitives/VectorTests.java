package unit.primitives;

import org.junit.Assert;
import org.junit.Test;

import math.compare.DoubleCompare;
import primitives.Point;
import primitives.NonZeroVector;
import primitives.ZeroVectorException;

/**
 * Tests the functions of the vector class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class VectorTests {
	private static final String NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT = "Non-parallel vectors gave wrong result.";
	private static final String PARALLEL_VECTORS_GAVE_WRONG_RESULT = "Parallel vectors gave wrong result.";
	private final NonZeroVector base = new NonZeroVector(1, 2, 3);

	/**
	 * Tests Vector.cross
	 */
	@Test
	public void cross() {
		NonZeroVector calc, actual;

		// @formatter:off
		//  _____            _            _
		// | ____|__ _ _   _(_)_   ____ _| | ___ _ __   ___ ___
		// |  _| / _` | | | | \ \ / / _` | |/ _ \ '_ \ / __/ _ \
		// | |__| (_| | |_| | |\ V / (_| | |  __/ | | | (_|  __/
		// |_____\__, |\__,_|_| \_/ \__,_|_|\___|_| |_|\___\___|
		//          |_|
		//  ____            _   _ _   _
		// |  _ \ __ _ _ __| |_(_) |_(_) ___  _ __  ___
		// | |_) / _` | '__| __| | __| |/ _ \| '_ \/ __|
		// |  __/ (_| | |  | |_| | |_| | (_) | | | \__ \
		// |_|   \__,_|_|   \__|_|\__|_|\___/|_| |_|___/
		// @formatter:on

		// Same quadrant
		calc = base.cross(new NonZeroVector(3, 4, 5));
		actual = new NonZeroVector(-2, 4, -2);
		Assert.assertEquals("Wrong output for cross product in the same quadrant.", calc, actual);

		// Opposite quadrant
		calc = base.cross(new NonZeroVector(-2, -4, -3));
		actual = new NonZeroVector(6, -3, 0);
		Assert.assertEquals("Wrong output for cross product in opposite quadrant.", calc, actual);

		// Other quadrant
		calc = base.cross(new NonZeroVector(-2, -8, 5));
		actual = new NonZeroVector(34, -11, -4);
		Assert.assertEquals("Wrong output for cross product in other quadrant.", calc, actual);

		// @formatter:off
		//  ____                        _
		// | __ )  ___  _   _ _ __   __| | __ _ _ __ _   _
		// |  _ \ / _ \| | | | '_ \ / _` |/ _` | '__| | | |
		// | |_) | (_) | |_| | | | | (_| | (_| | |  | |_| |
		// |____/ \___/ \__,_|_| |_|\__,_|\__,_|_|   \__, |
		//  _____         _                          |___/
		// |_   _|__  ___| |_ ___
		//   | |/ _ \/ __| __/ __|
		//   | |  __/\__ \ |_\__ \
		//   |_|\___||___/\__|___/
		// @formatter:on

		NonZeroVector colinear = new NonZeroVector(-2, -4, -6);
		Assert.assertThrows("Expected ZeroVectorException to be thrown for opposite vectors.",
			ZeroVectorException.class, () -> base.cross(colinear));
		Assert.assertThrows("Expected ZeroVectorException to be thrown co-directional vectors.",
			ZeroVectorException.class, () -> base.cross(colinear.reversed()));
	}

	/**
	 * Test Vector.add
	 */
	@Test
	public void add() {

		// @formatter:off
		//  _____            _            _
		// | ____|__ _ _   _(_)_   ____ _| | ___ _ __   ___ ___
		// |  _| / _` | | | | \ \ / / _` | |/ _ \ '_ \ / __/ _ \
		// | |__| (_| | |_| | |\ V / (_| | |  __/ | | | (_|  __/
		// |_____\__, |\__,_|_| \_/ \__,_|_|\___|_| |_|\___\___|
		//          |_|
		//  ____            _   _ _   _
		// |  _ \ __ _ _ __| |_(_) |_(_) ___  _ __  ___
		// | |_) / _` | '__| __| | __| |/ _ \| '_ \/ __|
		// |  __/ (_| | |  | |_| | |_| | (_) | | | \__ \
		// |_|   \__,_|_|   \__|_|\__|_|\___/|_| |_|___/
		// @formatter:on

		Assert.assertEquals(NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.add(new NonZeroVector(3, 2, 4)),
			new NonZeroVector(4, 4, 7));
		Assert.assertEquals(NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.add(new NonZeroVector(-3, -2, -4)),
			new NonZeroVector(-2, 0, -1));

		// @formatter:off
		//  ____                        _
		// | __ )  ___  _   _ _ __   __| | __ _ _ __ _   _
		// |  _ \ / _ \| | | | '_ \ / _` |/ _` | '__| | | |
		// | |_) | (_) | |_| | | | | (_| | (_| | |  | |_| |
		// |____/ \___/ \__,_|_| |_|\__,_|\__,_|_|   \__, |
		//  _____         _                          |___/
		// |_   _|__  ___| |_ ___
		//   | |/ _ \/ __| __/ __|
		//   | |  __/\__ \ |_\__ \
		//   |_|\___||___/\__|___/
		// @formatter:on

		// Parallel vectors
		Assert.assertEquals(PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.add(base), new NonZeroVector(2, 4, 6));
		Assert.assertEquals(PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.add(new NonZeroVector(-2, -4, -6)),
			new NonZeroVector(-1, -2, -3));
		Assert.assertThrows("A vector plus its negation should throw a ZeroVectorException.", ZeroVectorException.class,
			() -> base.add(new NonZeroVector(-1, -2, -3)));
	}

	/**
	 * Test Vector.subtract
	 */
	@Test
	public void subtract() {

		// @formatter:off
		//  _____            _            _
		// | ____|__ _ _   _(_)_   ____ _| | ___ _ __   ___ ___
		// |  _| / _` | | | | \ \ / / _` | |/ _ \ '_ \ / __/ _ \
		// | |__| (_| | |_| | |\ V / (_| | |  __/ | | | (_|  __/
		// |_____\__, |\__,_|_| \_/ \__,_|_|\___|_| |_|\___\___|
		//          |_|
		//  ____            _   _ _   _
		// |  _ \ __ _ _ __| |_(_) |_(_) ___  _ __  ___
		// | |_) / _` | '__| __| | __| |/ _ \| '_ \/ __|
		// |  __/ (_| | |  | |_| | |_| | (_) | | | \__ \
		// |_|   \__,_|_|   \__|_|\__|_|\___/|_| |_|___/
		// @formatter:on

		Assert.assertEquals(NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.subtract(new NonZeroVector(3, 2, 4)),
			new NonZeroVector(-2, 0, -1));
		Assert.assertEquals(NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.subtract(new NonZeroVector(-3, -2, -4)),
			new NonZeroVector(4, 4, 7));

		// @formatter:off
		//  ____                        _
		// | __ )  ___  _   _ _ __   __| | __ _ _ __ _   _
		// |  _ \ / _ \| | | | '_ \ / _` |/ _` | '__| | | |
		// | |_) | (_) | |_| | | | | (_| | (_| | |  | |_| |
		// |____/ \___/ \__,_|_| |_|\__,_|\__,_|_|   \__, |
		//  _____         _                          |___/
		// |_   _|__  ___| |_ ___
		//   | |/ _ \/ __| __/ __|
		//   | |  __/\__ \ |_\__ \
		//   |_|\___||___/\__|___/
		// @formatter:on

		// Parallel vectors
		Assert.assertEquals(PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.subtract(new NonZeroVector(-1, -2, -3)),
			new NonZeroVector(2, 4, 6));
		Assert.assertEquals(PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.subtract(new NonZeroVector(2, 4, 6)),
			new NonZeroVector(-1, -2, -3));
		Assert.assertThrows("A vector minus itself should throw a ZeroVectorException.", ZeroVectorException.class,
			() -> base.subtract(new NonZeroVector(1, 2, 3)));
	}

	/**
	 * Test Vector.scale
	 */
	@Test
	public void scale() {

		// @formatter:off
		//  _____            _            _
		// | ____|__ _ _   _(_)_   ____ _| | ___ _ __   ___ ___
		// |  _| / _` | | | | \ \ / / _` | |/ _ \ '_ \ / __/ _ \
		// | |__| (_| | |_| | |\ V / (_| | |  __/ | | | (_|  __/
		// |_____\__, |\__,_|_| \_/ \__,_|_|\___|_| |_|\___\___|
		//          |_|
		//  ____            _   _ _   _
		// |  _ \ __ _ _ __| |_(_) |_(_) ___  _ __  ___
		// | |_) / _` | '__| __| | __| |/ _ \| '_ \/ __|
		// |  __/ (_| | |  | |_| | |_| | (_) | | | \__ \
		// |_|   \__,_|_|   \__|_|\__|_|\___/|_| |_|___/
		// @formatter:on

		Assert.assertEquals("Wrong output for scale factor > 1", base.scale(2), new NonZeroVector(2, 4, 6));
		Assert.assertEquals("Wrong output for scale factor in range (0,1)", base.scale(0.5),
			new NonZeroVector(0.5, 1, 1.5));
		Assert.assertEquals("Wrong output for scale factor in range (-1,0)", base.scale(-0.5),
			new NonZeroVector(-0.5, -1, -1.5));
		Assert.assertEquals("Wrong output for scale factor < -1", base.scale(-2), new NonZeroVector(-2, -4, -6));

		// @formatter:off
		//  ____                        _
		// | __ )  ___  _   _ _ __   __| | __ _ _ __ _   _
		// |  _ \ / _ \| | | | '_ \ / _` |/ _` | '__| | | |
		// | |_) | (_) | |_| | | | | (_| | (_| | |  | |_| |
		// |____/ \___/ \__,_|_| |_|\__,_|\__,_|_|   \__, |
		//  _____         _                          |___/
		// |_   _|__  ___| |_ ___
		//   | |/ _ \/ __| __/ __|
		//   | |  __/\__ \ |_\__ \
		//   |_|\___||___/\__|___/
		// @formatter:on

		Assert.assertEquals("Wrong output for scale factor 1", base.scale(1), new NonZeroVector(1, 2, 3));
		Assert.assertThrows("Scale factor of zero did not throw ZeroVectorException.", ZeroVectorException.class,
			() -> base.scale(0));
		Assert.assertEquals("Wrong output for scale factor -1", base.scale(-1), new NonZeroVector(-1, -2, -3));
	}

	/**
	 * Test Vector.reversed
	 */
	@Test
	public void reversed() {
		Assert.assertEquals("Wrong reversed vector.", base.reversed(), new NonZeroVector(-1, -2, -3));
	}

	/**
	 * Test Vector.dot
	 */
	@Test
	public void dot() {

		// @formatter:off
		//  _____            _            _
		// | ____|__ _ _   _(_)_   ____ _| | ___ _ __   ___ ___
		// |  _| / _` | | | | \ \ / / _` | |/ _ \ '_ \ / __/ _ \
		// | |__| (_| | |_| | |\ V / (_| | |  __/ | | | (_|  __/
		// |_____\__, |\__,_|_| \_/ \__,_|_|\___|_| |_|\___\___|
		//          |_|
		//  ____            _   _ _   _
		// |  _ \ __ _ _ __| |_(_) |_(_) ___  _ __  ___
		// | |_) / _` | '__| __| | __| |/ _ \| '_ \/ __|
		// |  __/ (_| | |  | |_| | |_| | (_) | | | \__ \
		// |_|   \__,_|_|   \__|_|\__|_|\___/|_| |_|___/
		// @formatter:on

		Assert.assertTrue("Acute vectors give wrong dot product.",
			DoubleCompare.eq(base.dot(new NonZeroVector(1, 2, 4)), 17));
		Assert.assertTrue("Obtuse vectors give wrong dot product.",
			DoubleCompare.eq(base.dot(new NonZeroVector(-2, -2, -3)), -15));

		// @formatter:off
		//  ____                        _
		// | __ )  ___  _   _ _ __   __| | __ _ _ __ _   _
		// |  _ \ / _ \| | | | '_ \ / _` |/ _` | '__| | | |
		// | |_) | (_) | |_| | | | | (_| | (_| | |  | |_| |
		// |____/ \___/ \__,_|_| |_|\__,_|\__,_|_|   \__, |
		//  _____         _                          |___/
		// |_   _|__  ___| |_ ___
		//   | |/ _ \/ __| __/ __|
		//   | |  __/\__ \ |_\__ \
		//   |_|\___||___/\__|___/
		// @formatter:on

		Assert.assertTrue("Parallel vectors give wrong dot product.",
			DoubleCompare.eq(base.dot(new NonZeroVector(2, 4, 6)), 28));
		Assert.assertTrue("Perpendicular vectors give wrong dot product.",
			DoubleCompare.eq(base.dot(new NonZeroVector(3, -4, 5d / 3)), 0));
		Assert.assertTrue("Antiparallel vectors give wrong dot product.",
			DoubleCompare.eq(base.dot(new NonZeroVector(-2, -4, -6)), -28));
	}

	/**
	 * Test Vector.length
	 */
	@Test
	public void length() {
		Assert.assertTrue("Wrong vector length.", DoubleCompare.eq(base.length(), 3.741657386773941));
	}

	/**
	 * Test Vector.normalized
	 */
	@Test
	public void normalized() {

		// @formatter:off
		//  _____            _            _
		// | ____|__ _ _   _(_)_   ____ _| | ___ _ __   ___ ___
		// |  _| / _` | | | | \ \ / / _` | |/ _ \ '_ \ / __/ _ \
		// | |__| (_| | |_| | |\ V / (_| | |  __/ | | | (_|  __/
		// |_____\__, |\__,_|_| \_/ \__,_|_|\___|_| |_|\___\___|
		//          |_|
		//  ____            _   _ _   _
		// |  _ \ __ _ _ __| |_(_) |_(_) ___  _ __  ___
		// | |_) / _` | '__| __| | __| |/ _ \| '_ \/ __|
		// |  __/ (_| | |  | |_| | |_| | (_) | | | \__ \
		// |_|   \__,_|_|   \__|_|\__|_|\___/|_| |_|___/
		// @formatter:on

		Assert.assertTrue("Vector with magnitude larger than 1 not normalized correctly.",
			DoubleCompare.eq(Point.ORIGIN.add(new NonZeroVector(1, 2, 3).normalized()).distance(Point.ORIGIN), 1));
		Assert.assertTrue("Vector with magnitude smaller than 1 not normalized correctly.", DoubleCompare
			.eq(Point.ORIGIN.add(new NonZeroVector(0.1, 0.2, 0.3).normalized()).distance(Point.ORIGIN), 1));

		// @formatter:off
		//  ____                        _
		// | __ )  ___  _   _ _ __   __| | __ _ _ __ _   _
		// |  _ \ / _ \| | | | '_ \ / _` |/ _` | '__| | | |
		// | |_) | (_) | |_| | | | | (_| | (_| | |  | |_| |
		// |____/ \___/ \__,_|_| |_|\__,_|\__,_|_|   \__, |
		//  _____         _                          |___/
		// |_   _|__  ___| |_ ___
		//   | |/ _ \/ __| __/ __|
		//   | |  __/\__ \ |_\__ \
		//   |_|\___||___/\__|___/
		// @formatter:on

		Assert.assertTrue("Vector with magnitude equal to 1 not normalized correctly.",
			DoubleCompare.eq(Point.ORIGIN
				.add(new NonZeroVector(1.414213562373095, 1.414213562373095, 1.414213562373095).normalized())
				.distance(Point.ORIGIN), 1));
	}

	/**
	 * Test Vector.angle
	 */
	@Test
	public void angle() {

		// @formatter:off
		//  _____            _            _
		// | ____|__ _ _   _(_)_   ____ _| | ___ _ __   ___ ___
		// |  _| / _` | | | | \ \ / / _` | |/ _ \ '_ \ / __/ _ \
		// | |__| (_| | |_| | |\ V / (_| | |  __/ | | | (_|  __/
		// |_____\__, |\__,_|_| \_/ \__,_|_|\___|_| |_|\___\___|
		//          |_|
		//  ____            _   _ _   _
		// |  _ \ __ _ _ __| |_(_) |_(_) ___  _ __  ___
		// | |_) / _` | '__| __| | __| |/ _ \| '_ \/ __|
		// |  __/ (_| | |  | |_| | |_| | (_) | | | \__ \
		// |_|   \__,_|_|   \__|_|\__|_|\___/|_| |_|___/
		// @formatter:on

		Assert.assertTrue("Acute vectors give wrong angle.", DoubleCompare.eq(base.angle(new NonZeroVector(1, 2, 4)),
			0.130782633847917656944056860734086768630736087062570540166461649983025095922235607171292334532715044));
		Assert.assertTrue("Obtuse vectors give wrong angle.",
			DoubleCompare.eq(base.angle(new NonZeroVector(-2, -2, -3)), 2.905697773154866));

		// @formatter:off
		//  ____                        _
		// | __ )  ___  _   _ _ __   __| | __ _ _ __ _   _
		// |  _ \ / _ \| | | | '_ \ / _` |/ _` | '__| | | |
		// | |_) | (_) | |_| | | | | (_| | (_| | |  | |_| |
		// |____/ \___/ \__,_|_| |_|\__,_|\__,_|_|   \__, |
		//  _____         _                          |___/
		// |_   _|__  ___| |_ ___
		//   | |/ _ \/ __| __/ __|
		//   | |  __/\__ \ |_\__ \
		//   |_|\___||___/\__|___/
		// @formatter:on

		Assert.assertTrue("Parallel vectors give wrong angle.",
			DoubleCompare.eq(base.angle(new NonZeroVector(2, 4, 6)), 0));
		Assert.assertTrue("Perpendicular vectors give wrong angle.",
			DoubleCompare.eq(base.angle(new NonZeroVector(3, -4, 5d / 3)), Math.PI / 2));
		Assert.assertTrue("Antiparallel vectors give wrong angle.",
			DoubleCompare.eq(base.angle(new NonZeroVector(-2, -4, -6)), Math.PI));
	}

	/**
	 * Test Vector.equals
	 */
	@Test
	public void equals() {
		Assert.assertEquals("Equivalent vectors not treated as such.", base, new NonZeroVector(1, 2, 3));
		Assert.assertNotEquals("Nonequivalent vectors are treated as equal.", base, new NonZeroVector(2, 2, 3));
	}
}
