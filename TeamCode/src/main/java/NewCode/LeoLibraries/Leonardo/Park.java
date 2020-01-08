package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import NewCode.LeoLibraries.LeoLibraries.Drivetrain;

@Autonomous(name = "Park",group = "Autonomous")
public class Park extends LinearOpMode {

    Drivetrain drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new Drivetrain(this);

        waitForStart();


        drive.encoderMove(.5, 24, 5);
        drive.snowWhite();

    }
}
