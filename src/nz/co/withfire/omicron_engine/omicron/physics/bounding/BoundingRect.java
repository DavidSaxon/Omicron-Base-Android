/********************************\
| A rectangular shaped bounding. |
|                                |
| @author David Saxon            |
\********************************/

package nz.co.withfire.omicron_engine.omicron.physics.bounding;

import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicron_engine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicron_engine.override.DebugValues;

public class BoundingRect extends Bounding {

    //VARIABLES
    //the dimensions of the bounding rectangle
    private Vector2 dim = new Vector2();
    //the dimensions of the bounding rectangle after scaling
    private Vector2 scaleDim = new Vector2();
    
    //CONSTRUCTORS
    /**Creates a new bounding rectangle
    @param dim the dimensions of the bounding rectangle
    @param offset the offset of the bounding rectangle*/
    public BoundingRect(Vector2 dim, Vector3 offset) {
        
        this.dim.copy(dim);
        this.offset.copy(offset);
        scale();
    }
    
    /**Creates a bounding rectangle by copying another
    @param other the other bounding rectangle to copy*/
    public BoundingRect(BoundingRect other) {
        
        this.pos.copy(other.pos);
        this.scale.copy(other.scale);
        this.dim.copy(other.dim);
        this.offset.copy(other.offset);
        scale();
        
        //in debug mode create the debug mesh
        if (DebugValues.DEBUG_BOUNDINGS) {
            
            debugMesh = (Mesh)
                ResourceManager.getRenderable("debug_bounding_rect");
            debugMesh.getMaterial().setWireframe(true);
            this.debugMesh.setScale(
                new Vector3(scaleDim.x, scaleDim.y, 1));
        }
    }
    
    //PUBLIC METHODS
    @Override
    public Bounding clone() {
        
        return new BoundingRect(this);
    }
    
    //GETTERS
    /**@return dim the dimensions of the bounding rectangle*/
    public final Vector2 getDim() {
        
        return scaleDim;
    }
    
    //PROTECTED METHODS
    @Override
    protected void scale() {
        
        scaleDim.set(dim.x * (scale.x / 2.0f), dim.y * (scale.y / 2.0f));
        
        if (DebugValues.DEBUG_BOUNDINGS) {
            
            if (debugMesh != null) {
                
                debugMesh.setScale(
                    new Vector3(scaleDim.x, scaleDim.y, 1));
            }
        }
    }
}
