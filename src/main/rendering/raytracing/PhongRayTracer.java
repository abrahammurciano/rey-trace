package rendering.raytracing;

import java.util.List;

import geometries.Intersection;
import lighting.LightSource;
import primitives.Colour;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Ray;
import scene.Scene;

/**
 * This RayTracer only takes into account the ambient light of the scene with no
 * regard to other light sources or the colours of each geometry.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class PhongRayTracer extends RayTracer {

	/**
	 * Construct a new BasicRayTracer for the given scene.
	 *
	 * @param scene The scene to trace.
	 */
	public PhongRayTracer(Scene scene) {
		super(scene);
	}

	@Override
	public Colour trace(Ray ray) {
		List<Intersection> intersections = scene.geometries.intersect(ray);
		if (intersections.isEmpty()) {
			return scene.background;
		} else {
			return colour(ray.closest(intersections));
		}
	}

	private Colour colour(Intersection intersection) {
		Material material = intersection.geometry.material;
		Colour result = material.emission.add(scene.ambient.colour.scale(material.ambient));
		for (LightSource light : scene.lights) {
			result = result.add(localEffects(light, intersection));
		}
		return result;
	}

	private Colour localEffects(LightSource light, Intersection intersection) {
		NormalizedVector fromCamera = scene.camera().position.vectorTo(intersection.point).normalized();
		NormalizedVector fromLight = light.vectorTo(intersection.point);
		NormalizedVector normal = intersection.normal();
		double normalDotCamera = normal.dot(fromCamera);
		double normalDotLight = normal.dot(fromLight);
		if (normalDotCamera * normalDotLight < 0) {
			return Colour.BLACK; // camera and light are on the different sides
		}
		NormalizedVector reflected = fromLight.add(normal.scale(-2 * normal.dot(fromLight))).normalized();
		double reflectedDotV = reflected.dot(fromCamera.reversed());
		Colour colour = light.colourAt(intersection.point);
		Material material = intersection.geometry.material;
		return diffuse(colour, material, normalDotLight).add(specular(colour, material, reflectedDotV));
	}

	private Colour diffuse(Colour colour, Material material, double normalDotLight) {
		return colour.scale(material.diffuse).scale(normalDotLight);
	}

	private Colour specular(Colour colour, Material material, double reflectedDotV) {
		return colour.scale(material.specular).scale(Math.pow(reflectedDotV, material.shine));
	}

}
