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
    
    //the default view and projection matrices
    public static float[] viewMatrix;
    public static float[] projectionMatrix;
    
    //PUBLIC FUNCTIONS
    /**Initialise the need values for the transformation utilities
    @param screenDim the screen dimensions
    @param viewMatrix the view matrix
    @param projectionMatrix the projection matrix*/
    public static void init(Vector2 screenDim,
        float[] viewMatrix, float[] projectionMatrix) {
        
        TransformationsUtil.screenDim = screenDim;
        TransformationsUtil.viewMatrix = viewMatrix;
        TransformationsUtil.projectionMatrix = projectionMatrix;
        
        //set the opengl dimensions
        openGLDim.copy(screenPosToOpenGLPos(screenDim,
            viewMatrix, projectionMatrix));
        
        openGLDim.set(Math.abs(openGLDim.getX()),
    		Math.abs(openGLDim.getY()));
        
        //get the scaling amounts
        scale.setX((openGLDim.getX() / Values.NATURAL_SCREEN_SIZE.getX()));
        scale.setY((openGLDim.getY() / Values.NATURAL_SCREEN_SIZE.getY()));
    }
    
    /**Converts screen position to the equivalent OpenGL position
    @param screenPos the screen position to convert
    @param viewMatrix the view matrix
    @param projectionMatrix the projection matrix
    @return the new OpenGL position*/
    public static Vector2 screenPosToOpenGLPos(Vector2 screenPos,
        float[] viewMatrix, float[] projectionMatrix) {
        
    	float vectorArray[] = new float[4];
    	vectorArray[0] =
			(2.0f * (screenPos.getX() / Camera.getDimensions().getX())) - 1.0f;
    	vectorArray[1] =
			-(2.0f * (screenPos.getY() / Camera.getDimensions().getY())) + 1.0f;
    	
    	float viewProjectionInverse[] = new float[16];
    	Matrix.multiplyMM(viewProjectionInverse, 0,
			projectionMatrix,0, viewMatrix, 0);
    	Matrix.invertM(viewProjectionInverse, 0, viewProjectionInverse, 0);
    	
    	
    	Matrix.multiplyMV(vectorArray, 0, viewProjectionInverse, 0,
			vectorArray, 0);
    	
    	return new Vector2(vectorArray);
    	
//        //initialise the new position vector
//        Vector2 openGLPos = new Vector2();
//        
//        //matrix
//        float[] invertedMatrix = new float[16];
//        float[] transformationMatrix = new float[16];
//        
//        //points
//        float[] normalisedPoint = new float[4];
//        float[] outPoint = new float[4];
//        
//        //invert the positions
//        int screenPosX = (int) (screenDim.getX() - screenPos.getX());
//        int screenPosY = (int) (screenDim.getY() - screenPos.getY());
//        
//        //transform the screen point
//        normalisedPoint[0] =
//            (float) ((screenPosX) * 2.0f / screenDim.getX() - 1.0);
//        normalisedPoint[1] =
//                (float) ((screenPosY) * 2.0f / screenDim.getY() - 1.0);
//        normalisedPoint[2] =  -1.0f;
//        normalisedPoint[3] =   1.0f;
//        
//        //get the matrix
//        Matrix.multiplyMM(transformationMatrix, 0, projectionMatrix, 0,
//                viewMatrix, 0);
//        Matrix.invertM(invertedMatrix, 0, transformationMatrix, 0);
//        
//        //apply the inverse to the point
//        Matrix.multiplyMV(outPoint, 0, invertedMatrix, 0, normalisedPoint, 0);
//        
//        //avoid dividing by zero
//        if (outPoint[3] == 0.0f) {
//            
//            return openGLPos;
//        }
//        
//        //Divide by the 3rd component to find the real world position
//        openGLPos.setX(- (outPoint[0] / outPoint[3]));
//        openGLPos.setY(outPoint[1] / outPoint[3]);
//        
//        return openGLPos;
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