package maths;

/**
 * Created by perri on 26/12/2018.
 */
public class Vector4d {

    private double[] coordinates;

    /**
     * If nothing is specified, we consider that the w coordinates == 1
     */
    public Vector4d() {
        this.coordinates = new double[4];
        this.coordinates[0] = 0;
        this.coordinates[1] = 0;
        this.coordinates[2] = 0;
        this.coordinates[3] = 1;
    }

    public Vector4d(double[] newCoordinates) {
        this.coordinates = new double[4];
        if(newCoordinates.length < 4) {
            this.coordinates[0] = newCoordinates[0];
            this.coordinates[1] =  newCoordinates[1];
            this.coordinates[2] =  newCoordinates[2];
            this.coordinates[3] = 1;
        } else {
            //I don't recommend changing the W ;)
            this.coordinates = newCoordinates;
        }
    }

    public Vector4d(double x, double y, double z) {
        this.coordinates = new double[4];
        this.coordinates[0] = x;
        this.coordinates[1] = y;
        this.coordinates[2] = z;
        this.coordinates[3] = 1;
    }




    public void homogen() {
        if(this.coordinates[3] != 0) {
            for(int i = 0; i < this.coordinates.length; i++) {
                this.coordinates[i] /= this.coordinates[3];
            }
        }
    }

    public void perspectiveDivide() {
        if(this.coordinates[2] != 0) {
            for(int i = 0; i < 2; i++) {
                this.coordinates[i] /= this.coordinates[2];
            }
        }
    }

    public void normalize() {
        double length = this.coordinates[0] + this.coordinates[1] + this.coordinates[2];
        this.coordinates[0] /= length;
        this.coordinates[1] /= length;
        this.coordinates[2] /= length;
    }


    public void multiplyByScalar(double amount) {
        this.coordinates[0] *= amount;
        this.coordinates[1] *= amount;
        this.coordinates[2] *= amount;
        this.coordinates[3] *= amount;
    }



    public double get(int index) { return this.coordinates[index]; }
    public void set(int i, double value) { this.coordinates[i] = value; }

    public double[] getCoordinates(){
        return this.coordinates;
    }

    public String toString() {
        return "Ce vecteur 4D est (" + this.coordinates[0] + ", " + this.coordinates[1] + ", " + this.coordinates[2] + ", " + this.coordinates[3] + ")";
    }
}
