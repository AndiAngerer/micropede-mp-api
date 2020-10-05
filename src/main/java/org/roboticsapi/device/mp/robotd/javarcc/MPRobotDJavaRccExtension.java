package org.roboticsapi.device.mp.robotd.javarcc;

import org.roboticsapi.facet.javarcc.extension.JavaRccExtension;
import org.roboticsapi.facet.javarcc.extension.JavaRccExtensionPoint;
import org.roboticsapi.facet.runtime.rpi.RpiParameters;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdoubleArray;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIstring;

public class MPRobotDJavaRccExtension extends JavaRccExtension {

	@Override
	public void extend(JavaRccExtensionPoint ep) {
		ep.registerDevice("mp_robotd_arm_sim",
				(p, d) -> new JMockMPRobotD(param(p, "dh_d"), param(p, "dh_t"), param(p, "dh_a"), param(p, "dh_al"),
						param(p, "min_joint"), param(p, "max_joint"), param(p, "max_vel"), param(p, "max_acc"), param(p, "home_joint")));
		ep.registerDevice("mp_robotd_arm_serial",
				(p, d) -> new JSerialMPRobotD(strParam(p, "serialInterfaceName"), param(p, "dh_d"), param(p, "dh_t"), param(p, "dh_a"), param(p, "dh_al"),
						param(p, "min_joint"), param(p, "max_joint"), param(p, "max_vel"), param(p, "max_acc"), param(p, "home_joint")));
	}

	private double[] param(RpiParameters p, String name) {
		RPIdoubleArray val = p.get(RPIdoubleArray.class, name);
		double[] ret = new double[val.getSize()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = val.get(i).get();
		return ret;
	}
	
	private String strParam(RpiParameters p, String name) {
		RPIstring val = p.get(RPIstring.class, name);
		return val.get();
	}
}
