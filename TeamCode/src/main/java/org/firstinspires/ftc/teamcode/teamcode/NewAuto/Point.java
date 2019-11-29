package org.firstinspires.ftc.teamcode.teamcode.NewAuto;


public class Point {
    double deltaT = 0;
    double x = 0;
    double y = 0;
    double t = 0;
    double derivative = 0;
    double secondDerivative = 0;
    double dX = 0;
    double dY = 0;
    double sdX = 0;
    double sdY = 0;

    public double getDeltat() {
        return deltaT;
    }

    public double getdX() {
        return dX;
    }

    public double getdY() {
        return dY;
    }

    public double getSdX() {
        return sdX;
    }

    public double getSdY() {
        return sdY;
    }

    public Point(double t, double y) {
        this.y = y;
        this.t = t;
        this.x = 0;
    }

    public Point(double t, double x, double y) {
        this.x = x;
        this.y = y;
        this.t = t;
    }
    public Point(double t, double y, double derivative, double secondDerivative, double deltaT)
    {
        this.x = x;
        this.y = y;
        this.t = t;
        this.derivative = derivative;
        this.secondDerivative = secondDerivative;
        this.deltaT = deltaT;
    }

    public Point(double t, double x, double y, double derivative, double secondDerivative, double dX, double dY, double sdX, double sdY) {
        this.x = x;
        this.y = y;
        this.t = t;
        this.derivative = derivative;
        this.secondDerivative = secondDerivative;
        this.dX = dX;
        this.dY = dY;
        this.sdX = sdX;
        this.sdY = sdY;
    }

    public double getDerivative() {
        return derivative;
    }

    public double getSecondDerivative() {
        return secondDerivative;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", "  + y +")";
    }
}
