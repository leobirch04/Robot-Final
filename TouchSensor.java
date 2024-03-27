package package13;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class TouchSensor implements Behavior {
	
	private EV3TouchSensor touchLeft;
	private EV3TouchSensor touchRight;
	private SampleProvider touchedLeft;
	private SampleProvider touchedRight;
	private Behavior movement;
	private BaseRegulatedMotor mz;
	private boolean suppressed = false;
	private Share shared;
	
	public TouchSensor (Share shared, Behavior movement, BaseRegulatedMotor mz, EV3TouchSensor touchLeft,
	EV3TouchSensor touchRight) {
		this.touchLeft = touchLeft;
		this.touchRight = touchRight;
		this.touchedLeft = touchLeft.getTouchMode();
		this.touchedRight = touchRight.getTouchMode();
		this.movement = movement;
		this.mz =  mz;
		this.shared = shared;
	}
	
	@Override
	public boolean takeControl() {
		float[] check1 = new float[1];
		float[] check2 = new float[1];
		touchedLeft.fetchSample(check1, 0);
		touchedRight.fetchSample(check2, 0);
		return (check1[0] == 1 || check2[0] == 1) && shared.getSelect()== 6 ;
			
	}

	@Override
	public void action() {
		suppressed = false;
		
		mz.rotate(300);
		movement.suppress();
		suppressed = true;
		
		LCD.drawString("Touch Sensor", 2, 2);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;
	}

}
