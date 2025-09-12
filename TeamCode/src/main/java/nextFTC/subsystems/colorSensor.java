package nextFTC.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

import org.firstinspires.ftc.robotcore.external.Telemetry;
public class colorSensor extends Subsystem {

    public static final colorSensor INSTANCE = new colorSensor();
    private ColorSensor colorSensor;
    public Telemetry telemetry;
    private final String name = "colorSensor";

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        OpModeData.telemetry = telemetry;
        try {
            colorSensor = hardwareMap.get(ColorSensor.class, name);
            telemetry.addData("ColorSensor", "Initialized");
        } catch (Exception e) {
            telemetry.addData("ColorSensor", "Failed to initialize: " + e.getMessage());
            colorSensor = null;
        }
        telemetry.update();
    }


    public int red() {
        return colorSensor != null ? colorSensor.red() : 0;
    }

    public int green() {
        return colorSensor != null ? colorSensor.green() : 0;
    }

    public int blue() {
        return colorSensor != null ? colorSensor.blue() : 0;
    }


            public String getDominantColor(int red, int green, int blue, boolean isPurple) {
                if (isPurple) return "Purple";
                if (red > green && red > blue) return "Red";
                if (blue > red && blue > green) return "Blue";
                if (green > red && green > blue) return "Green";
                return "Unknown";
            }

    @Override
    public void periodic() {
        Telemetry telemetry = OpModeData.telemetry;

        if (telemetry != null && colorSensor != null) {
            int r = red();
            int g = green();
            int b = blue();

            boolean isPurple = r > 30 && b > 30 && g < 40 && Math.abs(r - b) < 25;

            telemetry.addData("Red", r);
            telemetry.addData("Green", g);
            telemetry.addData("Blue", b);
            telemetry.addData("Purple Score", (r + b) / 2 - g);
            telemetry.addData("Is Purple?", isPurple);
            telemetry.addData("Dominant", getDominantColor(r, green(), b, isPurple));
            telemetry.update();
        }
    }
    }
