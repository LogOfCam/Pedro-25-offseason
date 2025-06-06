package nextFTC.subsystems;
import com.rowanmcalpin.nextftc.core.Subsystem;

public class touchSensor extends Subsystem {
    public static final touchSensor INSTANCE = new touchSensor();
    public touchSensor touchSensor;
    public String name = "touchSensor";
    public String state;
}

