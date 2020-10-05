package org.roboticsapi.device.mp.robotd.runtime;

import org.roboticsapi.facet.runtime.rpi.extension.RpiExtension;
import org.roboticsapi.facet.runtime.rpi.mapping.MapperRegistry;

public class MPRobotDRuntimeExtension extends RpiExtension {

	public MPRobotDRuntimeExtension() {
		super(MPRobotDMockDriver.class, MPRobotDSerialDriver.class, MPRobotDGenericDriver.class);
	}

	@Override
	protected void registerMappers(MapperRegistry mr) {
		// do nothing...
	}

	@Override
	protected void unregisterMappers(MapperRegistry mr) {
		// do nothing...
	}

}
