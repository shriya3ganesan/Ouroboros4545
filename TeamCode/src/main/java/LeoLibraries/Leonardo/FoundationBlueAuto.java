package LeoLibraries.Leonardo;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Intake;
import LeoLibraries.LeoLibraries.Output;
import LeoLibraries.LeoLibraries.Sensors;

@Autonomous(name = "BLUE Foundation Auto",group = "Autonomous")
public class FoundationBlueAuto extends LinearOpMode {


    DriveTrainGood drive;
    Sensors sensors;
    Intake intake;
    Output out;

    @Override
    public void runOpMode() throws InterruptedException {

        sensors = new Sensors(this);
        drive = new DriveTrainGood(this);
        intake = new Intake(this);
        out = new Output(this);

        waitForStart();

        drive.encoderMove(-.5, 10, 3);
        drive.gyroStrafe(.7, 10, true, 3);
        drive.encoderMove(-.5, 25, 3);
        out.hookDown();
        sleep(2000);
        drive.encoderMove(.5, 50, 3);
        drive.turnGyro(.6, 90, false, 5);
        out.hookUp();
        drive.encoderMove(-.5, 5, 3);
        drive.encoderMove(.5, 40, 3);
        drive.gyroStrafe(.5, 10, true, 5);

        drive.snowWhite();
    }
}
