package nextFTC;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.rowanmcalpin.nextftc.pedro.FollowerNotInitializedException;

public class TrajectoryBuilder {

    public static final Pose startPosition = new Pose(8.5, 67, Math.toRadians(0));
    public static final Pose placePosition = new Pose(42, 66, Math.toRadians(0));
    public static final Pose endCurve = new Pose(66.5, 15.5, Math.toRadians(180));
    public static final Point firstPoint = new Point(23, 69);
    public static final Point secoundPoint = new Point(85, 46.5);
    public static final Pose pushPosition = new Pose(42, 66, Math.toRadians(0));



    public static PathChain StartPosition, PlacePosition, EndCurve, PushPosition;

    public static void buildPaths(Follower follower) {
        if (follower == null) {
            try {
                throw new FollowerNotInitializedException();
            } catch (FollowerNotInitializedException e) {
                throw new RuntimeException(e);
            }
        }

        StartPosition = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(startPosition), new Point(placePosition)
                        )
                ).setConstantHeadingInterpolation(startPosition.getHeading()).build();
        PlacePosition = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(placePosition), new Point(endCurve)
                        )
                ).setConstantHeadingInterpolation(placePosition.getHeading()).build();
        EndCurve = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(endCurve), firstPoint,secoundPoint, new Point(pushPosition)
                        )
                ).setLinearHeadingInterpolation(endCurve.getHeading(),pushPosition.getHeading()).build();
    }
}