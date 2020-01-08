package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Output;
import NewCode.LeoLibraries.LeoLibraries.ZeroMapBit;

@Autonomous(name = "Vexation+",group = "Autonomous")
public class Playground extends LinearOpMode {

    Drivetrain drive;
    Output out;

    @Override
    public void runOpMode() throws InterruptedException{

        drive = new Drivetrain(this);
        out = new Output(this);

        waitForStart();

        //drive.thread( this, out,-30);
        //drive.lift(out,false);
        //drive.basket(out,false);
        //drive.spline(1, 10, 90);
        //drive.spline(1, -30, -90);
        //drive.snowWhite();

    }
}
