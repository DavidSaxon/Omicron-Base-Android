/*********************\
| Stores a sound.     |
|                     |
| @author David Saxon |
\*********************/

package nz.co.withfire.omicron_engine.omicron.resources.types;

import android.content.Context;
import android.util.Log;
import nz.co.withfire.omicron_engine.omicron.sound.FxManager;
import nz.co.withfire.omicron_engine.override.Values;
import nz.co.withfire.omicron_engine.override.ResourceGroups.ResourceGroup;

public class SoundResource {

    //VARIABLES
    //the resource id of the sound
    private final int resourceId;

    //the resource group of the sound
    private final ResourceGroup group;
    
    //the sound
    private int sound;
    
    //is true once the sound has been loaded
    private boolean loaded = false;
    
    //CONSTRUCTOR
    /**Creates a new sound resource
    @param resourceId the resource id of the sound
    @param group the group of the sound*/
    public SoundResource(int resourceId, ResourceGroup group) {

        //initialise variables
        this.resourceId = resourceId;
        this.group = group;
    }
    
    //PUBLIC METHODS
    /**Loads the sound
    @param context the android context*/
    public void load(final Context context) {

        //check if the sound has already been loaded
        if (loaded) {

            return;
        }

        sound = FxManager.load(context, resourceId);

        //successfully loaded
        loaded = true;
    }

    /**Frees the sound from memory*/
    public void destroy() {

        //do nothing if the sound is not loaded
        if (!loaded) {

            return;
        }

        FxManager.unload(resourceId);

        //successfully destroyed
        loaded = false;
    }

    /**@return the loaded sound*/
    public int getSound() {

        //check that the texture has been loaded
        if (!loaded) {

            //report error
            Log.v(Values.TAG, "Attempted to use an un-loaded sound");
            throw new RuntimeException(
                "Attempted to use an un-loaded sound");
        }

        return sound;
    }

    /**@return the group the sound is within*/
    public ResourceGroup getGroup() {

        return group;
    }

    /**@return whether sound has been loaded*/
    public boolean isLoaded() {

        return loaded;
    }
}
