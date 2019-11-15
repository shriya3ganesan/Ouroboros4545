package org.firstinspires.ftc.teamcode.teamcode.NewAuto;

public class Motor_Power_Spline {
    double leftPower;
    double rightPower;
    public static double noLoadSpeed = 31.4 ; // Max Angular Velocity in radians/second for 20 : 1 motor
    public static double stallTorque = 2.1; // Max Torque in Newton Meters for 20 : 1 motor
    public static double robotlength = 1;


    @Override
    public String toString() {
        return "Left Motor : " + leftPower + "    Right Power : " + rightPower;
    }

    public double getLeftPower() {
        return leftPower;
    }

    public double getRightPower() {
        return rightPower;
    }

    public static double aungular_velocity( double dX, double dY, double sX, double sY)
    {
        return (1/(1+Math.pow(dY/dX, 2))) * ((sY * dX - sX * dY) / (Math.pow(dX, 2)));
    }

    public static double setLeftPower(double aungularVelocity) {


        double leftVelocity = 1 + robotlength * aungularVelocity / 2;
        return (-1) * (stallTorque / noLoadSpeed) * leftVelocity
                + stallTorque * leftVelocity;
    }

    public static double setRightPower(double aungularVelocity) {
        double rightVelocity = 1 - robotlength * aungularVelocity / 2;
        return (-1) * (stallTorque / noLoadSpeed) * rightVelocity
                + stallTorque * rightVelocity;
    }

    public Motor_Power_Spline(double leftPower, double rightPower) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
    }
}

