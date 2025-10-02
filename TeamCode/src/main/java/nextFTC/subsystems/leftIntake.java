package nextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import java.util.Objects;

public class leftIntake extends Subsystem {
    public static final leftIntake INSTANCE = new leftIntake();
    private leftIntake() {}
    public Servo servo;
    public String name = "leftIntake";
    public String state;

    public double leftIntake = 0;
    public double leftNotIntaking = 0.5;
    public double leftDoubleSpeed = 1.0;

    @Override
    public void initialize(){
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name);
    }

    @Override
    public void periodic(){OpModeData.telemetry.addData("rightIntake State", state);}

    public Command leftIntaking(){
        state = "pushed";
        return new ServoToPosition(servo, leftIntake, this);
    }

    public Command leftNotIntaking(){
        state = "notPushing";
        return new ServoToPosition(servo, leftNotIntaking, this);
    }
    public Command leftDoubleSpeed(){
        return new ServoToPosition(servo, leftDoubleSpeed, this);
    }
    public Command setPosition(double target){
        return new ServoToPosition(servo, target, this);
    }

    public Command toggleIntake(){
        if (Objects.equals(state, "pushed")){
            return leftNotIntaking();
        } else {
            return leftIntaking();
        }
    }
}
