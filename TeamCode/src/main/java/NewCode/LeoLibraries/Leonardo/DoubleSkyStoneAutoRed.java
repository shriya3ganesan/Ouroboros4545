package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.VisionWebcam;

import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "RED SkyStone Auto",group = "Autonomous")
public class DoubleSkyStoneAutoRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrainGood drive;
        Output out;
        VisionWebcam vision = new VisionWebcam(this);
        //ZeroMapBit zero = new ZeroMapBit();

        double pos = 1;


        drive = new DriveTrainGood(this);
        out = new Output(this);


        out.hookUp();

        waitForStart();


        switch (vision.senseRed(this)) {

            case ("left"):

                //Go to stone
                drive.encoderMove(.7, -5, 2);
                drive.gyroStrafe(1, 8, true, 2);

                //captures stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops off stone
                drive.encoderMove(.7, 6, 2);
                drive.gyroStrafe(.7, 54, false, 2);
                out.openBasketAuto(500);

                //gets stone
                drive.gyroStrafe(.7, 78, true, 2);
                out.raiseLiftAuto();
                drive.encoderMove(-.7, 5, 2);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();
                drive.encoderMove(.7, 5, 2);

                //drops stone + parks
                drive.gyroStrafe(.7, 80, false, 2);
                out.openBasketAuto(500);
                drive.gyroStrafe(.7, 12, true, 2);
                out.closeBasketAuto(1500);
                break;

            case ("right"):

                drive.encoderMove(-.7, 5, 2);
                drive.gyroStrafe(.7, 8, false, 5);

                //captures stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops off stone
                drive.encoderMove(.7, 6, 2);
                drive.gyroStrafe(1, 52, false, 5);
                out.openBasketAuto(1000);

                //gets stone
                drive.gyroStrafe(1, 76, true, 5);
                out.raiseLiftAuto();
                drive.encoderMove(.7, 5, 2);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops stone + parks
                drive.gyroStrafe(1, 80, false, 5);
                out.openBasketAuto(1000);
                drive.gyroStrafe(1, 12, true, 5);
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
                drive.gyroStrafe(1, 60, false, 5);
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
        }
            drive.snowWhite();
    }
}
