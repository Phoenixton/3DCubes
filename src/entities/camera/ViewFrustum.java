package entities.camera;

import main.StaticValues;
import maths.Matrix4d;

/**
 * Created by perri on 30/12/2018.
 */
public class ViewFrustum {

    private double left, right, bottom, top, near, far;
    private final double aspect_ratio = StaticValues.SCREEN_WIDTH/ StaticValues.SCREEN_HEIGHT;


    public ViewFrustum() {
        this.left = -1;
        this.right = 1;
        this.bottom = -1/aspect_ratio;
        this.top = 1/aspect_ratio;
        this.near = 1;
        this.far = 100;
    }

    public ViewFrustum(double left,double right,double bottom,double top,double near,double far) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.near = near;
        this.far = far;
    }


    /**
     * returns the projection matrix according to the current value of the viewfrustum for the concerned camera
     * Multiplying coordinates by this matrix return them in clip space. you still have to perform a division
     * via wclip to get the coordinates in NDC.
     * @return
     */
    public Matrix4d createProjectionMatrix() {
        return new Matrix4d(
                this.near/this.right, 0, 0, 0,
                0, this.near/this.top, 0, 0,
                0, 0, -(this.far+this.near)/(this.far-this.near), (-2* this.far * this.near)/(this.far * this.near),
                0, 0, -1, 0
        );
    }

    //getters and setters

    public double getNear() {
        return near;
    }

    public double getFar() {
        return far;
    }
}
