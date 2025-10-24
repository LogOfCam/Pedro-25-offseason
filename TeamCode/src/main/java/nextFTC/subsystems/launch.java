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
    public static double sevenPower = 0.7;
    public static double sixPower = 0.6;
    public static double fivePower = 0.5;
    public static double eightPower = 0.8;
    public static double ninePower = 0.9;
    public static double zeroPower = 0.0;

    @Override
    public void initialize() {
        motor = new MotorEx("launch");
        motor.getMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void sevenPower(double sevenPower) {
        motor.setPower(sevenPower);
    }
    public void eightPower(double eightPower) {
        motor.setPower(eightPower);
    }
    public void ninePower(double ninePower) {
        motor.setPower(ninePower);
    }
    public void sixPower(double sixPower) {
        motor.setPower(sixPower);
    }
    public void fivePower(double FivePower) {
        motor.setPower(fivePower);
    }
    public void zeroPower(double ZeroPower) {
        motor.setPower(zeroPower);
    }

    public double getPower() {
        return motor.getPower();
    }

    @Override
    public void periodic() {
        OpModeData.telemetry.addData("Launcher Power", motor.getPower());
    }
}

