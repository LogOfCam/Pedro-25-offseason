package nextFTC.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

@Config
public class launch extends Subsystem {
    public static final launch INSTANCE = new launch();

    private MotorEx motor;
    public static double setPower = 0.5;

    @Override
    public void initialize() {
        motor = new MotorEx("launch");
        motor.getMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public double getPower() {
        return motor.getPower();
    }

    @Override
    public void periodic() {
        OpModeData.telemetry.addData("Launcher Power", motor.getPower());
    }
}

