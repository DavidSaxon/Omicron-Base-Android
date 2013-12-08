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
    /**Process collisions collision between the two given groups
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
                
                //iterate over the boundings
                boolean exitLoop = false;
                for (Bounding b1 : c1.getBoundings()) {
                    for (Bounding b2 : c2.getBoundings()) {
                        
                        if (CollisionDetect.detect(b1, b2) > 0.0f) {
                            
                            //pass in collision data and break
                            b1.getParentEntity().collision(
                                b2.getParentEntity());
                            b2.getParentEntity().collision(
                                b1.getParentEntity());
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
    }
}
