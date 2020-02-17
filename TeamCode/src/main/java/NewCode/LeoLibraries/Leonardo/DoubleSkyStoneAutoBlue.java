package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.VisionWebcam;

import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Output;

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

        switch (vision.senseBlue(this)) {

            case ("right"):
                //Go to stone
                drive.encoderMove(-.7, 5, 2);
                drive.gyroStrafe(.7, 9, false, 2);

                //Gets stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();
                out.hookDown();

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
                out.tighten();

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
                out.closeBasketAuto(500);
                out.tighten();
                out.hookDown();

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

                drive.encoderMove(-.6, 11, 5);
                drive.gyroStrafe(.7, 27, false, 3);
                drive.thread(this, out, -16);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                drive.encoderMove(.7, 6, 5);
                //out.tighten();
                out.hookDown();
                sleep(400);
                drive.gyroStrafe(1, 115, true, 7);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);
                drive.gyroStrafe(.7, 23, false,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(.7, 50, false, 6);
                drive.thread(this, out, -20);
                out.lowerLiftAuto();
                out.closeBasketAuto(500);
                out.hookDown();
                sleep(400);
                drive.encoderMove(.7, 10, 2);
                drive.gyroStrafe(1, 65, true, 5);
                out.hookUp();
                out.openBasketAuto(500);
                drive.gyroStrafe(.7, 16, false, 5);
                out.closeBasketAuto(1000);
                drive.encoderMove(-.5, 15, 3);


                break;
        }
        drive.snowWhite();
    }
}
