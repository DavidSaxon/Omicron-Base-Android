/************************************************\
| A 2 dimensional sprite in 3 dimensional space. |
|                                                |
| @author David Saxon                            |
\************************************************/

package nz.co.withfire.omicronengine.omicron.graphics.renderable;

import android.util.Log;
import nz.co.withfire.omicronengine.omicron.logic.fps_manager.FPSManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.override.Values;

public class Animation extends Sprite {

    //VARIABLES
    //the length of the animation
    private int length;
    //the dimensions of the texture block
    private Vector2 blockDim;
    //the number of rows there are in the texture
    private int rows;
    
    //the current frame of the animation
    private float frame = 0;
    
    //the number of times the animation should play
    //(-1 for infinte)
    private int playTimes = -1;
    
    //the number of times the animation has played
    private int playCount = 0;
    
    //the framerate of the animation
    private float frameRate;
    //the frame change time scale
    private float timeScale;
    
    //CONSTRUCTOR
    /**Creates a new animation
    @param group of renderables this is in
    @param layer the layer of this
    @param vertices the vertex array
    @param uvCoords the uv co-ordinates array
    @param normals the normals array
    @param length the length of the animation
    @param blockDim the dimensions of the texture block
    @param rows the number of rows of the animation has
    @param the framerate of the animation
    @param playTimes the number of times the animation should play*/
    public Animation(Group group, int layer,
        float vertices[], float uvCoords[], float normals[],
        int length, Vector2 blockDim, int rows,
        float frameRate, int playTimes) {
        
        super(group, layer, vertices, uvCoords, normals);
        
        //initialise variables
        this.length = length;
        this.blockDim = blockDim.clone();
        this.rows = rows;
        this.playTimes = playTimes;
        this.frameRate = frameRate;
        //get the percent of the time scale to update
        timeScale = frameRate / FPSManager.getStdFrameRate();
    }
    
    //PUBLIC METHODS
    @Override
    public void render(float viewMatrix[], float projectionMatrix[]) {
        
        if (playCount < playTimes || playTimes == -1) {
        
            updateUV();
            super.render(viewMatrix, projectionMatrix);
        }        
        
        frame += (timeScale * FPSManager.getTimeScale());
        
        while (frame >= length) {
            
            ++playCount;
            frame -= length;
        }
        
    }
    
    @Override
    public Renderable clone() {
    
        //copy the lists
        float verticesCopy[] = new float[vertices.length];
        for (int i = 0; i < vertices.length; ++i) {
    
            verticesCopy[i] = vertices[i];
        }
        float uvCopy[] = new float[uvCoords.length];
        for (int i = 0; i < uvCoords.length; ++i) {
    
            uvCopy[i] = uvCoords[i];
        }
        float normalsCopy[] = new float[normals.length];
        for (int i = 0; i < normals.length; ++i) {
    
            normalsCopy[i] = normals[i];
        }
    
        Animation copy = new Animation(group, layer,
            verticesCopy, uvCopy, normalsCopy,
            length, blockDim, rows, frameRate, playTimes);
    
        //copy over the common renderable elements
        copyCommonElements(copy);
    
        return copy;
    }
    
    /**@return if the animation has finished playing*/
    public boolean finished() {
        
        return playCount >= playTimes;
    }
    
    //PRIVATE METHODS
    /**Updates the uv co-ordinates*/
    private void updateUV() {
        
        int intFrame = (int) frame;
        
        //shorthand
        float w1 = (intFrame / rows) * blockDim.x;
        float w2 = w1 + blockDim.x;
        float h1 = (intFrame % rows) * blockDim.y;
        float h2 = h1 + blockDim.y;
        h1 = -h1;
        h2 = -h2;
        
        //create the next tex coords
        float[] newUV = {
                
            w1, h2,
            w2, h2,
            w1, h1,
            w2, h2,
            w2, h1,
            w1, h1
        };
        
        //copy
        for(int i = 0; i < newUV.length; ++i) {
            
            uvCoords[i] = newUV[i];
        }
        
        buildUVBuffer();
    }
}
