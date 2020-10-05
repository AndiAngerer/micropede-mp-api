package org.roboticsapi.device.mp.robotd;

import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.framework.multijoint.Joint;
import org.roboticsapi.framework.multijoint.link.BaseLink;
import org.roboticsapi.framework.multijoint.link.FlangeLink;
import org.roboticsapi.framework.multijoint.link.JointLink;
import org.roboticsapi.framework.multijoint.link.Link;
import org.roboticsapi.framework.robot.AbstractRobotArm;
import org.roboticsapi.framework.robot.DHRobotArm;
import org.roboticsapi.framework.robot.RobotArmDriver;

public abstract class AbstractDHRobotArm extends AbstractRobotArm<Joint, RobotArmDriver> implements DHRobotArm {

	private final int numberOfAxes;
	
	public AbstractDHRobotArm(int numberOfAxes) {
		super(numberOfAxes);
		this.numberOfAxes = numberOfAxes;
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		super.defineDefaultParameters();
		addMaximumParameters(getJointDeviceParameters(0, 0));
	}

	@Override
	protected final Link createLink(int number) {
		if (number==0) return new BaseLink(getBase(), getJoint(0), new Transformation(0d, 0d, 0d, 0d, 0d, 0d));
		else if (number==numberOfAxes) return new FlangeLink(getJoint(numberOfAxes-1), getFlange(),
				new Transformation(0, 0, getD()[number-1], getTheta()[number-1], 0, 0).multiply(new Transformation(getA()[number-1],0,0,0,0,getAlpha()[number-1])));
		else return new JointLink(getJoint(number-1), getJoint(number),
				new Transformation(0, 0, getD()[number-1], getTheta()[number-1], 0, 0).multiply(new Transformation(getA()[number-1],0,0,0,0,getAlpha()[number-1])));
	}
	
	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
	}
	
}
