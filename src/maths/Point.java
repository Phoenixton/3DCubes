package maths;

/**
 * Created by perri on 31/12/2018.
 */
public class Point {

    private Vector4d position;

    public Point() {
        this.position = new Vector4d();
    }

    public Point(Vector4d position) {
        this.position = position;
    }


    public Point(double x, double y, double z) {
        //settles for w = 1
        this.position = new Vector4d(x, y,z);
    }


    public Point(double x, double y, double z, double w) {
        //Just in case we need to manually set w
        this.position = new Vector4d(new double[]{x,y,z,w});
    }


    public Vector4d getPosition() {
        return position;
    }

    public void setPosition(Vector4d position) {
        this.position = position;
    }


    //toString

    public String toString() {
        return "This point is at " + this.position.toString();
    }
}
