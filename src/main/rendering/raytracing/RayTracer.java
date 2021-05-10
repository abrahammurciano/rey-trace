package rendering.raytracing;

import primitives.Colour;
import primitives.Ray;
import scene.Scene;

/**
 * Classes which extend {@link RayTracer} are responsible for calculating the {@link Colour} at the intersections of a
 * {@link Ray} in the {@link Scene}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public abstract class RayTracer {
	/** The scene to trace each ray in. */
	protected final Scene scene;

	/**
	 * Construct a new {@link RayTracer} for the given {@link Scene}.
	 *
	 * @param scene The {@link Scene} to trace.
	 */
	protected RayTracer(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Trace the given ray to calculate the {@link Colour} that ray results in.
	 *
	 * @param ray The ray to trace in the scene.
	 * @return The {@link Colour} resulting from the trace of the ray.
	 */
	public abstract Colour trace(Ray ray);

	/**
	 * Trace the given rays to calculate the average {@link Colour} the rays result in.
	 *
	 * @param rays The rays to trace in the scene.
	 * @return The {@link Colour} resulting from the trace of the ray.
	 */
	public Colour trace(Ray... rays) {
		Colour[] colours = new Colour[rays.length];
		for (int i = 0; i < rays.length; ++i) {
			colours[i] = trace(rays[i]);
		}
		return Colour.average(colours);
	}
}
