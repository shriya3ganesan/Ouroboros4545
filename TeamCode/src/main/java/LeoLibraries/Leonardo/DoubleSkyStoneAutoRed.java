package LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Intake;
import LeoLibraries.LeoLibraries.Output;
import LeoLibraries.LeoLibraries.VisionWebcam;

@Autonomous(name = "RED SkyStone Auto",group = "Autonomous")
public class DoubleSkyStoneAutoRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrainGood drive;
        Output out;
        Intake in;
        VisionWebcam vision = new VisionWebcam(this);
        //ZeroMapBit zero = new ZeroMapBit();

        double pos = 1;


        drive = new DriveTrainGood(this);
        out = new Output(this);
        in = new Intake(this);


        out.hookUp();

        waitForStart();


        switch (vision.senseRed(this)) {

            case ("left"):

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



                break;
            case ("right"):

                drive.encoderMove(-.6, 11, 5);
                drive.gyroStrafe(.7, 7, true, 3);
                drive.thread(this, out, -15);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                drive.encoderMove(.7, 6, 5);
                //out.tighten();
                out.hookDown();
                sleep(400);
                drive.gyroStrafe(.7 , 80, false, 7);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);
                drive.gyroStrafe(.7, 15, true,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(.7, 40, true, 6);
                drive.thread(this, out, -18);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                drive.encoderMove(.7, 10, 2);
                out.closeBasketAuto(200);
                out.hookDown();
                sleep(400);
                drive.gyroStrafe(1, 62, false, 5);
                out.hookUp();
                out.openBasketAuto(500);
                drive.gyroStrafe(.7, 16, true, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.5, 10, 3);

                break;
            case("center"):

                //Gets stone
                drive.encoderMove(-.6, 11, 5);
                drive.gyroStrafe(.7, 27, true, 3);
                drive.thread(this, out, -16);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                drive.encoderMove(.7, 6, 5);
                //out.tighten();
                out.hookDown();
                sleep(400);
                drive.gyroStrafe(1, 115, false, 7);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);
                drive.gyroStrafe(.7, 23, true,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(.7, 50, true, 6);
                drive.thread(this, out, -20);
                out.lowerLiftAuto();
                out.closeBasketAuto(500);
                out.hookDown();
                sleep(400);
                drive.encoderMove(.7, 10, 2);
                drive.gyroStrafe(1, 65, false, 5);
                out.hookUp();
                out.openBasketAuto(500);
                drive.gyroStrafe(.7, 16, true, 5);
                out.closeBasketAuto(1000);
                drive.encoderMove(-.5, 15, 3);


                break;
        }
            drive.snowWhite();
    }
}
