package nextFTC;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.rowanmcalpin.nextftc.pedro.FollowerNotInitializedException;

public class TrajectoryBuilder {

    public static final Pose startPose = new Pose(5.5, 63.5, Math.toRadians(0));
    public static final Pose placePosition = new Pose(36, 72, Math.toRadians(0));
    public static final Pose endPosition = new Pose(24.5, 23.5, Math.toRadians(0));
    public static final Point firstCurve = new Point(23,69);


    public static PathChain beginningToPlace, middleToEnd;

    public static void buildBucketPaths(Follower follower) {
        if (follower == null) {
            try {
                throw new FollowerNotInitializedException();
            } catch (FollowerNotInitializedException e) {
                throw new RuntimeException(e);
            }
        }

        beginningToPlace = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(startPose), new Point(placePosition)
                        )
                ).setConstantHeadingInterpolation(startPose.getHeading()).build();

        middleToEnd = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(placePosition),firstCurve,new Point(endPosition)
                        )
                ).setConstantHeadingInterpolation(placePosition.getHeading()).build();
    }
}