package org.firstinspires.ftc.teamcode.teamcode.OpModes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Sensors;
import org.firstinspires.ftc.robotcontroller.external.samples.ZeroMapTheta;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.ZeroMap;

@Autonomous(name ="Playground v.3.6.11", group="Auto Basic")
public class Playground extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private double driveSpeed = 0.6;

    DriveTrain drive = new DriveTrain();
    Intake intake = new Intake();
    //Outtake outtake = new Outtake();
    ZeroMapTheta zero = new ZeroMapTheta();

    Sensors sensors = new Sensors();

    @Override
    public void runOpMode() {

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

        //outtake.initOuttake(this);
        sensors.initSensors(this);
        runtime.reset();


        waitForStart();
        //drive.thread(this, outtake, 30);
        drive.turn(0.6, true);
        sleep(3000);
        drive.encoderDrive(this, 1, -30,
                -30, 3);
        telemetry.addData("Completed: ", "Drive backward 30 Inches");
        telemetry.update();
        drive.turn(1, true);

        sleep(3000);
        drive.snowWhite();
        /*
        sleep(2000);
        drive.thread(this, outtake, 30);
        telemetry.addData("Completed:", "Threaded");
        telemetry.update();

        outtake.lowerLiftAuto(this);
        telemetry.addData("Completed:", "Lift Lowered");
        telemetry.update();

        outtake.closeBasketAuto();
        telemetry.addData("Completed:", "Basket Closed");
        telemetry.update();

        drive.reverse();
        drive.vectorDrive(this, 1, -12, sensors);
        telemetry.addData("Completed:", "Moved Backward");
        telemetry.update();

        drive.vectorTurn(this, sensors, 90);
        telemetry.addData("Completed:", "Turned");
        telemetry.update();
        sleep(2000);
         */

        //drive.vectorDrive(this, 1, 24, sensors);
        //drive.vectorStrafe(this, sensors, 24, 1);
        //drive.vectorDrive(this, 1, -24, sensors);
        //drive.vectorStrafe(this, sensors, -24, -1);
        //drive.vectorStrafe(this, sensors, 24,1);

        //Xenon Autonomous

        /*



         */

        //Placed facing neutral bridge red side



        /*
        drive.vectorStrafe(this, sensors, -80, -1);
        outtake.raiseLiftAuto(this);
        drive.gyroTurn(this, sensors, 180, true, 3000);
        drive.encoderDrive(this, 1, -12, -12, 3);
        drive.setHookDown(outtake);
        outtake.openBasketAuto();
        outtake.refractLiftAuto(this);
        drive.encoderDrive(this, 1, 48, 48, 5);
        drive.gyroTurn(this, sensors, 90, true, 3000);
        drive.encoderDrive(this, 1, -12, -12, 3);
        drive.setHookUp(outtake);
        drive.encoderDrive(this, 1, 20, 20, 2);
        outtake.raiseLiftAuto(this);
        drive.encoderDrive(this, 1, 40, 40, 4);
        /*
         */

        drive.snowWhite();

    }

}

