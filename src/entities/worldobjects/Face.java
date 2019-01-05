package entities.worldobjects;


import entities.camera.Camera;
import entities.camera.EulerCamera;
import maths.Calculus;
import maths.Matrix4d;
import maths.Point;
import maths.Vector4d;
import maths.transformations.Transform;

import java.awt.*;

/**
 * Created by perri on 31/12/2018.
 */
public class Face {

    private Point[] facePoints;
    private Polygon renderedFace;

    //TODO:Zbuffering
    private double currentZBuffering;

    /**
     * Constructor if we already have the array constructed
     * @param points
     */
    public Face(Point[] points){
        this.facePoints = points;
        //we don't actually give it any argument for now. We'll reset/add points to him each render call
        this.renderedFace = new Polygon();
    }

    /**
     * Constructor if we don't have the array of points constructed.
     * @param a
     * @param b
     * @param c
     * @param d
     */
    public Face(Point a, Point b, Point c, Point d){
        this.facePoints = new Point[4];
        this.facePoints[0] = a;
        this.facePoints[1] = b;
        this.facePoints[2] = c;
        this.facePoints[3] = d;
        //we don't actually give it any argument for now. We'll reset/add points to him each render call
        this.renderedFace = new Polygon();
    }


    public void update(Camera camera, Transform transform) {
        //resets the polygon, to which we re-add the newly calculated points
        this.renderedFace.reset();
        //TODO: Call the transform methods for every point of the face, then draw the polygon with the obtained values

        //we call the transform methods for every point of the face, then we
        for(int i = 0; i < this.facePoints.length; i++) {

            Matrix4d projectionMatrix = camera.getViewFrustum().createProjectionMatrix();
            Matrix4d worldToCameraMatrix = camera.getWorldToCameraMatrix();
            Matrix4d modelMatrix = transform.getModelMatrix();

            //renderedPosition is calculated as : clipPosition = Mprojection * Mcamera * Mmodel * originalPosition
            System.out.println("Model Matrix appliying");
            Vector4d temp = Calculus.multiplyMatrixVector(
                    modelMatrix, this.facePoints[i].getPosition()
            );
            System.out.println("World to Camera Matrix appliying");

            Vector4d temp1 = Calculus.multiplyMatrixVector(worldToCameraMatrix, temp);

            System.out.println("projection Matrix appliying");

            Vector4d temp2 = Calculus.multiplyMatrixVector(projectionMatrix, temp1);

                    /*
            Vector4d ndcPosition =
                Calculus.multiplyMatrixVector(
                    projectionMatrix, Calculus.multiplyMatrixVector(
                            worldToCameraMatrix, Calculus.multiplyMatrixVector(
                                    modelMatrix, this.facePoints[i].getPosition()
                            )
                    )
                );*/

            //TODO:APPLY THE CLIPPING -- IF A POINT'S W is  > 1 (in fact, > 0 because > near plane - 1), WE SHOULDN'T BE RENDERING THEM
            if(temp2.get(3) > (camera.getViewFrustum().getNear() -1)) {
                //ignores what's behind the camera -> CLIPPING
            } else {

                //apply the viewport transform to the ndc coordinates
                Vector4d renderedPosition = Calculus.getActualScreenCoordinates(temp2, camera);

                //Actual rendered coordinates of the point
                double renderedX = renderedPosition.get(0);
                double renderedY = renderedPosition.get(1);

                //adding the points to the polygon that represents the face, so that we can then draw the polygon itself
                this.renderedFace.addPoint((int)renderedX, (int)renderedY);
            }
        }
    }



    public void update(EulerCamera camera, Transform transform) {
        //resets the polygon, to which we re-add the newly calculated points
        this.renderedFace.reset();

        //we call the transform methods for every point of the face, then we draw the polygon with the obtained values
        for(int i = 0; i < this.facePoints.length; i++) {

            Matrix4d projectionMatrix = camera.getViewFrustum().createProjectionMatrix();
            Matrix4d worldToCameraMatrix = camera.getWorldToCameraMatrix();
            Matrix4d modelMatrix = transform.getModelMatrix();

            //renderedPosition is calculated as : clipPosition = Mprojection * Mcamera * Mmodel * originalPosition
            //since we are column-major, that means the order of the operations is reversed


            //System.out.println("Model Matrix applying");
            Vector4d temp = Calculus.multiplyMatrixVector(
                    modelMatrix, this.facePoints[i].getPosition()
            );

           // System.out.println("World to Camera Matrix applying");

            Vector4d temp1 = Calculus.multiplyMatrixVector(worldToCameraMatrix, temp);

            //System.out.println("projection Matrix applying");

            Vector4d temp2 = Calculus.multiplyMatrixVector(projectionMatrix, temp1);


                    /*
            Vector4d ndcPosition =
                Calculus.multiplyMatrixVector(
                    projectionMatrix, Calculus.multiplyMatrixVector(
                            worldToCameraMatrix, Calculus.multiplyMatrixVector(
                                    modelMatrix, this.facePoints[i].getPosition()
                            )
                    )
                );*/

            //TODO:APPLY THE CLIPPING -- IF A POINT'S W is  > 1 (in fact, > 0 because > near plane - 1), WE SHOULDN'T BE RENDERING THEM

            //System.out.println("Lol" + camera.getViewFrustum().getNear());
            //System.out.println("The vector" + temp2.toString());
            if(temp2.get(3) < (camera.getViewFrustum().getNear() -1)) {

            } else {

                //apply the viewport transform to the ndc coordinates
                Vector4d renderedPosition = Calculus.getActualScreenCoordinates(temp2, camera);

                double renderedX = renderedPosition.get(0);
                double renderedY = renderedPosition.get(1);

                this.renderedFace.addPoint((int)renderedX, (int)renderedY);
            }
        }
    }



    public void render(Graphics g/*, Color color*/) {

        /**Test color*
        g.setColor(color);
        g.fillPolygon(renderedFace);**/
        //actually draws the polygon
        g.setColor(new Color(0, 0, 0));
        g.drawPolygon(renderedFace);

    }

    public void render(Graphics g, Color color) {

        /**Test color**/
         g.setColor(color);
         g.fillPolygon(renderedFace);
         //actually draws the polygon
        g.setColor(new Color(0, 0, 0));
        g.drawPolygon(renderedFace);

    }

    public String toString(){
        String s = "This face contains the points \n";
        for(int i = 0; i < this.facePoints.length; i++) {
            s+=this.facePoints[i].toString();
        }
        return s;
    }
}
