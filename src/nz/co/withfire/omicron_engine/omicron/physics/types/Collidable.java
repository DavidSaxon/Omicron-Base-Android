/*******************************\
| An entity that is collidable. |
|                               |
| @author David Saxon           |
\*******************************/

package nz.co.withfire.omicron_engine.omicron.physics.types;

import java.util.ArrayList;
import java.util.List;

import nz.co.withfire.omicron_engine.omicron.logic.entity.Entity;
import nz.co.withfire.omicron_engine.omicron.physics.bounding.Bounding;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;

public abstract class Collidable extends Entity {

    //VARIABLES
    //the boundings of the entity
    protected List<Bounding> boundings = new ArrayList<Bounding>();
    //the list of entities that this has collided with in this frame
    protected List<Entity> collidedWith = new ArrayList<Entity>();
    
    //is true when this collidable is active
    protected boolean active = true;
    
    //PUBLIC METHODS
    /**Passes in an object that this has collided with
    @other the other entity this has collided with*/
    public void collision(Entity other) {
        
        collidedWith.add(other);
    }
    
    //GETTERS
    /**@return the boundings of this entity*/
    public List<Bounding> getBoundings() {
        
        return boundings;
    }
    
    /**@return if the collidable is active*/
    public boolean isActive() {
        
        return active;
    }
    
    //PROTECTED METHODS
    /**Processes collision data*/
    protected void processCollisions() {
        
        //TO OVERIDE
    }
    
    /**Sets the positions of the boundings
    @param pos the new position of the boundings*/
    protected void setBoundingPos(Vector3 pos) {
        
        for (Bounding b : boundings) {
            
            b.setPos(pos);
        }
    }
    
    /**Adds the debug meshes of the boundings*/
    protected void addBoundingDebugMesh() {
        
        for (Bounding b : boundings) {
            
            b.addDebugMesh();
        }
    }
    
    /**Cleans up all of the boundings*/
    protected void cleanUpAllBoundings() {
        
        for (Bounding b : boundings) {
            
            b.cleanUp();
        }
    }
}
