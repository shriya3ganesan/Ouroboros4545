package LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Intake;
import LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "Plagyround",group = "Autonomous")
public class Playground extends LinearOpMode {

    DriveTrainGood drive;
    Output out;
    Intake in;


    @Override
    public void runOpMode() throws InterruptedException{

        drive = new DriveTrainGood(this);
        out = new Output(this);
        in = new Intake(this);

        waitForStart();
        //For left side blue


        out.armDown(false);
        out.elbowUp(false);
        sleep(500);
        drive.gyroStrafe(1, 3, true, 3);
        out.elbowDown(false);
        sleep(500);
        out.armUp(false);

        drive.encoderMove(.6, 40, 4);
        sleep(10000);

    }
}
