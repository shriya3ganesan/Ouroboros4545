package NewCode.LeoLibraries.LeonardosFunhouse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(group = "TeleOp", name="tanuj sucks")
public class ServoProgrammer extends OpMode {


    public Servo hookRight;
    public Servo hookLeft;
    @Override
    public void init() {
        hookLeft = hardwareMap.servo.get("LHook");
        hookRight = hardwareMap.servo.get("RHook");


        hookLeft.setDirection(Servo.Direction.FORWARD);
        hookRight.setDirection(Servo.Direction.FORWARD);
    }

    @Override
    public void loop() {


        hookLeft.setPosition(((double)Math.round((Math.atan((((double)gamepad1.left_stick_y)/ ((double)gamepad1.left_stick_x))) / (2 * Math.PI))* 10)) / 10);
        hookRight.setPosition(-((double)Math.round((Math.atan((((double)gamepad1.left_stick_y)/ ((double)gamepad1.left_stick_x))) / (2 * Math.PI))* 10)) / 10);

        telemetry.addData("Left Hook ", hookLeft.getPosition());
        telemetry.addData("Right Hook ", hookRight.getPosition());

        telemetry.update();


    }
}
