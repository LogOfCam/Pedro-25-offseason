package nextFTC.subsystems;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

public class touchSensor extends Subsystem {
    public static final touchSensor INSTANCE = new touchSensor();
    public TouchSensor touch;
    public String name = "touchSensor";
    @Override
    public void initialize(){
        touch = OpModeData.INSTANCE.getHardwareMap().get(TouchSensor.class, name);
    }
    public boolean isPressed() {
        return touch != null && touch.isPressed();
    }

    @Override
    public void periodic() {
        if (touch != null) {
            OpModeData.telemetry.addData("Touch Sensor Pressed", touch.isPressed());
        } else {
            OpModeData.telemetry.addData("Touch Sensor", "Not Initialized");
        }
    }
}

