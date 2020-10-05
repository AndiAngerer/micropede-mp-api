package org.roboticsapi.device.mp.robotd;

import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.world.Pose;
import org.roboticsapi.framework.cartesianmotion.parameter.CartesianParameters;
import org.roboticsapi.framework.multijoint.JointDriver;
import org.roboticsapi.framework.multijoint.RevoluteJoint;
import org.roboticsapi.framework.robot.parameter.RobotTool;
import org.roboticsapi.framework.robot.parameter.RobotToolParameter;

public final class MPRobotD extends AbstractDHRobotArm {

	//  angleDegMin, angleDegMax, home position, maxAngularVel degree/sec
	private double[][] servoConfig = new double[][] {
		new double[] { -110.57, 60.57, 0, 150 },
		new double[] { -60.57, 79.22, 0, 150 },
		new double[] { -17.57, 63.41, 0, 150 },
		new double[] { -65.57, 170.31, 0, 150 },
		new double[] { -120.03, 50.02, 0, 150 },
		new double[] { -87.15, 90.15, 0, 150 }
	};
	
	private double[][] dh = new double[][] {
		new double[] {0.0765,          0,   0.042, -Math.PI/2},
		new double[] {     0, -Math.PI/2,   0.095,          0},
		new double[] {     0,          0,  0.0185,  Math.PI/2},
		new double[] {-0.157,          0,       0, -Math.PI/2},
		new double[] {     0,          0,       0, -Math.PI/2},
		new double[] {  0.02,          0,       0,          0}};
		
	private double[] d, theta, a, alpha;
	
	public MPRobotD() {
		super(6);
		d = new double[6];
		theta = new double[6];
		a = new double[6];
		alpha = new double[6];
		for (int i=0; i<6; i++) {
			d[i] = dh[i][0];
			theta[i] = dh[i][1];
			a[i] = dh[i][2];
			alpha[i] = dh[i][3];
		}
	}
	
	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		super.defineMaximumParameters();

		addMaximumParameters(new CartesianParameters(0.01, 0.3, 1, Math.toRadians(90), Math.toRadians(90) * 2,
				Math.toRadians(90) * 20, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		addMaximumParameters(new RobotToolParameter(new RobotTool(0.1, new Pose(getFlange()))));
	}

	@Override
	protected RevoluteJoint<JointDriver> createJoint(int number, String name) {
		return createRevoluteJoint(name,
				Math.toRadians(servoConfig[number][0]),
				Math.toRadians(servoConfig[number][1]),
				Math.toRadians(servoConfig[number][3]),
				Math.toRadians(servoConfig[number][3])*10,
				Math.toRadians(servoConfig[number][3])*100,
				Math.toRadians(servoConfig[number][2]));
	}

	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
	}
	
	@Override
	public double[] getD() {
		return d;
	}
	
	@Override
	public double[] getTheta() {
		return theta;
	}
			
	@Override
	public double[] getA() {
		return a;
	}
	
	@Override
	public double[] getAlpha() {
		return alpha;
	}
	
}
