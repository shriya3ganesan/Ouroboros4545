package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.VisionWebcam;

import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "RED SkyStone Auto",group = "Autonomous")
public class DoubleSkyStoneAutoRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drive;
        Output out;
        VisionWebcam vision = new VisionWebcam(this);
        //ZeroMapBit zero = new ZeroMapBit();

        double pos = 1;


            drive = new Drivetrain(this);
            out = new Output(this);




            waitForStart();

        String ob = vision.senseRed(this);

        double offset = 0;


        if (ob.equals("left"))
        {
            offset = -4;
            telemetry.addLine("left");
        }
        else if(ob.equals("right"))
        {
            offset = 4;
            telemetry.addLine("right");
        }
        else {
            offset = 0;
            telemetry.addLine("center");
        }
        telemetry.update();

        drive.doubleSkyStoneAuto(this, out, pos, offset);
            drive.snowWhite();
    }
}
