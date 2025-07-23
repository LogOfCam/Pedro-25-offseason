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
    public static final Pose startPosition = new Pose(9, 70, Math.toRadians(0));
    public static final Pose positionPlace1 = new Pose(36, 70, Math.toRadians(0));
    public static final Pose place1 = new Pose(33,70, Math.toRadians(0));
    public static final Point curve1 = new Point(4.5, 3.5);
    public static final Point curve2 = new Point(77.5, 56.5);
    public static final Pose preparePush1 = new Pose(56, 22, Math.toRadians(0));
    public static final Pose push1 = new Pose(19.5, 22, Math.toRadians(0));
    public static final Pose prepareToPush2 = new Pose(56, 13, Math.toRadians(0));
    public static final Point curve3 = new Point(80, 28.5);
    public static final Pose push2 = new Pose(20, 14, Math.toRadians(0));
    public static final Pose preparePickupSpecimen1 = new Pose(24.5, 50.5, Math.toRadians(50));
    public static final Pose pickupSpecimen1 = new Pose(19.5, 45.5, Math.toRadians(50));
    public static final Pose preparePlaceSpecimen2 = new Pose(38, 67, Math.toRadians(0));
    public static final Pose placeSpecimen2 = new Pose(35, 67, Math.toRadians(0));


    public static PathChain startToPlace, placeToPickup1, clip1, pickupPosition2, clip2;
    //      SpecimenTestAuto
    public static PathChain StartPosition,PlacePosition1,Place1, PreparePush1, Push1, PrepareToPush2,PreparePickupSpecimen1,PickupSpecimen1,PreparePlaceSpecimen2,PlaceSpecimen2;

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
                ).setConstantHeadingInterpolation(startPosition.getHeading()).build();
        PlacePosition1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(positionPlace1),new Point(place1)
                        )
                ).setConstantHeadingInterpolation(positionPlace1.getHeading()).build();
                        Place1 = follower.pathBuilder()
                                .addPath(
                                        new BezierCurve(
                                                new Point(place1), curve1, curve2,  new Point(preparePush1)
                                        )
                                ).setConstantHeadingInterpolation(place1.getHeading()).build();
        PreparePush1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(preparePush1),new Point(push1)
                        )
                ).setConstantHeadingInterpolation(preparePush1.getHeading()).build();
        Push1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(push1),curve3,new Point(prepareToPush2)
                        )
                ).setConstantHeadingInterpolation(push1.getHeading()).build();
        PrepareToPush2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(prepareToPush2), new Point(push2)
                        )
                ).setConstantHeadingInterpolation(prepareToPush2.getHeading()).build();
        PreparePickupSpecimen1= follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(push2), new Point(preparePickupSpecimen1)
                        )
                ).setConstantHeadingInterpolation(preparePickupSpecimen1.getHeading()).build();
    PickupSpecimen1= follower.pathBuilder()
            .addPath(
                        new BezierLine(
            new Point(preparePickupSpecimen1), new Point(pickupSpecimen1)
                        )
                                ).setConstantHeadingInterpolation(pickupSpecimen1.getHeading()).build();
        PreparePlaceSpecimen2= follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(pickupSpecimen1), new Point(preparePlaceSpecimen2)
                        )
                ).setConstantHeadingInterpolation(preparePlaceSpecimen2.getHeading()).build();
        PlaceSpecimen2= follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(preparePlaceSpecimen2), new Point(placeSpecimen2)
                        )
                ).setConstantHeadingInterpolation(placeSpecimen2.getHeading()).build();

    }
}