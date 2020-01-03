package NewCode.LeoLibraries.Tuner;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import NewCode.LeoLibraries.LeoLibraries.Drivetrain;
import NewCode.LeoLibraries.LeoLibraries.Sensors;


@TeleOp(name = "PID Tuner",group = "TeleOp")
public class PIDTuner extends OpMode {


    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

    ElapsedTime time = new ElapsedTime();
    double kP = 1;
    double kI = 0;
    double kD = 0;
    double[] constants = new double[3];
    int i = 0;

    @Override
    public void init() {

        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        time.reset();

    }

    @Override
    public void loop() {

        kP = constants[0];
        kI = constants[1];
        kD = constants[2];


        if (gamepad1.a) {
            time.reset();
            while (time.milliseconds() < 500) {
            }
            LinearOpMode opMode = new LinearOpMode() {
                @Override
                public void runOpMode() throws InterruptedException {
                    telemetry.addLine("PID Running");
                    //Drivetrain drive = new Drivetrain(this);
                    Sensors sensors = new Sensors();
                    sensors.initSensors(this);
                    telemetry.addData("Angle : ",  sensors.getGyroYaw());

                    //drive.turnPID(90, true, kP, kI, kD, 2);
                }
            };

        }

        if(gamepad1.dpad_right)
        {
            time.reset();
            while(time.milliseconds() < 500)
            {

            }

            i++;

        }else if (gamepad1.dpad_left)
        {
            time.reset();
            while(time.milliseconds() < 500)
            {

            }

            i--;
        }
        if(gamepad1.dpad_up)
        {
            time.reset();
            while(time.milliseconds() < 500)
            {

            }
            constants[i] += .001/90;
        }
        else if(gamepad1.dpad_down)
        {
            time.reset();
            while(time.milliseconds() < 500)
            {

            }
            constants[i] -= .001/90;
        }


        if(i == 0)
        {
            telemetry.addLine("kP");
        }
        else if(i == 1)
        {
            telemetry.addLine("kI");
        }
        else if(i == 2)
        {
            telemetry.addLine("kD");
        }
        else if (i > 2)
        {
            i = i % 3;
        }
        telemetry.addData("Proportional Constant", constants[0]);
        telemetry.addData("Integral Constant", constants[1]);
        telemetry.addData("Derivative Constant", constants[2]);
        telemetry.update();

    }


}
