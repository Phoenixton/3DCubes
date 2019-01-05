package maths.transformations;

import maths.Calculus;
import maths.Matrix4d;
import maths.Vector4d;

/**
 * Created by perri on 30/12/2018.
 */
public class Transform {

    private Matrix4d modelMatrix;


    public Transform(){
        this.modelMatrix = Matrix4d.unity;
    }

    /**
     *  if you want to instantly initiate your object at a specific world position, rotating a certain way
     * @param translation
     * @param rotations
     * @param scale
     */
    public Transform(Vector4d translation, Vector4d rotations, Vector4d scale){
        this.modelMatrix = Matrix4d.unity;
        Matrix4d temp = Calculus.multiply(Matrix4d.createScale(scale), this.modelMatrix);
        Matrix4d temp2 = Calculus.multiply(Matrix4d.createGlobalRotation(rotations), temp);
        this.modelMatrix = Calculus.multiply(Matrix4d.createTranslation(translation), temp2);
    }

    public void addTransformation(Matrix4d transformation) {
        this.modelMatrix = Calculus.multiply(transformation, this.modelMatrix);
    }


    public void resetTransformations() {
        this.modelMatrix = Matrix4d.unity;
    }



    //getters ans setters
    public Matrix4d getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4d transformations) {
        this.modelMatrix = transformations;
    }
}
