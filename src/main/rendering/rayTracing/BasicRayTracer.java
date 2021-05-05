package rendering.rayTracing;

import primitives.Colour;
import primitives.Ray;
import scene.Scene;

/**
 * This RayTracer only takes into account the ambient light of the scene with no regard to other light sources or the
 * colours of each geometry.
 *
 * @author
 * @author
 */
public class BasicRayTracer extends RayTracer {

	/**
	 * Construct a new BasicRayTracer for the given scene.
	 *
	 * @param scene The scene to trace.
	 */
	public BasicRayTracer(Scene scene) {
		super(scene);
		// TODO: implement
	}

	@Override
	public Colour trace(Ray ray) {
		// TODO: implement
		return null;
	}

}
