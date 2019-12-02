package org.firstinspires.ftc.teamcode.teamcode.NewAuto;

import java.util.ArrayList;

public class CubicSpline {


    public static void main(String[] args) {
        CubicSpline s = new CubicSpline();
        s.SplineOut(0, 0,
                    100,  100,
                    500, 80);
    }

    public void SplineOut(double t1, double y1, double t2, double y2, double t3, double y3)
    {
        if(t1 == t2 || t2 == t3 || t1 == t3)
        {
            System.out.println("ERROR : Incorrect Value Input");
            return;
        }

        ArrayList<Point> points = new ArrayList<>();

        points.add(new Point(t1, y1));
        points.add(new Point(t2, y2));
        points.add(new Point(t3,y3));

        Function[] f = makeSpline(points);

        System.out.println(f[0] + "\n" + f[1]);

        ArrayList<Motor_Power_Spline> s = splinePointsToMotorPoints(SplineToPoints(f));

        for(Motor_Power_Spline p : s)
        {
            System.out.println(p);
        }

    }

    public ArrayList<Motor_Power_Spline> splinePointsToMotorPoints(ArrayList<Point> splinePoints)
    {
        ArrayList<Motor_Power_Spline> s = new ArrayList<>();
        double rightp;
        double leftp;
        for(Point p : splinePoints)
        {

            leftp = Motor_Power_Spline.LeftPower(p.getDerivative(), p.getSecondDerivative());
            rightp = Motor_Power_Spline.RightPower(p.getDerivative(), p.getSecondDerivative());


            leftp = Math.abs(Math.round(leftp * 1000.0) / 1000.0);
            rightp = Math.abs(Math.round(rightp * 1000.0) / 1000.0);

            // Normalize Values
            if(leftp > 1 || rightp > 1)
            {
                if(leftp > rightp)
                {
                    rightp = rightp / leftp;
                    leftp = 1;

                }
                else if(rightp > leftp)
                {
                    leftp = leftp / rightp;
                    rightp = 1;

                }
                else
                {
                    rightp = 1;
                    leftp = 1;
                }
            }

            s.add(new Motor_Power_Spline(leftp, rightp, p.getDeltat(),p.getArcS()));

        }


        return s;


    }

    public ArrayList<Point> SplineToPoints(Function[] functions)
    {
        ArrayList<Point> splinePoints = new ArrayList<>();
        Function spline1 = functions[0];
        Function spline2 = functions[1];
        double arclength = spline1.getArcLength() + spline2.getArcLength();

        System.out.println(arclength);
        double delta_arclength = arclength / 100.0;

        double t = 0;
        double delta_t = delta_arclength / Math.sqrt(1 + Math.pow(spline1.getFuncY(functions[0].getStartT()) ,2));

        int i = 0;
        for(double s = 0 + delta_arclength; s < arclength;  s += delta_arclength)
        {
            Function f = functions[i];
            if(s > f.getArcLength() && i < 1)
            {
                i++;
            }

            t += delta_t;
            delta_t = delta_arclength / Math.sqrt(1 + Math.pow(spline1.getDerY(t) ,2));
            splinePoints.add(new Point(t, f.getFuncY(t), f.getDerY(t), f.getSecondDerY(t), delta_t, delta_arclength));
        }

        return splinePoints;
    }


    public Function[] makeSpline(ArrayList<Point> points)
    {



        double[][] constraints = new double[16][16];
        double[] solutions = new double[16];

        double t1 = points.get(0).getT();
        double t2 = points.get(1).getT();
        double t3 = points.get(2).getT();


        if(t1 == t2 || t2 == t3 || t1 == t3)
        {
            System.out.println("ERROR : Incorrect Value Input");
            return null;
        }
        //1.)   Solutions for Constraints

        solutions[0] = points.get(0).getX();
        solutions[1] = points.get(1).getX();
        solutions[2] = points.get(1).getX();
        solutions[3] = points.get(2).getX();
        solutions[4] = 0;
        solutions[5] = 0;
        solutions[6] = points.get(0).getY();
        solutions[7] = points.get(1).getY();
        solutions[8] = points.get(1).getY();
        solutions[9] = points.get(2).getY();
        solutions[10] = 0;
        solutions[11] = 0;
        solutions[12] = 0;
        solutions[13] = 0;
        solutions[14] = 0;
        solutions[15] = 0;


        //2.)   Set Constraints


        //X constraints

        constraints[0][0] = 1;

        constraints[1][0] =  1;
        constraints[1][1] = t2 - t1;
        constraints[1][2] = Math.pow(t2 - t1, 2);
        constraints[1][3] = Math.pow(t2 - t1, 3);

        constraints[2][4] =  1;

        constraints[3][4] =  1;
        constraints[3][5] = t3 - t2;
        constraints[3][6] = Math.pow(t3 - t2, 2);
        constraints[3][7] = Math.pow(t3 - t2, 3);

        constraints[4][1] = 1;
        constraints[4][5] = -1;
        constraints[4][2] = 2 * (t2 - t1);
        constraints[4][3] = 3 * Math.pow(t2 - t1, 2);

        constraints[5][2] = 2;
        constraints[5][6] = -2;
        constraints[5][3] = 6 * (t2 - t1);


        // Y Constraints

        constraints[6][8] =  1;

        constraints[7][8] =  1;
        constraints[7][9] = t2 - t1;
        constraints[7][10] = Math.pow(t2 - t1, 2);
        constraints[7][11] = Math.pow(t2 - t1, 3);

        constraints[8][12] =  1;

        constraints[9][12] =  1;
        constraints[9][13] = t3 - t2;
        constraints[9][14] = Math.pow(t3 - t2, 2);
        constraints[9][15] = Math.pow(t3 - t2, 3);

        constraints[10][9] = 1;
        constraints[10][13] = -1;
        constraints[10][10] = 2 * (t2 - t1);
        constraints[10][11] = 3 * Math.pow(t2 - t1, 2);

        constraints[11][10] = 2;
        constraints[11][14] = -2;
        constraints[11][11] = 6 * (t2 - t1);


        //X and Y Optimization Constraints - Ouroboros Method


        constraints[12][0]  = 0;
        constraints[12][1]  = 0;
        constraints[12][2]  = -2 * (t2 - t1);
        constraints[12][3]  = -3 * Math.pow(t2 - t1, 2);
        constraints[12][4]  = 0;
        constraints[12][5]  = 0;
        constraints[12][6]  = 2 * (t3 - t2);
        constraints[12][7]  = 3 * Math.pow(t3 - t2, 2);

       /* constraints[13][8]  = 0;
        constraints[13][9]  = 1/2;
        constraints[13][10]  = 0;
        constraints[13][11]  = 0;
        constraints[13][12]  = 0;
        constraints[13][13]  = -1/2;
        constraints[13][14]  = (t3 - t2);
        constraints[13][15]  = (3/2) * Math.pow(t3 - t2, 2);*/


        constraints[13][8]   = 0;
        constraints[13][9]   = 1;
        constraints[13][10]  = 0;
        constraints[13][11]  = 0;
        constraints[13][12]  = 0;
        constraints[13][13]  = -1;
        constraints[13][14]  = 2.0 * (t3 - t2);
        constraints[13][15]  = 3.0 * Math.pow((t3 - t2), 2);

        constraints[14][4]  =  0;
        constraints[14][5]  = 1;
        constraints[14][6] = 2 * (t2 - t1);
        constraints[14][7] = 3 * Math.pow(t2 - t1, 2);

        constraints[15][12] = 0;
        constraints[15][13] = 1;
        constraints[15][14] = 2 * (t3 - t2);
        constraints[15][15] = 3 * Math.pow(t3 - t2, 2);



        /*int i = 0;
        for(double[] row : constraints)
        {
            for(double val : row)
            {
                System.out.print(val + " ");
            }
            System.out.print(" " + solutions[i]);
            System.out.println("");
            i++;
        }*/


        double[] solvedConstraints;

        GaussianElimination eq = new GaussianElimination();

        solvedConstraints = eq.lsolve(constraints, solutions);



        Function[] funcs = new Function[2];

        funcs[0] = new Function(0, 1, 0, 0,
                solvedConstraints[8], solvedConstraints[9], solvedConstraints[10], solvedConstraints[11], t1, t2);
        funcs[1] = new Function(0, 1, 0,0,
                solvedConstraints[12], solvedConstraints[13], solvedConstraints[14], solvedConstraints[15], t2, t3);

        return funcs;
    }

}
