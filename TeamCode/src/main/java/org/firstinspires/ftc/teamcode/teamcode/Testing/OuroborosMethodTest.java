package org.firstinspires.ftc.teamcode.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Judging.CubicSpline;
import org.firstinspires.ftc.teamcode.teamcode.Judging.Motor_Power_Spline;

@Autonomous(name="Ouroboros Method", group = "Testing")
public class OuroborosMethodTest extends LinearOpMode {

    DriveTrain drive = new DriveTrain();
    CubicSpline c = new CubicSpline();
    ElapsedTime time = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        drive.initDriveTrain(this);
        c.main();
        waitForStart();
        for(Motor_Power_Spline m : c.getMotorPowerList())
        {
            drive.rightTank(m.getRightPower());
            drive.leftTank(m.getLeftPower());

            sleep(10);
        }

        drive.snowWhite();




    }
}
