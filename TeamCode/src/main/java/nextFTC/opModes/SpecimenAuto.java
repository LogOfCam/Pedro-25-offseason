package nextFTC.opModes;

import static nextFTC.routines.SpecimenRoutines.ThirdPosition;
import static nextFTC.subsystems.webcam.visionPortal;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.google.blocks.ftcrobotcontroller.util.AvailableTtsLocalesProvider;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

import nextFTC.TrajectoryBuilder;
import nextFTC.routines.SpecimenRoutines;
import nextFTC.subsystems.webcam;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "SpecimenAuto")
public class SpecimenAuto extends PedroOpMode {
    private final FConstants fConstants = new FConstants();
    private final LConstants lConstants = new LConstants();
    public boolean tag23Handled = false;
    public boolean tag21Handled = false;
    public boolean tag22Handled = false;


    @Override
    public void onInit() {
        webcam.init(hardwareMap, telemetry);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        OpModeData.telemetry = telemetry;
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        try {
            follower.poseUpdater.resetIMU();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        OpModeData.telemetry = telemetry;
    }

    @Override
    public void onWaitForStart() {
        telemetry.update();
    }

    @Override
    public void onUpdate() {
        telemetry.addData("Camera State", visionPortal.getCameraState());
        List<AprilTagDetection> detections = webcam.getInstance().getDetections();
        if (detections != null && !detections.isEmpty()) {
            telemetry.addData("AprilTags Detected", detections.size());

            for (AprilTagDetection tag : detections) {
                telemetry.addData("Tag ID", tag.id);

                if (tag.center != null) {
                    telemetry.addData("Position", "(%.2f, %.2f)", tag.center.x, tag.center.y);
                } else {
                    telemetry.addData("Position", "unknown");
                }

                if (tag.ftcPose != null) {
                    telemetry.addData("Distance", "%.2f meters", tag.ftcPose.range);
                    telemetry.addData("Bearing", "%.2f°", tag.ftcPose.bearing);
                } else {
                    telemetry.addData("Distance", "unknown");
                    telemetry.addData("Bearing", "unknown");
                }

                telemetry.addLine();
                if (tag.id == 21) {
                    telemetry.addLine("Tag GPP detected!");
                    TrajectoryBuilder.buildPaths(follower);
                    CommandManager.INSTANCE.scheduleCommand(
                            SpecimenRoutines.StartPose()
                    );
                    tag21Handled = true;
                }
                if (tag.id == 22) {
                    telemetry.addLine("Tag PGP detected!");
                    TrajectoryBuilder.buildPaths(follower);
                    CommandManager.INSTANCE.scheduleCommand(
                            SpecimenRoutines.EndCurve()
                    );
                    SpecimenRoutines.EndCurve();
                    tag22Handled = true;
                }
                if (tag.id == 23) {
                    telemetry.addLine("Tag PPG detected!");
                    TrajectoryBuilder.buildPaths(follower);
                    CommandManager.INSTANCE.scheduleCommand(
                            SpecimenRoutines.ThirdPosition()
                    );
                    tag23Handled = true;
                }
            }
        } else {
            telemetry.addData("AprilTags Detected", 0);
        }
        telemetry.update();
    }
    @Override
    public void onStop() {
        webcam.getInstance().close();
        webcam.reset();
    }

//    @Override
//    public void onStartButtonPressed() {
//        TrajectoryBuilder.buildPaths(follower);
//
//        CommandManager.INSTANCE.scheduleCommand(
//                new SequentialGroup(
//                )
//        );
//    }
}
