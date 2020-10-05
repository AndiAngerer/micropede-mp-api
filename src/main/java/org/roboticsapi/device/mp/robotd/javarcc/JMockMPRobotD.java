package org.roboticsapi.device.mp.robotd.javarcc;

import org.roboticsapi.facet.javarcc.devices.JDevice;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIFrame;
import org.roboticsapi.framework.multijoint.javarcc.devices.JMockMultijointDevice;
import org.roboticsapi.framework.multijoint.javarcc.interfaces.JMultijointInterface;
import org.roboticsapi.framework.robot.javarcc.interfaces.JArmKinematicsInterface;

public class JMockMPRobotD extends JMockMultijointDevice implements JDevice, JArmKinematicsInterface, JMultijointInterface {
	private Kin6Axes kin;

	public JMockMPRobotD(double[] d, double[] theta, double[] a, double[] alpha, double[] min, double[] max, double[] maxVel,
			double[] maxAcc, double[] homeJointPositions) {
		super(6, min, max, homeJointPositions, maxVel, maxAcc);
		kin = new Kin6Axes(d, theta, a, alpha, min, max);
	}

	@Override
	public RPIFrame kin(double[] joints, RPIFrame ret) {
		return kin.kin(joints, ret);
	}

	@Override
	public double[] invKin(double[] hintJoints, RPIFrame frame) {
		return kin.invKin(frame, hintJoints);
	}

}
