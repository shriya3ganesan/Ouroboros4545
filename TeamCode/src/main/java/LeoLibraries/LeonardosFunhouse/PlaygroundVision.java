package LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.VisionWebcam;

@Autonomous(name = "Playground Vision",group = "Autonomous")
public class PlaygroundVision extends LinearOpMode {

    VisionWebcam vision;


    @Override
    public void runOpMode() throws InterruptedException{

        vision = new VisionWebcam(this);

        waitForStart();

        vision.senseRed(this);
    }
}
