package LeoLibraries.Leonardo;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Output;
import LeoLibraries.LeoLibraries.Sensors;
import LeoLibraries.LeoLibraries.VisionWebcam;

@Disabled

@Autonomous(name = "BLUE Full Auto",group = "Autonomous")
public class FullBlueAuto extends LinearOpMode {

    DriveTrainGood drive;
    Output out;
    Sensors sensors;
    VisionWebcam vision;

    double pos = -1;
    double offset;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new DriveTrainGood(this);
        out = new Output(this);
        sensors = new Sensors(this);
        vision = new VisionWebcam(this);

        out.armUp(false);
        out.elbowUp(false);

        sleep(500);
        out.armUp(true);
        out.elbowUp(true);

        waitForStart();

        //Sense Stone
        if (vision.senseBlue(this) == "left") {
            offset = -8;
        }
        else if (vision.senseBlue(this) == "right") {
            offset = 8;
        }
        else {
            offset = 0;
        }

        //Align with block
        drive.encoderMove(-.7, 15, 4);
        drive.turnGyro(.7, 90, false, 3);
        drive.encoderMove(1, 24 + offset, 4);
        drive.strafeMove(5, 1, 4);

        //grab stone
        out.armDown(false);
        sleep(300);
        out.elbowDown(false);
        sleep(300);
        out.armUp(false);

        //Drop off at foundation
        drive.encoderMove(-1, 100, 6);
        out.armDown(false);
        sleep(300);
        out.elbowUp(false);
        sleep(300);

        //Gets second stone
        drive.encoderMove(1, 80, 6);
        out.armDown(false);
        sleep(300);
        out.elbowDown(false);

        //Drop off second stone
        drive.encoderMove(-1, 80, 6);
        out.armDown(false);
        sleep(300);
        out.elbowUp(false);
        sleep(300);

        //turn so hooks are facing foundation
        drive.turnGyro(.7, 90, true, 3);
        drive.encoderMove(-.5, 5, 3);

        //hook and drag
        out.hookDown();
        sleep(500);
        drive.encoderMove(.5, 50, 3);
        drive.turnGyro(.6, 90, false, 5);
        out.hookUp();
        drive.encoderMove(-.5, 5, 3);

        //park
        drive.encoderMove(.5, 40, 3);


        drive.snowWhite();

    }
}
