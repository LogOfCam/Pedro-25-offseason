package nextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import java.util.Objects;

public class rightIntake extends Subsystem {
    public static final rightIntake INSTANCE = new rightIntake();
    private rightIntake() {}
    public Servo servo;
    public String name = "rightIntake";
    public String state;

    public double rightIntake = 0;
    public double rightNotIntaking = 0.5;
    public double rightDoubleSpeed = 1.0;

    @Override
    public void initialize(){
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name);
    }

    @Override
    public void periodic(){OpModeData.telemetry.addData("rightIntake State", state);}

    public Command rightIntaking(){
        state = "pushed";
        return new ServoToPosition(servo, rightIntake, this);
    }

    public Command rightNotIntaking(){
        state = "notPushing";
        return new ServoToPosition(servo, rightNotIntaking, this);
    }
    public Command rightNotIntaking2(){
        return new ServoToPosition(servo, rightNotIntaking, this);
    }
    public Command rightDoubleSpeed(){
        return new ServoToPosition(servo, rightDoubleSpeed, this);
    }
    public Command setPosition(double target){
        return new ServoToPosition(servo, target, this);
    }

    public Command toggleIntake(){
        if (Objects.equals(state, "pushed")){
            return rightNotIntaking();
        } else {
            return rightIntaking();
        }
    }
}
