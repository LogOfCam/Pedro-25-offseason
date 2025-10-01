package nextFTC.routines;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.pedro.FollowPath;

import nextFTC.TrajectoryBuilder;

public class SpecimenRoutines {

    private SpecimenRoutines() {
    }
    public static Command StartPose() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.StartPose, true, 1.0)
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
    public static Command ThirdPosition() {
        return new ParallelGroup(
                new FollowPath(TrajectoryBuilder.ThirdPosition, true, 1.0)
        );
    }
}
