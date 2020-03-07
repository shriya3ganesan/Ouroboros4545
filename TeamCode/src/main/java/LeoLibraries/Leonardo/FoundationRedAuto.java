package LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Intake;
import LeoLibraries.LeoLibraries.Output;
import LeoLibraries.LeoLibraries.Sensors;


@Autonomous(name = "RED Foundation Auto",group = "Autonomous")
public class FoundationRedAuto extends LinearOpMode {

    Intake intake;
    Output out;
    DriveTrainGood drive;
    Sensors sensors;

    @Override
    public void runOpMode() throws InterruptedException {

        sensors = new Sensors(this);
        out = new Output(this);
        drive = new DriveTrainGood(this);
        intake = new Intake(this);

        waitForStart();

        drive.encoderMove(-.5, 10, 3);
        drive.gyroStrafe(.7, 10, false, 3);
        drive.encoderMove(-.5, 25, 3);
        out.hookDown();
        sleep(2000);
        drive.encoderMove(.5, 50, 3);
        drive.turnGyro(.6, 90, true, 5);
        out.hookUp();
        drive.encoderMove(-.5, 5, 3);
        drive.encoderMove(.5, 40, 3);
        drive.gyroStrafe(.5, 10, false, 5);

        drive.snowWhite();
    }

}
