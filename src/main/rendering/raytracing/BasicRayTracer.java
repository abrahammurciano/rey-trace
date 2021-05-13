package rendering.raytracing;

import java.util.List;
import geometries.Intersection;
import primitives.Colour;
import primitives.Ray;
import scene.Scene;

/**
 * This RayTracer only takes into account the ambient light of the scene with no regard to other light sources or the
 * colours of each geometry.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class BasicRayTracer extends RayTracer {

	/**
	 * Construct a new BasicRayTracer for the given scene.
	 *
	 * @param scene The scene to trace.
	 */
	public BasicRayTracer(Scene scene) {
		super(scene);
	}

	@Override
	public Colour trace(Ray ray) {
		List<Intersection> intersections = scene.geometries.intersect(ray);
		if (intersections.isEmpty()) {
			return scene.background;
		} else {
			return scene.ambient.colour.add(ray.closest(intersections).emission());
		}
	}
}
