/**************************************\
| Abstract class that camera's extend. |
|									   |
| @author David Saxon				   |
\**************************************/

package nz.co.withfire.omicronengine.omicron.graphics.camera;

import android.opengl.GLES20;
import android.opengl.Matrix;
import nz.co.withfire.omicronengine.omicron.utilities.MathUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public abstract class Camera {

	//VARIABLES
	//the dimensions of the camera (in pixels
	protected Vector2 dimensions = new Vector2();
	
	//the position of the camera
	protected Vector3 pos = new Vector3();
	//the local rotation of the camera
	protected Vector3 localRot = new Vector3();
	//the post translation of the camera
	protected Vector3 postTrans = new Vector3();
	//the zoom of the camera
	protected float zoom = 1.0f;
	
	//PUBLIC METHODS
	/**Sets the projection matrix
	@param projectionMatrix the projection matrix*/
	public void setProjection(float[] projectionMatrix) {
		
		//TO OVERRIDE
	}
	
	/**Sets the view matrix
	@param viewMatrix the view matrix*/
	public void setView(float[] viewMatrix) {
		
		//set the view port
		GLES20.glViewport(0, 0,
			(int) dimensions.getX(), (int) dimensions.getY());
		
		//reload the identity
		Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -0.1f, 0.0f,
    		0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
	}
	
	/**Applies the transformations
	@param viewMatrix the view matrix*/
	public void applyTransformations(float[] viewMatrix) {
		
        //post translate
  		Matrix.translateM(viewMatrix, 0, postTrans.getX(),
			postTrans.getY(), postTrans.getZ());
        
		//local rotation
		Matrix.rotateM(viewMatrix, 0, localRot.getY(), 0, 1, 0);
		Matrix.rotateM(viewMatrix, 0, localRot.getX(),
			(float) Math.cos(localRot.getY() * MathUtil.DEGREES_TO_RADIANS), 0,
			(float) Math.sin(localRot.getY() * MathUtil.DEGREES_TO_RADIANS));
		//TODO: z axis
		
		//translate
		Matrix.translateM(viewMatrix, 0, pos.getX(), pos.getY(), pos.getZ());
		
		//zoom
		Matrix.scaleM(viewMatrix, 0, zoom, zoom, zoom);
	}
	
	/**@return the current dimensions of the camera*/
	public Vector2 getDimensions() {
		
		return dimensions;
	}
	
	/**@return the position of the camera*/
	public Vector3 getPos() {
		
		return pos;
	}
	
	/**@return the local rotation of the camera*/
	public Vector3 getLocalRot() {
		
		return localRot;
	}
	
	/**@return the post translation of the camera*/
	public Vector3 getPostTrans() {
		
		return postTrans;
	}
	
	/**@return the zoom of the camera*/
	public float getZoom() {
		
		return zoom;
	}
	
	/**@param dimensions the new dimensions of the camera (in pixels)*/
	public void setDimensions(final Vector2 dimensions) {
		
		this.dimensions.copy(dimensions);
	}
	
	/**@param pos the new position of the camera*/
	public void setPos(final Vector3 pos) {
		
		this.pos.copy(pos);
	}
	
	/**@param localRot the new local rotation of the camera*/
	public void setLocalRot(final Vector3 localRot) {
		
		this.localRot.copy(localRot);
	}
	
	/**@param postTrans the new post translation of the camera*/
	public void setPostTrans(final Vector3 postTrans) {
		
		this.postTrans.copy(postTrans);
	}
	
	/**@param zoom the new zoom of the camera*/
	public void setZoom(float zoom) {
		
		this.zoom = zoom;
	}
}
