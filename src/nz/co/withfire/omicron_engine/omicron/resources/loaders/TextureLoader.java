/*******************************\
| Methods for loading textures. |
|                                |
| @author David Saxon            |
\******************************/

package nz.co.withfire.omicron_engine.omicron.resources.loaders;

import nz.co.withfire.omicron_engine.override.Values;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class TextureLoader {

    //PUBLIC METHODS
    /**Loads a .png file as an OpenGL texture
    @param context the android context
    @param resourceId the id of the .png file
    @return the OpenGL texture id*/
    public static int loadPNG(final Context context, int resourceId) {

        //create a pointer for the texture
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);

        //if generating the texture fails report error
        if (textureHandle[0] == 0) {

            Log.v(Values.TAG, "Error loading texture.");
            throw new RuntimeException("Error loading texture");
        }

        //set up the bitmap details
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        //read in the the png file
        final Bitmap bitmap = BitmapFactory.decodeResource(
            context.getResources(), resourceId, options);

        //bind the texture in openGL
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

        //set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        //load in the texture
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        //generate mipmap
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);

        //recycle the bitmap since the data has been loaded into OpenGL
        bitmap.recycle();

        return textureHandle[0];
    }
}
