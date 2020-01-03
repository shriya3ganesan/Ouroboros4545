package NewCode.LeoLibraries.LeoLibraries;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



public abstract class TeleLib extends OpMode {

    // ===========Static Variables================
    private static final double PICKUP = 1.0;
    private static final double IDLE = 0;
    private static double speedProp = 1;
    private static final double MAXHEIGHT = 34; // Precautions : Set to 20 inches if cabled wire broken
    static final double DISTANCE_BETWEEN_BLOCKS = 4.0; // In Inches
    static final double HORIZONTALEXTENSIONTIME = 1000;
    static final double encoderLevelCount = (360 / (Math.PI * .53));
    static final double BLOCKHEIGHT = 5.0; //Block Height In Inches

    static double LIFTPOWER = 1;
    static double HOOKDOWN = .60;
    static double HOOKUP = 1.0;

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
    //========Output==========

    double right_stick_y_output;
    double left_stick_y_output;

    public Servo pushBlock;
    public Servo hookRight;
    public Servo hookLeft;
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

        fl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //Output

        pushBlock = hardwareMap.servo.get("PB");
        rightVex = hardwareMap.crservo.get("ROut");
        leftVex = hardwareMap.crservo.get("LOut");
        liftLeft = hardwareMap.dcMotor.get("LLift");
        liftRight = hardwareMap.dcMotor.get("RLift");

        hookLeft = hardwareMap.servo.get("LHook");
        hookRight = hardwareMap.servo.get("RHook");


        hookLeft.setDirection(Servo.Direction.FORWARD);
        hookRight.setDirection(Servo.Direction.FORWARD);

        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetLiftEncoders();

        //Intake

        rightSide = hardwareMap.dcMotor.get("RIn");
        leftSide = hardwareMap.dcMotor.get("LIn");

        rightSide.setDirection(DcMotor.Direction.FORWARD);
        leftSide.setDirection(DcMotor.Direction.REVERSE);

        rightSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        rightSide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        time = new ElapsedTime();

    }



    //===============Drive Base Methods=================

    public void arcadedrive()
    {
        left_stick_y = gamepad1.left_stick_y;
        left_stick_x = gamepad1.left_stick_x;
        right_stick_x = -gamepad1.right_stick_x;

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
            rightVex.setPower(-right_stick_y_output / 2);
            leftVex.setPower(right_stick_y_output / 2);
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
            hookLeft.setPosition(0);
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
        telemetry.addData("Prev Encoder Pos : ", prevEncoderPos);
        telemetry.addData("Average : ", averageLiftPosition());
        telemetry.addData("Block Count", blockCount);
        telemetry.addData("Level", level);
        telemetry.addData("Vex Power Right", rightVex.getPower());
        telemetry.addData("Vex Power Left", leftVex.getPower());
        telemetry.addData("Left Hook", hookLeft.getPosition());
        telemetry.addData("Right Hook", hookRight.getPosition());
        telemetry.addData("push block", pushBlock.getPosition());
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
            pushBlock.setPosition(.3);
        }
        else if(Math.abs(gamepad2.right_trigger) > .5)
        {
            pushBlock.setPosition(1);
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
