/**********************************\
| The base class for all entities. |
|                                   |
| @author David Saxon               |
\**********************************/

package nz.co.withfire.omicronengine.omicron.logic.entity;

import java.util.List;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public abstract class Entity {

    //VARIABLES
    //the position of the entity
    protected Vector3 pos = new Vector3();

    //PUBLIC METHODS
    /**Updates the entity*/
    public void update() {

        //TO OVERRIDE
    }

    /**Cleans up when the entity is deleted*/
    public void cleanUp() {

        //TO OVERRIDE
    }

    /**@return the list of new entities to add*/
    public List<Entity> getAdd() {

        return null;
    }

    /**@return if the entity should be removed*/
    public boolean shouldRemove() {

        return false;
    }

    /**@return the position of the entity*/
    public Vector3 getPos() {

        return pos;
    }
}
