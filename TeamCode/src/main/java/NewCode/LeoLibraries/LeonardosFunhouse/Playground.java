package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.Output;
import NewCode.LeoLibraries.LeoLibraries.ZeroMapBit;

@Autonomous(name = "BLUE LEFT",group = "Autonomous")
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
        drive.gyroStrafe(.7, 7, false, 3);
        drive.thread(this, out, -15);
        out.lowerLiftAuto();
        out.closeBasketAuto(1000);
        drive.encoderMove(.7, 6, 5);
        //out.tighten();
        out.hookDown();
        sleep(400);
        drive.gyroStrafe(.7 , 80, true, 7);
        out.hookUp();
        out.openBasketAuto(200);
        drive.encoderMove(.7, 2, 2);
        drive.gyroStrafe(.7, 15, false,5);
        out.closeBasketAuto(1000);
        drive.gyroStrafe(.7, 40, false, 6);
        drive.thread(this, out, -18);
        out.lowerLiftAuto();
        out.closeBasketAuto(1000);
        drive.encoderMove(.7, 10, 2);
        out.closeBasketAuto(200);
        out.hookDown();
        sleep(400);
        drive.gyroStrafe(1, 62, true, 5);
        out.hookUp();
        out.openBasketAuto(500);
        drive.gyroStrafe(.7, 16, false, 5);
        out.closeBasketAuto(500);
        drive.encoderMove(-.5, 10, 3);


    }
}
