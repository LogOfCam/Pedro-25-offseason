package nextFTC.routines;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.pedro.FollowPath;

import nextFTC.TrajectoryBuilder;
import nextFTC.subsystems.claw;

public class SpecimenRoutines {

    private SpecimenRoutines() {
    }
    // SpecimenTestAuto
    public static Command StartPosition() {
        return new ParallelGroup(
                MechanismRoutines.preparePlace(),
                new FollowPath(TrajectoryBuilder.StartPosition, true, 1.0)
        );
    }
    public static Command PlacePosition1() {
        return new ParallelGroup(
                MechanismRoutines.place1Position(),
                new FollowPath(TrajectoryBuilder.PlacePosition1, true, 1.0)
        );
    }
    public static Command Place1() {
        return new SequentialGroup(
                new FollowPath(TrajectoryBuilder.Place1, true, 1.0),
                MechanismRoutines.clawOpen()
        );
    }
    public static Command PreparePush1() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.PreparePush1, true, 1.0)
        );
    }
    public static Command Push1() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.Push1, true, 1.0)
        );
    }
    public static Command PrepareToPush2() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.PrepareToPush2, true, 1.0)
        );
    }
    public static Command PreparePickupSpecimen1() {
        return new SequentialGroup(
                new FollowPath(TrajectoryBuilder.PreparePickupSpecimen1, true, 1.0),
                MechanismRoutines.pickupSpecimen(),
               new Delay(TimeSpan.fromSec(0.5))
        );
    }
    public static Command PickupSpecimen1() {
        return new SequentialGroup(
                new FollowPath(TrajectoryBuilder.PickupSpecimen1, true, 1.0),
                MechanismRoutines.clawClose()
        );
    }
    public static Command PreparePlaceSpecimen2() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.PreparePlaceSpecimen2, true, 1.0),
                MechanismRoutines.preparePlace2()
        );
    }
    public static Command PlaceSpecimen2() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.PlaceSpecimen2, true, 1.0),
                MechanismRoutines.clawOpen()
        );
    }
}
