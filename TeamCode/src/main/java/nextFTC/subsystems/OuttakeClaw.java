package nextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import java.util.Objects;

public class OuttakeClaw extends Subsystem {
    public static final OuttakeClaw INSTANCE = new OuttakeClaw();
    //private OuttakeClaw() {}
    public Servo servo;
    public String name = "OuttakeClaw";
    public String state;

    public double OuttakeClawOpen = 0.6;
    public double OuttakeClawClosed = 0.71;

    @Override
    public void initialize(){
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name);
        open();
    }

    @Override
    public void periodic(){
        OpModeData.telemetry.addData("OuttakeClaw State", state);
    }

    public Command open(){
        state = "OPEN";
        return new ServoToPosition(servo, OuttakeClawOpen, this);
    }

    public Command close(){
        state = "CLOSE";
        return new ServoToPosition(servo, OuttakeClawClosed, this);
    }

    public Command setPosition(double target){
        return new ServoToPosition(servo, target, this);
    }

    public Command toggle(){
        if (Objects.equals(state, "OPEN")){
            return close();
        } else {
            return open();
        }
    }
}
