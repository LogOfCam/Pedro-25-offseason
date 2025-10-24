package nextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import java.util.Objects;

public class arm extends Subsystem {
    public static final arm INSTANCE = new arm();
    private arm() {}
    public Servo servo;
    public String name = "arm";
    public String state;

    public double armPushed = 0.55;
    public double armNotPushed = 0.696;

    @Override
    public void initialize(){
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name);
    }

    public Command pushed(){
        return new ServoToPosition(servo, armPushed, this);
    }

    public Command notPushing(){
        return new ServoToPosition(servo, armNotPushed, this);
    }
    public Command setPosition(double target){
        return new ServoToPosition(servo, target, this);
    }

}
