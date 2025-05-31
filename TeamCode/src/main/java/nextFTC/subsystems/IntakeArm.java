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
public class IntakeArm extends Subsystem {

    public static final IntakeArm INSTANCE = new IntakeArm();

    public static double kP = 0.0043;
    public static double kI = 0.0;
    public static double kD = 0.0003;
    public static double kF = 0.1;
    public static double target = 0.0;
    public static double threshold = 20;

    public String name = "IntakeArm";

    private MotorEx motor;

    private final PIDFController controller = new PIDFController(kP, kI, kD, (pos) -> kF, threshold);

    public double pickupPosition = 450;
    public double transferPosition = -20;
    public double ramp = -140;
    public double clip2Position = -300;
    //    public Command IntakeArmUp() {
//        return new RunToPosition(motor,motor.getCurrentPosition()+20, controller, this);
//    }
//    public Command IntakeArmDown() {
//        return new RunToPosition(motor,motor.getCurrentPosition()-20, controller, this);
//    }
    public Command pickup() { return new RunToPosition(motor, pickupPosition, controller, this); }
    public Command transfer() { return new RunToPosition(motor, transferPosition, controller, this); }
    public Command ramp() {
        return new RunToPosition(motor, ramp, controller, this);
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
        

        OpModeData.telemetry.addData("IntakeArm Position", motor.getCurrentPosition());
        OpModeData.telemetry.addData("IntakeArm Target", controller.getTarget());
        OpModeData.telemetry.addData("IntakeArm Current(A):",motor.getMotor().getCurrent(CurrentUnit.MILLIAMPS));
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