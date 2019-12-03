
package org.firstinspires.ftc.teamcode.teamcode.Testing;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Sensors;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.VisionWebcam;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.ZeroMap;

@Autonomous(name ="Troll Auto Square", group="Auto Basic")
public class AutoTrollSquare extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private double driveSpeed = 0.6;
    private boolean right = true;
    private boolean left = false;

    DriveTrain drive = new DriveTrain();
    Intake intake = new Intake();
    Outtake outtake = new Outtake();
    Sensors sensors = new Sensors();
    VisionWebcam vuf = new VisionWebcam();

    @Override
    public void runOpMode() throws InterruptedException {

        drive.initDriveTrain(this);
        sensors.initSensors(this);
        //outtake.initOuttakeAuto(this);
        //telemetry.addData("current", sensors.getGyroYaw());
        telemetry.update();

        waitForStart();


        drive.gyroTurn(this, sensors, 90, true, 2800);
    }
}