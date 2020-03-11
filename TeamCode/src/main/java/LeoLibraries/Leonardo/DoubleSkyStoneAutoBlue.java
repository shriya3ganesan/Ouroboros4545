package LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Output;
import LeoLibraries.LeoLibraries.VisionWebcam;

@Autonomous(name = "BLUE Skystone Auto",group = "Autonomous")
public class DoubleSkyStoneAutoBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrainGood drive;
        Output out;
        //ZeroMapBit zero = new ZeroMapBit();
        VisionWebcam vision = new VisionWebcam(this);

        double pos = -1;


        drive = new DriveTrainGood(this);
        out = new Output(this);


        waitForStart();

        /*switch (vision.senseBlue(this)) {

            case ("right"):
                //Go to stone
                drive.encoderMove(-.7, 5, 2);
                drive.gyroStrafe(.7, 9, false, 2);

                //Gets stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();
                sleep(500);
                //drops off stone
                drive.encoderMove(.7, 6, 5);
                drive.gyroStrafe(1, 68, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(1, 10, false,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(1, 37, false,5);
                drive.thread(this, out, -20);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();
                sleep(500);

                //drops stone + parks
                drive.encoderMove(.7, 8, 3);
                drive.gyroStrafe(1, 15, true, 5);
                drive.encoderMove(-.7, 3, 2);
                drive.gyroStrafe(1, 36, true, 5);
                out.hookUp();
                out.openBasketAuto(500);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(1, 18, false, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 12, 2);

                break;
            case ("left"):

                drive.encoderMove(-.7, 5, 2);
                drive.gyroStrafe(.7, 8, true, 5);

                //Gets stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();
                out.hookDown();
                sleep(500);

                //drops off stone
                drive.encoderMove(.7, 6, 5);
                out.closeBasketAuto(500);
                drive.gyroStrafe(1, 52, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(1, 10, false,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(1, 72, false,5);
                drive.thread(this, out, -20);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();
                out.hookDown();
                sleep(500);

                //drops stone + parks
                drive.encoderMove(.7, 5, 3);
                drive.gyroStrafe(1, 40, true, 5);
                drive.encoderMove(-.7, 4, 2);
                drive.gyroStrafe(1, 47, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(1, 18, false, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 12, 2);

                break;
            case("center"):

                //Gets stone
                drive.thread(this, out, -30);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();
                sleep(500);

                //drops off stone
                drive.encoderMove(.7, 3, 5);
                drive.gyroStrafe(1, 60, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(1, 10, false,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(1, 76, false,5);
                drive.thread(this, out, -17);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();
                out.hookDown();
                sleep(500);

                //drops stone + parks
                drive.encoderMove(.7, 5, 3);
                drive.gyroStrafe(1, 54 , true, 5);
                drive.encoderMove(-.7, 3, 2);
                drive.gyroStrafe(1, 45, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(1, 18, false, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 12, 2);


                break;
        }
        drive.snowWhite();*/
    }
}
