package entities.camera;

import maths.Calculus;
import maths.Matrix4d;
import maths.Vector4d;

/**
 * Created by perri on 02/01/2019.
 */
public class EulerCamera {

    private ViewFrustum viewFrustum;

    private Vector4d cameraTarget, cameraUp, cameraRight, cameraPosition;
    private final Vector4d fixedUp = new Vector4d(0,1,0);

    private double yaw, pitch;

    public EulerCamera() {
        this.viewFrustum = new ViewFrustum();

        this.cameraPosition = new Vector4d(0,0,3);

        this.cameraTarget = new Vector4d(0,0,-1);
        this.cameraUp = fixedUp;

        this.yaw = 0;
        this.pitch = 0;

        updateCameraVectors();
    }


    private void updateCameraVectors() {

        //calculate front
        /*this.cameraTarget = new Vector4d(
                Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)),
                Math.sin(Math.toRadians(pitch)),
                Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))
        );
        this.cameraTarget.normalize();*/

        /*
        this.cameraTarget = new Vector4d(
                Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)),
                Math.sin(Math.toRadians(pitch)),
                - Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))
        );
        this.cameraTarget.normalize();
        */

        // Also re-calculate the Right and Up vector
        this.cameraRight = Calculus.crossProduct(this.cameraTarget, fixedUp);
        this.cameraRight.normalize();
        this.cameraUp = Calculus.crossProduct(this.cameraRight, this.cameraTarget);
        this.cameraUp.normalize();

    }

    public void changeDirectionOfCamera(double pitch, double yaw) {
        this.pitch+=pitch;
        this.yaw+=yaw;
        if(this.pitch > 89)
            this.pitch = 89;
        if(this.pitch < -89)
            this.pitch = -89;
        /*
        this.cameraTarget = new Vector4d(
                Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)),
                Math.sin(Math.toRadians(pitch)),
                Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))
        );
        this.cameraTarget.normalize();

        updateCameraVectors();*/
    }


    public Matrix4d getWorldToCameraMatrix() {
        /*Matrix4d left = new Matrix4d(
                cameraRight.getCoordinates()[0], cameraRight.getCoordinates()[1], cameraRight.getCoordinates()[2], 0,
                this.cameraUp.getCoordinates()[0], this.cameraUp.getCoordinates()[1], this.cameraUp.getCoordinates()[2], 0,
                this.cameraTarget.getCoordinates()[0], this.cameraTarget.getCoordinates()[1], this.cameraTarget.getCoordinates()[2], 0,
                0,0,0,1
        );

        Matrix4d right = new Matrix4d(
                1,0,0,-this.cameraPosition.getCoordinates()[0],
                0,1,0,-this.cameraPosition.getCoordinates()[1],
                0,0,1,-this.cameraPosition.getCoordinates()[2],
                0,0,0,1
        );

        return Calculus.multiply(left, right);*/


        Matrix4d globalRotation = Matrix4d.createGlobalRotation(new Vector4d(pitch, yaw, 0));

        Matrix4d translation = Matrix4d.createTranslation(
                new Vector4d(
                        -this.cameraPosition.get(0),
                        -this.cameraPosition.get(1),
                        -this.cameraPosition.get(2))
        );

        Matrix4d t = Calculus.multiply(globalRotation, translation);


        this.cameraTarget = new Vector4d(
                t.getValue(0,2), t.getValue(1,2), t.getValue(2,2)
        );

        updateCameraVectors();
        System.out.println("pitch " + this.pitch + " and yaw "+ this.yaw);
        return t;
    }


    public Matrix4d getCameraToWorldMatrix() {
        return Calculus.invertMatrix(getWorldToCameraMatrix());
    }

    public String toString() {
        String s = "This camera is located at \n" + this.cameraPosition.toString()
                + "\nlooking at \n " + this.cameraTarget.toString()
                + "\nwith a cameraUp \n" + this.cameraUp.toString()
                + "\nand a camera Right \n" + this.cameraRight.toString();

        return s;
    }

    //getters and setters

    public ViewFrustum getViewFrustum() {
        return viewFrustum;
    }

    public void setViewFrustum(ViewFrustum viewFrustum) {
        this.viewFrustum = viewFrustum;
    }

    public Vector4d getCameraFront() {
        return cameraTarget;
    }

    public void setCameraTarget(Vector4d cameraTarget) {
        this.cameraTarget = cameraTarget;
    }

    public Vector4d getCameraUp() {
        return cameraUp;
    }

    public void setCameraUp(Vector4d cameraUp) {
        this.cameraUp = cameraUp;
    }

    public Vector4d getCameraRight() {
        return cameraRight;
    }

    public void setCameraRight(Vector4d cameraRight) {
        this.cameraRight = cameraRight;
    }

    public Vector4d getLocalPosition() {
        return cameraPosition;
    }

    public void setLocalPosition(Vector4d cameraPosition) {
        this.cameraPosition = cameraPosition;
    }

    public Vector4d getFixedUp() {
        return fixedUp;
    }
}
