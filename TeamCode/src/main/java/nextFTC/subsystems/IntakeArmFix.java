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
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class IntakeArmFix extends Subsystem {

    public static final IntakeArmFix INSTANCE = new IntakeArmFix();

    public static double kP = 0.01;
    public static double kI = 0.0;
    public static double kD = 0.00015;
    public static double kF = 0.1;
    public static double target = 0.0;
    public static double threshold = 10;

    public String name = "IntakeArm";

    private MotorEx motor;

    private final PIDFController controller = new PIDFController(kP, kI, kD, (pos) -> kF, threshold);

    public double pickupPosition = 400;
    public double transferPosition = -20;
    public double clip = -300;
    //    public Command IntakeArmUp() {
//        return new RunToPosition(motor,motor.getCurrentPosition()+20, controller, this);
//    }
//    public Command IntakeArmDown() {
//        return new RunToPosition(motor,motor.getCurrentPosition()-20, controller, this);
//    }
    public Command pickup() { return new RunToPosition(motor, pickupPosition, controller, this); }
    public Command transfer() { return new RunToPosition(motor, transferPosition, controller, this); }
    public Command clip() {
        return new RunToPosition(motor, clip, controller, this);
    }

    @Override
    public void initialize() {
        motor = new MotorEx(name);
    }

    @NonNull
    @Override
    public Command getDefaultCommand() { return new HoldPosition(motor, controller, this);}

    @Override
    public void periodic() {
        controller.setKP(kP);
        controller.setKI(kI);
        controller.setKD(kD);
        controller.setSetPointTolerance(threshold);
        

        OpModeData.telemetry.addData("OuttakeSlide Position", motor.getCurrentPosition());
        OpModeData.telemetry.addData("OuttakeSlide Target", controller.getTarget());
        OpModeData.telemetry.addData("OuttakeSlide Current(A):",motor.getMotor().getCurrent(CurrentUnit.MILLIAMPS));
    }

    public void resetEncoder() {
        motor.resetEncoder();
    }

    public Command move(float power) {
        return new SetPower(motor,
                power,
                this);
    }
}