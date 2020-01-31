package NewCode.LeoLibraries.Judging;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;

public class OuroborosMethodAutoExample extends LinearOpMode {

    DriveTrainGood drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new DriveTrainGood(this);
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0, 0));
        points.add(new Point(0, 10, 10));
        points.add(new Point(0, 20, 0));



        waitForStart();

        drive.splineMove(this, points, 5, 1);



    }
}
