package LeoLibraries.LeoLibraries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(group = "TeleOp", name="Encoder Op")
public class EncoderOp extends OpMode {

    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

    double right_stick_x;
    double left_stick_x;
    double left_stick_y;

    double average = 0;


    @Override
    public void init() {


        fl = this.hardwareMap.dcMotor.get("fl");
        fr = this.hardwareMap.dcMotor.get("fr");
        bl = this.hardwareMap.dcMotor.get("bl");
        br = this.hardwareMap.dcMotor.get("br");

        fl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        left_stick_y = gamepad1.left_stick_y;
        left_stick_x = gamepad1.left_stick_x;
        right_stick_x = -gamepad1.right_stick_x;

        if (Math.abs(left_stick_x) > 0.05 ||
                Math.abs(left_stick_y) > 0.05 ||
                Math.abs(right_stick_x) > 0.05) {

            fl.setPower(((left_stick_y - left_stick_x) - right_stick_x));
            fr.setPower(((left_stick_y + left_stick_x) + right_stick_x));
            bl.setPower((left_stick_y + left_stick_x) - right_stick_x);
            br.setPower((left_stick_y - left_stick_x) + right_stick_x);
        }
        else {
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
        }




        if(gamepad1.a)
        {
            ElapsedTime time = new ElapsedTime();

            while(time.milliseconds() < 300)
            {

            }

            double count = 4.0;
            if(fr.getCurrentPosition() == 0)
            {
                count--;
            }
            if(fl.getCurrentPosition() == 0)
            {
                count--;
            }
            if(br.getCurrentPosition() == 0)
            {
                count--;
            }
            if(bl.getCurrentPosition() == 0)
            {
                count--;
            }
            if(count == 0)
            {
                 average = 0;
            }
            average = getStrafeEncoderAverage(1);

            telemetry.addData("Encoder Average", average);
            telemetry.update();
        }
        else if(gamepad1.b)
        {
            ElapsedTime time = new ElapsedTime();

            while(time.milliseconds() < 300)
            {

            }

            average = 0;

            fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    private double getStrafeEncoderAverage(double direction) {

        double count = 4.0;
        double average = 0;

        if(fr.getCurrentPosition() == 0)
        {
            count--;
        }
        if(fl.getCurrentPosition() == 0)
        {
            count--;
        }
        if(br.getCurrentPosition() == 0)
        {
            count--;
        }
        if(bl.getCurrentPosition() == 0)
        {
            count--;
        }
        if(direction > 0)
        {
            average = (((-1*fl.getCurrentPosition() + fr.getCurrentPosition()
                    + -1*br.getCurrentPosition() + bl.getCurrentPosition())) ) / count;
        }
        else if(direction < 0)
        {
            average = (((fl.getCurrentPosition() + -1*fr.getCurrentPosition()
                    + br.getCurrentPosition() + -1*bl.getCurrentPosition())))  / count;
        }
        return average;
    }
}
