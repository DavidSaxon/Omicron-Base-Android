/***********************************************************************\
| Is useful for grouping collidable to save on collision checking cost. |
|                                                                       |
| @author David Saxon                                                   |
\***********************************************************************/

package nz.co.withfire.omicronengine.omicron.physics.collision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import nz.co.withfire.omicronengine.omicron.physics.types.Collidable;
import nz.co.withfire.omicronengine.override.Values;

public class CollisionGroups {

    //VARIABLES
    //a map from names to collision groups
    private static Map<String, List<Collidable>> groups =
        new HashMap<String, List<Collidable>>();
    
    //PUBLIC METHODS
    /**Updates the collision groups*/
    public static void update() {
        
        for (List<Collidable> l : groups.values()) {
            
            //the remove list
            List<Collidable> removeList = new ArrayList<Collidable>();
            //iterate over and check for removed entities
            for (Collidable c : l) {
                
                //set to remove
                if (c.shouldRemove()) {
                    
                    removeList.add(c);
                }
            }
            
            //remove
            for (Collidable c : removeList) {
                
                l.remove(c);
            }
        }
    }
    
    /**Adds a new group
    @param name the name of the group*/
    public static void newGroup(String name) {
        
        //check that the map doesn't already contain the group
        if (!groups.containsKey(name)) {
            
            groups.put(name, new ArrayList<Collidable>());
        }
    }
    
    /**Adds a collidable to the given group
    @param name the name of the group to add to
    @param collidable the collidable to add*/
    public static void add(String name, Collidable collidable) {
        
        //make sure the map entry exists
        if (!groups.containsKey(name)) {
            
            Log.v(Values.TAG, "ERROR: " + name + " is not an existing group");
            throw new RuntimeException(
                name + "is not an existing group");
        }
        
        groups.get(name).add(collidable);
    }
    
    /**Removes the bounding from the group
    @param the name of group to remove from
    @param collidable the collidable to remove*/
    public static void remove(String name, Collidable collidable) {
        
        //make sure the map entry exists
        if (!groups.containsKey(name)) {
            
            Log.v(Values.TAG, "ERROR: " + name + " is not an existing group");
            throw new RuntimeException(
                name + "is not an existing group");
        }
        
        groups.get(name).remove(collidable);
    }
   
    
    /**Clears all the collision groups*/
    public static void clear() {
        
        groups = new HashMap<String, List<Collidable>>();
    }
    
    /**@return if the given group name exists
    @param name the name of the group to check*/
    public static boolean exists(String name) {
        
        return groups.containsKey(name);
    }
    
    /**@return the group with the given name
    @param name the name of the group to return*/
    public static List<Collidable> getGroup(String name) {
        
        return groups.get(name);
    }
}
