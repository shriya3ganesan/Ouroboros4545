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

        drive.encoderMove(1, 24, 5);
        drive.gyroStrafe(1, 24, true, 5, 0);
        drive.encoderMove(-1, 24, 5);
        drive.gyroStrafe(1, 24, false, 5, 0);

        drive.partyMode();

        drive.snowWhite();

    }
}
