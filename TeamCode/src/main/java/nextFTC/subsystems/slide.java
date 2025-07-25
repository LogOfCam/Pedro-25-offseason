package nextFTC.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
public class slide extends Subsystem {

    public static final slide INSTANCE = new slide();

    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static double target = 0.0;
    public static double threshold = 45;

    public String name = "slide";
    private MotorEx motor;

    private final PIDFController controller = new PIDFController(kP, kI, kD, (pos) -> kF, threshold);
    public double john = -1700;




        public Command SlideUp(float value) {
        return new RunToPosition(motor,motor.getCurrentPosition()+200, controller, this);
    }
    public Command SlideDown (float value) {
        return new RunToPosition(motor, motor.getCurrentPosition() -200, controller, this);
    }
    public Command John() {
        return new RunToPosition(motor, john, controller, this);
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
        

        OpModeData.telemetry.addData("slide Position", motor.getCurrentPosition());
        OpModeData.telemetry.addData("slide Target", controller.getTarget());
        OpModeData.telemetry.addData("slide Current(A):",motor.getMotor().getCurrent(CurrentUnit.MILLIAMPS));
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