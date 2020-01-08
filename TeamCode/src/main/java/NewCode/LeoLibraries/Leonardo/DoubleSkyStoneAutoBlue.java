package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.VisionWebcam;

import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "BLUE Skystone Auto",group = "Autonomous")
public class DoubleSkyStoneAutoBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drive;
        Output out;
        //ZeroMapBit zero = new ZeroMapBit();
        VisionWebcam vision = new VisionWebcam(this);

        double pos = -1;


        drive = new Drivetrain(this);
        out = new Output(this);

        String ob = vision.senseBlue(this);

        double offset = 0;


        if (ob.equals("left"))
        {
            offset = 8;
            telemetry.addLine("left");
        }
        else if(ob.equals("right"))
        {
            offset = -8;
            telemetry.addLine("right");
        }
        else {
            offset = 0;
            telemetry.addLine("center");
        }
        telemetry.update();

        waitForStart();

        drive.doubleSkyStoneAuto(this, out, pos, offset);
        drive.snowWhite();
    }
}
