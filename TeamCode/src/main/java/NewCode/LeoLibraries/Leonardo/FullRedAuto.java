package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "RED Full Auto",group = "Autonomous")
public class FullRedAuto extends LinearOpMode {

    Drivetrain drive;
    Output out;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new Drivetrain(this);
        out = new Output(this);

        waitForStart();
        drive.singleSkyStoneStackAuto(this, out, 1);
        //drive.doubleSkyStoneAuto(this, out, 1);
        drive.snowWhite();

    }
}
