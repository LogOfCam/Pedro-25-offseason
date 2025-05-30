package nextFTC;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.rowanmcalpin.nextftc.pedro.FollowerNotInitializedException;

public class TrajectoryBuilder {

    public static final Pose startPose = new Pose(5.5, 63.5, Math.toRadians(0));
    public static final Pose placePosition1 = new Pose(36, 72, Math.toRadians(0));
    public static final Pose pickup1Position = new Pose(30.5, 25.5, Math.toRadians(0));
    public static final Point firstPickupCurve = new Point(23,69);
    public static final Pose humanPlayer1 = new Pose(14.5, 25.5, Math.toRadians(0));


    public static PathChain startToPlace, placeToPickup1, clip1 ;

    public static void buildBucketPaths(Follower follower) {
        if (follower == null) {
            try {
                throw new FollowerNotInitializedException();
            } catch (FollowerNotInitializedException e) {
                throw new RuntimeException(e);
            }
        }

        startToPlace = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(startPose), new Point(placePosition1)
                        )
                ).setConstantHeadingInterpolation(startPose.getHeading()).build();

        placeToPickup1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(placePosition1), firstPickupCurve, new Point(pickup1Position)
                        )
                ).setConstantHeadingInterpolation(pickup1Position.getHeading()).build();
        clip1 = follower.pathBuilder()
                . addPath(
                        new BezierLine(
                                new Point(pickup1Position), new Point(humanPlayer1)
                        )
                ).setConstantHeadingInterpolation(humanPlayer1.getHeading()).build();
    }
}