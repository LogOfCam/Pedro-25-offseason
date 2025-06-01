package nextFTC.routines;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;

import nextFTC.TrajectoryBuilder;
import nextFTC.subsystems.IntakeClaw;

public class SpecimenRoutines {

    private SpecimenRoutines() {
    }

    public static Command firstSample() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.startToPlace, true, 1.0),
                MechanismRoutines.startingForward(),
                MechanismRoutines.testLift()
        );
    }

    public static Command pickup1() {
        return new SequentialGroup(
                IntakeClaw.INSTANCE.open(),
                new FollowPath(TrajectoryBuilder.placeToPickup1, true, 1.0),
                new Delay(0.5),
                MechanismRoutines.pickUp(),
                MechanismRoutines.CloseClaw()
        );
    }

    public static Command clip1() {
        return new SequentialGroup(
                IntakeClaw.INSTANCE.open(),
                new Delay(1),
         new ParallelGroup(
                new FollowPath(TrajectoryBuilder.clip1, true, 1.0),
                MechanismRoutines.rampToPickup2()
        )
        );
    }

    public static Command pickupPosition2() {
        return new SequentialGroup(
         new ParallelGroup(
                new FollowPath(TrajectoryBuilder.pickupPosition2, true, 1.0),
                MechanismRoutines.rampToPickup2()
                ),
                new Delay(0.5),
                MechanismRoutines.pickUp(),
                MechanismRoutines.CloseClaw()
        );

    }
    public static Command clip2() {
        return new SequentialGroup(
                new FollowPath(TrajectoryBuilder.clip2, true, 1.0)
        );
    }
    // SpecimenTestAuto
    public static Command firstSpecimen() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.startToPlace, true, 1.0),
                MechanismRoutines.testLift(),
                MechanismRoutines.place(),
         new SequentialGroup(
                IntakeClaw.INSTANCE.open(),
                new Delay(0.5),
                new FollowPath(TrajectoryBuilder.PreparePush1, true, 0.5)
        )
        );
    }
//    public static Command PreparePush1() {
//        return new SequentialGroup(
//                IntakeClaw.INSTANCE.open(),
//                new Delay(0.5),
//                new FollowPath(TrajectoryBuilder.PreparePush1, true, 0.5)
//                );
//   }
    public static Command Push1() {
        return new SequentialGroup(
                new FollowPath(TrajectoryBuilder.Push1, true, 1.0)
        );
    }
    public static Command PreparePush2() {
        return new SequentialGroup(
                new FollowPath(TrajectoryBuilder.prepareToPush2, true, 1.0)
        );
    }
    public static Command Push2() {
        return new SequentialGroup(
                new FollowPath(TrajectoryBuilder.Push2, true, 1.0)
        );
    }
}
