package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class ZeroMapTheta {

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;

    private static final String VUFORIA_KEY =
                    "AdzMYbL/////AAABmflzIV+frU0RltL/ML+2uAZXgJiI" +
                    "Werfe92N/AeH7QsWCOQqyKa2G+tUDcgvg8uE8QjHeBZPcpf5hAwlC5qCfvg76eBoaa2b" +
                    "MMZ73hmTiHmr9fj3XmF4LWWZtDC6pWTFrzRAUguhlvgnck6Y4jjM16Px5TqgWYuWnpcxNM" +
                    "HMyOXdnHLlyysyE64PVzoN7hgMXgbi2K8+pmTXvpV2OeLCag8fAj1Tgdm/kKGr0TX86aQsC2" +
                    "RVjToZXr9QyAeyODi4l1KEFmGwxEoteNU8yqNbBGkPXGh/+IIm6/s/KxCJegg8qhxZDgO8110F" +
                    "RzwA5a6EltfxAMmtO0G8BB9SSkApxkcSzpyI0k2LxWof2YZG6x4H";

    private static final float mmPerInch        = 25.4f;
    private static final float stoneZ = 2.00f * mmPerInch;

    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;

    WebcamName webcamName = null;

    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;

    public void zeroBrowse(LinearOpMode opMode) {

        webcamName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");

        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id",
                opMode.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters =
                new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = webcamName;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables targetsSkyStone =
                this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES,
                        90, 0, -90)));

        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;
        final float CAMERA_VERTICAL_DISPLACEMENT = -8.50f * mmPerInch;
        final float CAMERA_LEFT_DISPLACEMENT     = -7.250f * mmPerInch;

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                    .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT,
                            CAMERA_VERTICAL_DISPLACEMENT)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                            phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).
                    setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        opMode.waitForStart();

        targetsSkyStone.activate();
        while (!targetVisible) {

            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    opMode.telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    OpenGLMatrix robotLocationTransform =
                            ((VuforiaTrackableDefaultListener)
                                    trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            if (targetVisible) {
                VectorF translation = lastLocation.getTranslation();
                opMode.telemetry.addData("Pos (in)", "{Z, X, Y} = %.1f, %.1f, %.1f",
                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch,
                        translation.get(2) / mmPerInch);

                Orientation rotation =
                        Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                opMode.telemetry.addData("Rot (deg)",
                        "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f",
                        rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            }

            else opMode.telemetry.addData("Visible Target", "none");
            opMode.telemetry.update();
        }

        targetsSkyStone.deactivate();
    }
}
