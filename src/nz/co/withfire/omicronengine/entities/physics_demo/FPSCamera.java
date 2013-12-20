/***********************************\
| A first person camera controller. |
|                                   |
| @author David Saxon               | 
\***********************************/

package nz.co.withfire.omicronengine.entities.physics_demo;

import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.logic.entity.Entity;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.utilities.MathUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public class FPSCamera extends Entity {

    
    //VARIABLES
    //a reference to the camera
    private Camera camera;
    
    //the rotation of the camera
    private Vector3 rot = new Vector3();
    
    //is true when camera is moving
    private boolean move = false;
    
    //CONSTRUCTOR
    /**Creates a new first person camera
    @param camera the actual camera object*/
    public FPSCamera(Camera camera) {
        
        this.camera = camera;
        this.pos.copy(camera.getPos());
        camera.setLocalRot(new Vector3(0.0f, 0.0f, 0.0f));
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
        //move the camera
        if (move) {
            
            pos.x -= -0.05f * FPSManager.getTimeScale() * 
                Math.sin(rot.y * MathUtil.DEGREES_TO_RADIANS);
            pos.z -=  0.05f * FPSManager.getTimeScale() *
                Math.cos(rot.y * MathUtil.DEGREES_TO_RADIANS);
            camera.setPos(pos);
        }
    }
    
    /**Adds to the rotation of the camera
    @param rot the rotation to add*/
    public void addRot(Vector3 rot) {
        
        this.rot.add(rot);
        camera.setLocalRot(this.rot);
    }
    
    /**Sets the camera to move
    @param move wether to move or not*/
    public void move(boolean move) {
        
        this.move = move;
    }
    
}
