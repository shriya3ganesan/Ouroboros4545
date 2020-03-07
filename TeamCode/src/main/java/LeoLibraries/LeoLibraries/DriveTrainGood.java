package LeoLibraries.LeoLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

import LeoLibraries.Judging.CubicSpline;
import LeoLibraries.Judging.FunctionY;
import LeoLibraries.Judging.Motor_Power_Spline;
import LeoLibraries.Judging.Point;

public class DriveTrainGood {

    public double count;
    ElapsedTime runtime = new ElapsedTime();
    private LinearOpMode opMode;
    public Output out;
    public Sensors sensors;

     DcMotor fl;
     DcMotor fr;
     DcMotor bl;
     DcMotor br;

    private static double motorCounts = 518.4;
    private static double gearUp = 1;
    public static double wheelDiam = 4;
    public static double noLoadSpeed = 31.4 ; // Max Angular Velocity in radians/second for 20 : 1 motor
    public static double stallTorque = 2.1; // Max Torque in Newton Meters for 20 : 1 motor
    private static double inchCounts = (motorCounts / gearUp) / (wheelDiam * Math.PI);
    private double RDx;
    private double RDy;
    private double baseRatio;
    public double hTarget;


//---------------------------------------------Initialization-------------------------------------------------------------------



    public DriveTrainGood(LinearOpMode opMode) throws InterruptedException {
        this.opMode = opMode;

        fl = this.opMode.hardwareMap.dcMotor.get("fl");
        fr = this.opMode.hardwareMap.dcMotor.get("fr");
        bl = this.opMode.hardwareMap.dcMotor.get("bl");
        br = this.opMode.hardwareMap.dcMotor.get("br");

        sensors = new Sensors(opMode);

        fl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }


    //------------------------------------------MISCELLANEOUS METHODS-------------------------------------------


    public double getEncoderAverage() {
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
            return 0;
        }
        return (fl.getCurrentPosition() + fr.getCurrentPosition()
                + br.getCurrentPosition() + bl.getCurrentPosition()) / count;
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

    public void resetEncoders() {

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

    }
    public void setMotorsPower(double power)
    {

        fl.setPower(power);
        fr.setPower(power);
        br.setPower(power);
        bl.setPower(power);
    }

    public void setStrafePower(double power)
    {
        fr.setPower(-power);
        br.setPower(power);
        fl.setPower(power);
        bl.setPower(-power);
    }

    public void snowWhite () {

        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    public double getTargetPercentile(double reading) {
        return Math.abs(getEncoderAverage() / reading);
    }


    //------------------------------------------------MOVING----------------------------------------------------------------

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

    public void encoderMove(double power, double distance, double runtime)
    {
        resetEncoders();
        ElapsedTime time = new ElapsedTime();

        double initEncoder = getEncoderAverage();

        time.reset();

        distance = distance * inchCounts;

        while (Math.abs(getEncoderAverage() - initEncoder) < distance && time.seconds() < runtime && opMode.opModeIsActive()) {
            setMotorsPower(power);

            opMode.telemetry.addData("Encoder distance left", (distance - getEncoderAverage()));
            opMode.telemetry.update();

        }
        snowWhite();

    }

    public void strafeMove(double target, double power, double timeout)
    {

        ElapsedTime runtime = new ElapsedTime();
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

        gyroTurnStraight(1000);
        snowWhite();
    }

    public void thread (LinearOpMode opMode, Output out, double reverseInches) {

        ElapsedTime time = new ElapsedTime();
        boolean liftCheck = false;
        boolean basketCheck = false;
        boolean moveCheck = false;

        double inches = -reverseInches;

        resetEncoders();
        count = 0;

        int newLeftTarget = fl.getCurrentPosition() + (int) (inches * inchCounts);
        int newRightTarget = fr.getCurrentPosition() + (int) (inches * inchCounts);
        int newLeftBlarget = bl.getCurrentPosition() + (int) (inches * inchCounts);
        int newRightBlarget = br.getCurrentPosition() + (int) (inches * inchCounts);

        out.blockCount++;
        if (out.pushBlock.getPosition() != 1)
            out.pushBlock.setPosition(1);

        out.rightVex.setPower(-.5);
        out.leftVex.setPower(.5);
        runtime.reset();

        out.liftRight.setPower(out.LIFTPOWER);
        out.liftLeft.setPower(out.LIFTPOWER);

        fl.setTargetPosition(-newLeftTarget);
        fr.setTargetPosition(-newRightTarget);
        bl.setTargetPosition(-newLeftBlarget);
        br.setTargetPosition(-newRightBlarget);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fl.setPower(-0.6);
        fr.setPower(-0.6);
        bl.setPower(-0.6);
        br.setPower(-0.6);

        while (opMode.opModeIsActive() && (!basketCheck || !liftCheck || !moveCheck) && time.seconds() < 3) {

            if (runtime.milliseconds() >=
                    out.HORIZONTALEXTENSIONTIME) {
                basketCheck = true;
                out.rightVex.setPower(0);
                out.leftVex.setPower(0);
            }

            if (Math.abs((Math.abs(newLeftTarget) - Math.abs(getEncoderAverage()))) <= 5) {
                moveCheck = true;
                snowWhite();
            }

            else if (getTargetPercentile(newLeftTarget) >= 80) {
                fl.setPower(0.2);
                fr.setPower(0.2);
                bl.setPower(0.2);
                br.setPower(0.2);
            }

            else if (getTargetPercentile(newLeftTarget) >= 70) {
                fl.setPower(0.3);
                fr.setPower(0.3);
                bl.setPower(0.3);
                br.setPower(0.3);
            }

            else if (getTargetPercentile(newLeftTarget) >= 60) {
                fl.setPower(0.4);
                fr.setPower(0.4);
                bl.setPower(0.4);
                br.setPower(0.4);
            }

            else if (getTargetPercentile(newLeftTarget) >= 50) {
                fl.setPower(0.5);
                fr.setPower(0.5);
                bl.setPower(0.5);
                br.setPower(0.5);
            }

            if (out.encoderLevelCount * out.blockHeight * 1.25 <=
                    out.averageLiftPosition()) {
                liftCheck = true;
                mufasa(out);
                out.top = true;
            }

            else {
                if (out.top && out.averageLiftPosition() >
                        out.MAXHEIGHT * out.encoderLevelCount) {
                    liftCheck = true;
                    mufasa(out);
                }
            }
        }
        out.liftRight.setPower(0);
        out.liftLeft.setPower(0);
        snowWhite();
    }

    public void mufasa(Output out) {
        out.liftLeft.setPower(0);
        out.liftRight.setPower(0);
    }


    //----------------------------------------------------Movements--------------------------------------------------------------




    public void turn(double power, boolean isRight)
    {
        if(isRight)
        {
            fr.setPower(-power);
            br.setPower(-power);
            fl.setPower(power);
            bl.setPower(power);
        }
        else
        {
            fr.setPower(power);
            br.setPower(power);
            fl.setPower(-power);
            bl.setPower(-power);
        }
    }
    public void gyroTurnStraight(double timeOutMS) {

        ElapsedTime runtime = new ElapsedTime();
        double goal;

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
                turn(.23, false);
            }
            else {
                turn(.23, true);
            }


        } while (opMode.opModeIsActive() && Math.abs(goal - sensors.getGyroYaw()) > 2 && runtime.milliseconds() < timeOutMS);

        snowWhite();
    }

    public void turnGyro(double power, double angleChange, boolean turnRight, double timeout) {
        ElapsedTime time = new ElapsedTime();

        double initAngle = sensors.getGyroYaw();

        time.reset();

        while (Math.abs((sensors.getGyroYaw() - initAngle)) < angleChange - 5 && opMode.opModeIsActive() && time.seconds() < timeout) {
            turn(power, turnRight);

            opMode.telemetry.addData("Angle left", (angleChange - Math.abs((sensors.getGyroYaw() - initAngle))));
            opMode.telemetry.update();

        }
        snowWhite();
    }

    public void gyroStrafe(double power, double distance, boolean left, double timeout)
    {

        ElapsedTime time = new ElapsedTime();
        resetEncoders();
        gyroTurnStraight(500);
        double pos = 1;
        if(!left)
        {
            pos = -1;
        }


        double pfr = -power * pos;
        double pfl = power * pos;
        double pbl = -power * pos;
        double pbr = power * pos;



        double angle = sensors.getGyroYaw();
        double average = 0;
        while(opMode.opModeIsActive() && Math.abs(average) < Math.abs(distance) * inchCounts && time.seconds() < timeout)
        {

            average = getStrafeEncoderAverage(pos);

            if(angle > 2)
            {
                if(left)
                {
                    pfr = -power;
                    pfl = power;
                    pbr = power * .86;
                    pbl = -power * .89;
                }
                else if(!left)
                {
                    pfr = power * .9;
                    pfl = -power * .9;
                    pbr = -power;
                    pbl = power;
                }
            }
            else if(angle < -2)
            {
                if(left)
                {
                    pfr = -power * .86;
                    pfl = power * .89;
                    pbr = power;
                    pbl = -power;
                }
                else if(!left)
                {
                    pfr = power;
                    pfl = -power;
                    pbr = -power * .9;
                    pbl = power * .9;
                }
            }
            else {

                pfr = -power * pos;
                pfl = power * pos;
                pbl = -power * pos;
                pbr = power * pos;
            }


            fr.setPower(pfr);
            fl.setPower(pfl);
            bl.setPower(pbl);
            br.setPower(pbr);
            angle = sensors.getGyroYaw();
            opMode.telemetry.addData("Angle", angle);
            opMode.telemetry.update();
        }
        gyroTurnStraight(1000);
        snowWhite();
    }

    public void turnPID(double angleChange, boolean turnRight, double kP, double kI, double kD, double timeout) {

        ElapsedTime time = new ElapsedTime();
        ElapsedTime timeoutTimer = new ElapsedTime();

        double error;
        double power;

        double proportional;
        double integral = 0;
        double derivative;

        double prevRunTime;

        double initAngle = sensors.getGyroYaw();
        double lastError = angleChange - Math.abs(sensors.getGyroYaw() - initAngle);

        time.reset();
        timeoutTimer.reset();

        while (Math.abs(sensors.getGyroYaw() - (angleChange + initAngle)) > 1 && timeoutTimer.seconds() < timeout && opMode.opModeIsActive()) {
            prevRunTime = time.seconds();

            error = angleChange - Math.abs(sensors.getGyroYaw() - initAngle);


            proportional = error * kP;

            integral += (error * (time.seconds() - prevRunTime)) * kI;


            derivative = ((error - lastError) / (time.seconds() - prevRunTime)) * kD;


            power = proportional + integral + derivative;

            turn(power, turnRight);

            opMode.telemetry.addData("error ", error);
            opMode.telemetry.addData("P", proportional);
            opMode.telemetry.addData("I", integral);
            opMode.telemetry.addData("D", derivative);
            opMode.telemetry.addData("power", power);
            opMode.telemetry.update();

            lastError = error;

            opMode.idle();

        }
        snowWhite();

    }

    public void splineMove(LinearOpMode linearOpMode, ArrayList<Point> points, double runtime, double kT)
    {

        ElapsedTime t = new ElapsedTime();
        t.reset();

        CubicSpline c = new CubicSpline();
        FunctionY[] functions = c.makeSpline(points, 0 ,0);
        ArrayList<Point> splinePoints = c.SplineToPoints(functions);
        ArrayList<Motor_Power_Spline> motorPoints = c.splinePointsToMotorPoints(splinePoints);

        double rightp = 0;
        double leftp = 0;
        for(Motor_Power_Spline m : motorPoints)
        {

            if(t.seconds() > runtime)
            {
                break;
            }
            leftp = m.getLeftPower();
            rightp = m.getRightPower();

            leftTank(leftp);
            rightTank(rightp);

            linearOpMode.sleep((long)(m.getDeltaT() * kT));
        }

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

    public void spin(double power)
    {
        fr.setPower(power);
        br.setPower(power);
        bl.setPower(-power);
        br.setPower(-power);
    }
}
