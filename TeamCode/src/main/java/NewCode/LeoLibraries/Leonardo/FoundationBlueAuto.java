package NewCode.LeoLibraries.Leonardo;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

        sensors = new Sensors(this);
        drive = new Drivetrain(this);
        intake = new Intake(this);
        out = new Output(this);

        waitForStart();

        drive.foundationAuto(this, out, -1);

        drive.snowWhite();
    }
}
