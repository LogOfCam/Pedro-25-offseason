package nextFTC.opModes;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import nextFTC.TrajectoryBuilder;
import nextFTC.routines.SpecimenRoutines;
import nextFTC.routines.MechanismRoutines;
import nextFTC.subsystems.IntakeArm;
import nextFTC.subsystems.IntakeClaw;
import nextFTC.subsystems.IntakeSlide;
import nextFTC.subsystems.OuttakeClaw;
import nextFTC.subsystems.OuttakeSlide;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "SpecimenTestAuto")
public class SpecimenTestAuto extends PedroOpMode {
    private final FConstants fConstants = new FConstants();
    private final LConstants lConstants = new LConstants();

    public SpecimenTestAuto(){
        super(IntakeClaw.INSTANCE,
                IntakeSlide.INSTANCE,
                IntakeArm.INSTANCE,
                OuttakeSlide.INSTANCE,
                OuttakeClaw.INSTANCE);
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
                        SpecimenRoutines.firstSpecimen()
//                        SpecimenRoutines.PreparePush1(),
//                        SpecimenRoutines.Push1(),
//                        SpecimenRoutines.PreparePush2(),
//                        SpecimenRoutines.Push2()

                )
        );
    }
}
