package nextFTC.subsystems;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class colorSensor extends Subsystem {
    public static final colorSensor INSTANCE = new nextFTC.subsystems.colorSensor ();
    public ColorSensor colorSensor;
    public Telemetry telemetry;
    public String name = "colorSensor";

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        try {
            colorSensor = hardwareMap.get(ColorSensor.class, name);
        } catch (Exception e) {
            telemetry.addData("Error", "Color sensor not found: " + e.getMessage());
            telemetry.update();
            colorSensor = null;
        }
    }
    public int red() {
        return colorSensor != null ? colorSensor.red() : 0;
    }

    public int blue() {
        return colorSensor != null ? colorSensor.blue() : 0;
    }

    public int green() {
        return colorSensor != null ? colorSensor.green() : 0;
    }

    public String getDominantColor() {
        int red = red();
        int green = green();
        int blue = blue();

        int minPurpleValue = 50;
        int maxGreenValue = 40;
        int colorDifferenceThreshold = 30;

        if (red > minPurpleValue && blue > minPurpleValue &&
                Math.abs(red - blue) <= colorDifferenceThreshold &&
                green < maxGreenValue) {
            return "Purple";
        }

        if (red > green && red > blue) return "Red";
        if (blue > red && blue > green) return "Blue";
        if (green > red && green > blue) return "Green";
        return "Unknown";
    }

    @Override
    public void periodic() {
        if (telemetry != null) {
            telemetry.addData("Red", red());
            telemetry.addData("Green", green());
            telemetry.addData("Blue", blue());
            telemetry.addData("Dominant", getDominantColor());
            telemetry.update();
        }
    }
}
