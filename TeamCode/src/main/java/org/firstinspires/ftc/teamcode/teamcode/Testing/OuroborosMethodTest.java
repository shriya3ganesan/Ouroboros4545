package org.firstinspires.ftc.teamcode.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.NewAuto.CubicSpline;
import org.firstinspires.ftc.teamcode.teamcode.NewAuto.Motor_Power_Spline;
import org.firstinspires.ftc.teamcode.teamcode.NewAuto.Point;

import java.util.ArrayList;

@Disabled
@Autonomous(name="Ouroboros Method", group = "Testing")
public class OuroborosMethodTest extends LinearOpMode {

    DriveTrain drive = new DriveTrain();
    CubicSpline c = new CubicSpline();
    ElapsedTime time = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        drive.initDriveTrain(this);

        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(150, 50));
        points.add(new Point(300, 0));

        waitForStart();


        drive.snowWhite();

        //drive.splineMove(this, points, 5, 1);


    }
}
