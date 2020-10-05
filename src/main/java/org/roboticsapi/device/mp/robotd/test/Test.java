package org.roboticsapi.device.mp.robotd.test;

import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.core.world.relation.ConfiguredStaticConnection;
import org.roboticsapi.device.mp.robotd.MPRobotD;
import org.roboticsapi.device.mp.robotd.runtime.MPRobotDGenericDriver;
import org.roboticsapi.device.mp.robotd.runtime.MPRobotDMockDriver;
import org.roboticsapi.device.mp.robotd.runtime.MPRobotDSerialDriver;
import org.roboticsapi.facet.runtime.rcc.RccRuntime;
import org.roboticsapi.feature.runtime.javarcc.JavaRcc;
import org.roboticsapi.feature.startup.launcher.DefaultRapi;
import org.roboticsapi.feature.startup.launcher.Rapi;
import org.roboticsapi.framework.robot.activity.MotionInterface;

public class Test {

	private static String serialInterfaceName = "COM3";
	private static boolean simMode = false; // false: Serial, true: Mock
	
	public static void main(String[] args) throws Exception {
		
		try (Rapi rapi = DefaultRapi.createNewEmpty("Rapi")) {
			
			// rcc
			JavaRcc rcc = new JavaRcc();
			RccRuntime runtime = new RccRuntime();
			runtime.setRcc(rcc);
			rapi.add(rcc, runtime);
			
			// robot
			MPRobotD robot = new MPRobotD();
			robot.setName("Robot123");
			rapi.add(robot);
			rapi.add(new ConfiguredStaticConnection(rapi.getWorldOrigin(), robot.getBase(), Transformation.IDENTITY));
			
			// driver
			MPRobotDGenericDriver driver;
			if (simMode) {
				driver = new MPRobotDMockDriver();
			} else {
				MPRobotDSerialDriver _driver = new MPRobotDSerialDriver();
				_driver.setSerialInterfaceName(serialInterfaceName);
				driver = _driver;
			}
			driver.setName("MPRobotD driver");
			driver.setDevice(robot);
			driver.setRpiDeviceName("mprobotdriver");
			driver.setRuntime(runtime);
			rapi.add(driver);
			
			// Have fun!
			runtime.setOverride(1);
			
			robot.use(MotionInterface.class).ptpHome().execute();
			while(true) {
				robot.use(MotionInterface.class).ptp(new double[] {0.5,-0.2,0.3,-0.4,0.1,0}).execute();
//				robot.use(MotionInterface.class).lin(robot.getFlange().asPose().snapshot(robot.getBase()).plus(new Transformation(0.02, 0.02, 0, 0, 0, 0))).execute();
				robot.use(MotionInterface.class).ptpHome().execute();
			}

		}
	}
	
}
