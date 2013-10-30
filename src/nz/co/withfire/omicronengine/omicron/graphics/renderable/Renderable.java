/********************************************\
| Represents an object that can be rendered. |
|											 |
| @author David Saxon						 |
\********************************************/

package nz.co.withfire.omicronengine.omicron.graphics.renderable;

import nz.co.withfire.omicronengine.omicron.graphics.material.Material;
import nz.co.withfire.omicronengine.omicron.utilities.MathUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.override.Values;
import android.opengl.Matrix;
import android.util.Log;

public abstract class Renderable {

	//ENUMERATORS
	//the groups of renderables
	public enum Group {
		
		STD,    //standard object that are affected by the camera
		GUI     //user interface shapes that are not effected by the
			    //standard camera
	};
	
	//VARIABLES
	//the type this is
	protected Group group = Group.STD;
	//the layer of this shape
	protected int layer = 0;
	
	//the material of the renderable
	protected Material material = new Material();
	
	//Transformations info
	//the translation
	private Vector3 translation = new Vector3();
	//the local rotation
	private Vector3 localRot = new Vector3();
	//the global rotation
	private Vector3 globalRot = new Vector3();
	//the post rotation translation
	private Vector3 postRotTrans = new Vector3();
	//the scale
	private Vector3 scale = new Vector3(1.0f, 1.0f, 1.0f);
	
    //Matrix
	//the model view matrix
	protected float[] mvMatrix = new float[16];
    //the model view projection matrix
    protected float[] mvpMatrix = new float[16];
    //the model matrix
    protected float[] modelMatrix = new float[16];
	
    //CONSTRUCTOR
    /**Creates a new renderable
    @param group the group of the renderable this is in
    @param layer the layer of the renderable*/
    public Renderable(Group group, int layer) {
    	
    	this.group = group;
    	this.layer = layer;
    }
    
	//PUBLIC METHODS
	/**Renders the renderable
	@param viewMatrix the view matrix
	@param projectionMatrix the projection matrix*/
	public void render(float viewMatrix[], float projectionMatrix[]) {
		
		//TO OVERRIDE
	}
	
	/**@return a deep copy of the renderable*/
	public Renderable deepCopy() {
		
		//TO OVERRIDE
		
		return null;
	}
	
	//GETTERS
	/**@return the group*/
	public Group getGroup() {
		
		return group;
	}
	
	/**@return the layer of the renderable*/
	public int getLayer() {
		
		return layer;
	}
	
	/**@return the material of the renderable*/
	public Material getMaterial() {
		
		return material;
	}
	
	/**@param group the new group*/
	public void setGroup(Group group) {
		
		this.group = group;
	}
	
	/**@return the translation*/
	public Vector3 getTranslation() {
		
		return translation;
	}
	
	/**@return the local rotation*/
	public Vector3 getLocalRot() {
		
		return localRot;
	}
	
	/**@return the global rotation*/
	public Vector3 getGlobalRotation() {
		
		return globalRot;
	}
	
	/**@return the post rotation translation*/
	public Vector3 getPostRotTrans() {
		
		return postRotTrans;
	}
	
	/**@return the scale*/
	public Vector3 getScale() {
		
		return translation;
	}
	
	//SETTERS
	/**@param layer the new layer of the renderable*/
	public void setLayer(int layer) {
		
		this.layer = layer;
	}
	
	/**@param material the material of the renderable*/
	public void setMaterial(Material material) {
		
		this.material = material;
	}
	
	/**@param translation the new translation*/
	public void setTranslation(final Vector3 translation) {
		
		this.translation = translation;
	}
	
	/**@param localRot the new local rotation*/
	public void setLocalRot(final Vector3 localRot) {
		
		this.localRot = localRot;
	}
	
	/**@param globalRot the new global rotation*/
	public void setGlobalRot(final Vector3 globalRot) {
		
		this.globalRot = globalRot;
	}
	
	/**@param postRotTrans the new post rotation translation*/
	public void setPostRotTrans(final Vector3 postRotTrans) {
		
		this.postRotTrans = postRotTrans;
	}
	
	/**@param scale the new scale*/
	public void setScale(final Vector3 scale) {
		
		this.scale = scale;
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
        
        //post rotation translation
        Matrix.translateM(modelMatrix, 0, postRotTrans.getX(),
    		postRotTrans.getY(), postRotTrans.getZ());
        
        //local rotation
		Matrix.rotateM(modelMatrix, 0, localRot.getY(), 0, 1, 0);
		Matrix.rotateM(modelMatrix, 0, localRot.getX(),
			(float) Math.cos(localRot.getY() * MathUtil.DEGREES_TO_RADIANS), 0,
			(float) Math.sin(localRot.getY() * MathUtil.DEGREES_TO_RADIANS));
		//TODO: z axis
		
		//global rotation
		Matrix.rotateM(modelMatrix, 0, globalRot.getX(), 1, 0, 0);
		Matrix.rotateM(modelMatrix, 0, globalRot.getY(), 0, 1, 0);
		Matrix.rotateM(modelMatrix, 0, globalRot.getZ(), 0, 0, 1);
		
		//translation
        Matrix.translateM(modelMatrix, 0, translation.getX(),
    		translation.getY(), translation.getZ());
        
        //scale
        Matrix.scaleM(modelMatrix, 0, scale.getX(), scale.getY(), scale.getZ());
        
        //calculate the mvp matrix
        Matrix.multiplyMM(mvMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvMatrix, 0);
	}
	
	/**Copies the common renderable elements into the new renderable
	@param copy the renderable to copy into*/
	protected void copyCommonElements(Renderable copy) {
		
		copy.setMaterial(material);
		copy.setTranslation(translation);
		copy.setLocalRot(localRot);
		copy.setGlobalRot(globalRot);
		copy.setPostRotTrans(postRotTrans);
		copy.setScale(scale);
	}
}
