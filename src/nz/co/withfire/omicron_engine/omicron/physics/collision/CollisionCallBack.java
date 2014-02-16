/*****************************************************************\
| A wrapper class class around a function that can be called when |
| two entities collide.                                           |
|                                                                 |
| @author David Saxon                                             |
\*****************************************************************/

package nz.co.withfire.omicron_engine.omicron.physics.collision;

import nz.co.withfire.omicron_engine.omicron.physics.types.Collidable;

public interface CollisionCallBack {

    //PUBLIC METHODS
    /**This method is called back when a collision occurs between two
    collidables
    @param c1 the first collidable
    @param c2 the second collidable*/
    public void collision(Collidable c1, Collidable c2);
}
