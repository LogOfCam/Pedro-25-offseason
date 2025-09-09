package nextFTC.subsystems;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class webcam {

    private static webcam instance = null;
    public static VisionPortal visionPortal;
    private AprilTagProcessor tagProcessor;
    private Telemetry telemetry;
    private webcam(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        tagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .setCameraResolution(new Size(640, 480)) // or 800x448 or 320x240
                .setStreamFormat(VisionPortal.StreamFormat.YUY2) // Optional: improves compatibility
                .addProcessor(tagProcessor)
                .build();
    }
    // Public init method to be called ONCE during OpMode initialization
    public static void init(HardwareMap hardwareMap, Telemetry telemetry) {
        if (instance == null) {
            instance = new webcam(hardwareMap, telemetry);
        }
    }

    public static webcam getInstance() {
        if (instance == null) {
            throw new IllegalStateException("webcam not initialized. Call webcam.init() first.");
        }
        return instance;
    }


    public void updateTelemetry() {
        List<AprilTagDetection> detections = tagProcessor.getDetections();
        telemetry.addData("Detected Tags", detections.size());
        for (AprilTagDetection tag : detections) {
            telemetry.addData("ID", tag.id);
            telemetry.addData("Center", "(%.2f, %.2f)", tag.center.x, tag.center.y);
            telemetry.addData("Distance", "%.2f meters", tag.ftcPose.range);
            telemetry.addData("Bearing", "%.2f degrees", tag.ftcPose.bearing);
            telemetry.addLine();
        }
        telemetry.update();
    }

    public List<AprilTagDetection> getDetections() {
        return tagProcessor.getDetections();
    }

    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
    public static void reset() {
        instance = null;
    }
}

