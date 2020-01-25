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
                drive.encoderMove(.7, 5, 2);
                drive.gyroStrafe(.7, 8, false, 2);

                //captures stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops off stone
                drive.encoderMove(-.7, 6, 2);
                drive.gyroStrafe(.7, 54, true, 2);
                out.openBasketAuto(500);

                //gets stone
                drive.gyroStrafe(.7, 78, false, 2);
                out.raiseLiftAuto();
                drive.encoderMove(.7, 5, 2);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops stone + parks
                drive.gyroStrafe(.7, 80, true, 2);
                out.openBasketAuto(500);
                drive.gyroStrafe(.7, 12, false, 2);
                out.closeBasketAuto(1500);

            case ("left"):

                //Go to stone
                drive.encoderMove(.7, 5, 2);
                drive.gyroStrafe(.7, 8, true, 2);

                //captures stone
                drive.thread(this, out, -25);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops off stone
                drive.encoderMove(.7, 6, 2);
                drive.gyroStrafe(.7, 50, true, 2);
                out.openBasketAuto(500);

                //gets stone
                drive.gyroStrafe(.7, 74, false, 2);
                out.raiseLiftAuto();
                drive.encoderMove(-.7, 5, 2);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops stone + parks
                drive.gyroStrafe(.7, 80, true, 2);
                out.openBasketAuto(500);
                drive.gyroStrafe(.7, 12, false, 2);

            case("center"):

                //Gets stone
                drive.thread(this, out, -30);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops off stone
                drive.encoderMove(.7, 6, 2);
                drive.gyroStrafe(.7, 50, true, 5);
                out.openBasketAuto(500);

                //gets stone
                drive.gyroStrafe(.7, 74, false,5);
                out.raiseLiftAuto();
                drive.encoderMove(-.7, 5, 2);
                out.lowerLiftAuto();
                out.closeBasketAuto(1000);
                out.tighten();

                //drops stone + parks
                drive.gyroStrafe(.7, 80, true, 5);
                out.openBasketAuto(500);
                drive.gyroStrafe(.7, 12, false, 5);
        }
        drive.snowWhite();
    }
}
