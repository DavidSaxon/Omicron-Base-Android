/***************************************************\
| Abstract class that all bounding areas implement. |
|                                                   |
| @author David Saxon                               |
\***************************************************/

package nz.co.withfire.omicron_engine.omicron.physics.bounding;

import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicron_engine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicron_engine.override.DebugValues;

public abstract class Bounding {

    //VARIABLES
    //the offset of the bounding
    protected Vector3 offset = new Vector3();
    //the position of the bounding
    protected Vector3 pos = new Vector3();
    //the scale of the bounding
    protected Vector3 scale = new Vector3(1.0f, 1.0f, 1.0f);
    
    //the debug mesh of the bounding
    protected Mesh debugMesh;
    
    //PUBLIC METHODS
    /**Adds a debug mesh for the bounding*/
    @SuppressWarnings("unused")
	public void addDebugMesh() {
        
        //in debug mode create bounding outline
        if (debugMesh != null && DebugValues.DEBUG_BOUNDINGS) {
            
            debugMesh.setTranslation(pos.clone());
            OmicronRenderer.add(debugMesh);
        }
    }
    
    /**Cleans up the bounding box*/
    @SuppressWarnings("unused")
	public void cleanUp() {
        
        //remove the debug shape
        if (debugMesh != null && DebugValues.DEBUG_BOUNDINGS) {

            OmicronRenderer.remove(debugMesh);
        }
    }
    
    /**@return a copy of the bounding*/
    public abstract Bounding clone();
    
    //GETTERS
    /**@return the position of the bounding*/
    public final Vector3 getPos() {
        
        //the pos with the offset
        Vector3 offsetPos = offset.clone();
        offsetPos.add(pos);
        
        return offsetPos;
    }
    
    /**@return the scale of the bounding*/
    public final Vector3 getScale() {
        
        return scale;
    }
    
    //SETTERS
    /**@param pos the new position of the bounding*/
    @SuppressWarnings("unused")
	public void setPos(Vector3 pos) {
        
        this.pos.copy(pos);
        
        if (debugMesh != null && DebugValues.DEBUG_BOUNDINGS) {
            
            Vector3 meshPos = pos.clone();
            meshPos.add(offset);
            debugMesh.setTranslation(meshPos);
        }
    }
    
    /**@param scale the new scale of the bounding*/
    public void setScale(Vector3 scale) {
        
        this.scale.copy(scale);
        scale();
    }
    
    //PROTECTED METHODS
    /**Scales the dimensions*/
    protected abstract void scale();
}
