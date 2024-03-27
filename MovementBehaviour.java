package package13;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class MovementBehavior implements Behavior {
	
    private boolean suppressed = false;
    
    final static float XLINEAR_SPEED = (float) 234 ;
	final static float YLINEAR_SPEED = (float) 811 ;
	private int i;
	private double xcurrent = 0;
	private double ycurrent = 0;
	private double xnext ;
	private double ynext ;
	private List<Double> xlist = new ArrayList<Double>();
	private List<Double> ylist = new ArrayList<Double>();
	private BaseRegulatedMotor my;
	private BaseRegulatedMotor mx;
	private Share shared;
	
	public MovementBehavior(Share shared, ArrayList<Double> xlist, ArrayList<Double> ylist, BaseRegulatedMotor mx, BaseRegulatedMotor my) {
		this.xlist = xlist;
		this.ylist = ylist;
		this.mx = mx;
		this.my = my; 
		i = 0;
		xcurrent = 0;
		ycurrent = 0;
		this.shared = shared;
	}
	
	
	@Override
	public boolean takeControl() {
		return shared.getSelect() == 6 && (shared.getXlist().size() > 0);
	}

	@Override
	public void action() {
		LCD.clear();
		if(i < shared.getXlist().size()) { //shared.getXlist().size()
			
			xlist = shared.getXlist();
			ylist = shared.getYlist();
			xnext = xlist.get(i);
			ynext = ylist.get(i);
			float xdifference = (float) (xnext - xcurrent);
			float ydifference = (float) (ynext - ycurrent);
			mx.setSpeed(xdifference * XLINEAR_SPEED);
			my.setSpeed(ydifference * YLINEAR_SPEED);
			mx.startSynchronization();
			if (xdifference > 0 ) {mx.forward();}
			else {mx.backward();}
			if (ydifference > 0 ) {my.forward();}
			else {my.backward();}
			mx.endSynchronization();
			Delay.msDelay((long)500);
			mx.startSynchronization();
			mx.stop();
			my.stop();
			mx.endSynchronization();
			xcurrent = xnext;
			ycurrent = ynext;
			i++;
		}

	}

	@Override
	public void suppress() {
		suppressed = true;

	}
}
