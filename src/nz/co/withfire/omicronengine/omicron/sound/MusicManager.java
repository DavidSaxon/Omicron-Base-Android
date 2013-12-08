/*************************\
| Manages music playback. |
|                         |
| @author David Saxon     |
\*************************/

package nz.co.withfire.omicronengine.omicron.sound;

import nz.co.withfire.omicronengine.override.DebugValues;
import android.content.Context;
import android.media.MediaPlayer;

public class MusicManager {

    //VARIABLES
    //the android context
    private static Context context;
    
    //the media player
    private static MediaPlayer mediaPlayer;
    
    //PUBLIC METHODS
    /**Initalises the music manager
    @param context the android context*/
    public static void init(final Context context) {
        
        MusicManager.context = context;
    }
    
    /**Starts play back of a music file
    @param file the file to play
    @param */
    public static void play(int file, float volume) {
        
        if (!DebugValues.DISABLE_MUSIC) {
            
            mediaPlayer = MediaPlayer.create(context, file);
            mediaPlayer.setVolume(volume, volume);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    /**Stops the media player*/
    public static void stop() {
        
        if (mediaPlayer != null) {
            
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
