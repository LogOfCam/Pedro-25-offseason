package nextFTC.routines;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;

import nextFTC.TrajectoryBuilder;
import nextFTC.subsystems.claw;

public class SpecimenRoutines {

    private SpecimenRoutines() {
    }

    public static Command firstSample() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.startToPlace, true, 1.0)
        );
    }

    public static Command pickup1() {
        return new SequentialGroup(
                claw.INSTANCE.open(),
                new FollowPath(TrajectoryBuilder.placeToPickup1, true, 1.0),
                new Delay(0.5)
        );
    }

    public static Command clip1() {
        return new SequentialGroup(
                claw.INSTANCE.open(),
                new Delay(1),
         new ParallelGroup(
                new FollowPath(TrajectoryBuilder.clip1, true, 1.0)
        )
        );
    }

    public static Command pickupPosition2() {
        return new SequentialGroup(
         new ParallelGroup(
                new FollowPath(TrajectoryBuilder.pickupPosition2, true, 1.0)
                ),
                new Delay(0.5)
        );

    }
    public static Command clip2() {
        return new SequentialGroup(
                new FollowPath(TrajectoryBuilder.clip2, true, 1.0)
        );
    }
    // SpecimenTestAuto
    public static Command StartPosition() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.StartPosition, true, 1.0)
        );
    }
}
