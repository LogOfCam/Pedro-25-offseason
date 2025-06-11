package nextFTC.opModes;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import nextFTC.TrajectoryBuilder;
import nextFTC.routines.SpecimenRoutines;
import nextFTC.subsystems.claw;
import nextFTC.subsystems.arm;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "SpecimenTestAuto")
public class SpecimenTestAuto extends PedroOpMode {
    private final FConstants fConstants = new FConstants();
    private final LConstants lConstants = new LConstants();

    public SpecimenTestAuto(){
        super(claw.INSTANCE,
                arm.INSTANCE);
    }

    @Override
    public void onInit() {

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        try {
            follower.poseUpdater.resetIMU();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        follower.setStartingPose(TrajectoryBuilder.startPosition);

        arm.INSTANCE.resetEncoder();

        claw.INSTANCE.close().invoke();

        OpModeData.telemetry = telemetry;
    }

    @Override
    public void onWaitForStart() {
        claw.INSTANCE.close(); // Close claw
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
                        SpecimenRoutines.StartPosition()
//                        SpecimenRoutines.PreparePush1(),
//                        SpecimenRoutines.Push1(),
//                        SpecimenRoutines.PreparePush2(),
//                        SpecimenRoutines.Push2()

                )
        );
    }
}
