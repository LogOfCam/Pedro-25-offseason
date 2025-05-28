package nextFTC;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.rowanmcalpin.nextftc.pedro.FollowerNotInitializedException;

public class TrajectoryBuilder {

    public static final Pose startPose = new Pose(8.5, 87.5, Math.toRadians(0));
    public static final Pose middlePosition = new Pose(31, 36, Math.toRadians(0));
    public static final Pose endPosition = new Pose(33.5, 100, Math.toRadians(0));


    public static PathChain beginningToMiddle, middleToEnd;

    public static void buildBucketPaths(Follower follower) {
        if (follower == null) {
            try {
                throw new FollowerNotInitializedException();
            } catch (FollowerNotInitializedException e) {
                throw new RuntimeException(e);
            }
        }

        beginningToMiddle = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(startPose), new Point(middlePosition)
                        )
                ).setConstantHeadingInterpolation(startPose.getHeading()).build();

        middleToEnd = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(middlePosition), new Point(endPosition)
                        )
                ).setConstantHeadingInterpolation(middlePosition.getHeading()).build();
    }
}