package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.DriveTrainGood;
import NewCode.LeoLibraries.LeoLibraries.VisionWebcam;

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
