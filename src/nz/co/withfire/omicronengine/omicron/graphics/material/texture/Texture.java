/*****************************************************\
| An OpenGL texture which can be bound to a material. |
|                                                      |
| @author David Saxon                                  |
\*****************************************************/

package nz.co.withfire.omicronengine.omicron.graphics.material.texture;

public class Texture {

    //VARIABLES
    //the OpenGL id of the texture
    private final int id;

    //CONSTRUCTOR
    /**Creates a new texture
    @param id the OpenGL id of the texture*/
    public Texture(int id) {

        this.id = id;
    }

    //PUBLIC METHODS
    /**@return the OpenGL id of the texture*/
    public int getId() {

        return id;
    }
}
