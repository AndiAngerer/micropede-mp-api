package org.roboticsapi.device.mp.robotd.runtime;

import org.roboticsapi.configuration.ConfigurationProperty;
import org.roboticsapi.core.Dependency;
import org.roboticsapi.facet.runtime.rpi.RpiParameters;

public final class MPRobotDSerialDriver extends MPRobotDGenericDriver {

	private final static String DEVICE_TYPE = "mp_robotd_arm_serial";

	private final Dependency<String> serialInterfaceName;

	public MPRobotDSerialDriver() {
		serialInterfaceName = createDependency("serialInterfaceName");
	}
	
	@ConfigurationProperty(Optional = false)
	public final void setSerialInterfaceName(String serialInterfaceName) {
		this.serialInterfaceName.set(serialInterfaceName);
	}

	@Override
	public String getRpiDeviceType() {
		return DEVICE_TYPE;
	}

	@Override
	protected RpiParameters getRpiDeviceParameters() {
		return super.getRpiDeviceParameters().with("serialInterfaceName", serialInterfaceName.get());
	}

}
