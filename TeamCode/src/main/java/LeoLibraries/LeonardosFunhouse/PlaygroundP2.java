package LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Intake;
import LeoLibraries.LeoLibraries.Output;

@Autonomous(name = "Test Class",group = "Autonomous")
public class PlaygroundP2 extends LinearOpMode {

    DriveTrainGood drive;
    Output out;
    Intake in;


    @Override
    public void runOpMode() throws InterruptedException{

        drive = new DriveTrainGood(this);
        out = new Output(this);
        in = new Intake(this);


        waitForStart();



        drive.gyroTurnStraight(90, 2000);


    }
}
