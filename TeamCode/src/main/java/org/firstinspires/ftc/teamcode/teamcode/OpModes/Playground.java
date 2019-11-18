package org.firstinspires.ftc.teamcode.teamcode.OpModes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Outtake;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.Sensors;
import org.firstinspires.ftc.teamcode.teamcode.Hardware.ZeroMap;

@Autonomous(name ="Playground", group="Auto Basic")
public class Playground extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private double driveSpeed = 0.6;

    DriveTrain drive = new DriveTrain();
    Sensors sensors = new Sensors();
    Intake intake = new Intake();
    Outtake outtake = new Outtake();
    ZeroMap zero = new ZeroMap();

    @Override
    public void runOpMode() {

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

        telemetry.addLine("Drive motors initialized");

        sensors.initSensors(this);
        zero.zeroInit(this);

        telemetry.addLine("Gyro Initialized");
        telemetry.update();

        intake.initIntakeAuto(this);
        outtake.initOuttakeAuto(this);

        waitForStart();

        //drive.RDXVector(45, 24, 3000);
        //zero.zeroBrowse(this);


        //drive.shaftChange(35, 5000, 48,
                //0.6);

        //!
        //drive.strafeMove(this, 24, 5, 0.6);

        //!!!
        //drive.turnPID(this, 90, true, 0.6/90, 0.4, 0.03, 3000);
        //drive.zeroRun(this);
        //drive.postChange(this, true);
    }

}

