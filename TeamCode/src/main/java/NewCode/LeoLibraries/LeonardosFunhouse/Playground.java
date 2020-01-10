package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Output;
import NewCode.LeoLibraries.LeoLibraries.VisionWebcam;
import NewCode.LeoLibraries.LeoLibraries.ZeroMapBit;

@Autonomous(name = "Vexation+",group = "Autonomous")
public class Playground extends LinearOpMode {

    Drivetrain drive;
    Output out;
    VisionWebcam vuf;
    @Override
    public void runOpMode() throws InterruptedException{

        drive = new Drivetrain(this);
        out = new Output(this);
        vuf = new VisionWebcam(this);

        waitForStart();

        telemetry.addData("Pos", vuf.senseBlue(this));
        telemetry.update();
    }
}
