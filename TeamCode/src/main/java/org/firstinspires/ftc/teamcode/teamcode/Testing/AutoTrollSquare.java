
package org.firstinspires.ftc.teamcode.teamcode.Testing;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Sensors;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.VisionWebcam;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.ZeroMap;

@Disabled
@Autonomous(name ="Troll Auto Square", group="Auto Basic")
public class AutoTrollSquare extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private double driveSpeed = 0.6;
    private boolean right = true;
    private boolean left = false;

    Intake intake = new Intake();
    Outtake outtake = new Outtake();
    Sensors sensors = new Sensors();
    VisionWebcam vuf = new VisionWebcam();


    @Override
    public void runOpMode() throws InterruptedException {

        DriveTrain drive = new DriveTrain();


        drive.initDriveTrain(this);
        sensors.initSensors(this);
        //outtake.initOuttakeAuto(this);
        //telemetry.addData("current", sensors.getGyroYaw());
        //vuf.initVision(this);
        //telemetry.update();

        waitForStart();


        drive.encoderDrive(this, 1, 24, 24, 5);

        sleep(1000);
//        drive.fl.setPower(-1);
//        drive.fr.setPower(1);
//        drive.bl.setPower(-1);
//        drive.br.setPower(1);

        telemetry.addLine("way before method");
        telemetry.update();

        //drive.turnGyro(0);

        telemetry.addLine("Moving on");
        telemetry.update();
        sleep(3000);
        drive.snowWhite();
        //telemetry.addData("vision", vuf.senseRed(this));
        //drive.gyroTurn(this, sensors, 90, true, 2800);
    }
}