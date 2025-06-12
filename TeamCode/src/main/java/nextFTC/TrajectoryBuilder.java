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
    public static final Point firstPickupCurve = new Point(23, 69);
    public static final Pose humanPlayer1 = new Pose(14.5, 25.5, Math.toRadians(0));
    public static final Pose pickup2Position = new Pose(30.5, 15.5, Math.toRadians(0));
    public static final Pose humanPlayer2 = new Pose(14.5, 15.5, Math.toRadians(180));


    //   SpecimenTestAuto
    public static final Pose startPosition = new Pose(8, 58.5, Math.toRadians(0));
    public static final Pose positionPlace1 = new Pose(36.5, 63.5, Math.toRadians(0));
    public static final Pose preparePush1 = new Pose(69.5, 22, Math.toRadians(0));
    public static final Point curve1 = new Point(9, 0.8);
    public static final Point curve2 = new Point(73, 47.5);
    public static final Pose push1 = new Pose(18.5, 18, Math.toRadians(0));
    public static final Pose prepareToPush2 = new Pose(69, 11.5, Math.toRadians(0));
    public static final Pose push2 = new Pose(22, 6.5, Math.toRadians(0));


    public static PathChain startToPlace, placeToPickup1, clip1, pickupPosition2, clip2;
    //      SpecimenTestAuto
    public static PathChain StartPosition, PreparePush1, Push1, PrepareToPush2, Push2;

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
                .addPath(
                        new BezierLine(
                                new Point(pickup1Position), new Point(humanPlayer1)
                        )
                ).setConstantHeadingInterpolation(humanPlayer1.getHeading()).build();

        pickupPosition2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(humanPlayer1), new Point(pickup2Position)
                        )
                ).setConstantHeadingInterpolation(pickup2Position.getHeading()).build();
        clip2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(pickup2Position), new Point(humanPlayer2)
                        )
                ).setConstantHeadingInterpolation(humanPlayer2.getHeading()).build();


        // SpecimenTestAuto
        StartPosition = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(startPosition), new Point(positionPlace1)
                        )
                ).setConstantHeadingInterpolation(startPosition.getHeading())
                .addPath(
                        new BezierLine(
                                new Point(positionPlace1), new Point(preparePush1)
                        )
                ).setConstantHeadingInterpolation(positionPlace1.getHeading()).build();
        PreparePush1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(preparePush1), curve1, curve2, new Point(push1)
                        )
                ).setConstantHeadingInterpolation(preparePush1.getHeading()).build();
        Push1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(push1),new Point(prepareToPush2)
                        )
                ).setConstantHeadingInterpolation(push1.getHeading()).build();
        PrepareToPush2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(prepareToPush2), new Point(push2)
                        )
                ).setConstantHeadingInterpolation(prepareToPush2.getHeading()).build();
    }
}