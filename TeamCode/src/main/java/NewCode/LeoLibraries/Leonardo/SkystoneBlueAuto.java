package NewCode.LeoLibraries.Leonardo;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.Output;


@Autonomous(name = "BLUE Skystone Auto",group = "Autonomous")
public class SkystoneBlueAuto extends LinearOpMode {

    Drivetrain drive;
    Output output;
    Intake intake;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new Drivetrain(this);
        output = new Output(this);
        intake = new Intake(this);

        waitForStart();

        drive.encoderMove(.5, 24, 5);
        drive.turnGyro(1, 90, true, 5);




    }
}
