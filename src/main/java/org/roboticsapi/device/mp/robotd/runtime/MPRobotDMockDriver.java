package org.roboticsapi.device.mp.robotd.runtime;

public final class MPRobotDMockDriver extends MPRobotDGenericDriver {

	private final static String DEVICE_TYPE = "mp_robotd_arm_sim";

	@Override
	public String getRpiDeviceType() {
		return DEVICE_TYPE;
	}

}
