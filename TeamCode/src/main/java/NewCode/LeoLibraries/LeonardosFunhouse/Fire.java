package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.Output;

@Autonomous(group="Autonomous", name="Fire")
public class Fire extends LinearOpMode {
    DriveTrainGood drive;
    Output out;
    Intake in;
    @Override
    public void runOpMode() throws InterruptedException {

        drive = new DriveTrainGood(this);
        out = new Output(this);
        in = new Intake(this);

        waitForStart();

        //Gets stone
        drive.encoderMove(-.6, 11, 5);
        drive.gyroStrafe(.7, 25, true, 3);
        drive.thread(this, out, -16);
        out.lowerLiftAuto();
        out.closeBasketAuto(1000);
        drive.encoderMove(.7, 6, 5);
        //out.tighten();
        out.hookDown();
        sleep(600);
        drive.gyroStrafe(1, 100, false, 7);
        out.hookUp();
        out.openBasketAuto(200);
        drive.encoderMove(.7, 2, 2);
        drive.gyroStrafe(.7, 23, true,5);
        out.closeBasketAuto(1000);
        drive.gyroStrafe(.7, 43, true, 6);
        drive.thread(this, out, -20);
        out.lowerLiftAuto();
        out.closeBasketAuto(1400);
        out.hookDown();
        sleep(600);
        drive.encoderMove(.6, 10, 2);
        drive.gyroStrafe(1, 65, false, 5);
        out.hookUp();
        out.openBasketAuto(500);
        drive.gyroStrafe(.7, 16, true, 5);
        out.closeBasketAuto(1000);
        drive.encoderMove(-.5, 15, 3);
        drive.snowWhite();


    }
}
