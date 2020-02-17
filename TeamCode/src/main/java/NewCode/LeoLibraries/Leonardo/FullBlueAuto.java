package NewCode.LeoLibraries.Leonardo;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.Output;

@Disabled

@Autonomous(name = "BLUE Full Auto",group = "Autonomous")
public class FullBlueAuto extends LinearOpMode {

    Drivetrain drive;
    Output out;

    double pos = -1;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new Drivetrain(this);
        out = new Output(this);

        waitForStart();
        drive.singleSkyStoneStackAuto(this, out, -1);
        //drive.doubleSkyStoneAuto(this, out, -1);
        drive.snowWhite();

    }
}
