package LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Intake;
import LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "RED LEFT",group = "Autonomous")
public class PlaygroundP2 extends LinearOpMode {

    DriveTrainGood drive;
    Output out;
    Intake in;


    @Override
    public void runOpMode() throws InterruptedException{

        drive = new DriveTrainGood(this);
        out = new Output(this);
        in = new Intake(this);


        waitForStart();


        //Gets stone
        //Red Left

        drive.encoderMove(-.5, 18, 3);
        drive.gyroStrafe(.7, 12, true, 5);
        drive.turnGyro(.4, 90, true, 4);
        drive.turnGyro(.4, 45, true, 3);
        //drive.gyroStrafe(.6, 5, true, 5);
        in.intake_on();
        drive.encoderMove(.3, 20, 5);
        sleep(250);
        in.intake_off();
        drive.encoderMove(-.7, 20, 5);
        drive.turnGyro(.4, 135, true, 4);
        drive.gyroTurnStraight(1000);
        //CHANGE
        drive.gyroStrafe(.7, 120, false, 7);
        out.pushBlock.setPosition(0);
        in.intake_reverse();
        sleep(500);
        //CHANGE
        drive.gyroStrafe(.7, 70, true, 5);
        drive.thread(this, out, -20);
        out.lowerLiftAuto();
        out.closeBasketAuto(500);
        out.hookDown();
        sleep(400);
        drive.encoderMove(.7, 10, 2);
        drive.gyroStrafe(1, 73, false, 5);
        out.hookUp();
        out.openBasketAuto(500);
        drive.gyroStrafe(.7, 16, true, 5);
        out.closeBasketAuto(1000);
        drive.encoderMove(-.5, 15, 3);


    }
}
