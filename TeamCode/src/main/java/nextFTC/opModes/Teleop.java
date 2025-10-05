package nextFTC.opModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.PassiveConditionalCommand;
import com.rowanmcalpin.nextftc.core.command.utility.statemachine.AdvancingCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;


import nextFTC.subsystems.launch;
import nextFTC.subsystems.arm;
import nextFTC.subsystems.colorSensor;
import nextFTC.subsystems.leftIntake;
import nextFTC.subsystems.rightIntake;
import nextFTC.subsystems.touchSensor;

@TeleOp(name = "ClipBot")
public class Teleop extends PedroOpMode {
    public Teleop() {
        super(arm.INSTANCE,
                launch.INSTANCE,
                rightIntake.INSTANCE,
                leftIntake.INSTANCE
                //touchSensor.INSTANCE
        );
    }

    //   public MecanumDriverControlled driver;
//    public MotorEx frontLeft;
//    public MotorEx backLeft;
//    public MotorEx frontRight;
//    public MotorEx backRight;
//    public MotorEx[] driveMotors;
    private double lastLoopTimestamp = 0.0;
    private int step = 0;
    private boolean lastRightBumper = false;
    private boolean lastLeftBumper = false;

    @Override
    public void onInit() {
        colorSensor.INSTANCE.initialize(hardwareMap, telemetry);
        //mecanumDriveInit();
        telemetry.update();
    }

    @Override
    public void onWaitForStart() {
    }

    @Override
    public void onStartButtonPressed() {
//        driver = new MecanumDriverControlled(driveMotors, gamepadManager.getGamepad1());
//        driver.invoke();
        registerControls();
    }

    @Override
    public void onUpdate() {
        colorSensor.INSTANCE.periodic();
        if (gamepad1.a) {
            launch.INSTANCE.sevenPower(launch.sevenPower);
        } else if
        (gamepad1.x) {
            launch.INSTANCE.sixPower(launch.sixPower);
        } else if
        (gamepad1.b) {
            launch.INSTANCE.eightPower(launch.eightPower);
        } else if
        (gamepad1.y) {
            launch.INSTANCE.ninePower(launch.ninePower);
        } else if
        (gamepad1.dpad_up) {
            launch.INSTANCE.fivePower(launch.fivePower);
        } else {
            launch.INSTANCE.zeroPower(0);
        }
        boolean currentRightBumper = gamepad2.right_bumper;
        if (currentRightBumper && !lastRightBumper) {
            incrementStep();
        }
        lastRightBumper = currentRightBumper;

        // Handle left bumper press
        boolean currentLeftBumper = gamepad2.left_bumper;
        if (currentLeftBumper && !lastLeftBumper) {
            decrementStep();
        }
        lastLeftBumper = currentLeftBumper;
        if (lastLoopTimestamp == 0.0) {
            lastLoopTimestamp = System.nanoTime() / 1E9;
        }

        OpModeData.telemetry.addData("Loop time", (System.nanoTime() / 1E9) - lastLoopTimestamp);
        lastLoopTimestamp = System.nanoTime() / 1E9;
        OpModeData.telemetry.update();
    }


//    public void mecanumDriveInit() {
//        frontLeft = new MotorEx("frontLeft");
//        frontRight = new MotorEx("frontRight");
//        backLeft = new MotorEx("backLeft");
//        backRight = new MotorEx("backRight");
//
//        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
//        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
//
//        driveMotors = new MotorEx[]{frontLeft, frontRight, backLeft, backRight};
//
//        for (MotorEx driveMotor : driveMotors) {
//            driveMotor.getMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        }
//    }

    private void registerControls() {
        gamepadManager.getGamepad2().getX().setReleasedCommand(arm.INSTANCE::toggle);
        gamepadManager.getGamepad2().getX().setHeldCommand(arm.INSTANCE::toggle);
        gamepadManager.getGamepad2().getDpadLeft().setReleasedCommand(leftIntake.INSTANCE::toggleIntake);
        gamepadManager.getGamepad2().getDpadLeft().setHeldCommand(leftIntake.INSTANCE::toggleIntake);
        gamepadManager.getGamepad2().getDpadRight().setHeldCommand(leftIntake.INSTANCE::leftDoubleSpeed);
        gamepadManager.getGamepad2().getDpadRight().setReleasedCommand(leftIntake.INSTANCE::leftNotIntaking2);
        gamepadManager.getGamepad2().getB().setReleasedCommand(rightIntake.INSTANCE::toggleIntake);
        gamepadManager.getGamepad2().getB().setHeldCommand(rightIntake.INSTANCE::toggleIntake);
        gamepadManager.getGamepad2().getA().setHeldCommand(rightIntake.INSTANCE::rightDoubleSpeed);
        gamepadManager.getGamepad2().getA().setReleasedCommand(rightIntake.INSTANCE::rightNotIntaking2);

    }

    private void incrementStep() {
        step = (step + 1) % 4;
        applyStep();
    }

    private void decrementStep() {
        step = (step + 3) % 4;
        applyStep();
    }

    private void applyStep() {
        switch (step) {
            case 0:
                telemetry.addLine("Step 0: Idle");
                rightIntake.INSTANCE.moveServoToPosition(0.5); // Neutral position
                break;
            case 1:
                telemetry.addLine("Step 1: Right Intaking");
                rightIntake.INSTANCE.moveServoToPosition(1.0); // Position for intake
                break;
            case 2:
                telemetry.addLine("Step 2: Right Not Intaking");
                rightIntake.INSTANCE.moveServoToPosition(0.0); // Position for stopped intake
                break;
            case 3:
                telemetry.addLine("Step 3: Right Intake Out");
                rightIntake.INSTANCE.moveServoToPosition(0.75); // Position for outtake
                break;
            default:
                telemetry.addLine("Unknown step!");
                break;
        }
        telemetry.update();
    }
}
//   public boolean slowMode = true;
//    public Command toggleSpeed() {
//        return new SequentialGroup(
//                new InstantCommand(() -> {
//                    slowMode = !slowMode;
//                }),
//                new PassiveConditionalCommand(
//                        () -> slowMode,
//                        () -> new InstantCommand(() -> {
//                            driver.setScalar(0.2);
//                        }),
//                        () -> new InstantCommand(() -> {
//                            driver.setScalar(0.8);
//                        })
//                )
//        );
//    }
