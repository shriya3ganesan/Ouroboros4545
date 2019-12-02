package org.firstinspires.ftc.teamcode.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Sensors;

@Autonomous(name ="Red Foundation - 20p", group="Auto Basic")
public class RedFoundation extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private double driveSpeed = 0.6;

    DriveTrain drive = new DriveTrain();
    Sensors sensors = new Sensors();
    Outtake out = new Outtake();

    Boolean UP = false;
    Boolean DOWN = true;

    public void setHook (boolean position) {
        if (position) {
            out.hookRight.setPosition(1);
            out.hookLeft.setPosition(1);
        }
        else {
            out.hookRight.setPosition(0);
            out.hookLeft.setPosition(0);
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        sensors.initSensors(this);
        drive.initDriveTrain(this);
        out.initOuttakeAuto(this);

        waitForStart();

        //Drive backward at very beginning (assuming vector position
        // is borderline red depot)
        out.raiseLiftAuto(this);
        drive.encoderDrive(this, -1, -24, -24, 3);
        drive.encoderDrive(this, -0.6, -12, -12, 2);
        drive.encoderDrive(this, -0.2, -2, -2, 1);
        //Report:
        //1. Raises Lift - Check
        //2. Moves forward(?back) accumulated 38 inches in timeout 6s while decelerating
        //2a. Estimated Target Distance - 30~32

        //Captures the foundation and turns to place foundation
        setHook(DOWN);
        drive.encoderDrive(this, -1, 24, 24, 3);
        drive.encoderDrive(this, -0.2, 2, 2, 1);
        drive.gyroTurn(this, 90, false, 4000);
        drive.encoderDrive(this, -0.6, -6, -6, 2);
        setHook(UP);
        //Report:
        //1. Lowers hook for foundation abduction - Check
        //2. Moves back(?forward) accumulated 26 inches in timeout 4s.
        //2a. Predicted Position - 12 inches from start
        //3. Turns in position to plant foundation
        //4. Drives forward to lock in foundation 6 inches in 2s.
        //4a. Predicted Position - 12 inches forward, 6 inches to the left.
        //5. Moves hook back up - Check

        //Moves back to park in place
        drive.encoderDrive(this, -1, -40, -40, 5);
        sleep(200);
        out.lowerLiftAuto(this);
        drive.encoderDrive(this, -0.6, -10, -10, 4);
        drive.encoderDrive(this, -0.2, -4, -4, 2);
        //Report:
        //1. Moves back(?forward) accumulated 44 inches in timeout 11s while decelerating
        // AND interrupts by lowering lift.
        //1a. Predicted Position - 12 inches forward, 38 inches to the right
        //1b. Should be parked right on the middle

        //Commit what should've happened years ago
        drive.snowWhite();
        //Report:
        //1. Finishes the job
        //2. Overall Time - ~27s
    }
}
