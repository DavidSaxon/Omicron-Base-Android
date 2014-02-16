package nz.co.withfire.omicron_engine.omicron.resources.types;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import nz.co.withfire.omicron_engine.omicron.physics.bounding.Bounding;
import nz.co.withfire.omicron_engine.omicron.resources.loaders.BoundingLoader;
import nz.co.withfire.omicron_engine.override.ResourceGroups.ResourceGroup;

public class BoundingResource {

    //VARIABLES
    //the resource id of the bounding
    private int resourceId;
    //the group the bounding is in
    private ResourceGroup group;
    //the list of boundings
    private List<Bounding> boundings = new ArrayList<Bounding>();
    //is true once the bounding has been loaded
    private boolean loaded = false;
    
    //CONSTRUCTOR
    /**Creates a new bounding resource
    @param resourceId the resource id of the bounding
    @param group the group the bounding is in*/
    public BoundingResource(int resourceId, ResourceGroup group) {
        
        this.resourceId = resourceId;
        this.group = group;
    }
        
    //PUBLIC METHODS
    /**Loads the bounding into memory
    @param context the android context*/
    public void load(final Context context) {
        
        //check if it has already been loaded
        if (loaded) {
            
            return;
        }
        
        //load the bounding
        boundings = BoundingLoader.loadBounding(context, resourceId);
        
        loaded = true;
    }
    
    /**Frees the bounding from memory*/
    public void destroy() {
        
        //do nothing if the bounding is not loaded
        if (!loaded) {
            
            return;
        }
        
        //free
        boundings.clear();
        
        //successfully destroyed
        loaded = false;
    }
    
    /**@return the list of loaded boundings*/
    public List<Bounding> getBounding() {
        
        //check that the bounding has been loaded
        if(!loaded) {
            
            //report error
            throw new RuntimeException(
                "Attempted to use an un-loaded bounding");
        }
        
        return boundings;
    }
    
    /**@return the group the bounding is within*/
    public ResourceGroup getGroup() {
        
        return group;
    }
    
    /**@return whether the bounding has been loaded*/
    public boolean isLoaded() {
        
        return loaded;
    }
}
