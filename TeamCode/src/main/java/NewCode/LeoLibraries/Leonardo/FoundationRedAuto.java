package NewCode.LeoLibraries.Leonardo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

        sensors = new Sensors(this, true);
        out = new Output(this);
        drive = new Drivetrain(this);
        intake = new Intake(this);

        waitForStart();

        // sleep(5000);
        out.raiseLiftAuto();
        drive.encoderMove(.4,  -10/1.8, 4);
        drive.strafeMove(20/1.8, 5, .6);
        drive.encoderMove(.4, -54/1.8, 4);

        sleep(500);
        out.hookDown();
        sleep(2000);

        drive.encoderMove( .4,  65/1.8, 10);

        out.hookUp();
        sleep(500);

        drive.strafeMove(70/1.8, 5, -.7);
        out.lowerLiftAuto();
        drive.encoderMove( -.4, -10, 3);
        drive.strafeMove(10, 3, .7);
        drive.encoderMove(.4, 10, 3);
        drive.strafeMove(60/1.8 , 5, -.7);

        drive.snowWhite();
    }

}
