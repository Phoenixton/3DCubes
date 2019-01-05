package entities.worldobjects.cube;

import entities.camera.Camera;
import entities.camera.EulerCamera;
import entities.worldobjects.Face;
import entities.worldobjects.WorldObject;
import maths.Matrix4d;
import maths.Point;
import maths.Vector4d;
import maths.transformations.Transform;

import java.awt.*;

/**
 * Created by perri on 29/12/2018.
 */
public class Cube extends WorldObject {

    private Face[] faces;


    private Color color;

    private double size;

    public Cube(){
        super();
    }



    /**
     * Creates a cube by specifying a size for it
     * @param size
     */
    public Cube(double size) {

        this.transform = new Transform();
        this.size = size;
        //Default color, whatever
        this.color = Color.BLUE;

        //a cube has six faces, I'm a genius
        this.faces = new Face[6];

        //clockwise rotation, from bottom left corner to bottom right corner
        this.faces[0] = new Face(
                new Point[]{
                        new Point(-size / 2, -size / 2, -size / 2),
                        new Point(-size / 2, size / 2, -size / 2),
                        new Point(size / 2, size / 2, -size / 2),
                        new Point(size / 2, -size / 2, -size / 2)
                });

        //we do the same for the other faces
        //up face
        this.faces[1] = new Face(
                new Point[]{
                        new Point(-size / 2,size / 2,-size / 2),
                        new Point(-size / 2,size / 2,size / 2),
                        new Point(size / 2,size / 2,size / 2),
                        new Point(size/2,size/2,-size/2)
                });

        //right face
        this.faces[2] = new Face(
                new Point[]{
                        new Point(size/2,-size/2,-size/2),
                        new Point(size/2,size/2,-size/2),
                        new Point(size/2,size/2,size/2),
                        new Point(size/2,-size/2,size/2)
                });

        //Back face
        this.faces[3] = new Face(
                new Point[]{
                        new Point(-size/2,-size/2,size/2),
                        new Point(-size/2,size/2,size/2),
                        new Point(size/2,size/2,size/2),
                        new Point(size/2,-size/2,size/2)
                });

        //left face
        this.faces[4] = new Face(
                new Point[]{
                        new Point(-size/2,-size/2,-size/2),
                        new Point(-size/2,size/2,-size/2),
                        new Point(-size/2,size/2,size/2),
                        new Point(-size/2,-size/2,size/2)
                });

        //down face
        this.faces[5] = new Face(
                new Point[]{
                        new Point(-size/2,-size/2,-size/2),
                        new Point(-size/2,-size/2,size/2),
                        new Point(size/2,-size/2,size/2),
                        new Point(size/2,-size/2,-size/2)
                });
    }



    /**
     * Creates a cube by specifying a size for it and a color
     * @param size
     */
    public Cube(double size, Color color) {

        this.transform = new Transform();
        this.size = size;
        System.out.println("Color " + color);
        this.color = color;

        //a cube has six faces, I'm a genius
        this.faces = new Face[6];

        //clockwise rotation, from bottom left corner to bottom right corner
        this.faces[0] = new Face(
                new Point[]{
                        new Point(-size / 2, -size / 2, -size / 2),
                        new Point(-size / 2, size / 2, -size / 2),
                        new Point(size / 2, size / 2, -size / 2),
                        new Point(size / 2, -size / 2, -size / 2)
                });

        //we do the same for the other faces
        //up face
        this.faces[1] = new Face(
                new Point[]{
                        new Point(-size / 2,size / 2,-size / 2),
                        new Point(-size / 2,size / 2,size / 2),
                        new Point(size / 2,size / 2,size / 2),
                        new Point(size/2,size/2,-size/2)
                });

        //right face
        this.faces[2] = new Face(
                new Point[]{
                        new Point(size/2,-size/2,-size/2),
                        new Point(size/2,size/2,-size/2),
                        new Point(size/2,size/2,size/2),
                        new Point(size/2,-size/2,size/2)
                });

        //Back face
        this.faces[3] = new Face(
                new Point[]{
                        new Point(-size/2,-size/2,size/2),
                        new Point(-size/2,size/2,size/2),
                        new Point(size/2,size/2,size/2),
                        new Point(size/2,-size/2,size/2)
                });

        //left face
        this.faces[4] = new Face(
                new Point[]{
                        new Point(-size/2,-size/2,-size/2),
                        new Point(-size/2,size/2,-size/2),
                        new Point(-size/2,size/2,size/2),
                        new Point(-size/2,-size/2,size/2)
                });

        //down face
        this.faces[5] = new Face(
                new Point[]{
                        new Point(-size/2,-size/2,-size/2),
                        new Point(-size/2,-size/2,size/2),
                        new Point(size/2,-size/2,size/2),
                        new Point(size/2,-size/2,-size/2)
                });
    }



    /**
     * Creates a cube by specifying a size for it and a color
     * Mostly used for the bresenham algorithm
     * @param size
     */
    public Cube(double size, Color color, Vector4d position) {

        this.transform = new Transform(
                position,
                new Vector4d(0,0,0),
                new Vector4d(1,1,1)
        );
        this.size = size;
        this.color = color;

        //a cube has six faces, I'm a genius
        this.faces = new Face[6];

        //clockwise rotation, from bottom left corner to bottom right corner
        this.faces[0] = new Face(
                new Point[]{
                        new Point(-size / 2, -size / 2, -size / 2),
                        new Point(-size / 2, size / 2, -size / 2),
                        new Point(size / 2, size / 2, -size / 2),
                        new Point(size / 2, -size / 2, -size / 2)
                });

        //we do the same for the other faces
        //up face
        this.faces[1] = new Face(
                new Point[]{
                        new Point(-size / 2,size / 2,-size / 2),
                        new Point(-size / 2,size / 2,size / 2),
                        new Point(size / 2,size / 2,size / 2),
                        new Point(size/2,size/2,-size/2)
                });

        //right face
        this.faces[2] = new Face(
                new Point[]{
                        new Point(size/2,-size/2,-size/2),
                        new Point(size/2,size/2,-size/2),
                        new Point(size/2,size/2,size/2),
                        new Point(size/2,-size/2,size/2)
                });

        //Back face
        this.faces[3] = new Face(
                new Point[]{
                        new Point(-size/2,-size/2,size/2),
                        new Point(-size/2,size/2,size/2),
                        new Point(size/2,size/2,size/2),
                        new Point(size/2,-size/2,size/2)
                });

        //left face
        this.faces[4] = new Face(
                new Point[]{
                        new Point(-size/2,-size/2,-size/2),
                        new Point(-size/2,size/2,-size/2),
                        new Point(-size/2,size/2,size/2),
                        new Point(-size/2,-size/2,size/2)
                });

        //down face
        this.faces[5] = new Face(
                new Point[]{
                        new Point(-size/2,-size/2,-size/2),
                        new Point(-size/2,-size/2,size/2),
                        new Point(size/2,-size/2,size/2),
                        new Point(size/2,-size/2,-size/2)
                });
    }



    @Override
    public void update(Camera camera) {
        //the FACE HOLD THEIR CURRENT COORDINATES -- A cube could be different because a size could be all that you need

        //TODO:Cleanup this test
        /*this.addTransform(
                Matrix4d.createTranslation(new Vector4d(0, 0, -.50))
        );*/
        for(int i = 0; i < this.faces.length; i++){
            this.faces[i].update(camera, this.transform);
        }
    }


    @Override
    public void update(EulerCamera camera) {
        //the FACE HOLD THEIR CURRENT COORDINATES -- A cube could be different because a size could be all that you need

        //TODO:Cleanup this test
        /*this.addTransform(
                Matrix4d.createTranslation(new Vector4d(0, 0, -.50))
        );*/
        for(int i = 0; i < this.faces.length; i++){
            this.faces[i].update(camera, this.transform);
        }
    }



    @Override
    public void render(Graphics g) {

        //now that we have updated the faces, we render them
        for(int i = 0; i < this.faces.length; i++){
          //  this.faces[i].render(g/*, this.color*/);
            this.faces[i].render(g, this.color);
        }
    }


    public String toString() {
        String s = "This cube is \n";
        for(Face face : this.faces) {
            s+=face.toString();
        }
        return s;
    }


    //getters and setters

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
