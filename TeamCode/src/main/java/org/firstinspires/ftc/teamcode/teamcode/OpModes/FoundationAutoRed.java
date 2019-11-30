package org.firstinspires.ftc.teamcode.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Sensors;

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
        drive.encoderDrive(this, -1, -72, -72, 5);
        drive.strafeMove(this, 24, 5, -1); //hopefully right
        drive.encoderDrive(this, 1, 25, 25, 5);
        out.raiseLiftAuto(this);
        drive.strafeMove(this, 24, 5, -1); //hopefully right
        drive.encoderDrive(this, -.5, -10, -10, 5);
        out.hookLeft.setPosition(1);
        out.hookRight.setPosition(1);
        drive.encoderDrive(this, .25, 48, 48, 5);
        out.hookLeft.setPosition(0);
        out.hookRight.setPosition(0);
        drive.strafeMove(this, 100, 5, 1); //hopefully left

        drive.snowWhite();
    }
}
