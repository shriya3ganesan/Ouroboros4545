package LeoLibraries.LeoLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {


        public DcMotor rightSide;
        public DcMotor leftSide;

        ElapsedTime time = new ElapsedTime();
        private LinearOpMode opMode;


        private static final double PICKUP = 1.0;
        private static final double IDLE = 0;


        public Intake(LinearOpMode opMode)
        {
            time.reset();
            this.opMode = opMode;
            rightSide = opMode.hardwareMap.dcMotor.get("RIn");
            leftSide = opMode.hardwareMap.dcMotor.get("LIn");

            rightSide.setDirection(DcMotor.Direction.REVERSE);
            leftSide.setDirection(DcMotor.Direction.FORWARD);

            rightSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            leftSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            rightSide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftSide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            rightSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


        //is to be called in OpMode
        public void autoIntake (double runTime)
        {
            time.reset();

            while(time.seconds() < runTime){
                rightSide.setPower(PICKUP);
                leftSide.setPower(PICKUP);
            }

            rightSide.setPower(IDLE);
            leftSide.setPower(IDLE);

        }

        public void intake_on()
        {
            rightSide.setPower(PICKUP);
            leftSide.setPower(PICKUP);
        }

        public void intake_off()
        {
            rightSide.setPower(IDLE);
            leftSide.setPower(IDLE);
        }
        public void intake_reverse()
        {
            rightSide.setPower(-PICKUP);
            leftSide.setPower(-PICKUP);
        }
    }


