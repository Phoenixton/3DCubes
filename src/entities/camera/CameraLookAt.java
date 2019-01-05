package entities.camera;

import maths.Calculus;
import maths.Matrix4d;
import maths.Vector4d;

/**
 * Created by perri on 31/12/2018.
 */
public class CameraLookAt {

    private final Vector4d fixedUp = new Vector4d(0,1,0);

    private Vector4d localPosition;
    //direction vectors -- Camera Front changes based on the Look Around angle we input
    private Vector4d cameraFront, cameraUp, cameraRight, cameraDirection;

    private Matrix4d modelMatrix;
    private ViewFrustum viewFrustum;



    public CameraLookAt() {
        this.viewFrustum = new ViewFrustum();


        this.localPosition = new Vector4d(0,0,3);

        this.cameraFront = new Vector4d(0,0,-1);
        this.cameraDirection = Calculus.substractTwoVectors(this.localPosition, this.cameraFront);
        this.cameraDirection.normalize();

        this.cameraUp = new Vector4d(0,1,0);
        this.cameraRight = new Vector4d(1,0,0);
        this.modelMatrix = Matrix4d.unity;

    }


    /**
     * A more detailed constructor. You create a View Frustum from the get go, with personalised values
     * @param
     */
    /*public CameraLookAt(Vector4d pos, ViewFrustum vf){
        super();
        this.localPosition = pos;
        this.viewFrustum = vf;
        this.cameraFront = new Vector4d(0,0,-1);
        this.cameraUp = new Vector4d(0,1,0);

        this.modelMatrix = Matrix4d.unity;

        this.modelMatrix = getCameraToWorldMatrix();

    }*/







    //functions

    public void changeDirectionOfCamera(double pitch, double yaw) {
        //System.out.println("PITCH " + pitch + " YAW " + yaw);
        double pitchRadians = Math.toRadians(pitch);
        double yawRadians = Math.toRadians(yaw);


        Vector4d newDirection = new Vector4d();
        newDirection.set(0, Math.cos(yawRadians) * Math.cos(pitchRadians));
        newDirection.set(1, Math.sin(pitchRadians));
        newDirection.set(2, Math.sin(yawRadians) * Math.cos(pitchRadians));

        System.out.println("oldcameraFront " + this.cameraFront);
        this.cameraFront = newDirection;
        this.cameraFront.normalize();
        System.out.println("New cameraFront " + this.cameraFront);
        System.out.println("Old cameraDirection " + this.cameraDirection);
        this.cameraDirection = Calculus.substractTwoVectors(this.localPosition, this.cameraFront);
        this.cameraDirection.normalize();
        System.out.println("New cameraDirection " + this.cameraDirection);
        System.out.println("Old camera Right" + this.cameraRight);
        this.cameraRight = Calculus.crossProduct(fixedUp, this.cameraFront);
        this.cameraRight.normalize();
        System.out.println("New cameraRight " + this.cameraRight);
        System.out.println("Old cameraUp " + this.cameraUp);
        this.cameraUp = Calculus.crossProduct(this.cameraFront, this.cameraRight);
        System.out.println("New cameraUP" + this.cameraUp);


    }






    /**
     * Returns the model matrix for the camera, the matrix that places the camera in the world, like any other object
     * @return
     */

    public Matrix4d getCameraToWorldMatrix() {
        //System.out.println("camera front " +this.cameraFront);z
        this.cameraRight = Calculus.crossProduct(cameraUp, cameraDirection);
        this.cameraRight.normalize();

        Matrix4d left = new Matrix4d(
                cameraRight.getCoordinates()[0], cameraRight.getCoordinates()[1], cameraRight.getCoordinates()[2], 0,
                this.cameraUp.getCoordinates()[0], this.cameraUp.getCoordinates()[1], this.cameraUp.getCoordinates()[2], 0,
                this.cameraDirection.getCoordinates()[0], this.cameraDirection.getCoordinates()[1], this.cameraDirection.getCoordinates()[2], 0,
                0,0,0,1
        );
        /*Matrix4d left = new Matrix4d(
                cameraRight.getCoordinates()[0], this.cameraUp.getCoordinates()[0], this.cameraFront.getCoordinates()[0], 0,
                cameraRight.getCoordinates()[1], this.cameraUp.getCoordinates()[1], this.cameraFront.getCoordinates()[1], 0,
                cameraRight.getCoordinates()[2], this.cameraUp.getCoordinates()[2], this.cameraFront.getCoordinates()[2], 0,
                0,0,0,1
        );*/

        Matrix4d right = new Matrix4d(
                1,0,0,-this.localPosition.getCoordinates()[0],
                0,1,0,-this.localPosition.getCoordinates()[1],
                0,0,1,-this.localPosition.getCoordinates()[2],
                0,0,0,1
        );

        this.modelMatrix = Calculus.multiply(left, right);

        //TODO:Check if this is correct - We extract cameraFront from the view matrix
        /*this.cameraFront.set(0, this.modelMatrix.getValue(2,0));
        this.cameraFront.set(1, this.modelMatrix.getValue(2,1));
        this.cameraFront.set(2, this.modelMatrix.getValue(2,2));
        */
        return this.modelMatrix;
    }
    /**
     * Returns the inverse of the function you would use to place the camera in the world
     *
     * @return
     */
    public Matrix4d getWorldToCameraMatrix() {
        return Calculus.invertMatrix(getCameraToWorldMatrix());
    }


    public void move(Vector4d direction) {
        this.localPosition = Calculus.addTwoVectors(this.localPosition, direction);
        this.modelMatrix = getCameraToWorldMatrix();
    }

    public void addTransformation(Matrix4d transformation) {
        this.modelMatrix = Calculus.multiply(transformation, this.modelMatrix);
    }




    //getters and setters
    public ViewFrustum getViewFrustum() {
        return viewFrustum;
    }

    public Vector4d getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(Vector4d localPosition) {
        this.localPosition = localPosition;
    }

    public Vector4d getCameraFront() {
        return cameraFront;
    }

    public Vector4d getCameraUp() {
        return cameraUp;
    }
}
