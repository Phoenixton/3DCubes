package entities.camera;

import entities.worldobjects.WorldObject;
import maths.Matrix4d;
import maths.Vector4d;
import maths.transformations.Transform;

import java.awt.*;

/**
 * Created by perri on 29/12/2018.
 */
public class Camera extends WorldObject {

    private ViewFrustum viewFrustum;
    private Vector4d localPosition;

    /**
     * The Lazy camera. We all love the Lazy Camera
     */
    public Camera(){
        super();
        this.transform = new Transform();
        this.viewFrustum = new ViewFrustum();
    }

    /**
     * You can specify a starting position for the camera. The ViewFrustum is still the default one
     * @param pos
     */
    public Camera(Vector4d pos){
        super();
        this.localPosition = pos;
        this.viewFrustum = new ViewFrustum();
    }

    /**
     * A more detailed constructor. You create a View Frustum from the get go, with personalised values
     * @param pos
     */
    public Camera(Vector4d pos, ViewFrustum vf){
        super();
        this.localPosition = pos;
        this.viewFrustum = vf;
    }




    //functions
    /**
     * Returns the model matrix for the camera, the matrix that places the camera in the world, like any other object
     * @return
     */
    public Matrix4d getCameraToWorldMatrix() {
        return super.getViewToWorldMatrix();
    }
    /**
     * Returns the inverse of the function you would use to place the camera in the world
     *
     * @return
     */
    public Matrix4d getWorldToCameraMatrix() {
        return super.getWorldToViewMatrix();
    }


    /**
     * Doesn't need to be called, usually hehe
     * @param g
     */
    @Override
    public void render(Graphics g) {

    }

    @Override
    public void update(Camera camera) {

    }

    @Override
    public void update(EulerCamera camera) {

    }



    //getters and setters


    public ViewFrustum getViewFrustum() {
        return viewFrustum;
    }

}
