package xml.factories.element;

import primitives.NormalizedVector;
import java.util.function.Function;
import org.w3c.dom.Element;
import primitives.Point;
import scene.camera.Camera;
import scene.camera.CameraSettings;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlFactoryFromAttribute;
import xml.factories.attribute.XmlIntegerFactory;
import xml.factories.attribute.XmlResolutionFactory;
import xml.factories.attribute.XmlTripleFactory;

/**
 * Creates a {@link Camera} from the given XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlCameraFactory extends XmlFactoryFromElement<Camera> {

	private static final XmlTripleFactory<Point> POINT_FACTORY = new XmlTripleFactory<>(Point::new);
	private static final XmlTripleFactory<NormalizedVector> VECTOR_FACTORY =
		new XmlTripleFactory<>(NormalizedVector::new);
	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();
	private static final XmlIntegerFactory INT_FACTORY = new XmlIntegerFactory();
	private static final XmlResolutionFactory RESOLUTION_FACTORY = new XmlResolutionFactory();

	@Override
	protected Camera createHelper(Element element) {
		CameraSettings settings = new CameraSettings();

		loadSetting(element, "position", POINT_FACTORY, settings::position);
		loadSetting(element, "front", VECTOR_FACTORY, settings::front);
		loadSetting(element, "up", VECTOR_FACTORY, settings::up);
		loadSetting(element, "width", DOUBLE_FACTORY, settings::width);
		loadSetting(element, "height", DOUBLE_FACTORY, settings::height);
		loadSetting(element, "distance", DOUBLE_FACTORY, settings::distance);
		loadSetting(element, "resolution", RESOLUTION_FACTORY, settings::resolution);
		loadSetting(element, "sensor-size", DOUBLE_FACTORY, settings::sensorSize);
		loadSetting(element, "sensor-pixels", INT_FACTORY, settings::sensorPixels);
		loadSetting(element, "antialiasing", INT_FACTORY, settings::antialiasing);

		Camera camera = new Camera(settings);

		double pitch = DOUBLE_FACTORY.create(element, "pitch", 0d);
		double yaw = DOUBLE_FACTORY.create(element, "yaw", 0d);
		double roll = DOUBLE_FACTORY.create(element, "roll", 0d);

		return camera.rotate(toRadians(pitch), toRadians(yaw), toRadians(roll));
	}

	private <T> void loadSetting(Element element, String attrName, XmlFactoryFromAttribute<T> factory,
		Function<T, CameraSettings> setter) {
		String attribute = element.getAttribute(attrName);
		if (!attribute.isEmpty()) {
			T obj = factory.create(element, attrName);
			setter.apply(obj);
		}
	}

	private static double toRadians(double degrees) {
		return degrees * Math.PI / 180;
	}

}
