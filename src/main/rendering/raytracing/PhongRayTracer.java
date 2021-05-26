package rendering.raytracing;

import java.util.List;

import geometries.Intersection;
import lighting.LightSource;
import primitives.Colour;
import primitives.Factors;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Ray;
import primitives.LineSegment;
import scene.Scene;

/**
 * This RayTracer only takes into account the ambient light of the scene with no
 * regard to other light sources or the colours of each geometry.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class PhongRayTracer extends RayTracer {

	private int maxRecursionLevel;
	private double minEffectCoefficient;

	/**
	 * Construct a new BasicRayTracer for the given scene.
	 *
	 * @param scene The scene to trace.
	 */
	public PhongRayTracer(Scene scene, int maxRecursionLevel, double minEffectCoefficient) {
		super(scene);
		this.maxRecursionLevel = maxRecursionLevel;
		this.minEffectCoefficient = minEffectCoefficient;
	}

	@Override
	public Colour trace(Ray ray) {
		List<Intersection> intersections = scene.geometries.intersect(ray);
		if (intersections.isEmpty()) {
			return scene.background;
		} else {
			return colour(ray.closest(intersections), ray);
		}
	}

	private Colour colour(Intersection intersection, Ray fromCamera) {
		return colour(intersection, fromCamera, maxRecursionLevel, 1)
			.add(scene.ambient.colour.scale(intersection.geometry.material.ambient));
	}

	private Colour colour(Intersection intersection, Ray fromCamera, int level, double effectCoefficient) {
		Colour result = intersection.geometry.material.emission;
		result = result.add(localEffects(intersection, fromCamera.direction));
		if (level < maxRecursionLevel && effectCoefficient > minEffectCoefficient) {
			result = result.add(globalEffects(intersection, fromCamera, level, effectCoefficient));
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
			NormalizedVector reflected = fromLight.add(normal.scale(-2 * normal.dot(fromLight))).normalized();
			double reflectedDotV = reflected.dot(fromCamera.reversed());
			Colour colour = light.colourAt(intersection.point).scale(transparency);
			Material material = intersection.geometry.material;
			result.add(diffuse(colour, material, normalDotLight)).add(specular(colour, material, reflectedDotV));
		}
		return result;
	}

	private Colour globalEffects(Intersection intersection, Ray fromCamera, int level, Factors effectCoefficient) {
		if (level == 0 || effectCoefficient.lt(minEffectCoefficient)) {
			return Colour.BLACK;
		}
		intersection.geometry.material.reflectivity.scale()
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
		Factors transparency = Factors.MAX;
		List<Intersection> blockers = scene.geometries.intersect(shadow);
		for (Intersection blocker : blockers) {
			transparency = transparency.scale(blocker.geometry.material.transparency);
			if (transparency.lt(minEffectCoefficient)) {
				return Factors.MIN;
			}
		}
		return transparency;
	}

}
