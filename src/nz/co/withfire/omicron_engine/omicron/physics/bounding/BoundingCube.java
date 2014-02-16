/*************************\
| A sube shaped bounding. |
|                         |
| @author David Saxon     |
\*************************/

package nz.co.withfire.omicron_engine.omicron.physics.bounding;

import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicron_engine.override.DebugValues;

public class BoundingCube extends Bounding {

    //VARIABLES
    //the dimensions
    private Vector3 dim = new Vector3();
    //the dimensions after scaling
    private Vector3 scaleDim = new Vector3();
    
    //CONSTRUCTORS
    /**Creates a new bounding cube
    @param dim the dimensions of the bounding cube
    @param offset the offset of the bounding cube*/
    public BoundingCube(Vector3 dim, Vector3 offset) {
        
        this.dim.copy(dim);
        this.offset.copy(offset);
        scale();
    }
    
    /**Creates a bounding cube by copying another
    @param other the other bounding cube to copy*/
    public BoundingCube(BoundingCube other) {
        
        this.pos.copy(other.pos);
        this.scale.copy(other.scale);
        this.dim.copy(other.dim);
        this.offset.copy(other.offset);
        scale();
        
        //in debug mode create the debug mesh
        if (DebugValues.DEBUG_BOUNDINGS) {
            
            debugMesh = (Mesh)
                ResourceManager.getRenderable("debug_bounding_cube");
            debugMesh.getMaterial().setWireframe(true);
            this.debugMesh.setScale(
                new Vector3(scaleDim.x, scaleDim.y, scaleDim.z));
        }
    }
    
    //PUBLIC METHODS
    @Override
    public Bounding clone() {
        
        return new BoundingCube(this);
    }
    
    //GETTERS
    /**@return the dimensions of this bounding cube*/
    public final Vector3 getDim() {
        
        return scaleDim;
    }
    
    //PROTECTED METHODS
    @Override
    protected void scale() {
        
        scaleDim.set(dim.x * (scale.x / 2.0f),
            dim.y * (scale.y / 2.0f), dim.z * (scale.z / 2.0f));
        
        if (DebugValues.DEBUG_BOUNDINGS) {
            
            if (debugMesh != null) {
                
                debugMesh.setScale(
                    new Vector3(scaleDim.x, scaleDim.y, scaleDim.z));
            }
        }
    }
}
