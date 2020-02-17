package NewCode.LeoLibraries.LeoLibraries;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.security.spec.EllipticCurve;


public abstract class TeleLib extends OpMode {

    // ===========Static Variables================
    private static final double PICKUP = .9;
    private static final double IDLE = 0;
    private static double speedProp = 1;
    private static final double MAXHEIGHT = 38; // Precautions : Set to 20 inches if cabled wire broken
    static final double DISTANCE_BETWEEN_BLOCKS = 4.0; // In Inches
    static final double HORIZONTALEXTENSIONTIME = 1000;
    static final double encoderLevelCount = (360 / (Math.PI * .53));
    static final double BLOCKHEIGHT = 5.0; //Block Height In Inches

    static double LIFTPOWER = 1;
    static double HOOKDOWN = .60;
    static double HOOKUP = 1.0;

    private static double motorCounts = 518.4;
    private static double gearUp = 1;
    public static double wheelDiam = 4;
    public static double noLoadSpeed = 31.4 ; // Max Angular Velocity in radians/second for 20 : 1 motor
    public static double stallTorque = 2.1; // Max Torque in Newton Meters for 20 : 1 motor
    private static double inchCounts = (motorCounts / gearUp) / (wheelDiam * Math.PI);

    // ===========Utility============
    ElapsedTime time;
    boolean top;
    boolean bottom;
    double level = 0;
    double blockCount = 1.0;
    double prevEncoderPos = 0;
    private boolean toggled = false;

    //========Intake===========

    public DcMotor rightSide;
    public DcMotor leftSide;

    //========Drive Base========
    double right_stick_x;
    double left_stick_x;
    double left_stick_y;

    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;


    // CFM variables
    private static final double  massFoundation = 1.905; // Mass in kg
    private static final double massStone = .1882;
    static final double muBlocks = .78;
    static final double muMat = .535;
    double fix = 3.0;
    double tolerance = .05;
    double mass = 0.0;
    double maxCFM_Velocity = 0.0;
    double numberStackedBlocks = 0;
    boolean cfmToggled = false;

    //========Output==========

    double right_stick_y_output;
    double left_stick_y_output;


    public Servo pushBlock;
    public Servo hookRight;
    public Servo hookLeft;
    public Servo cap;
    public CRServo rightVex;
    public CRServo leftVex;
    public DcMotor liftRight;
    public DcMotor liftLeft;



    @Override
    public void init() {

        //Drive base

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


        //Output

        cap = hardwareMap.servo.get("Cap");
        hookLeft = hardwareMap.servo.get("LHook");
        hookRight = hardwareMap.servo.get("RHook");
        pushBlock = hardwareMap.servo.get("PB");
        rightVex = hardwareMap.crservo.get("ROut");
        leftVex = hardwareMap.crservo.get("LOut");
        liftLeft = hardwareMap.dcMotor.get("LLift");
        liftRight = hardwareMap.dcMotor.get("RLift");




        hookLeft.setDirection(Servo.Direction.FORWARD);
        hookRight.setDirection(Servo.Direction.FORWARD);
        cap.setDirection(Servo.Direction.FORWARD);

        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetLiftEncoders();
        numberStackedBlocks = 0;

        //Intake

        rightSide = hardwareMap.dcMotor.get("RIn");
        leftSide = hardwareMap.dcMotor.get("LIn");

        rightSide.setDirection(DcMotor.Direction.REVERSE);
        leftSide.setDirection(DcMotor.Direction.FORWARD);

        rightSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        rightSide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        time = new ElapsedTime();

    }



    //===============Drive Base Methods=================


    public void cfmSpeed()
    {
        //Foundation Moving Toggle
        //Toggle sets speed such that the robot can move the fastest
        //while moving the foundation and not dropping any blocks
        //Takes into account the mass of the foundation and block stack
        //and the friction of the floor

        if(gamepad2.dpad_up)
        {
            ElapsedTime time = new ElapsedTime();
            while(time.milliseconds() < 300)
            {

            }
            numberStackedBlocks++;
        }
        else if(gamepad2.dpad_down)
        {
            ElapsedTime time = new ElapsedTime();
            while(time.milliseconds() < 300)
            {

            }
            numberStackedBlocks--;
        }

        if(gamepad1.y)
        {
            ElapsedTime time = new ElapsedTime();
            while(time.milliseconds() < 300)
            {}
            if(!cfmToggled)
            {
                mass = massFoundation + numberStackedBlocks * massStone;
                maxCFM_Velocity = fix * Math.sqrt((9.81 * (tolerance / numberStackedBlocks) * massStone * (numberStackedBlocks + 1) * muBlocks) / mass);

                speedProp = Math.abs(maxCFM_Velocity);
                cfmToggled = true;
            }
            else {
                speedProp = 1;
                cfmToggled = false;
            }
        }

        telemetry.addData("CFM Toggle : ", cfmToggled);


    }

    public void arcadedrive()
    {
        left_stick_y = gamepad1.left_stick_y;
        left_stick_x = gamepad1.left_stick_x;
        right_stick_x = gamepad1.right_stick_x;

        if (Math.abs(left_stick_x) > 0.05 ||
                Math.abs(left_stick_y) > 0.05 ||
                Math.abs(right_stick_x) > 0.05) {

            fl.setPower(speedProp * ((left_stick_y - left_stick_x) - right_stick_x));
            fr.setPower(speedProp * ((left_stick_y + left_stick_x) + right_stick_x));
            bl.setPower(speedProp * (left_stick_y + left_stick_x) - right_stick_x);
            br.setPower(speedProp * (left_stick_y - left_stick_x) + right_stick_x);
        }
        else {
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
        }
    }

    public void halfspeed()
    {
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
        telemetry.addData("Half Speed Toggled : ", speedProp == .5);
    }


    //===========Output Methods===============

    private void resetLiftEncoders() {
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void horizontalLiftTele() {

        right_stick_y_output = gamepad2.right_stick_y;

        if (Math.abs(right_stick_y_output) >= .075) {
            rightVex.setPower(right_stick_y_output / 2);
            leftVex.setPower(-right_stick_y_output / 2);
        }
        else {
            rightVex.setPower(0);
            leftVex.setPower(0);

        }
    }

    private void hookToggle()
    {
        if(!toggled && gamepad2.y)
        {
            toggled = true;


            time.reset();
            while(time.milliseconds() < 300)
            {

            }

            //Hook Down
            hookLeft.setPosition(1);
            hookRight.setPosition(0);
        }
        else if(toggled && gamepad2.y)
        {
            toggled = false;

            time.reset();
            while (time.milliseconds() < 300)
            {

            }
            hookLeft.setPosition(.3);
            hookRight.setPosition(1);
        }
    }


    private double averageLiftPosition() {
        int count = 2;

        if (liftRight.getCurrentPosition() == 0 && !bottom) count--;
        if (liftLeft.getCurrentPosition() == 0 && !bottom) count--;
        if (count == 0) return 0;
        return (liftLeft.getCurrentPosition() + liftRight.getCurrentPosition()) / count;

    }


    private void lift() {


        left_stick_y_output = gamepad2.left_stick_y;

        if (averageLiftPosition() <= 0) {
            bottom = true;
            resetLiftEncoders();
        }
        else if (averageLiftPosition() >= MAXHEIGHT * encoderLevelCount) {
            top = true;
        }
        else
        {
            top = false;
            bottom = false;
        }

        if(gamepad2.b)
        {
            time.reset();
            while(time.milliseconds() < 100){}
            top = false;
            bottom = false;
        }

        if (top && -left_stick_y_output > 0) {
            liftRight.setPower(0);
            liftLeft.setPower(0);
        }
        else if (bottom && -left_stick_y_output < 0) {
            liftRight.setPower(0);
            liftLeft.setPower(0);
        }else if (Math.abs(left_stick_y_output) > .05) {
            liftRight.setPower(-left_stick_y_output);
            liftLeft.setPower(-left_stick_y_output);
        }
        else {
            liftRight.setPower(0);
            liftLeft.setPower(0);
        }

    }


    private void encoderCalibrate()
    {
        if(gamepad2.left_stick_button && gamepad2.right_stick_button)
        {
            time.reset();
            while(time.milliseconds() < 100){}
            resetLiftEncoders();
        }
    }
    private void Output_Telemetry()
    {
        telemetry.addData("Lift Bottom : ", bottom);
        telemetry.addData("Lift Top : ", top);
        telemetry.addData("Lift Right : ", liftRight.getCurrentPosition());
        telemetry.addData("Lift Left : ", liftLeft.getCurrentPosition());
        telemetry.addData("Block Count", numberStackedBlocks);
        telemetry.addData("Left Hook", hookLeft.getPosition());
        telemetry.addData("Right Hook", hookRight.getPosition());
        telemetry.addData("Push Block", pushBlock.getPosition());
        telemetry.addData("CFM Power", maxCFM_Velocity);

    }
    public void output()
    {
        horizontalLiftTele();
        hookToggle();
        lift();
        encoderCalibrate();


        if(blockCount == 2)
        {
            level++;
            blockCount = 0;
        }

        if(Math.abs(gamepad2.left_trigger) > .5)
        {
            pushBlock.setPosition(0);
        }
        else if(Math.abs(gamepad2.right_trigger) > .5)
        {
            pushBlock.setPosition(.5);
        }else if(gamepad2.dpad_up)
        {
            ElapsedTime time = new ElapsedTime();

            while(time.milliseconds() < 300)
            {
            }

            if(cap.getPosition() == 0)
            {
                cap.setPosition(1);
            }else if(cap.getPosition() == 1)
            {
                cap.setPosition(0);
            }

        }

        Output_Telemetry();
    }

    //===========Intake Methods=============

    public void intake()
    {
        if(gamepad2.right_bumper) //set game pad button to x, could change, survey people
        {
            rightSide.setPower(PICKUP);
            leftSide.setPower(PICKUP);


        }else if (gamepad2.left_bumper) {
            rightSide.setPower(-PICKUP);
            leftSide.setPower(-PICKUP);
        }
        else
        {
            rightSide.setPower(IDLE);
            leftSide.setPower(IDLE);
        }
    }
}
