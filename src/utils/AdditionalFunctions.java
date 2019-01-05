package utils;

import entities.worldobjects.cube.Cube;
import maths.Vector4d;

import java.awt.*;
import java.util.ArrayList;


/**
 * Created by perri on 05/01/2019.
 */
public class AdditionalFunctions {


    public static ArrayList<Cube> createLineInCube(double x0, double y0, double z0, double x1, double y1, double z1) {

        ArrayList<Cube> line = new ArrayList<>();

        Cube start = new Cube(1, Color.GREEN, new Vector4d(x0, y0, z0));
        line.add(start);

        double drivingAxixX = Math.abs(x1 - x0);
        double drivingAxixY = Math.abs(y1 - y0);
        double drivingAxixZ = Math.abs(z1 - z0);

        double xs, ys, zs;

        xs = (x1 > x0?1:-1);
        ys = (y1 > y0?1:-1);
        zs = (z1 > z0?1:-1);


        // Driving axis is X-axis"
        if (drivingAxixX >= drivingAxixY && drivingAxixX >= drivingAxixZ){
            double p1 = 2 * drivingAxixY - drivingAxixX;
            double p2 = 2 * drivingAxixZ - drivingAxixX;
            while (x0 != x1) {
                x0 += xs;
                if (p1 >= 0){
                    y0 += ys;
                    p1 -= 2 * drivingAxixX;
                }
                if (p2 >= 0){
                    z0 += zs;
                    p2 -= 2 * drivingAxixX;
                }
                p1 += 2 * drivingAxixY;
                p2 += 2 * drivingAxixZ;
                line.add(new Cube(1, Color.GREEN, new Vector4d(x0, y0, z0)));
            }
        } else if(drivingAxixY >= drivingAxixX && drivingAxixY >= drivingAxixZ) {

            // Driving axis is Y-axis"
            double p1 = 2 * drivingAxixX - drivingAxixY;
            double p2 = 2 * drivingAxixZ - drivingAxixY;
            while (y0 != y1) {

                y0 += ys;
                if (p1 >= 0){
                    x0 += xs;
                    p1 -= 2 * drivingAxixY;
                }
                if (p2 >= 0){
                    z0 += zs;
                    p2 -= 2 * drivingAxixY;
                }
                p1 += 2 * drivingAxixX;
                p2 += 2 * drivingAxixZ;
                line.add(new Cube(1, Color.GREEN, new Vector4d(x0, y0, z0)));
            }

        } else if(drivingAxixZ >= drivingAxixX && drivingAxixZ >= drivingAxixY) {

            // Driving axis is Z-axis"
            double p1 = 2 * drivingAxixY - drivingAxixZ;
            double p2 = 2 * drivingAxixX - drivingAxixZ;
            while (z0 != z1) {
                z0 += zs;
                if (p1 >= 0){
                    y0 += ys;
                    p1 -= 2 * drivingAxixZ;
                }
                if (p2 >= 0) {
                    x0 += xs;
                    p2 -= 2 * drivingAxixZ;
                }
                p1 += 2 * drivingAxixY;
                p2 += 2 * drivingAxixX;
                line.add(new Cube(1, Color.GREEN, new Vector4d(x0, y0, z0)));
            }

        }


        return line;


    }
}
