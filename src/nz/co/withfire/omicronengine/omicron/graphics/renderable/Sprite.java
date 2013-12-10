/************************************************\
| A 2 dimensional sprite in 3 dimensional space. |
|                                                |
| @author David Saxon                            |
\************************************************/

package nz.co.withfire.omicronengine.omicron.graphics.renderable;

public class Sprite extends Mesh {

    //CONSTRUCTOR
    /**Creates a new sprite
    @param group of renderables this is in
    @param layer the layer of this
    @param vertices the vertex array
    @param uvCoords the uv co-ordinates array
    @param normals the normals array*/
    public Sprite(Group group, int layer,
        float vertices[], float uvCoords[], float normals[]) {
        
        super(group, layer, vertices, uvCoords, normals);
    }

    //PUBLIC METHODS
    @Override
    public Renderable clone() {
    
        //copy the lists
        float verticesCopy[] = new float[vertices.length];
        for (int i = 0; i < vertices.length; ++i) {
    
            verticesCopy[i] = vertices[i];
        }
        float uvCopy[] = new float[uvCoords.length];
        for (int i = 0; i < uvCoords.length; ++i) {
    
            uvCopy[i] = uvCoords[i];
        }
        float normalsCopy[] = new float[normals.length];
        for (int i = 0; i < normals.length; ++i) {
    
            normalsCopy[i] = normals[i];
        }
    
        Sprite copy = new Sprite(group, layer,
            verticesCopy, uvCopy, normalsCopy);
    
        //copy over the common renderable elements
        copyCommonElements(copy);
    
        return copy;
    }
}
