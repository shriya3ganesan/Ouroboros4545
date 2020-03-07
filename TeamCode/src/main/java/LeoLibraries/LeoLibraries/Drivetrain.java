package LeoLibraries.LeoLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drivetrain {

    public double count;
    ElapsedTime runtime =
            new ElapsedTime();
    private LinearOpMode opMode;
    public LinearOpMode opModeX;
    public Output out;
    public Sensors sensors;

    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

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


    public Drivetrain(LinearOpMode opMode) throws InterruptedException {
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

    public void hook (LinearOpMode opMode, Output out, boolean down) {
        if (down) out.hookDown();
        else out.hookUp();
        opMode.sleep(300);
    }

    public void vex (Output out, boolean on) {
        if (on) {
            out.rightVex.setPower(.5);
            out.leftVex.setPower(-.5);
        }
        else {
            out.rightVex.setPower(-.5);
            out.leftVex.setPower(.5);
        }
    }

    public void kill (LinearOpMode opmode, Output out) {
        out.rightVex.setPower(.5);
        out.leftVex.setPower(.5);

        opmode.sleep(750);

        out.rightVex.setPower(0);
        out.leftVex.setPower(0);



    }

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

    public void partyMode()
    {
        ElapsedTime time = new ElapsedTime();
        time.reset();

        while(time.seconds() < 3)
        {
            fr.setPower(1);
            br.setPower(1);
            fl.setPower(-1);
            bl.setPower(-1);
        }

        time.reset();
        while(time.seconds() < 3)
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

    public void strafeMove(double target, double timeout, double power)
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
        double goal = 0;
        boolean isRight;

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
                turn(.3, false);
            }
            else {
                turn(.3, true);
            }


        } while (opMode.opModeIsActive() && Math.abs(goal - sensors.getGyroYaw()) > 2 && runtime.milliseconds() < timeOutMS);

        snowWhite();
    }


    public void gyroTurnNinety(double timeOutMS) {

        ElapsedTime runtime = new ElapsedTime();
        double goal = 90;
        //boolean isRight;

        do  {



            opMode.telemetry.addData("Goal", goal);
            opMode.telemetry.addData("Current Heading", sensors.getGyroYaw());
            opMode.telemetry.update();
            if (sensors.getGyroYaw() < goal) {
                turn(.4, true);
            }
            else {
                turn(.4, false);
            }


        } while (opMode.opModeIsActive() && Math.abs(goal - sensors.getGyroYaw()) > 2 && runtime.milliseconds() < timeOutMS);

        snowWhite();
    }

    public void gyroTurnMinusNinety(double timeOutMS) {

        ElapsedTime runtime = new ElapsedTime();
        double goal = -90;
        //boolean isRight;

        do  {


            opMode.telemetry.addData("Goal", goal);
            opMode.telemetry.addData("Current Heading", sensors.getGyroYaw());
            opMode.telemetry.update();
            if (sensors.getGyroYaw() < goal) {
                turn(.4, false);
            }
            else {
                turn(.4, true);
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

    public void pid (double angle, boolean right) {
        turnPID(angle, right, 0.6/angle,
                0.1/angle, 0.03/angle, 5);
    }

    public void xml (double speed, double target, double angle) {
        dulce(speed, speed - 0.1, target, angle);
    }

    public void set (LinearOpMode opMode2, Output out2) {
        opMode = opMode2;

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

    public void refract (Output out, boolean away) {
        lift(out, away);
        basket(out, away);
    }

    public void reflect (Output out, boolean away) {

        basket(out, away);
        lift(out, away);
    }

    public void lift (Output out, boolean raise) {
        if (raise) out.raiseLiftAuto();
        else out.lowerLiftAuto();
    }

    public void basket (Output out, boolean open) {
        if (open) out.openBasketAuto(1);
        else out.closeBasketAuto(1);
    }

    public void mufasa(Output out) {
        out.liftLeft.setPower(0);
        out.liftRight.setPower(0);
    }

    public double getTargetPercentile(double reading) {
        return Math.abs(getEncoderAverage() / reading);
    }

    public void doubleSkyStoneAuto(LinearOpMode opMode, Output out, double pos, double offset)
    {

        goToLocation(offset, pos);
        gyroTurnStraight(1000);
        thread(opMode, out,  -25);
        refract(out, false);
        out.rightVex.setPower(-.5);
        out.leftVex.setPower(.5);
        spline(0.8, 5, 90 * pos);
        //gyroTurnMinusNinety(1000);
        move(.9, -50 + offset);
        letGo(opMode, out);
        move(.9, 5);
        reflect(out, false);
        if(offset*pos == -5)
        {
            offset = 4;
        }
        move(1, (65 - offset));
        move(.7, 11);
        spline(.7 , 0, -90 * pos);
        gyroTurnStraight(1000);
        thread(opMode, out, -16);
        refract(out, false);
        out.rightVex.setPower(-.5);
        out.leftVex.setPower(.5);
        move(.9, 10);
        gyroTurnStraight(1000);
        spline(.7, 0, 90  * pos);
        move(1, -(58 - (offset)));
        thread(opMode, out, -15);
        reflect(out, false);
        move(.7, 12);
        snowWhite();
    }

    private void goToLocation(double offset, double pos) {

        if(offset == 0)
        {
            move(.6, -5);
            return;
        }

        double secondPos = offset / Math.abs(offset);
        spline(.8, -8, 90 * pos * secondPos);
        spline(.8, -Math.abs(offset), -90 * pos * secondPos);


    }

    public void foundationAuto(LinearOpMode opMode, Output out, double pos)
    {
        hook(opMode, out, false);
        move(1, -10);
        spline(.7, 0, 90 * pos);
        spline(.7, -5, -90 * pos);
        gyroTurnStraight(1000);
        lift(out, true);
        move(.6, -20);
        hook(opMode, out, true);
        opMode.sleep(1000);
        xml(.7, 40, 90 * pos);
        hook(opMode, out, false);
        move(1, 5);
        spline(.8, 0, -90 * pos);
        spline(.8,5 ,90 * pos);
        reflect(out, false);
        move(.7, 26);


    }

    private void letGo(LinearOpMode opMode, Output out) {
        ElapsedTime time = new ElapsedTime();

        out.rightVex.setPower(.5);
        out.leftVex.setPower(-.5);

        out.liftLeft.setPower(1);
        out.liftRight.setPower(1);

        while(time.milliseconds() < 1000 && opMode.opModeIsActive())
        {

        }

        out.leftVex.setPower(0);
        out.rightVex.setPower(0);

        out.liftLeft.setPower(0);
        out.liftRight.setPower(0);


    }

    public void doubleSkyStoneStackAuto(LinearOpMode opMode, Output out, double pos)
    {
        thread(opMode, out,-30);
        refract(out, false);
        spline(0.8, 3, 90 * pos);
        spline(0.8, -70, -90 * pos);
        hook(opMode, out, false);
        gyroTurnStraight(1000);
        vex(out, false);
        pass(opMode, out, -28);
        kill(opMode, out);
        move(.5, -2);
        hook(opMode, out, true);
        xml(.9, 40, 90 * pos);
        hook(opMode, out, false);
        move(.7, 10);
        reflect(out, false);
        strafeMove(-12 * pos, 5, 1);
        move(1, 87);
        spline(.8, 0, -90 * pos);
        gyroTurnStraight(1000);
        thread(opMode, out,-3);
        refract(out, false);
        spline(.8, 10, 90 * pos);
        move(1, -60);

    }
    public void singleSkyStoneStackAuto (LinearOpMode opMode, Output out, double pos) {

        thread(opMode, out,-30);
        refract(out, false);
        spline(1, 3, 90 * pos);
        spline(1, -70, -90 * pos);
        hook(opMode, out, false);
        gyroTurnStraight(1000);
        vex(out, false);
        pass(opMode, out, -25);
        kill(opMode, out);
        move(.5, -2);
        hook(opMode, out, true);

        out.liftRight.setPower(.25);
        out.liftLeft.setPower(.25);

        opMode.sleep(1000);

        mufasa(out);


        xml(.9, 40, 90 * pos);
        hook(opMode, out, false);
        move(1, 10);
        reflect(out, false);
        move(1, 25);
    }

    public void pass (LinearOpMode opMode, Output out, double reverseInches) {
        boolean liftCheck = false;
        boolean moveCheck = false;
        double passSpeed = 0.3;

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

        fl.setPower(-passSpeed);
        fr.setPower(-passSpeed);
        bl.setPower(-passSpeed);
        br.setPower(-passSpeed);

        while (opMode.opModeIsActive() && (!liftCheck || !moveCheck)) {

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

            if (out.encoderLevelCount * out.blockHeight * 1.5 <=
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

            if (out.encoderLevelCount * out.blockHeight * 1.5 <=
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

    public void spline (double originalSpeed,
                     double target, double angle) {
        move(originalSpeed, target);
        rotate(originalSpeed *
                0.5, angle);
    }

    public void dulce (double originalSpeed, double newSpeed,
                      double target, double angle) {
        move(originalSpeed, target);
        rotate(newSpeed, angle);
    }

    public void candy (double speed, double target, double angle) {
        move(speed, target);
        best(angle);
    }

    public void best (double angle) {
        boolean neg;
        if (isNeg(angle)) neg = false;
        else neg = true;
        pid(angle, neg);
    }

    public double getError (Sensors sensors, double targetHeading) {
        double test = sensors.getBestYaw();
        double newTest = test - targetHeading;
        newTest = Math.abs(newTest);
        return newTest;
    }

    public double errorPercentile (Sensors sensors,
                                   double targetHeading, double initial) {
        double completed = initial - getError(sensors, targetHeading);
        return completed / initial;
    }

    public void foundation (LinearOpMode opMode, Output out, boolean rotation,
                            Sensors sensors, double heading) {
        boolean liftCheck = false;
        boolean basketCheck = false;
        boolean moveCheck = false;

        boolean liftCheck2 = false;
        boolean liftCheck3 = false;

        resetEncoders();
        count = 0;

        double initial = getError(sensors, heading);

        out.blockCount++;
        if (out.pushBlock.getPosition() != 1)
            out.pushBlock.setPosition(1);

        out.rightVex.setPower(.5);
        out.leftVex.setPower(-.5);
        runtime.reset();

        out.liftRight.setPower(out.LIFTPOWER);
        out.liftLeft.setPower(out.LIFTPOWER);

        turn(0.4, rotation);

        while (opMode.opModeIsActive() && (!basketCheck || !liftCheck || !moveCheck)) {

            if (runtime.milliseconds() >=
                    out.HORIZONTALEXTENSIONTIME) {
                basketCheck = true;
                out.rightVex.setPower(0);
                out.leftVex.setPower(0);
            }

            if (getError(sensors, heading) <= 5) {
                moveCheck = true;
                snowWhite();
            }

            else if (errorPercentile(sensors, heading, initial) >= 95) {
                turn(0.1, rotation);
            }

            else if (errorPercentile(sensors, heading, initial) >= 80) {
                turn(0.2, rotation);
            }

            else if (errorPercentile(sensors, heading, initial) >= 70) {
                turn(0.3, rotation);
            }

            if (out.encoderLevelCount * out.blockHeight * 1.5 <=
                    out.averageLiftPosition()) {
                liftCheck = true;
                mufasa(out);
                out.top = true;
            }

            else if (out.top && out.averageLiftPosition() >
                    out.MAXHEIGHT * out.encoderLevelCount) {
                liftCheck = true;
                mufasa(out);
            }

        /*else if (liftCheck3 &&
                !liftCheck2 && !liftCheck) {

            if(out.averageLiftPosition() >= 0 && out.time.milliseconds()
                    < 1000 && opMode.opModeIsActive()) liftCheck2 = false;

            else {
                liftCheck2 = true;
                mufasa(out);

                out.liftRight.setPower(1);
                out.liftLeft.setPower(1);
            }

        }

        else if (liftCheck3 && liftCheck2
                && !liftCheck) {

            if (out.encoderLevelCount * out.blockHeight * 1.5 <=
                    out.averageLiftPosition()) {
                liftCheck = true;
                mufasa(out);
                out.top = true;
            }
        }

         */

        }
    }

    public void move (double speed, double target) {
        double neg;
        if (isNeg(target)) neg = -1;
        else neg = 1;
        encoderMove(speed * neg,
                Math.abs(target), 5);
    }

    public boolean isNeg (double neg) {
        if (Math.abs(neg) != neg)
            return true;
        else return false;
    }

    public void rotate (double speed, double angle) {
        boolean neg;
        if (isNeg(angle))
            neg = false;
        else neg = true;
        if (opMode.opModeIsActive()) {
            turnGyro(speed,
                    Math.abs(angle),
                    neg, 5);
        }
    }

    public void tread (Output out, boolean raise,
                       double speed, double target) {
        lift(out, raise);
        rotate(speed, target);
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

}




