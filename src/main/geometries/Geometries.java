package geometries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectible {

	private List<Intersectible> intersectibles = new LinkedList<>()

	@Override
	public List<Point> intersect(Ray ray) {
		List<Point> result = new LinkedList<>();
		for (Intersectible intersectible : intersectibles) {
			result.addAll(intersectible.intersect(ray));
		}
		return result;
	}
}
