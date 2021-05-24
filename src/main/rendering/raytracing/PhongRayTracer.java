package rendering.raytracing;

import java.util.List;

import geometries.Intersection;
import lighting.LightSource;
import math.compare.DoubleCompare;
import primitives.Colour;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Ray;
import primitives.Vector;
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
        NormalizedVector v = scene.camera().position.vectorTo(intersection.point).normalized();
        NormalizedVector n = intersection.normal();
        double nv = n.dot(v);
        if (DoubleCompare.eq(nv, 0)) {
            return Colour.BLACK;
        }
        Material material = intersection.geometry.material;
        Colour colour = material.colour.scale(1d / 255);
        Colour diffuse = colour.scale(material.diffuse);
        Colour ambient = colour.scale(material.ambient);
        double specular = material.specular;
        double shine = material.shine;
        Colour result = material.emission.add(ambient);
        for (LightSource light : scene.lights) {
            NormalizedVector l = light.vectorTo(intersection.point);
            double nl = n.dot(l);
            if (DoubleCompare.gt(nl * nv, 0)) {
                NormalizedVector reflected = l.add(n.scale(-2 * n.dot(l))).normalized();
                double reflectedDotV = reflected.dot(v.reversed());
                Colour intensity = light.colourAt(intersection.point);
                result = result.add(diffuse(intensity, diffuse, nl)).add(specular(intensity, specular, reflectedDotV, shine));
            }
        }
        return result;
    }

    private Colour diffuse(Colour intensity, Colour diffuse, double normalDotLight) {
        intensity = intensity.scale(normalDotLight);
        return new Colour(intensity.red() * diffuse.red(), intensity.green() * diffuse.green(),
                intensity.blue() * diffuse.blue());
    }

    private Colour specular(Colour intensity, double specular, double reflectedDotV, double shine) {
        return intensity.scale(specular * Math.pow(reflectedDotV, shine));
    }

}
