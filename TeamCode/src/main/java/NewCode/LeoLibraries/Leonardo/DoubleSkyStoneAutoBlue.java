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

        switch (vision.senseRed(this)) {

            case ("right"):
                //Go to stone
                drive.encoderMove(-.7, 5, 2);
                drive.gyroStrafe(1, 8, false, 2);

                //Gets stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();

                //drops off stone
                drive.encoderMove(.7, 6, 5);
                drive.gyroStrafe(1, 68, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(1, 10, true,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(1, 82, true,5);
                out.raiseLiftAuto();
                drive.encoderMove(-.7, 20, 3);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();

                //drops stone + parks
                drive.encoderMove(.7, 5, 3);
                drive.gyroStrafe(1, 95, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(1, 15, true, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 8, 2);

                break;
            case ("left"):

                drive.encoderMove(-.7, 5, 2);
                drive.gyroStrafe(.7, 8, true, 5);

                //Gets stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();

                //drops off stone
                drive.encoderMove(.7, 6, 5);
                drive.gyroStrafe(1, 52, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(1, 10, true,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(1, 74, true,5);
                out.raiseLiftAuto();
                drive.encoderMove(-.7, 20, 3);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();

                //drops stone + parks
                drive.encoderMove(.7, 5, 3);
                drive.gyroStrafe(1, 87, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(1, 15, true, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 8, 2);

                break;
            case("center"):

                //Gets stone
                drive.thread(this, out, -30);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();

                //drops off stone
                drive.encoderMove(.7, 6, 5);
                drive.gyroStrafe(1, 60, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(1, 10, false,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(1, 82, false,5);
                out.raiseLiftAuto();
                drive.encoderMove(-.7, 20, 3);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();

                //drops stone + parks
                drive.encoderMove(.7, 5, 3);
                drive.gyroStrafe(1, 95, true, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(1, 15, false, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 8, 2);


                break;
        }
        drive.snowWhite();
    }
}