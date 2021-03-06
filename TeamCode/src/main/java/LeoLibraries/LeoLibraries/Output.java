package LeoLibraries.LeoLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Output {


    private static final double MAXLEVEL = 14;
    public static final double MAXHEIGHT = 34; // Inches; set to 20 inches cap for broken wire
    private static final double DISTANCE_TO_BUILD_ZONE = 1; // what ever distance is from foundation to build zone
    public Servo pushBlock;
    public Servo hookRight;
    public Servo hookLeft;
    public Servo rightHookArm;
    public Servo leftHookArm;


    public DcMotor liftRight;
    public DcMotor liftLeft;
    public Servo leftElbow;
    public Servo rightElbow;

    boolean resetOuttakeToggle;
    boolean openBasketToggle;

    LinearOpMode opMode;
    ElapsedTime time = new ElapsedTime();

    //1.055 Inches Base, Foundation is 2.25 inches

    boolean top;
    boolean bottom;

    static final double DISTANCE_BETWEEN_BLOCKS = 4.0; // In Inches
    public static final double HORIZONTALEXTENSIONTIME = 1000 ; // Time it takes for lift to extend out = length of lift / speed of motors
    public static final double encoderLevelCount = (360 / (Math.PI * .53));

    public double LIFTPOWER = 1;
    double HOOKDOWN = 0;
    double HOOKUP = 1.0;


    double k = 1.0;
    double level = 0;
    double blockCount = 1.0;
    double blockHeight = 4.0; //Block Height In Inches
    double prevEncoderPos = 0;


    public Output(LinearOpMode opMode) {


        this.opMode = opMode;
        time.reset();

        top = false;
        bottom = true;

        level = 0.0;
        blockCount = 0.0;


        pushBlock = opMode.hardwareMap.servo.get("PB");
        liftLeft = opMode.hardwareMap.dcMotor.get("LLift");
        liftRight = opMode.hardwareMap.dcMotor.get("RLift");
        hookLeft = opMode.hardwareMap.servo.get("LHook");
        hookRight = opMode.hardwareMap.servo.get("RHook");
        rightElbow = opMode.hardwareMap.servo.get("RElbow");
        leftElbow = opMode.hardwareMap.servo.get("LElbow");
        rightHookArm = opMode.hardwareMap.servo.get("RArm");
        leftHookArm = opMode.hardwareMap.servo.get("LArm");



        hookLeft.setDirection(Servo.Direction.FORWARD);
        hookRight.setDirection(Servo.Direction.REVERSE);

        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        prevEncoderPos = averageLiftPosition();
        if (prevEncoderPos > 0) {
            bottom = false;
        }

        resetLiftEncoders();
        hookUp();
    }


    public int getNumberOfBlocks()
    {
        return (int) (level * 2 + blockCount);
    }



    //-------------------------------------------HOOK STUFF-----------------------------------------------



    public void hookDown() {
        hookRight.setPosition(-.5);
        hookLeft.setPosition(0);
    }

    public void hookUp() {
        hookRight.setPosition(1);
        hookLeft.setPosition(1);


    }

    public void skyStoneHooks(boolean up)
    {
        if(up)
        {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                }
            });
            rightElbow.setPosition(0);
            leftElbow.setPosition(1);
        }else
        {
            leftElbow.setPosition(0);
            rightElbow.setPosition(1);
        }
    }


    public void armUp(boolean left)
    {
        if (left) {
            leftHookArm.setPosition(.4);
        }
        else{
            rightHookArm.setPosition(.6);
        }
    }

    public void armDown(boolean left)
    {
        if (left) {
            leftHookArm.setPosition(1);

        }
        else {
            rightHookArm.setPosition(0);

        }
    }

    public void elbowDown(boolean left) {
        if (left) {
            leftElbow.setPosition(1);
        }
        else {
            rightElbow.setPosition(0);
        }
    }

    public void elbowUp(boolean left) {
        if (left) {
            leftElbow.setPosition(0);
        }
        else {
            rightElbow.setPosition(1);
        }
    }



    //--------------------------------------------------LIFT STUFF---------------------------------------------------------


    public void toggleBrake(boolean brake)
    {
        if(brake)
        {
            liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }else if(!brake)
        {
            liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    private void resetLiftEncoders() {
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    public double averageLiftPosition() {
        int count = 2;

        if (liftRight.getCurrentPosition() == 0 && !bottom) count--;
        if (liftLeft.getCurrentPosition() == 0 && !bottom) count--;
        if (count == 0) return 0;
        return (liftLeft.getCurrentPosition() + liftRight.getCurrentPosition()) / count;

    }


    public void raiseLiftAuto() {
        liftRight.setPower(LIFTPOWER);
        liftLeft.setPower(LIFTPOWER);

        while (encoderLevelCount * blockHeight * 1.5 > averageLiftPosition() && opMode.opModeIsActive()) {

            if(top && averageLiftPosition() > MAXHEIGHT * encoderLevelCount)
            {
                liftLeft.setPower(0);
                liftRight.setPower(0);
                return;
            }
        }

        liftLeft.setPower(0);
        liftRight.setPower(0);
        top = true;
    }

    public void raiseLiftAuto(double time) {

        ElapsedTime runtime = new ElapsedTime();
        liftRight.setPower(LIFTPOWER);
        liftLeft.setPower(LIFTPOWER);

        while (runtime.seconds() < time  && opMode.opModeIsActive()) {

            if(top && averageLiftPosition() > MAXHEIGHT * encoderLevelCount)
            {
                liftLeft.setPower(0);
                liftRight.setPower(0);
                return;
            }
        }

        liftLeft.setPower(0);
        liftRight.setPower(0);
        top = true;
    }


    public void lowerLiftAuto()
    {

        time.reset();
        while(averageLiftPosition() >= 0 && time.milliseconds() < 1000 && opMode.opModeIsActive())
        {

            liftRight.setPower(-LIFTPOWER);
            liftLeft.setPower(-LIFTPOWER);

        }

        liftLeft.setPower(0);
        liftRight.setPower(0);
    }



//--------------------------------------------------VEX MOTOR STUFF-------------------------------------------------------------




    public void clamp()
    {
        pushBlock.setPosition(.5);
    }
    public void unclamp()
    {
        pushBlock.setPosition(0);
    }

}