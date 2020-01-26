package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Output;
import NewCode.LeoLibraries.LeoLibraries.ZeroMapBit;

@Autonomous(name = "Playground",group = "Autonomous")
public class Playground extends LinearOpMode {

    DriveTrainGood drive;


    @Override
    public void runOpMode() throws InterruptedException{

        drive = new DriveTrainGood(this);


        waitForStart();

        drive.encoderMove(.5, 5, 2);
        drive.gyroStrafe(1, 100, true, 5);
        drive.encoderMove(-.5, 5, 2);
        drive.gyroStrafe(1, 100, false, 5);

       // drive.partyMode();

        drive.snowWhite();

    }
}
