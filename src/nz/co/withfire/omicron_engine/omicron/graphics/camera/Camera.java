/**************************************\
| Abstract class that camera's extend. |
|                                       |
| @author David Saxon                   |
\**************************************/

package nz.co.withfire.omicron_engine.omicron.graphics.camera;

import android.opengl.GLES20;
import android.opengl.Matrix;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;

public abstract class Camera {

    //VARIABLES
    //the dimensions of the camera (in pixels) statically stored
    protected static Vector2 dimensions = new Vector2();

    //the position of the camera
    protected Vector3 pos = new Vector3();
    //the local rotation of the camera
    protected Vector3 localRot = new Vector3();
    //the post roataion translation of the camera
    protected Vector3 postRotTrans = new Vector3();
    //the zoom of the camera
    protected float zoom = 1.0f;

    //DEBUGGING
    //id counter
    public static int idCounter = 0;
    //the id of this camera
    public int id = 0;

    //PUBLIC METHODS
    /**Sets the projection matrix
    @param projectionMatrix the projection matrix*/
    public abstract void setProjection(float[] projectionMatrix);

    /**Sets the view matrix
    @param viewMatrix the view matrix*/
    public void setView(float[] viewMatrix) {

        //set the view port
        GLES20.glViewport(0, 0, (int) dimensions.x, (int) dimensions.y);

        //reload the identity
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -0.001f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    /**Applies the transformations
    @param viewMatrix the view matrix*/
    public void applyTransformations(float[] viewMatrix) {

        //post rotation translate
        Matrix.translateM(viewMatrix, 0, postRotTrans.x,
            postRotTrans.y, postRotTrans.z);

        //local rotation
        Matrix.rotateM(viewMatrix, 0, localRot.z, 0, 0, 1);
        Matrix.rotateM(viewMatrix, 0, localRot.y, 0, 1, 0);
        Matrix.rotateM(viewMatrix, 0, localRot.x, 1, 0, 0);

        //translate
        Matrix.translateM(viewMatrix, 0, pos.x, pos.y, pos.z);

        //zoom
        Matrix.scaleM(viewMatrix, 0, zoom, zoom, zoom);
    }

    /**Cleans up the camera*/
    public void cleanUp() {

        dimensions = null;
    }

    /**@return the current dimensions of the camera*/
    public static Vector2 getDimensions() {

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

    /**@return the post rotation translation of the camera*/
    public Vector3 getPostRotTrans() {

        return postRotTrans;
    }

    /**@return the zoom of the camera*/
    public float getZoom() {

        return zoom;
    }

    /**@param dimensions the new dimensions of the camera (in pixels)*/
    public static void setDimensions(final Vector2 dimensions) {

        Camera.dimensions.copy(dimensions);
    }

    /**@param pos the new position of the camera*/
    public void setPos(final Vector3 pos) {

        this.pos.copy(pos);
    }

    /**@param localRot the new local rotation of the camera*/
    public void setLocalRot(final Vector3 localRot) {

        this.localRot.copy(localRot);
    }

    /**@param postRotTrans the new post rotation translation of the camera*/
    public void setRotPostTrans(final Vector3 postRotTrans) {

        this.postRotTrans.copy(postRotTrans);
    }

    /**@param zoom the new zoom of the camera*/
    public void setZoom(float zoom) {

        this.zoom = zoom;
    }
}
