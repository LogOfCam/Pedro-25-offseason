package nextFTC.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

@Config
public class Belt extends Subsystem {

    public static final Belt INSTANCE = new Belt();

    public static double kP = 0.009;
    public static double kI = 0.0;
    public static double kD = 0.001;
    public static double kF = 0.0;
    public static double threshold = 10;

    public String name = "Belt";

    private MotorEx motor;

    private final PIDFController controller = new PIDFController(kP, kI, kD, (pos) -> kF, threshold);

    public static double increment = 20;

    public Command up() { return new RunToPosition(motor, motor.getCurrentPosition() + increment, controller, this);}

    public Command down() { return new RunToPosition(motor, motor.getCurrentPosition() - increment, controller, this);}

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

        OpModeData.telemetry.addData("belt Position", motor.getCurrentPosition());
        OpModeData.telemetry.addData("belt Target", controller.getTarget());
    }

    public void resetEncoder() {
        motor.resetEncoder();
    }
}