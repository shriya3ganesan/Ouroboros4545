package org.firstinspires.ftc.teamcode.teamcode.NewAuto;


public class Function {

    double arclength = 0;

    public double aX = 0;
    public double bX = 0;
    public double cX = 0;
    public double dX = 0;

    public double aY = 0;
    public double bY = 0;
    public double cY = 0;
    public double dY = 0;

    double startT;
    double endT;

    public double getArcLength()
    {

        double delta_t = (endT - startT) / 1000.0;
        double t_i;
        double sum = 0.0;

        for(double i = 0; i < 1000.0; i++)
        {
            t_i = startT + i * delta_t;
            sum += Math.sqrt(1 + Math.pow(getDerY(t_i), 2));

        }
        return delta_t * sum;
    }


    public double getStartT() {
        return startT;
    }

    public double getEndT() {
        return endT;
    }

    public Function(double aX, double bX, double cX, double dX, double aY, double bY, double cY, double dY, double startT, double endT) {


        this.startT = startT;
        this.endT = endT;

        this.aX = aX;
        this.bX = bX;
        this.cX = cX;
        this.dX = dX;

        this.aY = aY;
        this.bY = bY;
        this.cY = cY;
        this.dY = dY;

        this.arclength = getArcLength();
    }

    public double getFuncX(double t)
    {
        t = t - startT;
        return aX + bX * t + cX * Math.pow(t, 2) + dX * Math.pow(t, 3);
    }

    public double getDerX(double t)
    {
        t = t - startT;
        return bX + 2 * cX * t + 3 * dX * Math.pow(t, 2);
    }

    public double getSecondDerX(double t)
    {
        t = t - startT;
        return 2 * cX + 6 * dX * t;
    }

    public double getSecondDerY(double t)
    {
        t = t - startT;
        return 2 * cY + 6 * dY * t;
    }
    public double getFuncY(double t)
    {
        t = t - startT;
        return aY + bY*t + cY*Math.pow(t, 2) + dY*Math.pow(t, 3);
    }

    public double getDerY(double t)
    {
        t = t - startT;
        return bY + 2 * cY * t + 3 * dY * Math.pow(t, 2);
    }

    @Override
    public String toString() {



        return "( t,"  + aY + " + " + bY + "(t -" + startT + ") + " + cY + "(t -" + startT + ")^2 + " +
                dY + "(t -" + startT + ")^3 ) ";
    }
}