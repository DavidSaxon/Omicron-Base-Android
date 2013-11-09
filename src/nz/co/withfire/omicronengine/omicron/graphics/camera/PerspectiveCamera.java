/***********************\
| A perspective camera. |
|						|
| @author David Saxon   |
\***********************/

package nz.co.withfire.omicronengine.omicron.graphics.camera;

import android.opengl.Matrix;

public class PerspectiveCamera extends Camera {

	//VARIABLES
	//the clipping planes
	private float nearPlane = 0.0f;
	private float farPlane = 0.0f;
	
	//CONSTRUCTOR
	/**Creates a new perspective camera
	@param nearPlane the near clipping plane
	@param farPlane the far clipping plane*/
	public PerspectiveCamera(float nearPlane, float farPlane) {
		
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
	}
	
	//PUBLIC METHODS
	@Override
	public void setProjection(float[] projectionMatrix) {
		
		//reload the identity matrix
		Matrix.setIdentityM(projectionMatrix, 0);
		
    	//apply the projection matrix
		float ratio = (float) dimensions.getX() / (float) dimensions.getY();
//        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1,
//    		nearPlane, farPlane);
		Matrix.perspectiveM(projectionMatrix, 0, 60.0f, ratio, 0.01f, 100.0f);
;
	}
}
