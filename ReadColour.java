package package13;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class ReadColour implements Behavior {
	
	private BaseRegulatedMotor mb;
	private SampleProvider rgbMode;
	private EV3ColorSensor cs ;
	private float[][] rgbValues = new float[4][3];  
	private CreateLine line;
	private Share shared;
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return shared.getSelect() == 6 && shared.getXlist().size()== 0;
	}
	
	public ReadColour(Share shared, BaseRegulatedMotor mb, EV3ColorSensor cs) {
		this.cs =cs;
		this.mb = mb;
		this.rgbMode = cs.getRGBMode();
		this .shared = shared;
	}

	@Override
	public void action() {
		
		mb.setSpeed(75);
		
		for (int i = 0; i < 3; i++) {
			
			
			
			
	          // Fetch RGB sample from the sensor
	          float[] sample = new float[rgbMode.sampleSize()];
	          rgbMode.fetchSample(sample, 0);
	          
	          // Store RGB values in the array
	          rgbValues[i][0] = sample[0]; // Red
	          rgbValues[i][1] = sample[1]; // Green
	          rgbValues[i][2] = sample[2]; // Blue            
	          
	          LCD.drawString("R" + (i+1) + ": " + rgbValues[i][0],2,2);
	          LCD.drawString("G" + (i+1) + ": " + rgbValues[i][1],2,3);
	          LCD.drawString("B" + (i+1) + ": " + rgbValues[i][2],2,4);
	      	  LCD.refresh();
	      	
	          // Move motor D 360 degrees
	          mb.rotate(360);
	          Delay.msDelay(2000); // Adjust delay as needed
	          
		  	}this.line = new CreateLine(shared, rgbValues[0][0],  rgbValues[0][1],  rgbValues[0][2], rgbValues[1][0],  rgbValues[1][1],  rgbValues[1][2], rgbValues[2][0],  rgbValues[2][1],  rgbValues[2][2]);
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	
	public Object getCreateLine(){
		return this.line;
	}

	

}
