package nextFTC.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

import nextFTC.subsystems.TestAMotor;

@TeleOp(name = "TestingAMotor")
public class TestingAMotor extends NextFTCOpMode {

    private double lastLoopTimestamp = 0.0;

    public TestingAMotor() {
        super(TestAMotor.INSTANCE);
    }

    @Override
    public void onInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        OpModeData.telemetry = telemetry;

        gamepadManager.getGamepad2().getA().setPressedCommand(TestAMotor.INSTANCE::getTo1000);
        gamepadManager.getGamepad2().getB().setPressedCommand(TestAMotor.INSTANCE::getToZero);

        TestAMotor.INSTANCE.resetEncoder();

    }

    @Override
    public void onUpdate() {
        if (lastLoopTimestamp == 0.0) {
            lastLoopTimestamp = System.nanoTime() / 1E9;
        }

        OpModeData.telemetry.addData("Loop time", (System.nanoTime() / 1E9) - lastLoopTimestamp);
        lastLoopTimestamp = System.nanoTime() / 1E9;

        OpModeData.telemetry.update();
    }
}
