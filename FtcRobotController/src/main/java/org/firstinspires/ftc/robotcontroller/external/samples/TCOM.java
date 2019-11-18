package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@TeleOp(name = "Tensor CAPTCHA Zero Map", group = "SorrowFiles")
public class TCOM extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    private static final String VUFORIA_KEY =
                    "AdzMYbL/////AAABmflzIV+frU0RltL/ML+2uAZXgJiI" +
                    "Werfe92N/AeH7QsWCOQqyKa2G+tUDcgvg8uE8QjHeBZPcpf5hAwlC5qCfvg76eBoaa2b" +
                    "MMZ73hmTiHmr9fj3XmF4LWWZtDC6pWTFrzRAUguhlvgnck6Y4jjM16Px5TqgWYuWnpcxNM" +
                    "HMyOXdnHLlyysyE64PVzoN7hgMXgbi2K8+pmTXvpV2OeLCag8fAj1Tgdm/kKGr0TX86aQsC2" +
                    "RVjToZXr9QyAeyODi4l1KEFmGwxEoteNU8yqNbBGkPXGh/+IIm6/s/KxCJegg8qhxZDgO8110F" +
                    "RzwA5a6EltfxAMmtO0G8BB9SSkApxkcSzpyI0k2LxWof2YZG6x4H";

    double height;
    double width;
    double intLeft;
    double intRight;
    double sky;
    double intDown;

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "Incompatible Device");
        }

        if (tfod != null) {
            tfod.activate();
        }

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {

                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
                      int i = 0;
                      for (Recognition recognition : updatedRecognitions) {
                          height = recognition.getImageHeight();
                          width = recognition.getImageWidth();
                          intLeft = recognition.getLeft();
                          sky = recognition.getTop();
                          intDown = recognition.getBottom();
                          intRight = recognition.getRight();
                          telemetry.addData(String.format("Label (%d)", i), recognition.getLabel());
                          telemetry.addData(String.format("Down Bound (%d)", i), intDown);
                          telemetry.addData(String.format("Sky Bound (%d)", i), sky);
                          telemetry.addData(String.format("Left Bound (%d)", i), intLeft);
                          telemetry.addData(String.format("Box Point (%d)", i),
                                "%.03f , %.03f", average(intLeft, intRight),
                                average (sky, intDown));
                          telemetry.addData(String.format("Box Bound (%d)", i),
                                  "%.03f , %.03f", Math.abs(Math.abs(intLeft)
                                          - Math.abs(intRight)), Math.abs(Math.abs(sky) -
                                        Math.abs(intDown)));
                          telemetry.addData(String.format("Image Bound (%d)", i),
                                "%.03f , %.03f", width, height);
                          telemetry.addData(String.format("Box Ratio (%d)", i),
                                  "%.03f , %.03f",
                                  Math.abs(Math.abs(intLeft) - Math.abs(intRight)) / width,
                                  Math.abs(Math.abs(sky) - Math.abs(intDown)) / height);
                          telemetry.addData("Bound Ratio",
                                  (Math.abs(Math.abs(intLeft)
                                          - Math.abs(intRight)) / width) * (Math.abs(Math.abs(sky)
                                          - Math.abs(intDown)) / height));
                          telemetry.addData("Image Area",
                                  ((1 / ((Math.abs(Math.abs(intLeft) - Math.abs(intRight)) /
                                          width) * (Math.abs(Math.abs(sky) - Math.abs(intDown))
                                          / height)))) * 40);
                      }
                      telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    public double average(double first, double second) {
        return (first + second) / 2;
    }

    private void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minimumConfidence = 0.6;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
