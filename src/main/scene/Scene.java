package scene;

import java.util.ArrayList;
import java.util.List;
import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Colour;
import scene.camera.Camera;

/**
 * This class groups together the elements of a scene. That is, the light sources, the geometries, and some additional
 * details.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Scene {
	/** The background colour of the image. This will be applied when a ray doesn't intersect any geometry. */
	public final Colour background;
	/** The ambient lighting of the scene. */
	public final AmbientLight ambient;
	/** The collection of geometries in the scene. */
	public final Geometries geometries;
	/** The light source of the scene. */
	public final List<LightSource> lights;

	private Camera camera;

	/**
	 * Constructor for the scene with no geometries.
	 *
	 * @param background The background colour of the scene.
	 * @param ambient    The ambient lighting of the scene.
	 */
	public Scene(Colour background, AmbientLight ambient) {
		this(background, ambient, new Geometries(), new ArrayList<>(), new Camera());
	}

	/**
	 * Constructor for the scene.
	 *
	 * @param background The background colour of the scene.
	 * @param ambient    The ambient colour of the scene.
	 * @param geometries The geometries to initialise the scene with.
	 * @param lights     The list of light sources
	 * @param camera     The camera to use to render the scene.
	 */
	public Scene(Colour background, AmbientLight ambient, Geometries geometries, List<LightSource> lights,
		Camera camera) {
		this.background = background;
		this.ambient = ambient;
		this.geometries = geometries;
		this.lights = lights;
		this.camera = camera;
	}

	/**
	 * Get the camera to be used to render the scene.
	 *
	 * @return The camera to be used to render the scene.
	 */
	public Camera camera() {
		return camera;
	}

	/**
	 * Replace the scenes camera with a new one.
	 *
	 * @param camera The new camera.
	 */
	public void replaceCamera(Camera camera) {
		this.camera = camera;
	}
}
