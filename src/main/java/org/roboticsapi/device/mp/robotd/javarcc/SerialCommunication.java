package org.roboticsapi.device.mp.robotd.javarcc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.roboticsapi.framework.multijoint.javarcc.devices.CyclicPositionMultijointDevice;

import com.fazecast.jSerialComm.SerialPort;

public class SerialCommunication {

	private Object monitor = new Object();
	private boolean alive = true;
	
	private SerialCommunication(String serialPortName, CyclicPositionMultijointDevice cpra) {
		Thread t = new Thread(() -> run(serialPortName, cpra));
		t.setDaemon(true);
		t.start();
	}
	
	public static SerialCommunication start(String serialPortName, CyclicPositionMultijointDevice cpra) {
		return new SerialCommunication(serialPortName, cpra);
	}
	
	public void stop() {
		synchronized (monitor) {
			if (!alive) return;
			
			alive = false;
			monitor.notify();
			try {
				monitor.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void run(String portName, CyclicPositionMultijointDevice cpra) {
		SerialPort comPort = SerialPort.getCommPort(portName);
		comPort.setBaudRate(9600);
		comPort.setParity(SerialPort.NO_PARITY);
		comPort.setNumDataBits(8);
		comPort.setNumStopBits(1);
		
		try {
			comPort.openPort();
			comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
			BufferedReader in = new BufferedReader(new InputStreamReader(comPort.getInputStream()));
			OutputStreamWriter out = new OutputStreamWriter(comPort.getOutputStream());
			
			while(alive) {
				try {
					Thread.sleep(10);
					loop(cpra, in, out);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} finally {
			comPort.closePort();
			synchronized (monitor) {
				alive = false;
				monitor.notify();
			}
		}
	}
	
	private void loop(CyclicPositionMultijointDevice cpra, BufferedReader serialIn, Writer serialOut) throws IOException {
		String data = ":S E M03 R0 " + angle(0, cpra) + " R1 " + angle(1, cpra) + " R2 " + angle(2, cpra) + " R3 " + angle(3, cpra) + " R4 " + angle(4, cpra) + " R5 " + angle(5, cpra) + " \r";
		serialOut.write(data);
		serialOut.flush();
		serialIn.readLine();
	}
	
	private int angle(int index, CyclicPositionMultijointDevice cpra) {
		return (int)Math.toDegrees(cpra.getCommandedJointPosition(index));
	}
	
}
