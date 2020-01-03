package NewCode.LeoLibraries.Leonardo;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;

import NewCode.LeoLibraries.LeoLibraries.Intake;
import NewCode.LeoLibraries.LeoLibraries.Sensors;
import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "BLUE Foundation Auto",group = "Autonomous")
public class FoundationBlueAuto extends LinearOpMode {


    Drivetrain drive;
    Sensors sensors;
    Intake intake;
    Output out;

    @Override
    public void runOpMode() throws InterruptedException {

        sensors = new Sensors(this, true);
        drive = new Drivetrain(this);
        intake = new Intake(this);
        out = new Output(this);

        waitForStart();

        out.raiseLiftAuto();
        drive.encoderMove( .4, -10/1.8, 4);
        drive.strafeMove(20/1.8, 5, -.6);
        drive.encoderMove(.4, -55/1.8, 4);
        sleep(500);

        out.hookDown();
        sleep(2000);

        drive.encoderMove(.4, 65/1.8, 10);
        out.hookDown();
        sleep(500);

        drive.gyroTurnStraight(1000);
        drive.strafeMove(70/1.8, 5, .7);
        out.lowerLiftAuto();
        drive.encoderMove(-.4, -10, 3);
        drive.gyroTurnStraight( 1000);
        drive.strafeMove(10, 3, -.7);
        drive.encoderMove(.4, 10, 3);
        drive.strafeMove(60/1.8 , 5, .7);

        drive.snowWhite();
    }
}
