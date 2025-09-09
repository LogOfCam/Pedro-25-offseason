package nextFTC.opModes;

import static nextFTC.subsystems.webcam.visionPortal;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.PassiveConditionalCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

import nextFTC.subsystems.webcam;
import nextFTC.subsystems.arm;
import nextFTC.subsystems.claw;
import nextFTC.subsystems.colorSensor;
import nextFTC.subsystems.touchSensor;

//test
@TeleOp(name = "ClipBot")
public class Teleop extends PedroOpMode {
    public Teleop() {
        super(claw.INSTANCE,
                arm.INSTANCE,
                colorSensor.INSTANCE,
                touchSensor.INSTANCE);
    }

    public MecanumDriverControlled driver;
    public MotorEx frontLeft;
    public MotorEx backLeft;
    public MotorEx frontRight;
    public MotorEx backRight;
    public MotorEx[] driveMotors;
    public String lastSequence;
    public int specimenSequenceCount = 0;
    private double lastLoopTimestamp = 0.0;

    @Override
    public void onInit() {
        webcam.init(hardwareMap, telemetry);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        OpModeData.telemetry = telemetry;
        mecanumDriveInit();
        telemetry.update();
    }

    @Override
    public void onWaitForStart() {}

    @Override
    public void onStartButtonPressed() {
        driver = new MecanumDriverControlled(driveMotors, gamepadManager.getGamepad1());
        driver.invoke();
        registerControls();


        claw.INSTANCE.close();
    }

    @Override
    public void onUpdate() {
        touchSensor.INSTANCE.periodic();
        telemetry.addData("Camera State", visionPortal.getCameraState());
        List<AprilTagDetection> detections = webcam.getInstance().getDetections();
        if (detections != null && !detections.isEmpty()) {
            telemetry.addData("AprilTags Detected", detections.size());

            for (AprilTagDetection tag : detections) {
                telemetry.addData("Tag ID", tag.id);

                // Check if 'center' is not null
                if (tag.center != null) {
                    telemetry.addData("Position", "(%.2f, %.2f)", tag.center.x, tag.center.y);
                } else {
                    telemetry.addData("Position", "unknown");
                }

                // Check if 'ftcPose' is not null
                if (tag.ftcPose != null) {
                    telemetry.addData("Distance", "%.2f meters", tag.ftcPose.range);
                    telemetry.addData("Bearing", "%.2f°", tag.ftcPose.bearing);
                } else {
                    telemetry.addData("Distance", "unknown");
                    telemetry.addData("Bearing", "unknown");
                }

                telemetry.addLine();
                // Your custom tag id check
                if (tag.id == 21) {
                    telemetry.addLine("Tag GPP detected!");
                }
            }
        } else {
            telemetry.addData("AprilTags Detected", 0);
        }
        if (lastLoopTimestamp == 0.0) {
            lastLoopTimestamp = System.nanoTime() / 1E9;
        }

        OpModeData.telemetry.addData("Loop time", (System.nanoTime() / 1E9) - lastLoopTimestamp);
        lastLoopTimestamp = System.nanoTime() / 1E9;
        OpModeData.telemetry.update();
    }
        @Override
        public void onStop() {
            webcam.getInstance().close();
            webcam.reset();
        }

    public void mecanumDriveInit() {
        frontLeft = new MotorEx("frontLeft");
        frontRight = new MotorEx("frontRight");
        backLeft = new MotorEx("backLeft");
        backRight = new MotorEx("backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        driveMotors = new MotorEx[]{frontLeft, frontRight, backLeft, backRight};

        for (MotorEx driveMotor : driveMotors) {
            driveMotor.getMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        arm.INSTANCE.resetEncoder();
    }

    private void registerControls() {
        //gamepadManager.getGamepad1().getRightBumper().setPressedCommand(this::specimenNextStep);
        //gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(this::specimenPreviousStep);
        //gamepadManager.getGamepad1().getA().setPressedCommand(this::toggleSpeed);
        gamepadManager.getGamepad1().getDpadUp().setHeldCommand(arm.INSTANCE::armUp);
        gamepadManager.getGamepad1().getDpadDown().setHeldCommand(arm.INSTANCE::armDown);
        gamepadManager.getGamepad1().getX().setReleasedCommand(claw.INSTANCE::toggle);
        gamepadManager.getGamepad1().getX().setPressedCommand(claw.INSTANCE::toggle);

        gamepadManager.getGamepad2().getX().setReleasedCommand(claw.INSTANCE::toggle); // When pressed it triggers it so say open
        gamepadManager.getGamepad2().getX().setPressedCommand(claw.INSTANCE::toggle);  // Then when released it should close it
        gamepadManager.getGamepad2().getA().setPressedCommand(arm.INSTANCE::ramp);
        gamepadManager.getGamepad2().getB().setPressedCommand(arm.INSTANCE::transfer);
        gamepadManager.getGamepad2().getY().setPressedCommand(arm.INSTANCE::pickup);
        //gamepadManager.getGamepad2().getRightBumper().setPressedCommand(this::forwardCommand);
        //gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(this::backCommand);
    }

    public boolean slowMode = true;
    public Command toggleSpeed() {
        return new SequentialGroup(
                new InstantCommand(() -> {
                    slowMode = !slowMode;
                }),
                new PassiveConditionalCommand(
                        () -> slowMode,
                        () -> new InstantCommand(() -> {
                            driver.setScalar(0.2);
                        }),
                        () -> new InstantCommand(() -> {
                            driver.setScalar(0.8);
                        })
                )
        );
    }

//    private int step = 0; // Tracks current step
//
//    public Command forwardCommand() {
//        return new AdvancingCommand()
//                .add(OuttakeSlide.INSTANCE.highBasket())
//                .add(OuttakeSlide.INSTANCE.transfer());
//    }
}
