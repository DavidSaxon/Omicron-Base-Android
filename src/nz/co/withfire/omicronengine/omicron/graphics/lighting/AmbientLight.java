/************************************************************************\
| Ambient light values. Note that this class is entirely static as there |
| can only be one ambient light.                                         |
|                                                                         |
| @author David Saxon                                                     |
\************************************************************************/


package nz.co.withfire.omicronengine.omicron.graphics.lighting;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;

public class AmbientLight {

    //VARIABLES
    //the colour of the light
    private static Vector3 colour = new Vector3(1.0f, 1.0f, 1.0f);
    //the strength of the ambient light
    private static float strength = 1.0f;

    //PUBLIC METHODS
    //GETTERS
    /**@return the actual light value (colour * strength)*/
    public static Vector4 getValue() {

        return new Vector4(colour.x * strength,
            colour.y * strength, colour.z * strength, 0.0f);
    }

    /**@return the colour of the ambient light*/
    public static Vector3 getColour() {

        return colour;
    }

    /**@return the strength of the light*/
    public static float getStrength() {

        return strength;
    }

    //SETTERS
    /**Sets the values of the ambient light
    @param colour the new colour
    @param strength the strength*/
    public static void set(Vector3 colour, float strength) {

        AmbientLight.colour = colour;
        AmbientLight.strength = strength;
    }

    /**@param colour the new colour of the light*/
    public static void setColour(Vector3 colour) {

        AmbientLight.colour = colour;
    }

    /**@param strength the new strength of the light*/
    public static void setStrength(float strength) {

        AmbientLight.strength = strength;
    }
}
