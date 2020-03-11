package LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import LeoLibraries.LeoLibraries.DriveTrainGood;
import LeoLibraries.LeoLibraries.Output;

@Disabled
@Autonomous(group="Autonomous", name="Fire")
public class Fire extends LinearOpMode {
    DriveTrainGood drive;
    Output out;
    @Override
    public void runOpMode() throws InterruptedException {

        drive = new DriveTrainGood(this);
        out = new Output(this);

        waitForStart();

        for(int i = 0; i < 4; i++)
        {

            if(i % 2 == 0)
            {
                drive.turnGyro(1, 20, true, 3);
            }else if(i % 2 == 1)
            {
                drive.turnGyro(1, 40, false, 3);
            }
            drive.encoderMove(1, 7, 3);
        }


        drive.spin(1);
        out.liftLeft.setPower(1);
        out.liftRight.setPower(1);

        sleep(2000);

        out.liftRight.setPower(0);
        out.liftLeft.setPower(0);

        drive.snowWhite();


    }
}
