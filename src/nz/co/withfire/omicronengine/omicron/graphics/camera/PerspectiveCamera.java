/***********************\
| A perspective camera. |
|						|
| @author David Saxon   |
\***********************/

package nz.co.withfire.omicronengine.omicron.graphics.camera;

import android.annotation.TargetApi;
import android.opengl.Matrix;
import android.os.Build;

public class PerspectiveCamera extends Camera {

	//CONSTRUCTOR
	/**Creates a new perspective camera*/
	public PerspectiveCamera() {
	}
	
	//PUBLIC METHODS
	@Override
	public void setProjection(float[] projectionMatrix) {
		
		//reload the identity matrix
		Matrix.setIdentityM(projectionMatrix, 0);
		
    	//apply the projection matrix
        float ratio = (float) dimensions.getX() / (float) dimensions.getY();
        Matrix.perspectiveM(projectionMatrix, 0, 60, ratio, 0.1f, 500.0f);
	}
}
