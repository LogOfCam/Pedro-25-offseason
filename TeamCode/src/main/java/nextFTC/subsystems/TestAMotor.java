package nextFTC.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

@Config
public class TestAMotor extends Subsystem {

    public static final TestAMotor INSTANCE = new TestAMotor();

    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.0;
    public static double target = 0.0;
    public static double threshold = 10;

    public String name = "Belt";

    private MotorEx motor;

    private final PIDFController controller = new PIDFController(kP, kI, kD, (pos) -> kF, threshold);

    public static boolean zero = false;

    public Command getToZero() {
        return new RunToPosition(motor, 0.0, controller, this);
    }

    public Command getTo1000() {
        return new RunToPosition(motor, 300.0, controller, this);
    }

    @Override
    public void initialize() {
        motor = new MotorEx(name);
    }

//    @NonNull
//    @Override
//    public Command getDefaultCommand() { return new HoldPosition(motor, controller, this);}

    @Override
    public void periodic() {
        controller.setKP(kP);
        controller.setKI(kI);
        controller.setKD(kD);
        controller.setSetPointTolerance(threshold);

        if (zero) {
            getTo1000().invoke();
        } else {
            getToZero().invoke();
        }

        OpModeData.telemetry.addData("OuttakeSlide Position", motor.getCurrentPosition());
        OpModeData.telemetry.addData("OuttakeSlide Target", controller.getTarget());
    }

    public void resetEncoder() {
        motor.resetEncoder();
    }
}