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
                new FollowPath(TrajectoryBuilder.StartPosition, true, 1.0)
        );
    }
    public static Command PlacePosition() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.PlacePosition, true, 1.0)
        );
    }
    public static Command EndCurve() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.EndCurve, true, 1.0)
        );
    }
}
