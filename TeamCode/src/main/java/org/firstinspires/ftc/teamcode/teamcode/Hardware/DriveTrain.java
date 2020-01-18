package org.firstinspires.ftc.teamcode.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import NewCode.LeoLibraries.Judging.CubicSpline;
import NewCode.LeoLibraries.Judging.FunctionY;
import NewCode.LeoLibraries.Judging.Motor_Power_Spline;
import NewCode.LeoLibraries.Judging.Point;

import java.text.DecimalFormat;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class DriveTrain {

    private static double motorCounts = 518.4;
    private static double gearUp = 1;
    public static double wheelDiam = 4;
    public static double noLoadSpeed = 31.4 ; // Max Angular Velocity in radians/second for 20 : 1 motor
    public static double stallTorque = 2.1; // Max Torque in Newton Meters for 20 : 1 motor
    private static double inchCounts = (motorCounts / gearUp) / (wheelDiam * Math.PI);
    double count = 4.0;

    double totalDisb;
    double xDisb;

    boolean refab = false;

    double prevFoc;
    double newFoc;

    double flAcc = 0.0;
    double frAcc = 0.0;
    double brAcc = 0.0;
    double blAcc = 0.0;

    double RDXdiag;
    double RDXalpha;

    double gyre;
    double bOffset;
    double cOffset;
    double primeSin;
    double alpha;

    double goalProg;
    double goalTarg;

    boolean pastB = false;
    int quadrant;
    int RDXquadrant;

    double baseRatio;
    double hTarget;

    public ElapsedTime runtime = new ElapsedTime();
    private LinearOpMode opMode;
    private Sensors sensors;

    public DcMotor fl; //Front Left Motor
    public DcMotor fr; //Front Right Motor
    public DcMotor bl; //Back Left Motor
    public DcMotor br; //Back Right Motor

    DecimalFormat format = new DecimalFormat("###.##");

    public double secondPrevPosition;
    public double secondTime;
    public double secondPosition;
    public double prevError = 0;
    public double prevTime = 0;
    public double power = 0;
    public double integral;
    public double derive;
    public double proportional;
    public double error = 0;
    public double time;

    double c2Offset;
    double a2Offset;
    double b3Offset;
    double c3Offset;

    double RDXc;
    double RDXtheta;
    double RDx;
    double Rb;
    double RDy;

    double prevEncode;

    double aOffset;
    double b2Offset;
    double yCalc;

    //encoded acceleration variables

    double position;
    double time_ea;
    double prevPosition;
    double prevTime_ea;
    double prevNewTime;
    double prevAccel;
    double accel;
    double masterAccel;

    int newLeftTarget;
    int newRightTarget;
    int newRightBlarget;
    int newLeftBlarget;

    // Get Velocity Variables
    private double masterVelocity;
    private double flVel;
    private double frVel;
    private double brVel;
    private double blVel;
    private double modPower = 0.0;


    public void initDriveTrain(LinearOpMode opMode) {

        this.opMode = opMode;

        sensors = new Sensors();
        sensors.initSensors(opMode);

        //Sets Hardware Map
        fl = opMode.hardwareMap.dcMotor.get("fl");
        fr = opMode.hardwareMap.dcMotor.get("fr");
        bl = opMode.hardwareMap.dcMotor.get("bl");
        br = opMode.hardwareMap.dcMotor.get("br");

        //Sets Motor Directions
        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        //Set Power For Static Motors - When Robot Not Moving
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        count = 4.0;
        modPower = 0.0;

        runtime.reset();
        resetEncoders();
        //runEncoders();
    }


    //Making 90 Degree Turns
    public void turn(double speed, boolean isRight) {

        if (isRight) {
            fl.setPower(-1);
            fr.setPower(1);
            bl.setPower(-1);
            br.setPower(1);
        }
        else
        {
            fl.setPower(1);
            fr.setPower(-1);
            bl.setPower(1);
            br.setPower(-1);
        }
    }

    public double getEncoderAverage() {
        count = 4.0;
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
          return 0;
        }

        return (fl.getCurrentPosition() + fr.getCurrentPosition()
                + br.getCurrentPosition() + bl.getCurrentPosition()) / count;
    }

    //Method for Resetting Encoders
    public void resetEncoders() {

        /*opMode.telemetry.addData("Status", "Resetting Encoders");
        opMode.telemetry.update();
*/
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();

        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();

        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();

        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();

        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();

        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();

        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();

        /*opMode.telemetry.addData("Path0", "Starting at %7d : %7d",
                bl.getCurrentPosition(),
                br.getCurrentPosition());
        opMode.telemetry.update();*/
    }


    public void runEncoders() {
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runToPosition() {
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void encoderBase (double speed, int leftFront, int rightFront, int leftBack, int rightBack, double timeoutS) {
        fl.setTargetPosition(leftFront);
        fr.setTargetPosition(rightFront);
        bl.setTargetPosition(leftBack);
        br.setTargetPosition(rightBack);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();

        fl.setPower(Math.abs(speed));
        fr.setPower(Math.abs(speed));
        bl.setPower(Math.abs(speed));
        br.setPower(Math.abs(speed));


        while (opMode.opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (bl.isBusy() && br.isBusy())) {

            //opMode.telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
            //opMode.telemetry.addData("Path2", "Running at %7d :%7d",
            // fl.getCurrentPosition(),
            //  fr.getCurrentPosition());

            opMode.telemetry.update();
        }

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        opMode.sleep(50);
    }

    public void strafeEqualizer()
    {
        flAcc = getHolon(fl);
        frAcc = getHolon(fr);
        brAcc = getHolon(br);
        blAcc = getHolon(bl);

        if(Math.abs(flAcc + blAcc) >= .05)
        {
            fl.setPower(fl.getPower() - (flAcc + blAcc));
        }
        if(Math.abs(frAcc + brAcc) >= .05)
        {
            fr.setPower(fr.getPower() - (frAcc + brAcc));
        }
    }


    public void strafeMove(LinearOpMode opMode, double target, double timeout, double power)
    {
        runtime.reset();
        resetEncoders();

        double averageStrafe = 0.0;
        double speed = power * .6;


        while(Math.abs(averageStrafe) < target * inchCounts && runtime.seconds() < timeout && opMode.opModeIsActive())
        {

            setStrafePower(speed);
            averageStrafe = getStrafeEncoderAverage(power);


            if (Math.abs(speed) < Math.abs(power)){
                speed = speed * 1.05;
            }

        }

        gyroTurnStraight(opMode, 1000);
        snowWhite();
    }

    public void setStrafePower(double power)
    {
        fr.setPower(-power);
        br.setPower(power);
        fl.setPower(power);
        bl.setPower(-power);
    }

    private double getStrafeEncoderAverage(double direction) {
        count = 4.0;
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
            average = (-1*fl.getCurrentPosition() + fr.getCurrentPosition()
                    + -1*br.getCurrentPosition() + bl.getCurrentPosition()) / count;
        }
        else if(direction < 0)
        {
            average = (fl.getCurrentPosition() + -1*fr.getCurrentPosition()
                    + br.getCurrentPosition() + -1*bl.getCurrentPosition()) / count;
        }
        return average;
    }

 /*   public void encoderStrafe(LinearOpMode opMode, double speed,
                              double leftInches, double rightInches,
                              double timeoutS) {

        runtime.reset();

        int newLeftTarget = 0;
        int newRightTarget = 0;
        int newRightBlarget = 0;
        int newLeftBlarget = 0;

            if (opMode.opModeIsActive()) {
                newLeftTarget = fl.getCurrentPosition() + (int) (-leftInches * inchCounts);
                newRightTarget = fr.getCurrentPosition() + (int) (rightInches * inchCounts);
                newLeftBlarget = bl.getCurrentPosition() + (int) (leftInches * inchCounts);
                newRightBlarget = br.getCurrentPosition() + (int) (-rightInches * inchCounts);
            }

        }

        else {
            if (opMode.opModeIsActive()) {
                newLeftTarget = fl.getCurrentPosition() + (int) (leftInches * inchCounts);
                newRightTarget = fr.getCurrentPosition() + (int) (-rightInches * inchCounts);
                newLeftBlarget = bl.getCurrentPosition() + (int) (leftInches * inchCounts);
                newRightBlarget = br.getCurrentPosition() + (int) (-rightInches * inchCounts);
            }
        }
        fl.setTargetPosition(newLeftTarget);
        fr.setTargetPosition(newRightTarget);
        bl.setTargetPosition(newLeftBlarget);
        br.setTargetPosition(newRightBlarget);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        fl.setPower(speed);
        fr.setPower(speed);
        bl.setPower(speed);
        br.setPower(speed);


        while (opMode.opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (bl.isBusy() && br.isBusy())) {

            /*opMode.telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
            opMode.telemetry.addData("Path2", "Running at %7d :%7d",
            fl.getCurrentPosition(),
            fr.getCurrentPosition());
            opMode.telemetry.update();

        }

        snowWhite();

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        opMode.sleep(50);

    }
    */

    public void encoderDrive(LinearOpMode opMode, double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        runtime.reset();
        resetEncoders();
        opMode.sleep(100);

        newLeftTarget = fl.getCurrentPosition() + (int) (leftInches * inchCounts);
        newRightTarget = fr.getCurrentPosition() + (int) (rightInches * inchCounts);
        newLeftBlarget = bl.getCurrentPosition() + (int) (leftInches * inchCounts);
        newRightBlarget = br.getCurrentPosition() + (int) (rightInches * inchCounts);

        fl.setTargetPosition(-newLeftTarget);
        fr.setTargetPosition(-newRightTarget);
        bl.setTargetPosition(-newLeftBlarget);
        br.setTargetPosition(-newRightBlarget);

       do {

            fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            fl.setPower(-speed);
            fr.setPower(-speed);
            bl.setPower(-speed);
            br.setPower(-speed);

        }
        while (opMode.opModeIsActive() && runtime.seconds() < timeoutS &&
               Math.abs((Math.abs(newLeftTarget) - Math.abs(getEncoderAverage()))) > 5 );




        snowWhite();

        opMode.sleep(50);
    }

    public double[] hermite (double[] lStick) {
        gyre = sensors.getGyroYaw();
        while (gyre > 90) {
            gyre -= 90;
            quadrant++;
        }

        primeSin = (Math.sin(gyre / 2) * 2);
        alpha = Math.acos(primeSin);
        bOffset = primeSin * Math.cos(alpha);
        bOffset = 1 - bOffset;
        cOffset = primeSin * Math.sin(alpha);

        switch (quadrant) {
            case 1:
                lStick[0] += cOffset;
                lStick[1] += bOffset;
            case 2:
                lStick[0] += cOffset;
                lStick[1] -= bOffset;
            case 3:
                lStick[0] -= cOffset;
                lStick[1] -= bOffset;
            case 4:
                lStick[0] -= cOffset;
                lStick[1] += bOffset;
        }

        return lStick;
    }

    public void turnGyro(double yaw) {
        ElapsedTime time = new ElapsedTime();

        double initYaw = sensors.getGyroYaw();

        opMode.telemetry.addLine("Entered method");
        opMode.telemetry.update();

        while (Math.abs(sensors.getGyroYaw() - initYaw) < yaw) {
            turn(1, true);

            opMode.telemetry.addData("CU=urr angle", sensors.getGyroYaw());
            opMode.telemetry.update();
        }

        opMode.telemetry.addLine("Finished turn");
        opMode.telemetry.update();
        snowWhite();
    }

    public boolean[] RDXsplineOptimize (boolean[] procReport, double aOff, double bOff, double cOff,
                                        double firstx, double firsty, double secondx,
                                        double secondy) {
        if (aOff * Math.log(cOff) > 0.01) {
            cOff += 0.0001;
            procReport[0] = false;
        } else if (aOffset * Math.log(cOff) < 0.01) {
            cOff -= 0.0001;
            procReport[0] = false;
        } else {
            procReport[0] = true;
        }

        if (aOff * Math.log((bOff * firstx) + cOff) > firsty + 0.01) {
            aOff -= 0.0001;
            procReport[1] = false;
        } else if (aOff * Math.log((bOff * firstx) + cOff) < firsty - 0.01) {
            aOff += 0.0001;
            procReport[1] = false;
        } else {
            procReport[1] = true;
        }
        if (aOffset * Math.log((b2Offset * secondx) + c2Offset) > secondy + 0.01) {
            aOffset -= 0.0001;
            procReport[2] = false;
        } else if (aOffset * Math.log((b2Offset * secondx) + c2Offset) < secondy - 0.01) {
            aOffset += 0.0001;
            procReport[2] = false;
        } else {
            procReport[2] = true;
        }

        return procReport;
    }

    public double[] plotWaypoints (double[] splineTraj, double x1, double y1, double x2, double y2) {

        boolean[] procRun;
        procRun = new boolean[3];
        procRun[1] = false;
        procRun[2] = false;
        procRun[0] = false;

        if (Math.abs(y2) < Math.abs(y1)) {
            refab = true;

            aOffset = (- y1) / Math.log(1 / (x1 + 1));
            b2Offset = - Math.log(x1 + 1) / (- y1);
            c2Offset = 0;

            a2Offset = (y1 - y2) / Math.log(x1/x2);
            b3Offset = -Math.exp((y2 * Math.log(x1) - y1 * Math.log(x2)) / (y1 - y2));
            c3Offset = 0;
        }

        else {
            aOffset = (y1 - y2) / Math.log(x1/x2);
            b2Offset = Math.exp((y2 * Math.log(x1) - y1 * Math.log(x2)) / (y1 - y2));
            c2Offset = 0;
        }

        while (!procRun[1] && !procRun[2] && !procRun[0]) {
            if (!refab) {
                RDXsplineOptimize(procRun, aOffset, b2Offset, c2Offset, x1, y1, x2, y2);
            }
            else {
                RDXsplineOptimize(procRun, aOffset, b2Offset, c2Offset, 0, 0, x1, y1);
                RDXsplineOptimize(procRun, a2Offset, b3Offset, c3Offset, x1, y1, x2, y2);
            }
        }

        if (!refab) {
            for (int i = 0; i > x2; i += 0.01) {
                splineTraj[i * 100] = aOffset * Math.log((b2Offset * i) + c2Offset);
            }
        }

        else {
            for (int i = 0; i > x1; i += 0.01) {
                splineTraj[i * 100] = aOffset * Math.log((b2Offset * i) + c2Offset);
            }
            for (int i = 0; i > x2; i += 0.01) {
                splineTraj[20400 + i] = a2Offset * Math.log((b3Offset * i) + c3Offset);
            }
        }

        return splineTraj;
    }

    public void RDXspline (double x1, double y1, double x2, double y2) {

        refab = false;
        if (Math.abs(y2) < Math.abs(y1)) {
            refab = true;
        }

        double[] RDXtraj;
        RDXtraj = new double[40800];
        plotWaypoints(RDXtraj, x1, y1, x2, y2);

        double[] RDXarr2;
        RDXarr2 = new double[2];

        double arrayLength;
        if (!refab) {
            arrayLength = x2 * 100;
        }

        else {
            arrayLength = x1 * 100;
        }

        if (!refab) {
            goalProg = getEncoderAverage() / inchCounts;
            goalTarg = goalProg + x2;
        }
        else {
            goalProg = getEncoderAverage() / inchCounts;
            goalTarg = goalProg + x1;
        }

        while (goalProg != goalTarg) {
            for (int i = 0; i > arrayLength; i++) {
                RDy = RDXtraj[i];
                RDXdiag = Math.sqrt((i * i) + (RDy * RDy));
                RDXalpha = Math.asin(i / RDXdiag);
                prevEncode = getEncoderAverage();
                prevFoc = getEncoderAverage();
                RDXVector(RDXalpha, 5, 5);
                lifeblood(RDXarr2, RDXalpha);

                newFoc = getEncoderAverage();
                totalDisb = ((newFoc - prevFoc) / (RDXarr2[0] + RDXarr2[1])) / inchCounts;
                xDisb = totalDisb * RDXarr2[0];
                goalProg += xDisb;

                while (prevEncode != prevEncode + (inchCounts / 100)) {
                    opMode.sleep(1000);
                }
            }
            if (refab) {
                goalProg = getEncoderAverage() / inchCounts;
                goalTarg = goalProg + x2;

                arrayLength = (x2 - x1) * 100;
                for (int i = 20400; i > arrayLength; i++) {
                    RDy = RDXtraj[i];
                    RDXdiag = Math.sqrt((i * i) + (RDy * RDy));
                    RDXalpha = Math.asin(i / RDXdiag);
                    prevEncode = getEncoderAverage();
                    prevFoc = getEncoderAverage();
                    RDXVector(RDXalpha, 5, 5);
                    lifeblood(RDXarr2, RDXalpha);

                    newFoc = getEncoderAverage();
                    totalDisb = ((newFoc - prevFoc) / (RDXarr2[0] + RDXarr2[1])) / inchCounts;
                    xDisb = totalDisb * RDXarr2[0];
                    goalProg += xDisb;

                    while (prevEncode != prevEncode + (inchCounts / 100)) {
                        opMode.sleep(1000);
                    }
                }
            }
        }
    }

    public double[] lifeblood (double[] RDXlifeblood, double radiax) {
        RDXlifeblood[0] = Math.sin(radiax);
        RDXlifeblood[1] = Math.cos(radiax);

        return RDXlifeblood;
    }

    double baseLineEncoder;

    public void RDXVector (double radiax, double target, double timeOutMS) {

        runtime.reset();
        while (radiax >= 360) {
            radiax -= 360;
        }

        RDx = Math.sin(radiax);
        RDy = Math.cos(radiax);
        baseRatio = RDx/RDy;
        hTarget = (radiax * radiax) /
                ((baseRatio * baseRatio) + 1);
        hTarget = Math.sqrt(hTarget);
        baseLineEncoder = getEncoderAverage();

        while(Math.abs(getEncoderAverage()) < target * inchCounts
                && runtime.milliseconds() < timeOutMS) {
            fl.setPower(RDy - RDx);
            fr.setPower(RDy + RDx);
            bl.setPower(RDy + RDx);
            br.setPower(RDy - RDx);
        }
        snowWhite();
    }

    public void encoderMove(LinearOpMode opMode, double target, double timeout, double radiax) {

        this.opMode = opMode;
        runtime.reset();
        resetEncoders();

        opMode.sleep(100);

        double average = 0.0;

        setMotorsPower(power);
        while (Math.abs(average) < target * inchCounts && runtime.seconds() < timeout)
        {

            average = getEncoderAverage();
            RDXVector(radiax, target, timeout);

            opMode.telemetry.addData("Current Positions: ", "fl %7d : fr %7d : bl %7d : br %7d",
                    fl.getCurrentPosition(), fr.getCurrentPosition(), bl.getCurrentPosition(), br.getCurrentPosition());
            opMode.telemetry.update();
        }


        opMode.telemetry.addData("Encoder Average : ", getEncoderAverage());
        opMode.telemetry.addData("Target : ", target * inchCounts);
        opMode.telemetry.update();
        snowWhite();
    }


    //double kP = 0.6/90;
    //double kI = 0.0325;
    //double kD = 0.2;

    //PID Turns for Macanum Wheels
    //Proportional Integral Derivative Turn
    public void turnPID (double goal, boolean isRight, double kP, double kI,
                         double kD, double timeOutS) {

        runtime.reset();
        //sensors.angles = sensors.gyro.getAngularOrientation();

        prevTime = runtime.seconds();
        error = goal - sensors.getGyroYaw();

        while (runtime.seconds() <= timeOutS && Math.abs(error) > 1 ) {

            //  sensors.angles = sensors.gyro.getAngularOrientation();

            error = goal - sensors.getGyroYaw();

            proportional = error * kP;

            time = runtime.seconds();

            integral += ((time - prevTime) * error) * kI;

            derive = ((error - prevError) / (time - prevTime)) * kD;

            power = proportional + integral + derive;

            turn(power, isRight);


            prevError = error;


        }

        snowWhite();
    }
    public void gyroTurnStraight(LinearOpMode opMode, double timeOutMS) {

        double goal = 0;
        boolean isRight;
        runtime.reset();
        do  {

            if (sensors.getGyroYaw() > 0 && sensors.getGyroYaw() < 180) {
                goal = 0;
            }
            else {
                goal = 360;
            }

            opMode.telemetry.addData("Goal", goal);
            opMode.telemetry.addData("Current Heading", sensors.getGyroYaw());
            opMode.telemetry.update();
            if (sensors.getGyroYaw() < goal) {
                turn(.2, false);
            }
            else {
                turn(.2, true);
            }


        } while (opMode.opModeIsActive() && Math.abs(goal - sensors.getGyroYaw()) > 2 && runtime.milliseconds() < timeOutMS);

        snowWhite();
    }


    public void align()
    {
        if(sensors.getGyroYaw() > 1) {
            while (sensors.getGyroYaw() > 0.05) {
                fl.setPower(.25);
                bl.setPower(.25);
                fr.setPower(-.25);
                br.setPower(-.25);
            }
            snowWhite();
        }
        else if(sensors.getGyroYaw() < -1) {
            while (sensors.getGyroYaw() < -0.05) {
                fl.setPower(-.25);
                bl.setPower(-.25);
                br.setPower(.25);
                fr.setPower(.25);
            }
            snowWhite();
        }
    }

    public void move(LinearOpMode opMode, double power, double distance, double timeout) {
        resetEncoders();

        ElapsedTime t2 = new ElapsedTime();

        double initEncoder = getEncoderAverage();
        double average = initEncoder;

        t2.reset();

        setMotorsPower(power);

        while (Math.abs(average - initEncoder) < distance * inchCounts && t2.seconds() < timeout && opMode.opModeIsActive()) {

            average = getEncoderAverage();
        }
        snowWhite();

    }

    public void setMotorsPower(double power)
    {

        fl.setPower(power);
        fr.setPower(power);
        br.setPower(power);
        bl.setPower(power);
    }

    public void snowWhite () {

        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    public void partyMode()
    {
        runtime.reset();

        while(runtime.seconds() < 3)
        {
            fr.setPower(1);
            br.setPower(1);
            fl.setPower(-1);
            bl.setPower(-1);
        }
        runtime.reset();
        while(runtime.seconds() < 3)
        {

            fr.setPower(-1);
            br.setPower(-1);
            fl.setPower(1);
            bl.setPower(1);
        }
    }

    public double getVelocity(DcMotor motor)
    {
        runtime.reset();

        prevPosition = motor.getCurrentPosition();
        prevTime = runtime.seconds();

        masterVelocity = (motor.getCurrentPosition() - prevPosition) / (runtime.seconds() - prevTime);
        return masterVelocity;
    }

    public double getHolon (DcMotor motor) {
        runtime.reset();

        prevPosition = motor.getCurrentPosition();
        prevTime = runtime.milliseconds();
        time = runtime.milliseconds();
        position = motor.getCurrentPosition();

        secondPrevPosition = motor.getCurrentPosition();
        prevNewTime = runtime.milliseconds();
        secondTime = runtime.milliseconds();
        secondPosition = motor.getCurrentPosition();

        masterAccel =  (((secondPosition - secondPrevPosition) * (time - prevTime) +
                (prevPosition - position) * (secondTime - prevNewTime))
                / (runtime.milliseconds() - prevTime) * (secondTime -
                prevNewTime) * (time - prevTime)) ;
        return masterAccel;
    }

    public void equalize()
    {
        flAcc = getHolon(fl);
        frAcc = getHolon(fr);
        brAcc = getHolon(br);
        blAcc = getHolon(bl);

        if(Math.abs(flAcc - brAcc) >= .25)
        {
            fl.setPower(fl.getPower() - (flAcc - brAcc));
        }
        if(Math.abs(frAcc - blAcc) >= .25)
        {
            fr.setPower(fr.getPower() - (frAcc - blAcc));
        }

    }
//hello

    public double getEncodedAccel () {
        runtime.reset();

        prevPosition = (fl.getCurrentPosition() + fr.getCurrentPosition()
                + br.getCurrentPosition() + bl.getCurrentPosition()) / 4;
        prevTime = runtime.milliseconds();
        time_ea = runtime.milliseconds();
        position = (fl.getCurrentPosition() + fr.getCurrentPosition()
                + br.getCurrentPosition() + bl.getCurrentPosition()) / 4;

        prevAccel = ((position - prevPosition) / (time_ea - prevTime_ea));

        prevPosition = (fl.getCurrentPosition() + fr.getCurrentPosition()
                + br.getCurrentPosition() + bl.getCurrentPosition()) / 4;
        prevNewTime = runtime.milliseconds();
        time_ea = runtime.milliseconds();
        position = (fl.getCurrentPosition() + fr.getCurrentPosition()
                + br.getCurrentPosition() + bl.getCurrentPosition()) / 4;

        accel = ((position - prevPosition) / (time_ea - prevNewTime));

        masterAccel =  Math.abs(((accel - prevAccel) / (time_ea - prevTime_ea)));
        return masterAccel;
    }

    public double average (double first, double second) { return (first + second) / 2; }

    public double getRadiaxVertical() {
        return average(average(fr.getPower(), bl.getPower()),
                average(fl.getPower(), br.getPower()));
    }

    public double getRadiaxHorizontal () {
        double frSpeed = fr.getPower() - getRadiaxVertical();
        double flSpeed = -(fl.getPower() - getRadiaxVertical());
        double blSpeed = bl.getPower() - getRadiaxVertical();
        double brSpeed = -(br.getPower() - getRadiaxVertical());
        return average(average(brSpeed, frSpeed), average(flSpeed, blSpeed));
    }

    public double getRadiaxHypotenuse () {
        return Math.sqrt((getRadiaxVertical() * getRadiaxVertical()) +
                (getRadiaxHorizontal() * getRadiaxHorizontal()));
    }

    public void leftTank(double power)
    {
        fl.setPower(power);
        bl.setPower(power);
    }

    public void rightTank(double power)
    {
        fr.setPower(power);
        br.setPower(power);
    }

    public boolean getRadiaxRefactor () {
        if (getRadiaxHorizontal() < 0) return true;
        else return false;
    }

    public double getAbsoluteRadiax (double value, double degree) {
        double refact = value - degree;
        return Math.abs(refact);
    }

    /*public double getNodalRadiax () {
        double frSpeed = fr.getPower() - (getRadiaxVertical() + getRadiaxHorizontal());
        double flSpeed = (fl.getPower() - (getRadiaxVertical() - getRadiaxHorizontal()));
        double blSpeed = bl.getPower() - (getRadiaxVertical() + getRadiaxHorizontal());
        double brSpeed = (br.getPower() - (getRadiaxVertical() - getRadiaxHorizontal()));
        return average(average(brSpeed, frSpeed), average(flSpeed, blSpeed));
    }*/

    double currentRadiax;

    public void setTargetRadiax (double radiax) { currentRadiax = radiax; }

    public double getTargetRadiax () { return currentRadiax; }

    public double getAllowedRadiaxOffset (double allow) {
        return getTargetRadiax() + allow;
    }

    public boolean notOffTargetRadiax (double offset) {
        if (getRadiax() > getAllowedRadiaxOffset(offset)
                || getRadiax() < getAllowedRadiaxOffset(-offset)) return false;
        else return true;
    }

    public double getRadiax () {
        double radiax = ((Math.acos(getRadiaxVertical() /
                getRadiaxHypotenuse())) / (2 * Math.PI)) * 360;
        radiax = getAbsoluteRadiax(radiax, 180);
        if (getRadiaxRefactor()) radiax = getAbsoluteRadiax(radiax, 360);
        return radiax;
    }

    /*public double getNodalRadiax() {
        double frSpeed = (fr.getPower() - getRadiaxVertical()) - getRadiaxHorizontal();
        double flSpeed = -((fl.getPower() - getRadiaxVertical()) + getRadiaxHorizontal());
        double blSpeed = -((bl.getPower() - getRadiaxVertical()) - getRadiaxHorizontal());
        double brSpeed = (br.getPower() - getRadiaxVertical()) + getRadiaxHorizontal();
        return average(average(brSpeed, frSpeed), average(flSpeed, blSpeed));
    }*/

    public double getVector () {
        double yaw = sensors.getGyroYaw();
        while (yaw >= 360) yaw -= 360;
        double vector = getRadiax() + yaw;
        while (vector > 360) vector -= 360;
        return vector;
    }

    public double getNirIsBadFlux () {

        double currentEncoderTix = (br.getCurrentPosition() +
                bl.getCurrentPosition() +
                fl.getCurrentPosition() +
                fr.getCurrentPosition());

        double newEncoderTix = (br.getCurrentPosition() +
                bl.getCurrentPosition() +
                fl.getCurrentPosition() +
                fr.getCurrentPosition());
        double encoderTikChange = -(newEncoderTix - currentEncoderTix);
        double storedRuntime = runtime.seconds();

        double encoderVelocity = ((encoderTikChange / 1800)
                * (4 * Math.PI)) / storedRuntime;

        return encoderVelocity * storedRuntime;
    }

    public double getVectorX () {
        return Math.sin(getVector()) * getNirIsBadFlux();
    }

    public double getVectorY () {
        return Math.cos(getVector()) * getNirIsBadFlux();
    }

    public double getBaseRadiax (double x1, double y1) {
        return Math.atan(x1/y1);
    }

    public ArrayList<Point> conflictArray;

    public void setBaseConflictArray () {
        //ADD FOR STATEMENT

        //conflictArray.add(new Point());
        //conflictArray.add(new Point());
        //conflictArray.add(new Point());
        //conflictArray.add(new Point());
        //conflictArray.add(new Point());
    }

    //public boolean checkArrayConflict () {

    //}

    public void splineMove(LinearOpMode linearOpMode, ArrayList<Point> points, double runtime, double kT)
    {

        ElapsedTime t = new ElapsedTime();
        t.reset();

        CubicSpline c = new CubicSpline();
        //FunctionY[] functions = c.makeSpline(points, endHeading);
        FunctionY[] functions = c.makeSpline(points, 0 ,0);
        ArrayList<Point> splinePoints = c.SplineToPoints(functions);
        ArrayList<Motor_Power_Spline> motorPoints = c.splinePointsToMotorPoints(splinePoints);

        double rightp = 0;
        double leftp = 0;
        for(Motor_Power_Spline m : motorPoints)
        {

            if(t.seconds() > runtime)
            {
                return;
            }
            leftp = m.getLeftPower();
            rightp = m.getRightPower();

            leftTank(leftp);
            rightTank(rightp);

            linearOpMode.sleep((long)(m.getDeltaT() * kT));
        }

    }

    public void gyroForward(LinearOpMode LopMode, Sensors sensor, double distance, double power, double runtime, double heading)
    {
        ElapsedTime t = new ElapsedTime();
        t.reset();
        resetEncoders();
        distance = distance * inchCounts;

        double powerR = power;
        double powerL = power;
        double changeHeading;

        while(LopMode.opModeIsActive() &&  Math.abs(getEncoderAverage()) < distance && t.seconds() < runtime)
        {
            changeHeading = heading - (360 - sensor.getGyroYaw());

            if(Math.abs(changeHeading) != 0)
            {
                powerR = power + Math.sin(changeHeading);
                powerL = power - Math.sin(changeHeading);
            }
            else
            {
                powerR = power;
                powerL = power;
            }

            rightTank(powerR);
            leftTank(powerL);
        }

        snowWhite();
    }

    public void gyroStrafe(LinearOpMode LopMode, Sensors sensor, double distance, double power, double runtime, double heading)
    {
        ElapsedTime t = new ElapsedTime();
        t.reset();
        resetEncoders();
        distance = distance * inchCounts;

        double changeHeading;
        double powerR = power;
        double powerL = power;

        double fr_power = power;
        double fl_power = -power;
        double bl_power = power;
        double br_power = -power;

        while(LopMode.opModeIsActive() &&  Math.abs((Math.abs(distance) - Math.abs(getStrafeEncoderAverage(power)))) > 5 && t.seconds() < runtime)
        {
            changeHeading = heading - (360 - sensor.getGyroYaw());

            if(Math.abs(changeHeading) > 1)
            {
                fr_power = fr_power + Math.sin(changeHeading);
                fl_power = fl_power - Math.sin(changeHeading);
                br_power = br_power + Math.sin(changeHeading);
                bl_power = bl_power - Math.sin(changeHeading);
            }
            else
            {
                powerR = power;
                powerL = power;
            }

            // Set Strafe Motor Powers

            fr.setPower(fr_power);
            br.setPower(br_power);
            fl.setPower(fl_power);
            bl.setPower(bl_power);

        }

        snowWhite();
    }
}
