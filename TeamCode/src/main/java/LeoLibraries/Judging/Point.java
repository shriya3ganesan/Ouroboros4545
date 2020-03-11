package LeoLibraries.Judging;


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
    double arcS = 0;

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
    public Point(double t, double x, double y, double derivative, double secondDerivative, double deltaT, double s)
    {
        this.x = x;
        this.y = y;
        this.t = t;
        this.derivative = derivative;
        this.secondDerivative = secondDerivative;
        this.deltaT = deltaT;
        this.arcS = s;
    }
    public double getArcS(){return arcS;}
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
        return "Point{" +
                "deltaT =" + deltaT +
                ", x =" + x +
                ", y =" + y +
                ", t =" + t +
                ", derivative =" + derivative +
                ", secondDerivative =" + secondDerivative +
                ", dX =" + dX +
                ", dY =" + dY +
                ", sdX =" + sdX +
                ", sdY =" + sdY +
                ", arcS =" + arcS +
                '}';
    }
}
