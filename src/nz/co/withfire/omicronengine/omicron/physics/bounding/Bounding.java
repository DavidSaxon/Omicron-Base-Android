/***************************************************\
| Abstract class that all bounding areas implement. |
|                                                   |
| @author David Saxon                               |
\***************************************************/

package nz.co.withfire.omicronengine.omicron.physics.bounding;

import nz.co.withfire.omicronengine.omicron.physics.types.Collidable;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public abstract class Bounding {

    //VARIABLES
    //the offset of the bounding
    protected Vector3 offset = new Vector3();
    //the position of the bounding
    protected Vector3 pos = new Vector3();
    //the scale of the bounding
    protected Vector3 scale = new Vector3(1.0f, 1.0f, 1.0f);
    
    //the parent entity of the bounding
    protected Collidable parentEntity;
    
    //PUBLIC METHODS
    /**Adds a debug mesh for the bounding*/
    public abstract void addDebugMesh();
    
    /**Cleans up the bounding box*/
    public abstract void cleanUp();
    
    /**@return a copy of the bounding*/
    public abstract Bounding clone();
    
    //GETTERS
    /**@return the position of the bounding*/
    public final Vector3 getPos() {
        
        //the pos with the offset
        Vector3 offsetPos = new Vector3(pos);
        offsetPos.add(pos);
        
        return offsetPos;
    }
    
    /**@return the scale of the bounding*/
    public final Vector3 getScale() {
        
        return scale;
    }
    
    /**@return the parent entity of the bounding*/
    public Collidable getParentEntity() {
        
        return parentEntity;
    }
    
    //SETTERS
    /**@param pos the new position of the bounding*/
    public void setPos(Vector3 pos) {
        
        this.pos.copy(pos);
    }
    
    /**@param scale the new scale of the bounding*/
    public void setScale(Vector3 scale) {
        
        this.scale.copy(scale);
        scale();
    }
    
    /**@param parentEntity the parent entity of the bounding*/
    public void setParentEntity(Collidable parentEntity) {
        
        this.parentEntity = parentEntity;
    }
    
    //PROTECTED METHODS
    /**Scales the dimensions*/
    protected abstract void scale();
}
