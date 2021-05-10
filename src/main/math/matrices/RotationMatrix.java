package math.matrices;

import primitives.VectorBase;

/**
 * Represents a rotation matrix.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class RotationMatrix extends Matrix {
	/**
	 * Constructs a rotation matrix from the pitch, yaw, and roll.
	 *
	 * @param pitch The angle in radians to rotate about the left-right axis.
	 * @param yaw   The angle in radians to rotate about the up-down axis.
	 * @param roll  The angle in radians to rotate about the front-back axis.
	 */
	public RotationMatrix(double pitch, double yaw, double roll) {
		this(Math.sin(pitch), Math.cos(pitch), Math.sin(yaw), Math.cos(yaw), Math.sin(roll), Math.cos(roll));
	}

	/**
	 * Helper for the public constructor.
	 *
	 * @param sinPitch sin(pitch)
	 * @param cosPitch cos(pitch)
	 * @param sinYaw   sin(yaw)
	 * @param cosYaw   cos(yaw)
	 * @param sinRoll  sin(roll)
	 * @param cosRoll  cos(roll)
	 */
	private RotationMatrix(double sinPitch, double cosPitch, double sinYaw, double cosYaw, double sinRoll,
		double cosRoll) {
		super(
			new VectorBase(cosPitch * cosYaw, sinPitch * cosYaw * sinRoll - sinYaw * cosRoll,
				sinPitch * cosYaw * cosRoll + sinYaw * sinRoll),
			new VectorBase(cosPitch * sinYaw, sinYaw * sinPitch * sinRoll + cosYaw * cosRoll,
				sinYaw * sinPitch * cosRoll - cosYaw * sinRoll),
			new VectorBase(-sinPitch, cosPitch * sinRoll, cosPitch * cosRoll));
	}
}
