package org.firstinspires.ftc.teamcode.teamcode.NewAuto;

public class Motor_Power_Spline {
    double leftPower;
    double rightPower;
    public static double noLoadSpeed = 31.4 ; // Max Angular Velocity in radians/second for 20 : 1 motor
    public static double stallTorque = 2.1; // Max Torque in Newton Meters for 20 : 1 motor
    public static double robotlength = 16;
    double deltaT;
    double deltaS;
    static int i = 0;
    static double arc;


    @Override
    public String toString() {
        i++;
        arc += deltaS;
        return i + "  Left Motor : " + leftPower + "    Right Power : " + rightPower + "   " + arc;
    }

    public double getDeltaT() {
        return deltaT;
    }

    public double getLeftPower() {
        return leftPower;
    }

    public double getRightPower() {
        return rightPower;
    }

    public static double aungular_velocity(double dY, double sY)
    {
        return (1/(1 + dY * dY)) * sY;
    }

    public static double LeftPower(double dY, double sY) {

        double aungularVelocity = aungular_velocity(dY, sY);
        double leftVelocity = 1 - (robotlength * aungularVelocity / 2);
        return (-1) * (stallTorque / noLoadSpeed) * leftVelocity
                + stallTorque * leftVelocity;
        //return leftVelocity;
    }

    public static double RightPower(double dY, double sY) {

        double aungularVelocity = aungular_velocity(dY, sY);
        double rightVelocity = 1 + (robotlength * aungularVelocity / 2);
        return (-1) * (stallTorque / noLoadSpeed) * rightVelocity
                + stallTorque * rightVelocity;
        //return rightVelocity;
    }

    public Motor_Power_Spline(double leftPower, double rightPower, double deltaT, double deltaS) {

        this.leftPower = leftPower;
        this.rightPower = rightPower;
        this.deltaT = deltaT;
        this.deltaS = deltaS;
    }
}

