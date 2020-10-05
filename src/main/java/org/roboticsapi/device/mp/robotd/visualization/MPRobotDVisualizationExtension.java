package org.roboticsapi.device.mp.robotd.visualization;

import java.net.URL;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.device.mp.robotd.MPRobotD;
import org.roboticsapi.extension.RoboticsObjectListener;
import org.roboticsapi.framework.multijoint.link.Link;

public class MPRobotDVisualizationExtension implements RoboticsObjectListener {

//	private static final Visualisation[] RobotD_MODELS = new Visualisation[] {
//			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0),
//					getResource("RobotD_Link0.dae")),
//			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0),
//					getResource("RobotD_Link1.dae")),
//			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0),
//					getResource("RobotD_Link2.dae")),
//			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0),
//					getResource("RobotD_Link3.dae")),
//			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0),
//					getResource("RobotD_Link4.dae")),
//			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0),
//					getResource("RobotD_Link5.dae")),
//			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0),
//					getResource("RobotD_Link6.dae")) };

	@Override
	public void onAvailable(RoboticsObject object) {
		if (object instanceof MPRobotD) {
			MPRobotD robot = (MPRobotD) object;

			for (int i = 0; i < 7; i++) {
				Link link = robot.getLink(i);
//				link.addProperty(new VisualizationProperty(RobotD_MODELS[i]));
			}
		}
	}

	@Override
	public void onUnavailable(RoboticsObject object) {
		// do nothing...
	}

	private static final URL getResource(String resource) {
		return MPRobotDVisualizationExtension.class.getResource(resource);
	}

}
