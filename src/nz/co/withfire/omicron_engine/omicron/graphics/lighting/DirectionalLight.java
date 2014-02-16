package nz.co.withfire.omicron_engine.omicron.graphics.lighting;

import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;

public class DirectionalLight extends Light {
    
    //CONSTRUCTOR
    /**Creates a new directional light
    @param colour the colour of the light
    @param strength the strength of the light
    @param position the position of the light*/
    public DirectionalLight(Vector3 colour, float strength,
        Vector3 position) {
        
        //super call
        super(colour, strength, position);
    }
}
