/***********************\
| A perspective camera. |
|						|
| @author David Saxon   |
\***********************/

package nz.co.withfire.omicronengine.omicron.graphics.camera;

import android.opengl.Matrix;

public class PerspectiveCamera extends Camera {

	//VARIABLES
	//the field of view of the camera
	private float fieldOfView = 0.0f;
	//the clipping planes
	private float nearPlane = 0.0f;
	private float farPlane = 0.0f;
	
	//CONSTRUCTOR
	/**Creates a new perspective camera
	@param fieldOfView the field of view of the camera
	@param nearPlane the near clipping plane
	@param farPlane the far clipping plane*/
	public PerspectiveCamera(float fieldOfView,
		float nearPlane, float farPlane) {
		
		this.fieldOfView = fieldOfView;
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
		
		this.id = idCounter++;
	}
	
	//PUBLIC METHODS
	@Override
	public void setProjection(float[] projectionMatrix) {
		
		//reload the identity matrix
		Matrix.setIdentityM(projectionMatrix, 0);
		
    	//apply the perspective matrix
		setPerspective(projectionMatrix);
	}
	
	//PRIVATE METHODS
	/**Calculates the perspective matrix
	@param projectionMatrix the projection matrix*/
	private void setPerspective(float[] projectionMatrix) {
		
		//calculate the aspect ratio of the camera
		float aspect = (float) dimensions.x / (float) dimensions.y;
		
		//calculate the values to pass to the frustum call
		float yMax = nearPlane * (float)
			Math.tan(fieldOfView * (Math.PI / 360.0f));
		float yMin = -yMax;
		float xMin = yMin * aspect;
		float xMax = yMax * aspect;
		
		//make the frustum call
		Matrix.frustumM(projectionMatrix, 0, xMin, xMax,
			yMin, yMax, nearPlane, farPlane);
	}
}
