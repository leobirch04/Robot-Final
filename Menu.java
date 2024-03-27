package package1;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Menu implements Behavior{
	static int select;
	private boolean suppressed = false;
    private Arbitrator arbitrator;
    private Share shared;
    private BaseRegulatedMotor xAxis;
	private BaseRegulatedMotor yAxis;
	private BaseRegulatedMotor zAxis;
	private BaseRegulatedMotor brickEject;
	
	private EV3TouchSensor sensorx;
	private EV3TouchSensor sensory;
	
	
    public Menu(Share shared, BaseRegulatedMotor xAxis, BaseRegulatedMotor yAxis, BaseRegulatedMotor zAxis, BaseRegulatedMotor brickEject, EV3TouchSensor sensorx, EV3TouchSensor sensory) {
    	this.shared = shared;
    	this.xAxis = xAxis;
    	this.yAxis = yAxis;
    	this.zAxis = zAxis;
    	this.brickEject = brickEject;
    	this.sensorx = sensorx;
    	this.sensory = sensory;
    	
    }
    
    
	@Override
	public boolean takeControl() {
		return shared.getSelect() != 6;
	}
    
    
    public void action() {
        // This method defines the action the behavior performs.
    	
    	
        SensorMode sensorModex = ((EV3TouchSensor) sensorx).getTouchMode();
        SensorMode sensorModey = ((EV3TouchSensor) sensory).getTouchMode();
        
        float[] samplex = new float[sensorModex.sampleSize()]; // Create an array to hold sensor data for x
        float[] sampley = new float[sensorModey.sampleSize()]; // Create an array to hold sensor data for y

		
		
		
		xAxis.setSpeed(150);
		yAxis.setSpeed(520);
		zAxis.setSpeed(200);
		brickEject.setSpeed(200);
		
		boolean calibrating = false; 
		boolean running = true;
//		int select = 0; // Initialise the selections on main menu
		int arrowPosition = 1;
		
		String position = "UP"; // z-axis state
		boolean positionUp = true;
		
		LCD.clear();
		LCD.drawString("Authors",4,0);
		LCD.drawString("Leo",4,1);
		LCD.drawString("Zeinab",4,2);
		LCD.drawString("Jamie",4,3);
		LCD.drawString("Ali",4,4);
		LCD.drawString("2.3.12",4,5);
		
		Button.ENTER.waitForPressAndRelease();
		
		// controls the arrow and selection when ENTER is pressed
		while(running) {
			
			if(Button.DOWN.isDown() & arrowPosition < 7) {
				Delay.msDelay(250);
				arrowPosition = arrowPosition + 1;
			}
			
			if(Button.UP.isDown() & arrowPosition > 1) {
				Delay.msDelay(250);
				arrowPosition = arrowPosition - 1;
			}
			
			if(Button.ENTER.isDown()) {
				shared.setSelect(arrowPosition);
			}
			
			// option 1 move x-axis
			while(shared.getSelect() == 1) {
				LCD.clear();
				LCD.drawString("Move x-axis",4,0);
				LCD.drawString("POSITION: " + xAxis.getPosition(),0,3);
				LCD.refresh();
				if(Button.LEFT.isDown()){
					xAxis.rotate(-90);
				}
				if(Button.RIGHT.isDown()) {
					xAxis.rotate(90);
				}
				if(Button.ESCAPE.isDown()) {
					shared.setSelect(0); // returns to main menu
				}
			}
			// option 2 move y-axis
			while(shared.getSelect() == 2) {
				LCD.clear();
				LCD.drawString("Move y-axis",4,0);
				LCD.drawString("POSITION: " + yAxis.getPosition(),0,3);
				LCD.refresh();
				if(Button.UP.isDown()){
					yAxis.rotate(90);
				}
				if(Button.DOWN.isDown()) {
					yAxis.rotate(-90);
				}
				if(Button.ESCAPE.isDown()) {
					shared.setSelect(0); // returns to main menu
				}
			}
			//option 3 move z-axis
			while(shared.getSelect() == 3) {
				LCD.clear();
				LCD.drawString("Move z-axis",4,0);
				LCD.drawString("POSITION: " + position,0,3);
				LCD.refresh();
				if(Button.UP.isDown() & positionUp == false){
					zAxis.rotate(340); // change value if pen is too high or low
					positionUp = true;
					position = "UP";
				}
				if(Button.DOWN.isDown()) {
					zAxis.rotate(-340);
					positionUp = false;
					position = "DOWN";
				}
				if(Button.ESCAPE.isDown()) {
					shared.setSelect(0); // returns to main menu
				}
			}
			// option 4 move brick eject
			while(shared.getSelect() == 4) {
				LCD.clear();
				LCD.drawString("Eject Brick",4,0);
				LCD.drawString("Press ENTER",0,3);
				LCD.refresh();
				Delay.msDelay(250);
				if(Button.ENTER.isDown()){
					brickEject.rotate(360);
				}
				if(Button.ESCAPE.isDown()) {
					shared.setSelect(0); // returns to main menu
				}
			}
			// option 5 calibration process
			while(shared.getSelect() == 5) {
				LCD.clear();
				LCD.drawString("Calibrate",4,0);
				LCD.drawString("ENTER to begin",0,7);
				LCD.refresh();
				Delay.msDelay(250);
				
				if(Button.ENTER.isDown()) {
					calibrating = true;
				}
				
				boolean xCalibrated = false;
				boolean yCalibrated = false;
				
				while(calibrating) {
					LCD.clear();
					LCD.drawString("CALIBRATING...",3,3);
					LCD.refresh();
					
					xAxis.setSpeed(150);
					yAxis.setSpeed(400);
					
					while(xCalibrated == false) {
						sensorModex.fetchSample(samplex, 0);
						xAxis.backward();
						// sample is set to 1 when switch is pressed
						if(samplex[0] == 1) {
							xCalibrated = true;
						}
					}
					//backs off the x-axis once it reaches endstop
					xAxis.stop();					
					xAxis.rotate(620);
					
					while(yCalibrated == false) {
						sensorModey.fetchSample(sampley, 0);
						yAxis.forward();
						// sample is set to 1 when switch is pressed
						if(sampley[0] == 1) {
							yCalibrated = true;
						}
					}
					//backs off the y-axis once it reaches endstop
					yAxis.stop();
					yAxis.rotate(-2700);
					
					calibrating = false;
				}
				
				if(shared.getSelect() == 6) {
					shared.setSelect(6);
					LCD.clear();
					zAxis.rotate(-340);
					break;
				}
				
				if(shared.getSelect() == 7) {
					running = false;
		            arbitrator.stop(); // Stop the Arbitrator
		            LCD.clear();
		            LCD.drawString("Program Ended", 0, 0);
		            LCD.refresh();
		            Delay.msDelay(2000);
				}
				
				if(Button.ESCAPE.isDown()) {
					shared.setSelect(0); // returns to main menu
				}
			}
			
			// Displayed text on main menu screen
			LCD.clear();
			LCD.drawString("->", 0, arrowPosition);
			LCD.drawString("Main Menu",5,0);
			LCD.drawString("Move x-axis",2,1);
			LCD.drawString("Move y-axis",2,2);
			LCD.drawString("Move z-axis",2,3);
			LCD.drawString("Eject Brick",2,4);
			LCD.drawString("Calibrate",2,5);
			LCD.drawString("Draw",2,6);
			LCD.drawString("End Program",2,7);
			LCD.refresh();
			
		}
    }
    
    @Override
	public void suppress() {
		suppressed = true;

	}
    
}
