package org.roboticsapi.device.mp.robotd.javarcc;

import org.roboticsapi.facet.javarcc.devices.AbstractJDevice;
import org.roboticsapi.facet.javarcc.devices.JDevice;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIFrame;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIVector;
import org.roboticsapi.framework.multijoint.javarcc.devices.CyclicPositionMultijointDevice;
import org.roboticsapi.framework.multijoint.javarcc.interfaces.JMultijointInterface;
import org.roboticsapi.framework.robot.javarcc.interfaces.JArmKinematicsInterface;

public class JSerialMPRobotD extends AbstractJDevice implements JDevice, JArmKinematicsInterface, JMultijointInterface {
	private Kin6Axes kin;
	private CyclicPositionMultijointDevice cpra;
	private int jointCount;
	private double[] maxAngle;
	private double[] minAngle;
	private SerialCommunication serial;

	public JSerialMPRobotD(String serialPortName, double[] d, double[] theta, double[] a, double[] alpha, double[] min, double[] max, double[] maxVel,
			double[] maxAcc, double[] homeJointPositions) {
		jointCount = 6;
		this.minAngle = min;
		this.maxAngle = max;
		cpra = new CyclicPositionMultijointDevice(jointCount, 0);
		cpra.setMaximumVelocity(maxVel);
		cpra.setMaximumAcceleration(maxAcc);
		for (int i = 0; i < jointCount; i++) {
			cpra.setJointPositionStatic(i, homeJointPositions[i]);
		}
		kin = new Kin6Axes(d, theta, a, alpha, min, max);
		
		serial = SerialCommunication.start(serialPortName, cpra);
	}

	@Override
	public RPIFrame kin(double[] joints, RPIFrame ret) {
		return kin.kin(joints, ret);
	}

	@Override
	public double[] invKin(double[] hintJoints, RPIFrame frame) {
		return kin.invKin(frame, hintJoints);
	}

	@Override
	public int getJointCount() {
		return jointCount;
	}

	@Override
	public int getJointError(int axis) {
		return 0;
	}

	@Override
	public double getMeasuredJointAcceleration(int axis) {
		return cpra.getCommandedJointAcceleration(axis);
	}

	@Override
	public double getMeasuredJointVelocity(int axis) {
		return cpra.getCommandedJointVelocity(axis);
	}

	@Override
	public double getMeasuredJointPosition(int axis) {
		return cpra.getCommandedJointPosition(axis);
	}

	@Override
	public double getCommandedJointAcceleration(int axis) {
		return cpra.getCommandedJointAcceleration(axis);
	}

	@Override
	public double getCommandedJointVelocity(int axis) {
		return cpra.getCommandedJointVelocity(axis);
	}

	@Override
	public double getCommandedJointPosition(int axis) {
		return cpra.getCommandedJointPosition(axis);
	}

	@Override
	public int checkJointPosition(int axis, double pos) {
		if (pos != pos) // NaN
			return 1;

		if (axis < 0 || axis >= getJointCount())
			return 1;

		if (pos > maxAngle[axis] || pos < minAngle[axis])
			return 2;

		return 0;
	}

	@Override
	public void setJointPosition(int axis, double pos, Long time) {
		cpra.setJointPosition(axis, pos, time);
	}

	@Override
	public void setToolCOM(RPIVector com, int axis) {
	}

	@Override
	public void setToolMOI(RPIVector moi, int axis) {
	}

	@Override
	public void setToolMass(double massVal, int axis) {
	}

	@Override
	public int getToolError(int axis) {
		return 0;
	}

	@Override
	public boolean getToolFinished(int axis) {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		serial.stop();
		super.destroy();
	}
}
