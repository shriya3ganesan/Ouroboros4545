package org.firstinspires.ftc.teamcode.teamcode.OpModes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Sensors;

@TeleOp(name="Arcade", group= "Tele Op")
public class TeleOpMecanum extends OpMode {

    DriveTrain drive = new DriveTrain();
    Intake intake = new Intake();
    Outtake outtake = new Outtake();
    ElapsedTime time = new ElapsedTime();
    ElapsedTime runtime = new ElapsedTime();

    double direction;
    double velocity;
    double speed;
    double speedProp = 1.0;
    boolean pastX = false;

    double currentEncoderTix;
    double newEncoderTix;
    double encoderTikChange;
    double calculatedSpeed;

    // CFM variables

    private static final double  massFoundation = 1.905; // Mass in kg
    private static final double massStone = .1882;
    static final double muBlocks = .78;
    static final double muMat = .535;
    double fix = 1.0;
    double tolerance = .05;
    double mass = 0.0;
    double foundationFriction = 0.0;
    double maxCFM_Velocity = 0.0;
    double CFM_AngularVelocity = 0.0;
    double cfm_power = 0.0;

    int numberStackedBlocks = 0;
    double storedRuntime;
    double encoderVelocity;


    //  Game pad Control Stick Variables
    double right_stick_x;
    double left_stick_x;
    double left_stick_y;

    double flTestPower = 0;
    double frTestPower = 0;
    double blTestPower = 0;
    double brTestPower = 0;

    double wheelDiam = 4;
    double encoderRevolutionTix = 1800;
    double vectorPositionX;
    double vectorPositionY;
    double encoderInches;

    public BNO055IMU gyro;
    public Orientation angles;
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    double gyroOffset = 0;
    double targetSlope;
    double firstBlank;
    boolean secondBlank;

    @Override
    public void init() {

        drive.fl = hardwareMap.dcMotor.get("fl");
        drive.fr = hardwareMap.dcMotor.get("fr");
        drive.bl = hardwareMap.dcMotor.get("bl");
        drive.br = hardwareMap.dcMotor.get("br");

        drive.fl.setDirection(DcMotor.Direction.FORWARD);
        drive.fr.setDirection(DcMotor.Direction.REVERSE);
        drive.bl.setDirection(DcMotor.Direction.FORWARD);
        drive.br.setDirection(DcMotor.Direction.REVERSE);

        drive.fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake.initIntakeTele(this);
        outtake.initOuttake(this);

            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "BNO055IMUCalibration.json";
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

            gyro = hardwareMap.get(BNO055IMU.class, "imu");

            gyro.initialize(parameters);
            angles = gyro.getAngularOrientation();

            gyroOffset = getGyroYaw();
            while (gyroOffset >= 360) gyroOffset -= 360;
            while (gyroOffset < 0) gyroOffset += 360;
            gyroOffset = 0 - gyroOffset;

        drive.runtime.reset();
        time.reset();
    }

    public double MRConvert (double angle) {
        while (angle <= 0) angle += 360;
        while (angle >= 360) angle -= 360;
        return angle;
    }

    public double getGyroYaw() {
        angles = gyro.getAngularOrientation();
        return MRConvert(angles.firstAngle) + gyroOffset;
    }

    //Main Loop
    @Override
    public void loop() {

        encoderInches = encoderVelocity * storedRuntime;

        telemetry.addData("Predicted Inches",
                encoderInches);

        telemetry.addData("Heading",
                getGyroYaw());

        vectorPositionX += Math.sin(getGyroYaw())
                * (encoderInches);

        vectorPositionY += Math.cos(getGyroYaw())
                * (encoderInches);

        telemetry.addData("Vector Position",
                "(" + vectorPositionX + ", " +
                vectorPositionY + ")");

        currentEncoderTix = (drive.br.getCurrentPosition() +
                drive.bl.getCurrentPosition() +
                drive.fl.getCurrentPosition() +
                drive.fr.getCurrentPosition());

        //  Set Join Sticks for Arcade Drive

        if (gamepad1.y) {
            currentEncoderTix = (drive.br.getCurrentPosition() +
                    drive.bl.getCurrentPosition() +
                    drive.fl.getCurrentPosition() +
                    drive.fr.getCurrentPosition());
                    runtime.reset();
            while (gamepad1.y) {
                drive.fl.setPower(1);
                drive.fr.setPower(1);
                drive.bl.setPower(1);
                drive.br.setPower(1);
            }
            newEncoderTix = (drive.br.getCurrentPosition() +
                    drive.bl.getCurrentPosition() +
                    drive.fl.getCurrentPosition() +
                    drive.fr.getCurrentPosition());
            encoderTikChange = newEncoderTix - currentEncoderTix;
            storedRuntime = runtime.seconds();

            encoderVelocity = ((encoderTikChange / encoderRevolutionTix)
                    * (wheelDiam * Math.PI)) / storedRuntime;

            telemetry.addData("Predicted Inches",
                    encoderVelocity * storedRuntime);

            telemetry.addData("Predicted Speed in in/s",
                    encoderVelocity);

            telemetry.addData("Predicted Acceleration in in/s",
                    encoderVelocity / storedRuntime);

            telemetry.addData("Precise+ Predicted Inches",
                    (encoderVelocity * storedRuntime) + (0.5 *
                            (encoderVelocity / storedRuntime) *
                            (storedRuntime * storedRuntime)));

            telemetry.update();
        }

        //telemetry.addData("Delta etx",
        //        encoderTikChange);

        /*
        telemetry.addData("Predicted Speed in in/s",
                encoderVelocity);

        telemetry.addData("Predicted Acceleration in in/s",
                encoderVelocity / storedRuntime);

        telemetry.addData("Precise+ Predicted Inches",
                (encoderVelocity * storedRuntime) + (0.5 *
                        (encoderVelocity / storedRuntime) *
                        (storedRuntime * storedRuntime)));
         */

            left_stick_y = gamepad1.left_stick_y;
            left_stick_x = gamepad1.left_stick_x;
            right_stick_x = gamepad1.right_stick_x;




        /*if (gamepad1.x != pastX) {
            pastX = gamepad1.x;
            if (gamepad1.x) {
                if (speedProp == 1) {
                    speedProp = 0.5;
                } else {
                    speedProp = 1;
                }
            }
        }*/



            /*//Foundation Moving Toggle
            //Toggle sets speed such that the robot can move the fastest
            //while moving the foundation and not dropping any blocks
            //Takes into account the mass of the foundation and block stack
            //and the friction of the floor


            numberStackedBlocks = outtake.getNumberOfBlocks();
            //  Mass of Whole Object
            mass = massFoundation + numberStackedBlocks * massStone;

            //  Max CFM velocity, calculated
            maxCFM_Velocity = fix * Math.sqrt((2 * tolerance * 9.81 * massStone * numberStackedBlocks * muBlocks)
                    / mass);

            //  CFM velocity to Angular Velocity
            CFM_AngularVelocity = maxCFM_Velocity / (DriveTrain.wheelDiam / 2);

            //  Power to set motors to follow CFM velocity.
            cfm_power = (-1) * (DriveTrain.stallTorque / DriveTrain.noLoadSpeed) * CFM_AngularVelocity
                    + DriveTrain.stallTorque * CFM_AngularVelocity;

            telemetry.addData("Number of Blocks : ", numberStackedBlocks);*/

        if (gamepad1.b) {
             firstBlank = Math.atan((0 - vectorPositionX) /
                     (20 - vectorPositionY));
             drive.turnPID(firstBlank, true, 0.6/90, 0.1/90,
                     0.03/90, 3);
             firstBlank = Math.sqrt(((20 - vectorPositionY) * (20 - vectorPositionY)) +
                     ((0 - vectorPositionX) * (0 - vectorPositionX)));
             //drive.encoderDrive(1, firstBlank, firstBlank, 5);
        }

        if(gamepad1.x)
        {
            time.reset();
            while(time.milliseconds() < 300){ }
            if(speedProp == 1)
            {
                speedProp = .5;
            }
            else {
                speedProp = 1;
            }
        }

        telemetry.addData("Speed", speedProp);
        telemetry.addData("Vertical", drive.getRadiaxVertical());
        telemetry.addData("Hypotenuse", drive.getRadiaxHypotenuse());
        //telemetry.addData("Radiaz Faux", drive.getNodalRadiax());
        telemetry.addData("Horizontal", drive.getRadiaxHorizontal());
        telemetry.addData("Radiax", drive.getRadiax());
        //telemetry.addData("Vector", drive.getVector());

        if(gamepad1.dpad_left)
        {
            drive.setStrafePower(-1);
        }
        else if(gamepad1.dpad_right)
        {
            drive.setStrafePower(1);
        }
        else if (Math.abs(left_stick_x) > 0.05 ||
                 Math.abs(left_stick_y) > 0.05 ||
                 Math.abs(right_stick_x) > 0.05) {

            drive.fl.setPower(speedProp * ((left_stick_y - left_stick_x) - right_stick_x));
            drive.fr.setPower(speedProp * ((left_stick_y + left_stick_x) + right_stick_x));
            drive.bl.setPower(speedProp * (left_stick_y + left_stick_x) - right_stick_x);
            drive.br.setPower(speedProp * (left_stick_y - left_stick_x) + right_stick_x);
        }
        else {
            drive.snowWhite();
        }

        /*if (gamepad1.left_trigger > 0.05f) {
            drive.fl.setPower(flTestPower++);
        }
        if (gamepad1.left_bumper) {
            drive.bl.setPower(blTestPower++);
        }
        if (gamepad1.right_trigger > 0.05f) {
            drive.fr.setPower(frTestPower++);
        }
        if (gamepad1.right_bumper) {
            drive.br.setPower(brTestPower++);
        }*/

        telemetry.addData("FL Power: ", flTestPower);

        telemetry.addData("BL Power: ", blTestPower);

        telemetry.addData("FR Power: ", frTestPower);

        telemetry.addData("BR Power: ", brTestPower);

        intake.Intake_TeleOp();

        outtake.outTake_TeleOp(this);

        newEncoderTix = (drive.br.getCurrentPosition() +
                drive.bl.getCurrentPosition() +
                drive.fl.getCurrentPosition() +
                drive.fr.getCurrentPosition());
        encoderTikChange = -(newEncoderTix - currentEncoderTix);
        storedRuntime = runtime.seconds();

        encoderVelocity = ((encoderTikChange / encoderRevolutionTix)
                * (wheelDiam * Math.PI)) / storedRuntime;

        telemetry.update();
    }

    @Override
    public void stop()
    {

    }
}
