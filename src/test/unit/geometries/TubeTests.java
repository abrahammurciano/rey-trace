package unit.geometries;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import geometries.Tube;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;

public class TubeTests {

	@Test
	public void testNormal() {
		Ray ray = new Ray(new Point(1, 2, 3), new NormalizedVector(4, 5, 6));
		Tube tube = new Tube(ray, 3);
		// find random point on tube by scaling the vector by some random number,
		// then going perpendicular from there for a length of radius.
		// Scale vector by 6.9
		NormalizedVector calc = tube.normal(new Point(30.942606428329093, 34.625914857336724, 44.4));
		NormalizedVector actual = new NormalizedVector(5, -4, 0);
		Assert.assertEquals("Normalized vectors should be equal", calc, actual);

		// One more, this time perpendicular to the source of the ray
		calc = tube.normal(new Point(1, 8, -2));
		actual = new NormalizedVector(0, 6, -5);
		Assert.assertEquals("Normalized vectors should be equal", calc, actual);
	}

	@Test
	public void testIntersect() {

		Ray axis = new Ray(new Point(-1, 1, 0), new NormalizedVector(1, 1, 1));
		Tube tube = new Tube(axis, Math.sqrt(2));
		Point p1 = new Point(2, 2, 2);
		Point p2 = new Point(0, 4, 2);

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

		// 1. Hits tube twice (starts outside).
		Ray ray = new Ray(new Point(-1, 5, 2), new NormalizedVector(1, -1, 0));
		Assert.assertEquals("Ray which passes through center", new HashSet<>(tube.intersect(ray)),
			new HashSet<>(List.of(p1, p2)));

		// 2. Hits tube once (starts inside).
		ray = new Ray(new Point(1, 3, 2), new NormalizedVector(1, -1, 0));
		Assert.assertEquals("Ray which starts on tube and passes through center", tube.intersect(ray), List.of(p1));

		// 3. Doesn't intersect tube (starts outside)
		ray = new Ray(new Point(3, 1, 2), new NormalizedVector(1, -1, 0));
		Assert.assertEquals("Ray starts outside tube and never enters", tube.intersect(ray), Collections.emptyList());

		// 4. Doesn't intersect tube (starts inside and is parallel to axis
		ray = new Ray(new Point(1.5, 2.5, 2), new NormalizedVector(1, 1, 1));
		Assert.assertEquals("Ray starts inside tube and never exits", tube.intersect(ray), Collections.emptyList());

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

		// Start on side of cylinder going outwards
		ray = new Ray(new Point(2, 2, 2), new NormalizedVector(1, -1, 0));
		Assert.assertEquals("Ray starts on side of tube and points away", tube.intersect(ray), Collections.emptyList());

		// Start on side of cylinder going inwards
		ray = new Ray(new Point(2, 2, 2), new NormalizedVector(-1, 1, 0));
		Assert.assertEquals("Ray starts on side of tube and goes inwards", tube.intersect(ray), List.of(p2));

		// When ray IS axis
		ray = axis;
		Assert.assertEquals("Ray is identical to axis", tube.intersect(ray), Collections.emptyList());

		// Tangent to outside of tube
		ray = new Ray(new Point(3, 3, 1), new NormalizedVector(-1, -1, 1));
		Assert.assertEquals("Ray is tangent to tube", tube.intersect(ray), Collections.emptyList());

		// Start on axis and go orthogonal to it
		ray = new Ray(new Point(0, 2, 1), new NormalizedVector(-1, 1, 0));
		Assert.assertEquals("Ray starts on axis and is orthogonal to it", tube.intersect(ray),
			List.of(new Point(-1, 3, 1)));

		// tube's center is at origin
		tube = new Tube(new Ray(new Point(0, 0, 0), new NormalizedVector(1, 1, 1)), Math.sqrt(2));
		ray = new Ray(new Point(-1, 3, 1), new NormalizedVector(1, -1, 0));
		Assert.assertEquals("Ray starts on axis and is orthogonal to it", new HashSet<>(tube.intersect(ray)),
			new HashSet<>(List.of(new Point(0, 2, 1), new Point(2, 0, 1))));
	}

}
