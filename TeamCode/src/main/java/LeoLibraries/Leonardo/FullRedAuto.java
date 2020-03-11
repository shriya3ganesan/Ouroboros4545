package LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Output;
import LeoLibraries.LeoLibraries.Sensors;
import LeoLibraries.LeoLibraries.VisionWebcam;


@Autonomous(name = "RED Full Auto",group = "Autonomous")
public class FullRedAuto extends LinearOpMode {

    DriveTrainGood drive;
    Output out;
    VisionWebcam vision;
    Sensors sensors;


    double offset;
    @Override
    public void runOpMode() throws InterruptedException {

        drive = new DriveTrainGood(this);
        out = new Output(this);
        //vision = new VisionWebcam(this);
        sensors = new Sensors(this);

        out.armUp(false);
        out.elbowDown(false);

        sleep(500);
        out.leftHookArm.setPosition(0);
        out.elbowDown(true);

        waitForStart();
        //Sense Stone
        /*if (vision.senseRed(this) == "left") {
            offset = -8;
        }
        else if (vision.senseRed(this) == "right") {
            offset = 8;
        }
        else {
            offset = 0;
        }*/

        offset = 0;

        //Align with block
        drive.encoderMove(-.5, 15, 4);
        drive.snowWhite();
        drive.turnPID(90, true, .9/90, .0125, .02/90, 1);
        drive.encoderMove(.5, 15 + offset, 4);

        //grab stone
        out.armDown(false);
        out.elbowUp(false);
        sleep(500);
        drive.gyroStrafe(.5, 7, true, 3);
        out.elbowDown(false);
        sleep(500);
        out.armUp(false);

        //Drop off at foundation
        drive.gyroStrafe(.7, 10, false, 3);
        drive.encoderMove(-.5, 100, 6);
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
        drive.turnGyro(.7, 90, false, 3);
        drive.encoderMove(-.5, 5, 3);

        //hook and drag
        out.hookDown();
        sleep(500);
        drive.encoderMove(.5, 50, 3);
        drive.turnGyro(.6, 90, true, 5);
        out.hookUp();
        drive.encoderMove(-.5, 5, 3);

        //park
        drive.encoderMove(.5, 40, 3);

        drive.snowWhite();

    }
}
