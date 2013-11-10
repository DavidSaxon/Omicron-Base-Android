/***************************************\
| Utilities relating to transformations |
|                                       |
| @author David Saxon                   |
\***************************************/

package nz.co.withfire.omicronengine.omicron.utilities;

import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.override.Values;
import android.opengl.Matrix;

public class TransformationsUtil {

    //VARIABLES
    //the screen dimensions
    private static Vector2 screenDim = new Vector2();
    //the openGL view port dimensions
    private static Vector2 openGLDim = new Vector2();
    //the scale amounts
    private static Vector2 scale = new Vector2();
    
    
    //PUBLIC FUNCTIONS
    /**Initialise the need values for the transformation utilities
    @param screenDim the screen dimensions
    @param projectionMatrix the projection matrix*/
    public static void init(Vector2 screenDim, float[] projectionMatrix) {
        
        TransformationsUtil.screenDim = screenDim;
        
        //set the opengl dimensions
        openGLDim.copy(screenPosToOpenGLPos(screenDim, projectionMatrix));
        
        openGLDim.set(Math.abs(openGLDim.getX()),
    		Math.abs(openGLDim.getY()));
        
        //get the scaling amounts
        scale.setX((openGLDim.getX() / Values.NATURAL_SCREEN_SIZE.getX()));
        scale.setY((openGLDim.getY() / Values.NATURAL_SCREEN_SIZE.getY()));
    }
    
    /**Converts screen position to the equivalent OpenGL position
    @param screenPos the screen position to convert
    @param projectionMatrix the projection matrix
    @return the new OpenGL position*/
    public static Vector2 screenPosToOpenGLPos(Vector2 screenPos,
		float[] projectionMatrix) {
        
    	float vectorArray[] = new float[4];
    	vectorArray[0] =
			(2.0f * (screenPos.getX() / Camera.getDimensions().getX())) - 1.0f;
    	vectorArray[1] =
			-(2.0f * (screenPos.getY() / Camera.getDimensions().getY())) + 1.0f;

    	float projectionInverse[] = new float[16];
    	Matrix.invertM(projectionInverse, 0, projectionMatrix, 0);
    	
    	Matrix.multiplyMV(vectorArray, 0, projectionInverse, 0,
			vectorArray, 0);
    	
    	return new Vector2(vectorArray);
    }
    
    /**Get the scaled position of the given scalar
    @param scalar the scalar to scale
    @return the new scaled scalar*/
    public static float scaleToScreen(float scalar) {
        
        if (scale.getX() < scale.getY()) {
            
            return scalar * scale.getX();
        }
        
        return scalar * scale.getY();
    }
    
    /**Get the scaled position of the given position
    @param pos the position to scale
    @return the new scaled position*/
    public static Vector2 scaleToScreen(Vector2 pos) {
        
        return new Vector2(pos.getX() * scale.getX(),
            pos.getY() * scale.getY());
    }
    
    /**@return the OpenGL perspective dimensions*/
    public static Vector2 getOpenGLDim() {
        
        return openGLDim;
    }
    
    /**@return the screen dimensions*/
    public static Vector2 getScreenDim() {
        
        return screenDim;
    }
}