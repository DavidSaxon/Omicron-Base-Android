/*********************\
| A point light.       |
|                      |
| @author David Saxon |
\*********************/

package nz.co.withfire.omicron_engine.omicron.graphics.lighting;

import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;

public class PointLight extends Light {

    //VARIABLES
    //the scale of distance the light can reach
    private float distance;

    //CONSTRUCTOR
    /**Creates a new point light
    @param colour the colour of the light
    @param strength the strength of the light
    @param distance the scale of distance the light can reach
    @param position the position of the light*/
    public PointLight(Vector3 colour, float strength,
        float distance, Vector3 position) {

        //super call
        super(colour, strength, position);

        //initialise variables
        this.distance = distance;
    }

    //PUBLIC METHODS
    /**@return the scale of the distance the light can reach*/
    public float getDistance() {

        return distance;
    }

    /**@param distance the new distance of the light*/
    public void setDistance(float distance) {

        this.distance = distance;
    }
}
