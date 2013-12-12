/**************************************\
| Handles play back of sounds effects. |
|                                      |
| @author David Saxon                  |
\**************************************/

package nz.co.withfire.omicronengine.omicron.sound;

import java.util.HashMap;

import nz.co.withfire.omicronengine.omicron.utilities.TransformationsUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.override.DebugValues;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class FxManager {

    //VARIABLES
    //the sound pool
    private static SoundPool soundPool;
    //the sounds pool map
    private static HashMap<String, Integer> soundPoolMap;
    
    //PUBLIC METHODS
    /**Initialises the fx manager
    @param context the android context*/
    public static void init(Context context) {
        
        //create the sound pool
        soundPool = new SoundPool(32, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<String, Integer>();
    }
    
    /**Loads the sound
    @param context the android context
    @return the sound id*/
    public static int load(Context context, int resourceId) {
        
        return soundPool.load(context, resourceId, 1);
    }
    
    /**Plays the sound with the requested id
    @param sound the id of the sound to play
    @param volume the volume to play the sound at*/
    public static void play(int sound, float volume) {
        
        if (!DebugValues.DISABLE_SOUNDS) {
        
            soundPool.play(sound, volume, volume, 1, 0, 1f);
        }
    }
    
    /**Plays the sound with the request id in 3d
    @param sound the id of the sound to play
    @param pos the position of the sound
    @param volume the volume to play the sound at*/
    public static void play3d(int sound, Vector3 pos, float volume) {
        
        float pan = pos.x / (TransformationsUtil.getOpenGLDim().x * 1.3f);
        if (pan > 1.0f) {
            
            pan = 1.0f;
        }
        else if (pan < -1.0f) {
            
            pan = -1.0f;
        }
        
        float leftVolume = volume;
        float rightVolume = volume;
        
        if (pan < 0.0f) {
            
            pan = Math.abs(pan);
            leftVolume = volume - (volume * pan);
        }
        else {
            
            rightVolume = volume - (volume * pan);
        }
        
        if (!DebugValues.DISABLE_SOUNDS) {
        
            soundPool.play(sound, leftVolume, rightVolume, 1, 0, 1f);
        }
    }
    
    /**Clears the sound pool*/
    public static void clear() {
        
        soundPool.release();
        soundPoolMap.clear();
    }
    
}
