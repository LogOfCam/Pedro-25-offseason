package nextFTC.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

@Config
public class launch extends Subsystem {
        public static final launch INSTANCE = new launch();
        public String name = "launch";
        private MotorEx motor;

    public static final double TICKS_PER_REV = 28.0;
    public static double targetRPM = 500;
    public static double Power = 0.5;


        @Override
        public void initialize() {
            motor = new MotorEx(name);
            DcMotor dcMotor = motor.getMotor();
            motor.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            new PIDFCoefficients(10.0, 0.0, 0.0, 0.0);
            motor.getMotor().setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(10.0, 0.0, 0.0, 0.0));

        }

        @NonNull
        @Override
        public Command getDefaultCommand() {
            return stop();
        }
    public Command stop() {
        return new InstantCommand(() -> motor.setVelocity(0));
    }
    @Override
    public void periodic() {
        double ticksPerSecond = motor.getVelocity();
        double rpm = (ticksPerSecond * 60.0) / TICKS_PER_REV;

        OpModeData.telemetry.addData("Launcher Velocity (Ticks/sec)", ticksPerSecond);
        OpModeData.telemetry.addData("Launcher Velocity (RPM)", rpm);
    }
    public Command runAtTargetRPM() {
        return new InstantCommand(() -> {
            double ticksPerSecond = (targetRPM * TICKS_PER_REV) / 60.0;
            motor.setVelocity(ticksPerSecond);
        });
    }
    public Command runAtPower(double power) {
        return new InstantCommand(() -> motor.setPower(power));
    }
    public void resetEncoder() {
        motor.resetEncoder();
    }
}