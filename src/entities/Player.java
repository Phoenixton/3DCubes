package entities;

import entities.camera.Camera;
import entities.camera.EulerCamera;
import entities.worldobjects.WorldObject;

/**
 * Created by perri on 31/12/2018.
 */
public class Player {

    private EulerCamera playerCamera;
    private WorldObject currentlyHeldWorldObject;

    //movement
    private double playerSpeed = 0.5;
    //TODO


    public Player(EulerCamera playerCamera) {
        this.playerCamera = playerCamera;
        this.currentlyHeldWorldObject = null;
    }



    /**
     * Assigns worldObject to the slot the player can use to hold a world object
     * @param worldObject
     * @return
     */
    public boolean pickUpWorldObject(WorldObject worldObject){
        if(isHoldingWorldObject()){
            //You can't pick up a world object if you already have one in hand
            return false;
        } else {
            this.currentlyHeldWorldObject = worldObject;
            return true;
        }
    }

    /**
     * Gets rid of the world object the player is holding (either by going to the bin, or putting it down)
     * @return
     */
    public boolean disposeOfWorldObject() {
        this.currentlyHeldWorldObject = null;
        return true;
    }


    /**
     * Tells you if the player is currently holding a worldobject(here, a cube, probably) that he picked up in front of him
     * @return
     */
    public boolean isHoldingWorldObject() {
        return(this.currentlyHeldWorldObject==null);
    }



    //getters and setters

    public EulerCamera getPlayerCamera() {
        return playerCamera;
    }

    public WorldObject getCurrentlyHeldWorldObject() {
        return currentlyHeldWorldObject;
    }

    public void setCurrentlyHeldWorldObject(WorldObject currentlyHeldWorldObject) {
        this.currentlyHeldWorldObject = currentlyHeldWorldObject;
    }

    public double getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(double playerSpeed) {
        this.playerSpeed = playerSpeed;
    }
}
