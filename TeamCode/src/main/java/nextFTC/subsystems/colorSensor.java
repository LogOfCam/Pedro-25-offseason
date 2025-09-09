package nextFTC.subsystems;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

        public class colorSensor extends Subsystem {
        public static final colorSensor INSTANCE = new nextFTC.subsystems.colorSensor ();
        public ColorSensor colorSensor;
        public String name = "colorSensor";
        @Override
        public void initialize() {
            colorSensor = OpModeData.INSTANCE.getHardwareMap().get(ColorSensor.class, name);
        }
        public int red() {
            return colorSensor.red();
        }

        public int blue() {
            return colorSensor.blue();
        }

        public int green() {
            return colorSensor.green();
        }

        public String getDominantColor() {
            int red = red();
            int green = green();
            int blue = blue();

            if (red > green && red > blue) return "Red";
            if (blue > red && blue > green) return "Blue";
            if (green > red && green > blue) return "Green";
            return "Unknown";
        }

    @Override
    public void periodic() {
//        OpModeData.telemetry.addData("Red", red());
//        OpModeData.telemetry.addData("Green", green());
//        OpModeData.telemetry.addData("Blue", blue());
//        OpModeData.telemetry.addData("Dominant", getDominantColor());
        OpModeData.telemetry.update();
            }
    }


