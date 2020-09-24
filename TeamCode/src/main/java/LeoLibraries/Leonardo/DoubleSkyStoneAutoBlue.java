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
                drive.gyroStrafe(.7, 8, false, 3);

                drive.thread(this, out, -28);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();
                out.hookDown();
                sleep(500);

                //drops off stone
                drive.encoderMove(.7, 10, 5);
                out.closeBasketAuto(500);
                drive.gyroStrafe(1, 54, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(.8, 15, false,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(.8, 22, false,6);

                drive.gyroTurnStraight(500);
                drive.thread(this, out, -27);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                sleep(500);

                //drops stone + parks
                drive.encoderMove(.7, 10, 3);
                out.hookDown();
                drive.gyroStrafe(1, 36, true, 7);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(.7, 10, false, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 10, 2);


                break;
            case ("left"):

                drive.encoderMove(-.7, 5, 2);
                drive.gyroStrafe(.7, 8, true, 5);

                drive.thread(this, out, -27);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();


                //drops off stone
                drive.encoderMove(.7, 9, 5);
                out.hookDown();
                sleep(500);
                out.closeBasketAuto(500);
                drive.gyroStrafe(1, 38, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(.8, 15, false,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(.8, 47, false,6);

                drive.gyroTurnStraight(500);
                drive.thread(this, out, -27);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();


                //drops stone + parks
                drive.encoderMove(.7, 15, 3);
                out.hookDown();
                sleep(500);
                drive.gyroStrafe(1, 36, true, 7);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(.7, 10, false, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 10, 2);


                break;
            case("center"):

                //Gets stone
                drive.thread(this, out, -32);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();


                //drops off stone
                drive.encoderMove(.7, 9, 5);
                out.hookDown();
                sleep(500);
                out.closeBasketAuto(500);
                drive.gyroStrafe(1, 46, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(.8, 15, false,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(.8, 55, false,6);

                drive.gyroTurnStraight(500);
                drive.thread(this, out, -27);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();


                //drops stone + parks
                drive.encoderMove(.7, 10, 3);
                out.hookDown();
                sleep(500);
                drive.gyroStrafe(1, 74, true, 7);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(.7, 10, false, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 10, 2);


                break;
        }
        drive.snowWhite();*/
    }
}
