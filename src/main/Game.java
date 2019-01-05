package main;

import entities.Player;
import entities.camera.Camera;
import entities.camera.EulerCamera;
import entities.worldobjects.cube.Cube;
import maths.Calculus;
import maths.Matrix4d;
import maths.Vector4d;
import utils.AdditionalFunctions;
import utils.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Game extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private Dimension dimension;

    // keys
    private boolean[] keys = new boolean[9];
    //mouse
    private double lastX, lastY, pitch, yaw;
    private boolean firstTimeOpeningWindow; //This allows us to avoid having a huge jump the first time the mouse is calculated

    //allows us to recalibrate the cursor each frame
    private Robot robot;


    //TODO: Taken from whatever game loop I could find on the internet. Not final. But whatever, it works for now
    double drawFPS = 0, MaxFPS = 1000, SleepTime = 1000.0/MaxFPS, LastRefresh = 0, StartTime = System.currentTimeMillis(), LastFPSCheck = 0, Checks = 0;


    //game related
    private Player player;

    //world objects
    private Cube[][][] worldObjects;

    //Bresenham 3D
    private ArrayList<Cube> lineOfCubes;


    public Game(int w,int h) {

        setFocusable(true);
        this.dimension = new Dimension(w, h);

        //Keys -- Mouse listeners, basic
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

        //mouse init
        this.lastX = StaticValues.SCREEN_WIDTH/2;
        this.lastY = StaticValues.SCREEN_HEIGHT/2;
        this.pitch = 0;
        this.yaw = 0;// this.yaw = -90;
        this.firstTimeOpeningWindow = true;


        this.player = new Player(new EulerCamera());
        this.worldObjects = new Cube[3][3][3];



        //bresenham definition
        lineOfCubes = AdditionalFunctions.createLineInCube(6,-3,-3,6,6,-3);


        //world definition
        this.worldObjects = Parser.parseWorld("src/main/world.txt");
        if(this.worldObjects == null){
            System.out.println("problem");
        }

    }



    @Override
    public void paintComponent(Graphics g) {

        //clears the screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, dimension.width, dimension.height);


        g.setColor(Color.BLACK);


        //PAINT HERE

        //WORLD RENDERING
        for(int i = 0; i < this.worldObjects.length; i++) {
            for (int j = 0; j < this.worldObjects.length; j++) {
                for (int k = 0; k < this.worldObjects.length; k++) {
                    this.worldObjects[i][j][k].render(g);
                }
            }
        }

        //3D BRESENHAM RENDERING
        for(Cube cube : lineOfCubes) {
            cube.render(g);
        }

        //


        //Calls the update methods, movement, cubes, player
        update();
    }


    private void update()
    {

        //Random game loop found on the internet
        long timeSLU = (long) (System.currentTimeMillis() - LastRefresh);

        Checks ++;
        if(Checks >= 15)
        {
            drawFPS = Checks/((System.currentTimeMillis() - LastFPSCheck)/1000.0);
            LastFPSCheck = System.currentTimeMillis();
            Checks = 0;
        }

        if(timeSLU < 1000.0/MaxFPS)
        {
            try {
                Thread.sleep((long) (1000.0/MaxFPS - timeSLU));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LastRefresh = System.currentTimeMillis();


        //Handle Input
        handleMovement();

        //WORLD UPDATING
        for(int i = 0; i < this.worldObjects.length; i++) {
            for (int j = 0; j < this.worldObjects.length; j++) {
                for (int k = 0; k < this.worldObjects.length; k++) {
                    //this.worldObjects[i][j][k].update(this.player.getPlayerCamera());
                    this.worldObjects[i][j][k].update(this.player.getPlayerCamera());
                }
            }
        }

        //BRESENHAM 3D UPDATING
        for(Cube cube : lineOfCubes) {
            cube.update(this.player.getPlayerCamera());
        }
        //TODO:ZBUFFERING
        //So, technically, now that we have the renderedCoordinates by calling this.worldObjects[i][j][k].getFace(l).getRenderedPolygon(DO NOT FORGET THE VIEWPORT TRANSFORM)
        //we could do a double for loop to populate an array that is reset every frame (every update call actually) that contains the polygons that SHOULD BE DRAWN
        //then in render, we call the render method on the polygon of THAT array, not the global one
        //https://www.scratchapixel.com/lessons/3d-basic-rendering/rasterization-practical-implementation
        //we might have to give a proper position to a cube, I don't like it much but it's maybe the best solution
        //TODO: IF WE DECIDE TO GIVE A POSITION TO THE CUBE, WE DON'T NEED A TRIPLE ARRAY
        //We can imagine a function (in update) in Cube that retries its points calculated for the screen (after the update of each of its face)



        //redraws by calling painComponents
        repaint();
    }


    //handle movements

    private void handleMovement(){

        if(this.keys[0]) {
            //Z pressed

            Vector4d temp = Calculus.multiplyByScalar(this.player.getPlayerCamera().getCameraFront(), this.player.getPlayerSpeed());
            this.player.getPlayerCamera().setLocalPosition(
                    Calculus.substractTwoVectors(this.player.getPlayerCamera().getLocalPosition(), temp)
            );

        } else if(this.keys[1]) {
            //Q pressed


            Vector4d temp = Calculus.multiplyByScalar(
                    this.player.getPlayerCamera().getCameraRight(), this.player.getPlayerSpeed()
            );
            temp.normalize();
            this.player.getPlayerCamera().setLocalPosition(
                    Calculus.substractTwoVectors(this.player.getPlayerCamera().getLocalPosition(), temp)
            );
        } else if(this.keys[2]) {
            //S pressed
            Vector4d temp = Calculus.multiplyByScalar(this.player.getPlayerCamera().getCameraFront(), this.player.getPlayerSpeed());
            this.player.getPlayerCamera().setLocalPosition(
                    Calculus.addTwoVectors(this.player.getPlayerCamera().getLocalPosition(), temp)
            );
        } else if(this.keys[3]) {
            //D pressed
            Vector4d temp = Calculus.multiplyByScalar(
                    this.player.getPlayerCamera().getCameraRight(), this.player.getPlayerSpeed()
            );
            temp.normalize();
            this.player.getPlayerCamera().setLocalPosition(
                    Calculus.addTwoVectors(this.player.getPlayerCamera().getLocalPosition(), temp)
            );
        } else if(this.keys[4]) {
            //A pressed, temporary go up

          /*  this.player.getPlayerCamera().addTransform(
                    Matrix4d.createTranslation(
                            new Vector4d(new double[]{0,this.player.getPlayerSpeed(),0})
                    )
            );
            */
        } else if(this.keys[5]) {
            //E pressed, temporary go down
            /*this.player.getPlayerCamera().addTransform(
                    Matrix4d.createTranslation(
                            new Vector4d(new double[]{0,-this.player.getPlayerSpeed(),0})
                    )
            );*/

        } else if(this.keys[6]) {

        } else if(this.keys[7]) {

        } else if(this.keys[8]) {

        }
    }


    /**
     * Pressing a key activates it in the boolean array
     * @param e
     */
    public void keyPressed(KeyEvent e) {
      //  System.out.println("Pressed");
        if(e.getKeyCode() == KeyEvent.VK_Z)
            keys[0] = true;
        if(e.getKeyCode() == KeyEvent.VK_Q)
            keys[1] = true;
        if(e.getKeyCode() == KeyEvent.VK_S)
            keys[2] = true;
        if(e.getKeyCode() == KeyEvent.VK_D)
            keys[3] = true;
        if(e.getKeyCode() == KeyEvent.VK_A)
            keys[4] = true;
        if(e.getKeyCode() == KeyEvent.VK_E)
            keys[5] = true;
        if(e.getKeyCode() == KeyEvent.VK_C)
            keys[6] = true;
        if(e.getKeyCode() == KeyEvent.VK_K)
            keys[7] = true;
        if(e.getKeyCode() == KeyEvent.VK_L)
            keys[8] = true;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }

    /**
     * Releasing a key deactivates it in the boolean array
     * @param e
     */
    public void keyReleased(KeyEvent e) {
      //  System.out.println("Released");
        if(e.getKeyCode() == KeyEvent.VK_Z)
            keys[0] = false;
        if(e.getKeyCode() == KeyEvent.VK_Q)
            keys[1] = false;
        if(e.getKeyCode() == KeyEvent.VK_S)
            keys[2] = false;
        if(e.getKeyCode() == KeyEvent.VK_D)
            keys[3] = false;
        if(e.getKeyCode() == KeyEvent.VK_A)
            keys[4] = false;
        if(e.getKeyCode() == KeyEvent.VK_E)
            keys[5] = false;
        if(e.getKeyCode() == KeyEvent.VK_C)
            keys[6] = false;
        if(e.getKeyCode() == KeyEvent.VK_K)
            keys[7] = false;
        if(e.getKeyCode() == KeyEvent.VK_L)
            keys[8] = false;
    }

    public void keyTyped(KeyEvent e) {
    }

    //mouse functions


    /**
     * Recenters the mouse after each mouse moved call
     */
    void centerTheMouse()
    {
        try {
            robot = new Robot();
            robot.mouseMove((int)StaticValues.SCREEN_WIDTH/2, (int)StaticValues.SCREEN_HEIGHT/2);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public void mouseDragged(MouseEvent arg0) {

    }

    public void mouseMoved(MouseEvent arg0) {

        if(firstTimeOpeningWindow) {
            this.lastX = StaticValues.SCREEN_WIDTH/2;
            this.lastY = StaticValues.SCREEN_HEIGHT/2;
            this.firstTimeOpeningWindow = false;
        }

        double xoffset = arg0.getX() - lastX;
        double yoffset = lastY - arg0.getY();
        lastX = arg0.getX();
        lastY = arg0.getY();

        //TODO:Check the sensibility
        double sensitivity = 0.1;
        xoffset *= sensitivity;
        yoffset *= sensitivity;


        //System.out.println(this.cameraLookAt.getCameraToWorldMatrix());
        this.player.getPlayerCamera().changeDirectionOfCamera(yoffset, xoffset);

        //centerTheMouse();
    }


    public void mouseClicked(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {

    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void mouseWheelMoved(MouseWheelEvent arg0) {

    }

    @Override
    public Dimension getPreferredSize() {
        return dimension;
    }


}
