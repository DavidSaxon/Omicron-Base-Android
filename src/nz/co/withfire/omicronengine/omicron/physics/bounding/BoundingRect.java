/********************************\
| A rectangular shaped bounding. |
|                                |
| @author David Saxon            |
\********************************/

package nz.co.withfire.omicronengine.omicron.physics.bounding;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.override.DebugValues;

public class BoundingRect extends Bounding {

    //VARIABLES
    //the dimensions of the bounding rectangle
    private Vector2 dim = new Vector2();
    //the dimensions of the bounding box after scaling
    private Vector2 scaleDim = new Vector2();
    
    //the debug mesh
    private Mesh debugMesh;
    
    //CONSTRUCTORS
    /**Creates a new bounding rectangle
    @param dim the dimensions of the bounding box
    @param offset the offset of the bounding box*/
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
        
        //in debug mode create bounding outline
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
    public void cleanUp() {
        
        //remove the debug shape
        if (DebugValues.DEBUG_BOUNDINGS) {

            OmicronRenderer.remove(debugMesh);
        }
    }
    
    @Override
    public Bounding clone() {
        
        return new BoundingRect(this);
    }
    
    /**Adds the shape to the render lists in debug mode*/
    public void addDebugMesh() {
        
        //in debug mode create bounding outline
        if (DebugValues.DEBUG_BOUNDINGS) {
            
            debugMesh.setTranslation(pos.clone());
            OmicronRenderer.add(debugMesh);
        }
    }
    
    //GETTERS
    /**@return dim the dimensions of the bounding rectangle*/
    public final Vector2 getDim() {
        
        return scaleDim;
    }

    //SETTERS
    @Override
    public void setPos(Vector3 pos) {
        
        super.setPos(pos);
        
        if (DebugValues.DEBUG_BOUNDINGS) {
            
            debugMesh.setTranslation(pos.clone());
        }
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
