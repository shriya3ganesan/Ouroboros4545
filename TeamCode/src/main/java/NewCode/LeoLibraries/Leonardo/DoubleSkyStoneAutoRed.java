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


        out.hookDown();

        waitForStart();


        switch (vision.senseRed(this)) {

            case ("left"):

                //Go to stone
                drive.encoderMove(.7, 5, 2);
                drive.strafeMove(8, .7, 2);

                //captures stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops off stone
                drive.encoderMove(-.7, 5, 2);
                drive.strafeMove(54, -.7, 2);
                out.openBasketAuto(500);

                //gets stone
                drive.strafeMove(78, .7, 2);
                out.raiseLiftAuto();
                drive.encoderMove(.7, 5, 2);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops stone + parks
                drive.strafeMove(80, -.7, 2);
                out.openBasketAuto(500);
                drive.strafeMove(12, .7, 2);
                out.closeBasketAuto(1500);

                break;

            case ("right"):

                //Go to stone
                drive.encoderMove(-.7, 5, 2);
                drive.strafeMove(8, .7, 2);

                //captures stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops off stone
                drive.encoderMove(-.7, 5, 2);
                drive.strafeMove(50, -.7, 2);
                out.openBasketAuto(500);

                //gets stone
                drive.strafeMove(74, .7, 2);
                out.raiseLiftAuto();
                drive.encoderMove(.7, 5, 2);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops stone + parks
                drive.strafeMove(80, -.7, 2);
                out.openBasketAuto(500);
                drive.strafeMove(12, .7, 2);

                break;
            case("center"):

                //Gets stone
                drive.thread(this, out, -30);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops off stone
                drive.encoderMove(-.7, 5, 2);
                drive.strafeMove(50, -.7, 2);
                out.openBasketAuto(500);

                //gets stone
                drive.strafeMove(74, .7, 2);
                out.raiseLiftAuto();
                drive.encoderMove(.7, 5, 2);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops stone + parks
                drive.strafeMove(80, -.7, 2);
                out.openBasketAuto(500);
                drive.strafeMove(12, .7, 2);
                break;
        }
            drive.snowWhite();
    }
}
