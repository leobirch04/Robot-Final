package package1;

import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class LowBatteryBehavior implements Behavior {
    private boolean suppressed = false;
    private final double LOW_BATTERY_THRESHOLD = 6.0; // Example threshold in volts

    @Override
    public boolean takeControl() {
        // Check if the battery voltage is below the threshold
        return Battery.getVoltage() < LOW_BATTERY_THRESHOLD;
    }

    @Override
    public void action() {
        suppressed = false;
        System.out.println("Low battery voltage! Exiting program...");
        Sound.buzz(); // Alert sound
        Delay.msDelay(3000);
        
        // You can add any additional actions here before exiting
        // For example, stop motors, close connections, etc.
        
        // Exit the program gracefully
        System.exit(0);
    }

    @Override
    public void suppress() {
        suppressed = true;
    }
}