package package1;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Main {
    public static void main(String[] args) {
    	
    	Share shared = new Share();
    	EV3ColorSensor cs =  new EV3ColorSensor(SensorPort.S3);
    	BaseRegulatedMotor mx = new EV3LargeRegulatedMotor(MotorPort.A);
    	BaseRegulatedMotor my = new EV3LargeRegulatedMotor(MotorPort.B);
    	BaseRegulatedMotor mz = new EV3MediumRegulatedMotor(MotorPort.C);
    	BaseRegulatedMotor mb = new EV3LargeRegulatedMotor(MotorPort.D);
    	EV3TouchSensor touchLeft = new EV3TouchSensor(SensorPort.S2);
    	EV3TouchSensor touchRight = new EV3TouchSensor(SensorPort.S1);
        // Create instances of your behaviors
    	Behavior menu = new Menu(shared, mx, my, mz, mb, touchLeft, touchRight);
    	Behavior readColour = new ReadColour(shared, mb, cs);
        Behavior movementBehavior = new MovementBehavior(shared, shared.getXlist(), shared.getYlist(), mx, my);
        Behavior touchSensor = new TouchSensor(shared, movementBehavior, mz, touchLeft, touchRight);
        Behavior lowBatteryBehavior = new LowBatteryBehavior();
        

        // Create an array to hold all behaviors
        Behavior[] behaviors = {lowBatteryBehavior, movementBehavior, menu, readColour,touchSensor};

        // Create the arbitrator
        Arbitrator arbitrator = new Arbitrator(behaviors);

        // Start the arbitrator
        arbitrator.go();

        // Wait for ESCAPE button to exit
        while (!Button.ESCAPE.isDown()) {
            // Do nothing, just wait
            Delay.msDelay(100);
        }

        // Stop the arbitrator
        arbitrator.stop();
    }
}