package rendering.raytracing;

import java.util.List;
import java.util.function.Supplier;
import geometries.Intersection;
import lighting.LightSource;
import primitives.Colour;
import primitives.Factors;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Ray;
import primitives.Vector;
import primitives.ZeroVectorException;
import primitives.LineSegment;
import scene.Scene;

/**
 * This RayTracer implements the Phong model.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class PhongRayTracer extends RayTracer {

	private int maxRecursionLevel;
	private double minEffectCoefficient;

	/**
	 * Construct a new PhongRayTracer for the given scene.
	 *
	 * @param scene                The scene to trace.
	 * @param maxRecursionLevel    The maximum number of times to trace reflections and refractions.
	 * @param minEffectCoefficient The minimum coefficient to consider calculating.
	 */
	public PhongRayTracer(Scene scene, int maxRecursionLevel, double minEffectCoefficient) {
		super(scene);
		this.maxRecursionLevel = maxRecursionLevel;
		this.minEffectCoefficient = minEffectCoefficient;
	}

	@Override
	public Colour trace(Ray ray) {
		return trace(ray, maxRecursionLevel, Factors.ONE);
	}

	private Colour trace(Ray ray, int level, Factors effectCoefficient) {
		List<Intersection> intersections = scene.geometries.intersect(ray);
		if (intersections.isEmpty()) {
			return scene.background;
		} else {
			return colour(ray.closest(intersections), ray, level, effectCoefficient);
		}
	}

	private Colour colour(Intersection intersection, Ray fromCamera, int level, Factors effectCoefficient) {
		Colour result = intersection.geometry.material.emission
			.add(scene.ambient.colour.scale(intersection.geometry.material.ambient))
			.add(localEffects(intersection, fromCamera.direction));
		if (level > 0) {
			result = globalEffects(result, intersection, fromCamera, level, effectCoefficient);
		}
		return result;
	}

	private Colour localEffects(Intersection intersection, NormalizedVector fromCamera) {
		Colour result = Colour.BLACK;
		for (LightSource light : scene.lights) {
			NormalizedVector fromLight = light.vectorTo(intersection.point);
			Factors transparency = transparency(
				new LineSegment(intersection.point, fromLight.reversed(), light.squareDistance(intersection.point)));
			if (transparency.lt(minEffectCoefficient)) {
				continue;
			}
			NormalizedVector normal = intersection.normal();
			double normalDotCamera = normal.dot(fromCamera);
			double normalDotLight = normal.dot(fromLight);
			if (normalDotCamera * normalDotLight < 0) {
				continue; // camera and light are on the different sides
			}
			double reflectedDotV = reflectedVector(fromLight, normal).dot(fromCamera.reversed());
			Colour colour = light.colourAt(intersection.point).scale(transparency);
			Material material = intersection.geometry.material;
			result =
				result.add(diffuse(colour, material, normalDotLight)).add(specular(colour, material, reflectedDotV));
		}
		return result;
	}

	private Colour globalEffects(Colour result, Intersection intersection, Ray fromCamera, int level,
		Factors effectCoefficient) {
		Material material = intersection.geometry.material;

		// reflection
		result = globalEffect(result, material.reflectivity, effectCoefficient, intersection,
			() -> reflectedVector(fromCamera.direction, intersection.normal()), level);

		// refraction
		result = globalEffect(result, material.transparency, effectCoefficient, intersection,
			() -> fromCamera.direction, level);

		return result;
	}

	private Colour globalEffect(Colour result, Factors materialFactors, Factors accumulatedFactors,
		Intersection intersection, Supplier<NormalizedVector> directionSupplier, int level) {
		Factors newFactors = materialFactors.scale(accumulatedFactors);
		if (!newFactors.lt(minEffectCoefficient)) {
			result = result.add(trace(new Ray(intersection.point, directionSupplier.get()), level - 1, newFactors));
		}
		return result;
	}

	private NormalizedVector reflectedVector(Vector incident, NormalizedVector normal) {
		try {
			return incident.add(normal.scale(-2 * normal.dot(incident))).normalized();
		} catch (ZeroVectorException __) {
			return incident.normalized();
		}
	}

	private Colour diffuse(Colour colour, Material material, double normalDotLight) {
		return colour.scale(material.diffuse).scale(Math.abs(normalDotLight));
	}

	private Colour specular(Colour colour, Material material, double reflectedDotV) {
		return colour.scale(material.specular).scale(Math.pow(reflectedDotV, material.shine));
	}

	/**
	 * Calculate how much transparency is encountered by the given ray between its source and the light source. For
	 * example, if the ray encounters two geometries each of transparency 0.8 (very transparent) before reaching the
	 * light source, the transparency of the shadow ray is 0.8*0.8=0.64 (less transparent).
	 *
	 * @param shadow The shadow ray; from the intersection to the light source.
	 * @return the transparency the ray encounters between its source and the light source.
	 */
	private Factors transparency(LineSegment shadow) {
		Factors transparency = Factors.ONE;
		List<Intersection> blockers = scene.geometries.intersect(shadow);
		for (Intersection blocker : blockers) {
			transparency = transparency.scale(blocker.geometry.material.transparency);
			if (transparency.lt(minEffectCoefficient)) {
				return Factors.ZERO;
			}
		}
		return transparency;
	}

}
