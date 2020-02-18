package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.VisionWebcam;

import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Output;

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

                //Go to stone
                drive.encoderMove(-.7, 5, 2);
                drive.gyroStrafe(.7, 9, true, 3);

                //Gets stone
                drive.thread(this, out, -27);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();
                sleep(500);

                //drops off stone
                drive.encoderMove(.7, 9, 5);
                drive.gyroStrafe(1, 68, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(1, 10, true,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(1, 35, true,5);
                drive.thread(this, out, -20);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();
                sleep(500);
                //drops stone + parks
                drive.encoderMove(.7, 8, 3);
                drive.gyroStrafe(1, 50, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(1, 15, true, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 12, 2);
                drive.snowWhite();

                break;
            case ("right"):

                drive.encoderMove(-.7, 5, 2);
                drive.gyroStrafe(.7, 8, false, 5);

                //Gets stone
                drive.thread(this, out, -27);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();
                sleep(500);

                //drops off stone
                drive.encoderMove(.7, 9, 5);
                drive.gyroStrafe(1, 52, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(1, 10, true,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(1, 74, true,5);
                drive.thread(this, out, -20);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();
                sleep(500);

                //drops stone + parks
                drive.encoderMove(.7, 8, 3);
                drive.gyroStrafe(1, 87, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(1, 15, true, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 12, 2);

                break;
            case("center"):

                //Gets stone
                drive.thread(this, out, -32);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();
                sleep(500);

                //drops off stone
                drive.encoderMove(.7, 9, 5);
                out.closeBasketAuto(500);
                drive.gyroStrafe(1, 60, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                //gets stone
                drive.gyroStrafe(1, 10, true,5);
                out.closeBasketAuto(1000);
                drive.gyroStrafe(1, 78, true,5);
                drive.thread(this, out, -20);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                //out.tighten();
                out.hookDown();
                sleep(500);

                //drops stone + parks
                drive.encoderMove(.7, 8, 3);
                drive.gyroStrafe(1, 91, false, 5);
                out.hookUp();
                out.openBasketAuto(200);
                drive.encoderMove(.7, 2, 2);

                drive.gyroStrafe(1, 15, true, 5);
                out.closeBasketAuto(500);
                drive.encoderMove(-.7, 12, 2);


                break;
        }
        drive.snowWhite();
    }
}
