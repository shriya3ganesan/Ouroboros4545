package LeoLibraries.LeoLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@TeleOp(name = "Zero Map MMMLXXV", group = "SorrowFiles")
public class VOMCOT extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";

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

    double x1;
    double x2;

    double y1;
    double y2;

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    boolean checkable = false;
    boolean ready = false;

    private static final float princh = 1;

    @Override
    public void runOpMode() {

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) initTfod();

        if (tfod != null) tfod.activate();

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {

                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                    if (updatedRecognitions != null) {

                      if (updatedRecognitions.size() == 2) checkable = true;
                      telemetry.addData("# Object Detected", updatedRecognitions.size());

                      int i = 0;
                      for (Recognition recognition : updatedRecognitions) {
                          height = Math.abs(recognition.getImageHeight() / 2);
                          //height = 448
                          width = Math.abs(recognition.getImageWidth() / 2);
                          //width = 800
                          intLeft = recognition.getLeft();
                          sky = recognition.getTop();
                          intDown = recognition.getBottom();
                          intRight = recognition.getRight();
                          telemetry.addData(String.format("Label (%d)", i), recognition.getLabel());
                          telemetry.addData(String.format("Confidence (%d)", i),
                                  recognition.getConfidence());
                          telemetry.addData(String.format("Box Point (%d)", i),
                                  "%.03f , %.03f", average(intLeft, intRight),
                                  average (sky, intDown));

                          if (checkable && !ready) {
                              x1 = (average(intLeft, intRight) - width) / princh;
                              y1 = (average (sky, intDown) - height) / princh;
                              ready = true;
                          }

                          else if (checkable && ready) {
                              x2 = (average(intLeft, intRight) - width) / princh;
                              y2 = (average (sky, intDown) - height) / princh;
                              ready = false;
                              checkable = true;
                          }

                      }
                      telemetry.addData(String.format("Box Point (%d)", i),
                              "%.03f , %.03f", average(x1, x2),
                              average (y1, y2));

                      telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) tfod.shutdown();
    }

    public double average(double first, double second) { return (first + second) / 2; }

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
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT);
    }
}
