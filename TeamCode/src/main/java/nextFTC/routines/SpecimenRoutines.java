package nextFTC.routines;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.pedro.FollowPath;

import nextFTC.TrajectoryBuilder;
import nextFTC.subsystems.IntakeClaw;

public class SpecimenRoutines {

    private SpecimenRoutines() {}

    public static Command firstSample() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.startToPlace, true, 1.0),
                MechanismRoutines.startingForword(),
                MechanismRoutines.testLift()
        );
    }

    public static Command pickup1() {
        return new SequentialGroup(
                IntakeClaw.INSTANCE.open(),
                new FollowPath(TrajectoryBuilder.placeToPickup1, true, 1.0),
                MechanismRoutines.pickUp(),
                MechanismRoutines.CloseClaw()
        );
    }
    public static Command clip1() {
        return new SequentialGroup(
                new FollowPath(TrajectoryBuilder.humanPlayer1, true, 1.0)
        );
    }
}
