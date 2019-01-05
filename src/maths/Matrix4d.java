package maths;

/**
 * Created by perri on 26/12/2018.
 */
public class Matrix4d {

    /**
     * Horrible copie de votre cours, monsieur. Mais ça fonctionne tellement bien, pourquoi le changer ;) ?
     *
     */
    private double[][] values = new double[4][4];

    public static Matrix4d unity;

    static {
        unity = new Matrix4d(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
    }


    public Matrix4d(double a11, double a21, double a31, double a41,
                  double a12, double a22, double a32, double a42,
                  double a13, double a23, double a33, double a43,
                  double a14, double a24, double a34, double a44) {
        values = new double[4][4];
        values[0][0] = a11;
        values[1][0] = a21;
        values[2][0] = a31;
        values[3][0] = a41;
        values[0][1] = a12;
        values[1][1] = a22;
        values[2][1] = a32;
        values[3][1] = a42;
        values[0][2] = a13;
        values[1][2] = a23;
        values[2][2] = a33;
        values[3][2] = a43;
        values[0][3] = a14;
        values[1][3] = a24;
        values[2][3] = a34;
        values[3][3] = a44;
    }

    public Matrix4d(double [][]values) {
        this.values = new double[4][4];
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                this.values[i][j] = values[i][j];
            }
        }
    }



    public static Matrix4d createTranslation(Vector4d v) {
        return new Matrix4d(
                1, 0, 0, v.getCoordinates()[0],
                0, 1, 0, v.getCoordinates()[1],
                0, 0, 1, v.getCoordinates()[2],
                0, 0, 0, 1
        );
    }

    public static Matrix4d createScale(Vector4d v) {
        return new Matrix4d(
                v.getCoordinates()[0], 0, 0,0,
                0, v.getCoordinates()[1], 0, 0,
                0, 0, v.getCoordinates()[2], 0,
                0, 0, 0, 1
        );
    }



    public static Matrix4d createGlobalRotation(Vector4d v) {
        Matrix4d rotationX = Matrix4d.createRotationX(v.getCoordinates()[0]);
        Matrix4d rotationY = Matrix4d.createRotationY(v.getCoordinates()[1]);
        Matrix4d rotationZ = Matrix4d.createRotationZ(v.getCoordinates()[2]);

        //TODO: Check the order
        return Calculus.multiply(rotationZ, Calculus.multiply(rotationX, rotationY));
    }


    public static Matrix4d createRotationX(double angle) {
        angle = Math.toRadians(angle);
        return new Matrix4d(
                1, 0, 0, 0,
                0, Math.cos(angle), -Math.sin(angle), 0,
                0, Math.sin(angle), Math.cos(angle), 0,
                0, 0, 0, 1
        );
    }
    public static Matrix4d createRotationY(double angle) {

        angle = Math.toRadians(angle);
        return new Matrix4d(
                Math.cos(angle), 0, Math.sin(angle), 0,
                0, 1, 0, 0,
                -Math.sin(angle), 0, Math.cos(angle), 0,
                0, 0, 0, 1
        );
    }
    public static Matrix4d createRotationZ(double angle) {

        angle = Math.toRadians(angle);
        return new Matrix4d(
                Math.cos(angle), -Math.sin(angle), 0, 0,
                Math.sin(angle), Math.cos(angle), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
    }

    /**
     * Remember that we work with column ord -> i = column, j = line
     * @param i
     * @param j
     * @param value
     */
    public void setValue(int i, int j, double value){
        this.values[i][j] = value;
    }


    public double getValue(int i, int j) {
        return this.values[i][j];
    }




    //getters
    public double[][] getValues(){
        return this.values;
    }


    /**
     * toString
     * @return a description of the matrix
     */
    public String toString() {
        String s = "";
        for (int j=0; j<4; j++) {
            s += "| ";
            for (int i=0; i<4; i++) {
                s += values[i][j]+" ";
            }
            s += "|\n";
        }
        return s;
    }

}
