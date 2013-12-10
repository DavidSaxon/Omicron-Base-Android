package nz.co.withfire.omicronengine.omicron.physics.bounding;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.override.DebugValues;

public class BoundingCircle extends Bounding {

    //VARIABLES
    //the radius of the bounding circle
    private float radius = 0;
    //the radius of the circle after scaling
    private float scaleRadius = 0;
    
    //CONSTRUCTORS
    /**Creates a new bounding circle
    @param radius of the bounding circle
    @param offset the offset of the bounding circle*/
    public BoundingCircle(float radius, Vector3 offset) {
        
        this.radius = radius;
        this.offset.copy(offset);
        scale();
    }
    
    /**Creates a bounding copy by copying another
    @param other the other bounding circle to copy*/
    public BoundingCircle(BoundingCircle other) {
        
        pos.copy(other.pos);
        scale.copy(other.scale);
        radius = other.radius;
        offset.copy(other.offset);
        scale();
        
        //if in debug mode create the mesh
        if (DebugValues.DEBUG_BOUNDINGS) {
            
            debugMesh = (Mesh)
                ResourceManager.getRenderable("debug_bounding_circle");
            debugMesh.getMaterial().setWireframe(true);
            this.debugMesh.setScale(
                new Vector3(scaleRadius / 2.0f, scaleRadius / 2.0f, 1));
        }
    }
    
    //PUBLIC METHODS
    @Override
    public Bounding clone() {
        
        return new BoundingCircle(this);
    }
    
    //GETTERS
    /**@return the radius of the bounding circle*/
    public float getRadius() {
        
        return radius;
    }
    
    //PROTECTED METHODS
    @Override
    protected void scale() {
        
        scaleRadius = radius * scale.x;
        
        if (DebugValues.DEBUG_BOUNDINGS) {
            
            if (debugMesh != null) {
                
                debugMesh.setScale(
                    new Vector3(scaleRadius / 2.0f, scaleRadius / 2.0f, 1));
            }
        }
    }
}
