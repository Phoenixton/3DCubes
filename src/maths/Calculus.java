package maths;

import entities.camera.Camera;
import entities.camera.EulerCamera;
import main.StaticValues;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by perri on 26/12/2018.
 */
public class Calculus {

    //Helper methods to process results.

    public static Matrix4d multiply(Matrix4d m1, Matrix4d m2) {
        double [][]values = new double[4][4];
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                values[i][j] = 0;
                for (int k=0; k<4; k++) {
                    values[i][j] += m1.getValue(k,j)*m2.getValue(i,k);
                }
            }
        }
        return new Matrix4d(values);
    }



    /**
     * Multiplies a vector (with w assumed to be equal to 1!) and a matrix
     * In case it is multiplied by a projection matrix, the w value obtained will probably be different from 1,
     * in which case we divide by w, sending the coordinates to NDC space (from clip space)
     * * @param matrix
     * @param vector , a normalized vector
     * @return
     */
    public static Vector4d multiplyMatrixVector(Matrix4d matrix, Vector4d vector)
    {
        //w is immediately 1 with this constructor
        Vector4d toReturn = new Vector4d();


        // w is assumed to always be equal to 1, no need to compute it
        double x = matrix.getValue(0,0) * vector.getCoordinates()[0] +
                    matrix.getValue(1,0) * vector.getCoordinates()[1] +
                    matrix.getValue(2,0) * vector.getCoordinates()[2] +
                    matrix.getValue(3,0);

        double y = matrix.getValue(0,1) * vector.getCoordinates()[0] +
                matrix.getValue(1,1) * vector.getCoordinates()[1] +
                matrix.getValue(2,1) * vector.getCoordinates()[2] +
                matrix.getValue(3,1);

        double z = matrix.getValue(0,2) * vector.getCoordinates()[0] +
                matrix.getValue(1,2) * vector.getCoordinates()[1] +
                matrix.getValue(2,2) * vector.getCoordinates()[2] +
                matrix.getValue(3,2);

        double w = matrix.getValue(0,3) * vector.getCoordinates()[0] +
                matrix.getValue(1,3) * vector.getCoordinates()[1] +
                matrix.getValue(2,3) * vector.getCoordinates()[2] +
                matrix.getValue(3,3);


        if (w != 1 && w!=0) {
            //When dealing with a projection matrix
            toReturn.set(0, x / w);
            toReturn.set(1, y / w);
            toReturn.set(2, z / w);
            toReturn.set(3, w);
        } else {
            //When dealing with an affine matrix
            toReturn.set(0, x);
            toReturn.set(1, y);
            toReturn.set(2, z);
        }

        return toReturn;
    }


    /**
     * Adds two vectors together, order is not important
     * @param origin
     * @param added
     * @return
     */
    public static Vector4d addTwoVectors(Vector4d origin, Vector4d added) {
        Vector4d toReturn = new Vector4d();
        toReturn.set(0, origin.getCoordinates()[0] + added.getCoordinates()[0]);
        toReturn.set(1, origin.getCoordinates()[1] + added.getCoordinates()[1]);
        toReturn.set(2, origin.getCoordinates()[2] + added.getCoordinates()[2]);
        return toReturn;
    }

    /**
     * Substracts two vectors together, order IS important
     * @param origin
     * @param substracted
     * @return
     */
    public static Vector4d substractTwoVectors(Vector4d origin, Vector4d substracted) {
        Vector4d toReturn = new Vector4d();
        toReturn.set(0, origin.getCoordinates()[0] - substracted.getCoordinates()[0]);
        toReturn.set(1, origin.getCoordinates()[1] - substracted.getCoordinates()[1]);
        toReturn.set(2, origin.getCoordinates()[2] - substracted.getCoordinates()[2]);
        return toReturn;
    }



    public static Vector4d multiplyByScalar(Vector4d toMultiply, double amount) {
        Vector4d toReturn = new Vector4d();
        toReturn.set(0, toMultiply.get(0)* amount);
        toReturn.set(1, toMultiply.get(1)* amount);
        toReturn.set(2, toMultiply.get(2)* amount);

        return toReturn;
    }


    /**
     * ORDER MATTERS -- computes the cross product of two given vectors
     * @param origin
     * @param crossed
     * @return
     */
    public static Vector4d crossProduct(Vector4d origin, Vector4d crossed) {
        Vector4d toReturn = new Vector4d();
        toReturn.set(0, origin.getCoordinates()[1] * crossed.getCoordinates()[2] - origin.getCoordinates()[2] * crossed.getCoordinates()[1]);
        toReturn.set(1, origin.getCoordinates()[2] * crossed.getCoordinates()[0] - origin.getCoordinates()[0] * crossed.getCoordinates()[2]);
        toReturn.set(2, origin.getCoordinates()[0] * crossed.getCoordinates()[1] - origin.getCoordinates()[1] * crossed.getCoordinates()[0]);
        return toReturn;
    }


    /**
     * Makes use of the apache commons way of inverting matrices
     * @param toInvert
     * @return
     */
    public static Matrix4d invertMatrix(Matrix4d toInvert) {
        RealMatrix m = MatrixUtils.createRealMatrix(toInvert.getValues());

        RealMatrix mInverse = new LUDecomposition(m).getSolver().getInverse();

        Matrix4d newMatrix = new Matrix4d(mInverse.getData());
        return newMatrix;
    }


    /**
     * These are the NDC coordonates of the vector given as a parameter. Note that we settle for w = 1, but mainly
     * because we know that no operation should be performed afterwards on those coordinates, other than to draw them
     * We need a camera passed so that we can access the near and far values in the view Frustum it contains
     * @param coordinates
     * @param camera
     * @return
     */
    public static Vector4d getActualScreenCoordinates(Vector4d coordinates, Camera camera) {

        double x = -1; //x+w = 1
        double y = -1; //y+h = 1
        //-1 = n, 1 =f -> NDC space
        Vector4d screenCoordVector = new Vector4d(
                (StaticValues.WINDOW_WIDTH/2)*coordinates.get(0)+(x+StaticValues.WINDOW_WIDTH/2),
                (StaticValues.WINDOW_HEIGHT/2)*coordinates.get(1)+(y+StaticValues.WINDOW_HEIGHT/2),
                ((camera.getViewFrustum().getFar() - camera.getViewFrustum().getNear())/2)*coordinates.get(2)
                        +((camera.getViewFrustum().getFar() + camera.getViewFrustum().getNear())/2)
        );
        return screenCoordVector;
    }


    /**
     * These are the NDC coordonates of the vector given as a parameter. Note that we settle for w = 1, but mainly
     * because we know that no operation should be performed afterwards on those coordinates, other than to draw them
     * We need a camera passed so that we can access the near and far values in the view Frustum it contains
     * @param coordinates
     * @param camera
     * @return
     */
    public static Vector4d getActualScreenCoordinates(Vector4d coordinates, EulerCamera camera) {

        double x = -1; //x+w = 1
        double y = -1; //y+h = 1
        //-1 = n, 1 =f -> NDC space
        Vector4d screenCoordVector = new Vector4d(
                (StaticValues.WINDOW_WIDTH/2)*coordinates.get(0)+(x+StaticValues.WINDOW_WIDTH/2),
                (StaticValues.WINDOW_HEIGHT/2)*coordinates.get(1)+(y+StaticValues.WINDOW_HEIGHT/2),
                ((camera.getViewFrustum().getFar() - camera.getViewFrustum().getNear())/2)*coordinates.get(2)
                        +((camera.getViewFrustum().getFar() + camera.getViewFrustum().getNear())/2)
        );
        return screenCoordVector;
    }
}
