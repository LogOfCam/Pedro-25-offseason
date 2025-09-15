package nextFTC.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

@Config
public class launch extends Subsystem {
        public static final launch INSTANCE = new launch();
        public String name = "launch";
        private MotorEx motor;

        public static double power = 1.0;
        public static double targetVelocity = 1400;

        @Override
        public void initialize() {
            motor = new MotorEx(name);
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        @NonNull
        @Override
        public Command getDefaultCommand() {
            return stop();
        }

        @Override
        public void periodic() {
            OpModeData.telemetry.addData("Launcher Velocity", motor.getVelocity());
            OpModeData.telemetry.addData("Launcher Power", power);
        }

        public Command runWithPower(double power) {
            return new InstantCommand(() -> motor.setPower(power));
        }
        public Command runWithCurrentPower() {
            return runWithPower(power);
        }
        public Command stop() {
            return new InstantCommand(() -> motor.setPower(0));
        }
        public Command runAtVelocity(double velocity) {
            return new InstantCommand(() -> motor.setVelocity(velocity));
        }
    public void resetEncoder() {
        motor.resetEncoder();
    }
}