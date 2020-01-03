package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "RED Skystone Auto",group = "Autonomous")
public class SkystoneRedAuto extends LinearOpMode {

    Drivetrain drive;
    Output output;
    Intake intake;


    @Override
    public void runOpMode() throws InterruptedException {

        drive = new Drivetrain(this);
        output = new Output(this);
        intake = new Intake(this);


        waitForStart();

    }
}
