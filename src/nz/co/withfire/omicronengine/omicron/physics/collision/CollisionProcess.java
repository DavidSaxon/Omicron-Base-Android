/*************************************************************\
| Methods used for processing collision checks and performing |
| call backs to entities.                                     |
|                                                             |
| @author David Saxon                                         |
\*************************************************************/

package nz.co.withfire.omicronengine.omicron.physics.collision;

import nz.co.withfire.omicronengine.omicron.physics.bounding.Bounding;
import nz.co.withfire.omicronengine.omicron.physics.types.Collidable;

public class CollisionProcess {

    //PUBLIC METHODS
    /**Processed collisions between the same group and sends callbacks to
    the collidables
    @param group the group to process*/
    public static void betweenGroup(String group) {
        
        int size = CollisionGroups.getGroup(group).size();
        
        for (int i = 0; i < size; ++i) {
            
            Collidable c1 = CollisionGroups.getGroup(group).get(i);
            
            if (!c1.isActive()) {
                
                continue;
            }
            
            for (int j = i + 1; j < size; ++j) {
                
                Collidable c2 = CollisionGroups.getGroup(group).get(j);
                
                if (!c2.isActive()) {
                    
                    continue;
                }
                
                processBoundings(c1, c2);
            }
        }
    }
    
    /**Process collisions bewtween the same group and sends callbacks to
    the given callback class
    @param group the group to process
    @param callBack the call back class to use*/
    public static void betweenGroup(String group, CollisionCallBack callBack) {
        
        int size = CollisionGroups.getGroup(group).size();
        
        for (int i = 0; i < size; ++i) {
            
            Collidable c1 = CollisionGroups.getGroup(group).get(i);
            
            if (!c1.isActive()) {
                
                continue;
            }
            
            for (int j = i + 1; j < size; ++j) {
                
                Collidable c2 = CollisionGroups.getGroup(group).get(j);
                
                if (!c2.isActive()) {
                    
                    continue;
                }
                
                processBoundings(c1, c2, callBack);
            }
        }
    }
    
    /**Processed collisions between the two given groups and sends
    call backs to the collidables
    @param group1 the name of the first group
    @param group2 the name of the second group*/
    public static void betweenGroups(String group1, String group2) {
        
        //iterate over the entities
        for (Collidable c1 : CollisionGroups.getGroup(group1)) {
            
            if (!c1.isActive()) {
                
                continue;
            }
            
            for (Collidable c2 : CollisionGroups.getGroup(group2)) {
                
                if (!c2.isActive() || c1 == c2) {
                    
                    continue;
                }
                
                processBoundings(c1, c2);
            }
        }
    }
    
    //PRIVATE METHODS
    /**Checks boundings between collidables and sends call back to
    the collidable entities
    @param c1 the first collidable
    @param c2 the second collidable*/
    private static void processBoundings(Collidable c1, Collidable c2) {
        
        //iterate over the boundings
        boolean exitLoop = false;
        for (Bounding b1 : c1.getBoundings()) {
            for (Bounding b2 : c2.getBoundings()) {
                
                if (CollisionDetect.detect(b1, b2)) {
                    
                    //pass in collision data and break
                    c1.collision(c2);
                    c2.collision(c1);
                    exitLoop = true;
                    break;
                }
            }
            
            if (exitLoop) {
                
                break;
            }
        }
    }
    
    /**Checks boundings between collidables and sends call back to
    the call back object
    @param c1 the first collidable
    @param c2 the second collidable
    @param callBack the call back object*/
    private static void processBoundings(Collidable c1, Collidable c2,
        CollisionCallBack callBack) {
        
        //iterate over the boundings
        boolean exitLoop = false;
        for (Bounding b1 : c1.getBoundings()) {
            for (Bounding b2 : c2.getBoundings()) {
                
                if (CollisionDetect.detect(b1, b2)) {
                    
                    //pass in collisiosn and break
                    callBack.collision(c1, c2);
                    exitLoop = true;
                    break;
                }
            }
            
            if (exitLoop) {
                
                break;
            }
        }
    }
}
