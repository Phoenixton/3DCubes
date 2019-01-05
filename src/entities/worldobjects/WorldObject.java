package entities.worldobjects;


import entities.camera.Camera;
import entities.camera.EulerCamera;
import maths.Calculus;
import maths.Matrix4d;
import maths.transformations.Transform;

import java.awt.*;

/**
 * Created by perri on 29/12/2018.
 */
public abstract class WorldObject {

    protected Transform transform;


    public WorldObject() {
    }


    public void addTransform(Matrix4d transformationMatrix) {
        this.transform.addTransformation(transformationMatrix);
    }

    public Matrix4d getViewToWorldMatrix() {
        return this.transform.getModelMatrix();
    }
    public Matrix4d getWorldToViewMatrix() {
       return Calculus.invertMatrix(this.transform.getModelMatrix());
    }

    public abstract void update(Camera camera);


    public abstract void update(EulerCamera camera);

    public abstract void render(Graphics g);

}
