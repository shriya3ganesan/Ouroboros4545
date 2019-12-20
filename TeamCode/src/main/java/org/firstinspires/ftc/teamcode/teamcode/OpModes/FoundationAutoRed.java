package org.firstinspires.ftc.teamcode.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Sensors;

@Disabled
@Autonomous(name ="AML Red Green Foundation Move", group="Auto Basic")
public class FoundationAutoRed extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private double driveSpeed = 0.6;

    DriveTrain drive = new DriveTrain();
    Sensors sensors = new Sensors();
    Intake intake = new Intake();
    Outtake out = new Outtake();

    @Override
    public void runOpMode() throws InterruptedException {

        //sensors.initSensors(this);
        drive.initDriveTrain(this);
        out.initOuttakeAuto(this);

        waitForStart();

        // sleep(5000);
        out.raiseLiftAuto(this);
        drive.encoderDrive(this, .4, -10/1.8, -10/1.8, 4);
        drive.strafeMove(this, 20/1.8, 5, .6);
        drive.encoderDrive(this, .4, -54/1.8, -54/1.8, 4);
        sleep(500);
        out.hookLeft.setPosition(1);
        out.hookRight.setPosition(1);
        sleep(2000);
        drive.encoderDrive(this, .4, 65/1.8, 65/1.8, 10);
        //drive.strafeMove(this, 40, 5, -.7);
        out.hookLeft.setPosition(0);
        out.hookRight.setPosition(0);
        sleep(500);
        drive.strafeMove(this, 70/1.8, 5, -.7);
        out.lowerLiftAuto(this);
        drive.encoderDrive(this, -.4, -10, -10, 3);
        drive.strafeMove(this, 10, 3, .7);
        drive.encoderDrive(this, .4, 10, 10, 3);
        drive.strafeMove(this, 60/1.8 , 5, -.7);

        drive.snowWhite();
    }
}
