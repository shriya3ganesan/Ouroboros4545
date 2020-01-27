package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "Vexation Right",group = "Autonomous")
public class PlaygroundRight extends LinearOpMode {

    DriveTrainGood drive;
    Output out;

    @Override
    public void runOpMode() throws InterruptedException{

        drive = new DriveTrainGood(this);
        out = new Output(this);

        waitForStart();

      //  drive.strafeMoveNormal( 50, 2000, -1);

        //drive.thread( this, out,-30);
        //drive.lift(out,false);
        //drive.basket(out,false);
        //drive.spline(1, 10, 90);
        //drive.spline(1, -30, -90);
        //drive.snowWhite();

    }
}
