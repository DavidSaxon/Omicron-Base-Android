/****************************\
| A glowing fish type thing. |
|                            |
| @author David Saxon        |
\****************************/

package nz.co.withfire.omicronengine.entities.twod_demo;

import android.opengl.GLES20;
import nz.co.withfire.omicronengine.omicron.graphics.material.Material;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.CustomShaderInputFunction;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable.CustomShaderInputMode;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.physics.collision.CollisionGroups;
import nz.co.withfire.omicronengine.omicron.physics.types.Collidable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.ColourUtil;
import nz.co.withfire.omicronengine.omicron.utilities.MathUtil;
import nz.co.withfire.omicronengine.omicron.utilities.TransformationsUtil;
import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.scenes.TwoDDemoScene;

public class GlowFish extends Collidable {

    //VARIABLES
    //the mesh
    private Mesh mesh;
    
    //is true if we should remove
    private boolean shouldRemove = false;
    
    //the rotation of the fish
    private Vector3 rot = new Vector3(0.0f, 0.0f, 135.0f);
    
    //the move speed of the fish
    private final float MOVE_SPEED = 0.01f;
    
    //a list of potential colours
    private static final Vector4 colours[] = {
        
        new Vector4(1.0f, 0.0f, 0.0f, 1.0f),
        new Vector4(1.0f, 0.5f, 0.0f, 1.0f),
        new Vector4(0.0f, 1.0f, 0.0f, 1.0f),
        new Vector4(0.0f, 1.0f, 1.0f, 1.0f),
        new Vector4(0.0f, 0.0f, 1.0f, 1.0f),
        new Vector4(1.0f, 0.0f, 1.0f, 1.0f),
        new Vector4(0.2f, 0.0f, 1.0f, 1.0f),
    };
    
    //CONSTRUCTOR
    /**Creates a new glow fish
    @param pos the initial position of the fish
    @param rot the initial rotation*/
    public GlowFish(Vector3 pos, Vector3 rot) {
        
        //initialise
        this.pos.copy(pos);
        this.rot.copy(rot);
        
        //get the mesh
        mesh = (Mesh) ResourceManager.getRenderable("glow_fish");
        //set colour
        setColour();
        //set translation
        mesh.setTranslation(this.pos);
        //set rotation
        mesh.setGlobalRot(this.rot);
        //add the custom shader input function
        mesh.setCustomShaderInputMode(CustomShaderInputMode.ADD);
        mesh.setCustomShaderInputFunction(new FishShaderInput());
        //add to the renderer
        OmicronRenderer.add(mesh);
        
        //boundings
        boundings = ResourceManager.getBounding("glow_fish");
        setBoundingPos(pos);
        setBoundingParent(this);
        addBoundingDebugMesh();
        
        //add to the collision group
        CollisionGroups.add("fish", this);
    }
    
    @Override
    public void update() {
        
        //process collision
        processCollisions();
        //clear the collision data
        collidedWith.clear();
        
        //move
        pos.x -= MOVE_SPEED * FPSManager.getTimeScale() * 
            Math.cos(rot.z * MathUtil.DEGREES_TO_RADIANS);
        pos.y -= MOVE_SPEED * FPSManager.getTimeScale() * 
            Math.sin(rot.z * MathUtil.DEGREES_TO_RADIANS);
        mesh.setTranslation(pos);
        setBoundingPos(pos);
        
        //bounce of walls
        if (pos.x <= -TransformationsUtil.getOpenGLDim().x) {
            
            rot.z = ValuesUtil.rand.nextInt(90) + 135.0f;
        }
        else if (pos.x >= TransformationsUtil.getOpenGLDim().x) {
            
            rot.z = ValuesUtil.rand.nextInt(90) - 45.0f;
        }
        else if (pos.y <= -TransformationsUtil.getOpenGLDim().y) {
            
            rot.z = ValuesUtil.rand.nextInt(90) - 135.0f;
        }
        else if (pos.y >= TransformationsUtil.getOpenGLDim().y) {
            
            rot.z = ValuesUtil.rand.nextInt(90) + 45.0f;
        }
    }
    
    @Override
    public boolean shouldRemove() {
        
        return shouldRemove;
    }
    
    @Override
    public void cleanUp() {
        
        cleanUpAllBoundings();
        
        CollisionGroups.remove("fish", this);
        OmicronRenderer.remove(mesh);
    }
    
    //PRIVATE METHODS
    /**Sets the colour of the fish*/
    private void setColour() {
        
        //create a new material
        Material material = new Material();
        //set values
        material.setShader(ResourceManager.getShader("glow_fish"));
        material.setTexture(ResourceManager.getTexture("glow_fish"));
        material.setShadeless(true);
        
        int index = Math.abs(ValuesUtil.rand.nextInt()) % colours.length;
        
        //choose a random colour
        material.setColour(colours[index]);
        mesh.setMaterial(material);
    }
    
    //PRIVATE INNER CLASS
    private class FishShaderInput implements CustomShaderInputFunction {
        
        //VARIABLES
        //start time
        private long startTime = System.currentTimeMillis();
        //the clamped time difference
        private float timeDiff = 0.0f;
        
        //PUBLIC METHODS
        @Override
        public void shaderInput(int program) {
            
            //get the time difference
            long currentTime = System.currentTimeMillis();
            int diff = (int) (currentTime - startTime);
            startTime = currentTime;
            
            //convert to seconds and add
            timeDiff += ((float) diff) / ValuesUtil.MS_IN_SEC;
            
            //clamp
            while (timeDiff >= 8.0f) {
                
                timeDiff -= 8.0f;
            }
            
            //pass in time
            GLES20.glUniform1f(
                GLES20.glGetUniformLocation(program, "u_Time"),
                timeDiff);
            
            //pass in the position
            GLES20.glUniform3fv(
                GLES20.glGetUniformLocation(program, "u_Position"),
                1, pos.toArray(), 0);
        }
    }

    @Override
    protected void processCollisions() {
        
        if (collidedWith.size() > 0) {
        
            //combine colours
            Vector4 thisColour = mesh.getMaterial().getColour();
            GlowFish other = (GlowFish) collidedWith.get(0);
            Vector4 otherColour = other.mesh.getMaterial().getColour();
            Vector4 combine = ColourUtil.average(thisColour, otherColour);
            
            TwoDDemoScene.fishExplode(this, other, pos, combine);
            shouldRemove = true;
        }
    }
}
