/********************************************\
| Represents an object that can be rendered. |
|                                             |
| @author David Saxon                         |
\********************************************/

package nz.co.withfire.omicron_engine.omicron.graphics.renderable;

import nz.co.withfire.omicron_engine.omicron.graphics.material.Material;
import nz.co.withfire.omicron_engine.omicron.utilities.MathUtil;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;
import android.opengl.Matrix;

public abstract class Renderable implements Comparable<Renderable> {

    //ENUMERATORS
    //the groups of renderables
    public enum Group {

        STD,    //standard object that are affected by the camera
        GUI     //user interface shapes that are not effected by the
                //standard camera
    };

    //The custom shader input function modes
    public enum CustomShaderInputMode {

        ADD, //is done alongside the current shader input code
        REPLACE //replace the current shader input code
    };

    //VARIABLES
    //the type this is
    protected Group group = Group.STD;
    //the layer of this shape
    protected int layer = 0;

    //the material of the renderable
    protected Material material = new Material();

    //Custom shader input function
    //the custom shader input function mode
    protected CustomShaderInputMode customShaderInputMode =
        CustomShaderInputMode.ADD;
    //the custom shader input function
    protected CustomShaderInputFunction customShaderInputFunction = null;

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
    public Renderable clone() {

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

    /**@return the current custom shader input mode*/
    public CustomShaderInputMode getCustomShaderInputMode() {

        return customShaderInputMode;
    }

    /**@return the current custom shader input function*/
    public CustomShaderInputFunction getCustomShaderInputFunction() {

        return customShaderInputFunction;
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
    /**@param group the new group*/
    public void setGroup(Group group) {

        this.group = group;
    }

    /**@param layer the new layer of the renderable*/
    public void setLayer(int layer) {

        this.layer = layer;
    }

    /**@param material the material of the renderable*/
    public void setMaterial(Material material) {

        this.material = material;
    }

    /**@param customShaderInputMode the new custom shader input mode*/
    public void setCustomShaderInputMode(
        CustomShaderInputMode customShaderInputMode) {

        this.customShaderInputMode = customShaderInputMode;
    }

    /**@param customShaderInputFunction the new custom shader input function*/
    public void setCustomShaderInputFunction(
        CustomShaderInputFunction customShaderInputFunction) {

        this.customShaderInputFunction = customShaderInputFunction;
    }

    /**@param translation the new translation*/
    public void setTranslation(final Vector3 translation) {

        this.translation.copy(translation);
    }

    /**@param localRot the new local rotation*/
    public void setLocalRot(final Vector3 localRot) {

        this.localRot.copy(localRot);
    }

    /**@param globalRot the new global rotation*/
    public void setGlobalRot(final Vector3 globalRot) {

        this.globalRot.copy(globalRot);
    }

    /**@param postRotTrans the new post rotation translation*/
    public void setPostRotTrans(final Vector3 postRotTrans) {

        this.postRotTrans.copy(postRotTrans);
    }

    /**@param scale the new scale*/
    public void setScale(final Vector3 scale) {

        this.scale.copy(scale);
    }

    @Override
    public int compareTo(Renderable r) {
        
        if (translation.z - r.translation.z < 0.0f) {
            
            return 1;
        }
        
        return -1;
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

        //translation
        Matrix.translateM(modelMatrix, 0, translation.x,
            translation.y, translation.z);

        //local rotation
        //Matrix.rotateM(modelMatrix, 0, localRot.y, 0, 1, 0);
        Matrix.rotateM(modelMatrix, 0, localRot.x,
            (float) Math.cos(localRot.y * MathUtil.DEGREES_TO_RADIANS), 0,
            (float) Math.sin(localRot.y * MathUtil.DEGREES_TO_RADIANS));
        Matrix.rotateM(modelMatrix, 0, localRot.y, 0, 1, 0);
        //TODO: z axis
        Matrix.rotateM(modelMatrix, 0, localRot.z, 0, 0, 1);

        //global rotation
        Matrix.rotateM(modelMatrix, 0, globalRot.x, 1, 0, 0);
        Matrix.rotateM(modelMatrix, 0, globalRot.z, 0, 0, 1);
        Matrix.rotateM(modelMatrix, 0, globalRot.y, 0, 1, 0);

        //post rotation translation
        Matrix.translateM(modelMatrix, 0, postRotTrans.x,
            postRotTrans.y, postRotTrans.z);

        //scale
        Matrix.scaleM(modelMatrix, 0, scale.x, scale.y, scale.z);

        //calculate the mvp matrix
        Matrix.multiplyMM(mvMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvMatrix, 0);
    }

    /**Copies the common renderable elements into the new renderable
    @param copy the renderable to copy into*/
    protected void copyCommonElements(Renderable copy) {

        copy.setMaterial(material.clone());
        copy.setTranslation(translation.clone());
        copy.setLocalRot(localRot.clone());
        copy.setGlobalRot(globalRot.clone());
        copy.setPostRotTrans(postRotTrans.clone());
        copy.setScale(scale.clone());
    }
}
