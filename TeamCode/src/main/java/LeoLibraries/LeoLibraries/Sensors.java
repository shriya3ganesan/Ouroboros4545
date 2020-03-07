package LeoLibraries.LeoLibraries;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Sensors {

    private LinearOpMode opMode;

    // initalizing gyro
    public BNO055IMU gyro;
    private Orientation angles;
    Acceleration gravity;
    private BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    double gyroOffset;
    double startGyro;
    // constructor for initializing Sensors class

    public Sensors(LinearOpMode opMode) {
        this.opMode = opMode;

        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        gyro = this.opMode.hardwareMap.get(BNO055IMU.class, "imu");

        gyro.initialize(parameters);
        angles = gyro.getAngularOrientation();
        opMode.telemetry.addLine("gyro calibrated");
        opMode.telemetry.update();

        //uSonic = opMode.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "Ultrasonic");
    }

    // Note: Due to positioning of REV Hub, yaw is the 1st Angle, roll 3rd Angle, and pitch 2nd Angle.

    public void updateGyroValues() {
        angles = gyro.getAngularOrientation();

    }

    public void updateGyroR() {
        angles = gyro.getAngularOrientation().
                toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);

    }

    public double yaw() {
        angles = gyro.getAngularOrientation();
        return angles.firstAngle;
    }

    public double getTrueYaw () {
        angles = gyro.getAngularOrientation();
        double playTool = angles.firstAngle;
        if (playTool == 0) {
            playTool = -180;
        }
        return (playTool + 180);
    }

    public double getBestYaw() {
        angles = gyro.getAngularOrientation();
        double playTool = angles.firstAngle;
        if (playTool < 0) {
            playTool = -playTool;
        }
        else if (playTool > 0) {
            playTool = -playTool;
            playTool += 360;
        }
        return playTool;
    }

    public double MRConvert (double angle) {
        while (angle <= 0) angle += 360;
        while (angle >= 360) angle -= 360;
        return angle;
    }

    public double getGyroYaw() {
        updateGyroValues();
        return angles.firstAngle;
    }

    public double getGyroYawMR() {
        updateGyroValues();
        return MRConvert(angles.firstAngle) + gyroOffset;
    }
    // method to return yaw of robot for all turns

    public double getGyroYawR() {
        return -angles.firstAngle;

    }

    public double getGyroPitch() {
        updateGyroValues();
        return angles.secondAngle;

    }

    public double getGyroRoll() {
        updateGyroValues();
        return angles.thirdAngle;

    }
}
