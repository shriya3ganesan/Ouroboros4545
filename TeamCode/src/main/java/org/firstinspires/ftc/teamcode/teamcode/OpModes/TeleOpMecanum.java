package org.firstinspires.ftc.teamcode.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;
@TeleOp(name="Arcade", group= "Tele Op")
public class TeleOpMecanum extends OpMode {

    DriveTrain drive = new DriveTrain();
    Intake intake = new Intake();
    Outtake outtake = new Outtake();
    ElapsedTime time = new ElapsedTime();

    double speedProp = 1.0;
    double right_stick_x;
    double left_stick_x;
    double left_stick_y;

    @Override
    public void init() {

        drive.fl = hardwareMap.dcMotor.get("fl");
        drive.fr = hardwareMap.dcMotor.get("fr");
        drive.bl = hardwareMap.dcMotor.get("bl");
        drive.br = hardwareMap.dcMotor.get("br");

        drive.fl.setDirection(DcMotor.Direction.FORWARD);
        drive.fr.setDirection(DcMotor.Direction.REVERSE);
        drive.bl.setDirection(DcMotor.Direction.FORWARD);
        drive.br.setDirection(DcMotor.Direction.REVERSE);

        drive.fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake.initIntakeTele(this);

        drive.runtime.reset();
        time.reset();
    }

    @Override
    public void loop() {

        left_stick_y = gamepad1.left_stick_y;
        left_stick_x = gamepad1.left_stick_x;
        right_stick_x = gamepad1.right_stick_x;

        if(gamepad1.x)
        {
            time.reset();
            while(time.milliseconds() < 300){ }
            if(speedProp == 1)
            {
                speedProp = .5;
            }
            else {
                speedProp = 1;
            }
        }

        telemetry.addData("Speed", speedProp);

        if(gamepad1.dpad_left)
        {
            drive.setStrafePower(-1);
        }
        else if(gamepad1.dpad_right)
        {
            drive.setStrafePower(1);
        }
        else if (Math.abs(left_stick_x) > 0.05 ||
                Math.abs(left_stick_y) > 0.05 ||
                Math.abs(right_stick_x) > 0.05) {

            drive.fl.setPower(speedProp * ((left_stick_y - left_stick_x) - right_stick_x));
            drive.fr.setPower(speedProp * ((left_stick_y + left_stick_x) + right_stick_x));
            drive.bl.setPower(speedProp * (left_stick_y + left_stick_x) - right_stick_x);
            drive.br.setPower(speedProp * (left_stick_y - left_stick_x) + right_stick_x);
        }
        else {
            drive.snowWhite();
        }

        intake.Intake_TeleOp();

        telemetry.update();
    }
}
