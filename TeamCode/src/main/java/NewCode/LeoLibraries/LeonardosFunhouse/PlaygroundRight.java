package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.Output;


@Autonomous(name = "Playground 2",group = "Autonomous")
public class PlaygroundRight extends LinearOpMode {

    DriveTrainGood drive;
    Output out;

    @Override
    public void runOpMode() throws InterruptedException{

        drive = new DriveTrainGood(this);
        out = new Output(this);

        waitForStart();


        out.hookUp();

        sleep(1000);
        out.hookDown();
        sleep(2000);

    }
}
