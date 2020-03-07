package LeoLibraries.Judging;

public class Motor_Power_Spline {
    double leftPower;
    double rightPower;
    public static double noLoadSpeed = 31.4 ; // Max Angular Velocity in radians/second for 20 : 1 motor
    public static double stallTorque = 2.1; // Max Torque in Newton Meters for 20 : 1 motor
    public static double robotlength = 8;
    double deltaT;
    double deltaS;
    static int i = 0;
    static double arc;
    static double t = 0.0;


    @Override
    public String toString() {
        i++;
        t+= deltaT;
        arc += deltaS;
        return i + "  Left Motor : " + leftPower + "    Right Power : " + rightPower + "    Arc Length :     " + arc + "     T :     " + deltaT
                + " Time : " + t;
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

    public static double aungular_velocity(double d, double s)
    {
        return (1/(1 + d * d)) * s;
    }

    public static double LeftPower(double d, double s) {

        double aungularVelocity = aungular_velocity(d, s);
        double leftVelocity = 1 - (robotlength * aungularVelocity / 2);
        return (-1) * (stallTorque / noLoadSpeed) * leftVelocity
                + stallTorque * leftVelocity;
        //return leftVelocity;
    }

    public static double RightPower(double d, double s) {

        double aungularVelocity = aungular_velocity(d, s);
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

