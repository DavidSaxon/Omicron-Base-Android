/********************************************\
| Represents an object that can be rendered. |
|											 |
| @author David Saxon						 |
\********************************************/

package nz.co.withfire.omicronengine.omicron.graphics.renderable;

import android.opengl.Matrix;

public abstract class Renderable {

	//ENUMERATORS
	//the types of renderables
	public enum Type {
		
		STD,    //standard object that are affected by the camera
		EFFECT, //objects that are affected by the camera but rendered
				//after the camera
		GUI     //user interface shapes that are not effected by the
			    //standard camera
	};
	
	//VARIABLES
	//the type this is
	private Type type = Type.STD;
	//TODO: transformation info
	
    //Matrix
	//the model view matrix
	protected float[] mvMatrix = new float[16];
    //the model view projection matrix
    protected float[] mvpMatrix = new float[16];
    //the model matrix
    private float[] modelMatrix = new float[16];
	
	//PUBLIC METHODS
	/**Renders the renderable
	@param viewMatrix the view matrix
	@param projectionMatrix the projection matrix*/
	public void render(float viewMatrix[], float projectionMatrix[]) {
		
		//TO OVERRIDE
	}
	
	/**@return the type*/
	public Type getType() {
		
		return type;
	}
	
	/**@param type the new type*/
	public void setType(Type type) {
		
		this.type = type;
	}
	
	//PROTECTED METHODS
	/**Applies the transformations
	@param viewMatrix the view matrix
	@param projectionMatrix the projection matrix*/
	protected void applyTransformations(
		float viewMatrix[], float projectionMatrix[]) {
		
		//set the transformation matrix to the identity matrix
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(mvMatrix, 0);
        
        //calculate the mvp matrix
        Matrix.multiplyMM(mvMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvMatrix, 0);
	}
}
