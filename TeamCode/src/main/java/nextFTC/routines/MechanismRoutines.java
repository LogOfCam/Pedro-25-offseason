package nextFTC.routines;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;

import nextFTC.TrajectoryBuilder;
import nextFTC.subsystems.IntakeArm;
import nextFTC.subsystems.IntakeClaw;
import nextFTC.subsystems.IntakeSlide;
import nextFTC.subsystems.OuttakeClaw;
import nextFTC.subsystems.OuttakeSlide;

public class MechanismRoutines {

    private MechanismRoutines() {}


public static Command testLift() {
    return new SequentialGroup(
            IntakeClaw.INSTANCE.open(),
            new Delay(0.35),
            OuttakeSlide.INSTANCE.highChamber(),
            IntakeClaw.INSTANCE.close(),
            new Delay(0.25)
            //new Delay(1),
            //OuttakeSlide.INSTANCE.transfer()
    );
}


   public static Command place() {
        return new SequentialGroup(
                new Delay(0.35),
                OuttakeSlide.INSTANCE.placePosition(),
                OuttakeClaw.INSTANCE.open(),
                new Delay(0.3)
                //new Delay(1),
                //OuttakeSlide.INSTANCE.transfer()
        );
    }
    public static Command startingForword() {
        return new SequentialGroup(
                new Delay(0.5),
                new FollowPath(TrajectoryBuilder.startToPlace, true, 1.0)

        );
    }
    public static Command pickUp() {
        return new ParallelGroup(
                IntakeArm.INSTANCE.pickup()
        );
    }
    public static Command CloseClaw() {
        return new SequentialGroup(
        IntakeClaw.INSTANCE.close(),
                new Delay(0.5),
                IntakeArm.INSTANCE.ramp()
        );
    }
    public static Command rampToPickup2() {
        return new SequentialGroup(
                new Delay(0.5),
                IntakeClaw.INSTANCE.close(),
                new Delay(0.5),
                IntakeArm.INSTANCE.transfer(),
                IntakeClaw.INSTANCE.open()
        );

    }
}
//    public static Command intakeOut() {
//        return new SequentialGroup(
//                //IntakeArm.INSTANCE.pickup(),
//                IntakeClaw.INSTANCE.open(),
//                //new Delay(TimeSpan.fromMs(200)),
//                IntakeSlide.INSTANCE.out()
//        );
//    }