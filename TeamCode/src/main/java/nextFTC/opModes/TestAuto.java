package nextFTC.opModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import nextFTC.TrajectoryBuilder;
import nextFTC.routines.BucketRoutines;
import nextFTC.subsystems.Belt;
import nextFTC.subsystems.Clipper;
import nextFTC.subsystems.IntakeArm;
import nextFTC.subsystems.IntakeClaw;
import nextFTC.subsystems.IntakeSlide;
import nextFTC.subsystems.OuttakeClaw;
import nextFTC.subsystems.OuttakeSlide;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "TestAuto")
public class TestAuto extends PedroOpMode {
    private final FConstants fConstants = new FConstants();
    private final LConstants lConstants = new LConstants();

    public TestAuto(){
        super(IntakeClaw.INSTANCE,
                IntakeSlide.INSTANCE,
                IntakeArm.INSTANCE,
                OuttakeSlide.INSTANCE,
                OuttakeClaw.INSTANCE,
                Belt.INSTANCE,
                Clipper.INSTANCE);
    }

    @Override
    public void onInit() {

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        try {
            follower.poseUpdater.resetIMU();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        follower.setStartingPose(TrajectoryBuilder.startPose);

        IntakeArm.INSTANCE.resetEncoder();
        OuttakeSlide.INSTANCE.resetEncoder();

        IntakeClaw.INSTANCE.close().invoke();
        OuttakeClaw.INSTANCE.close().invoke();

        OpModeData.telemetry = telemetry;
    }

    @Override
    public void onWaitForStart() {
        IntakeClaw.INSTANCE.close(); // Close claw
        telemetry.update();
    }

    @Override
    public void onUpdate() {
        telemetry.update();
    }

    @Override
    public void onStartButtonPressed() {
        TrajectoryBuilder.buildBucketPaths(follower);

        CommandManager.INSTANCE.scheduleCommand(
                new SequentialGroup(
                        BucketRoutines.testLift(),
                        BucketRoutines.firstSample(),
                        BucketRoutines.bucketToSpecimen()
                )
        );
    }
}
