package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.Output;
import NewCode.LeoLibraries.LeoLibraries.ZeroMapBit;

@Autonomous(name = "Playground Houyong",group = "Autonomous")
public class Playground extends LinearOpMode {

    DriveTrainGood drive;
    Output out;
    Intake in;


    @Override
    public void runOpMode() throws InterruptedException{

        drive = new DriveTrainGood(this);
        out = new Output(this);
        in = new Intake(this);

        waitForStart();
        //For left side blue


        drive.encoderMove(-.6, 11, 5);
        drive.gyroStrafe(.7, 16, true, 3);
        drive.thread(this, out, -16);
        out.lowerLiftAuto();
        out.closeBasketAuto(1000);
        drive.encoderMove(.7, 6, 5);
        //out.tighten();
        out.hookDown();
        sleep(400);
        drive.gyroStrafe(.7 , 96, false, 7);
        out.hookUp();
        out.openBasketAuto(200);
        drive.encoderMove(.7, 2, 2);
        drive.gyroStrafe(.7, 15, true,5);
        out.closeBasketAuto(1000);
        drive.gyroStrafe(.7, 45, true, 6);
        drive.thread(this, out, -20);
        out.lowerLiftAuto();
        out.closeBasketAuto(1000);
        drive.encoderMove(.7, 10, 2);
        out.hookDown();
        sleep(400);
        drive.gyroStrafe(1, 60, false, 5);
        out.hookUp();
        out.openBasketAuto(500);
        drive.gyroStrafe(.7, 16, true, 5);
        out.closeBasketAuto(500);
        drive.encoderMove(-.5, 15, 3);
        drive.snowWhite();


    }
}
