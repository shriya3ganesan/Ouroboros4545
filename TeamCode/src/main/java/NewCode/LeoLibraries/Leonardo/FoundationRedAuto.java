package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.Output;
import NewCode.LeoLibraries.LeoLibraries.Sensors;


@Autonomous(name = "RED Foundation Auto",group = "Autonomous")
public class FoundationRedAuto extends LinearOpMode {

    Intake intake;
    Output out;
    Drivetrain drive;
    Sensors sensors;

    @Override
    public void runOpMode() throws InterruptedException {

        sensors = new Sensors(this);
        out = new Output(this);
        drive = new Drivetrain(this);
        intake = new Intake(this);

        waitForStart();

        drive.foundationAuto(this, out, 1);
        drive.snowWhite();
    }

}
