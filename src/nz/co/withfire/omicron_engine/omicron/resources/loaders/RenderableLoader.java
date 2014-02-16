/**********************************\
| Methods for loading Renderables. |
|                                  |
| @author David Saxon              |
\**********************************/

package nz.co.withfire.omicron_engine.omicron.resources.loaders;

import java.util.ArrayList;
import java.util.Scanner;

import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Animation;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Sprite;
import nz.co.withfire.omicron_engine.omicron.resources.types.RenderableResource.FaceDirection;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;
import android.content.Context;

public class RenderableLoader {

    //PUBLIC METHODS
    /**Loads an OBJ file file as a mesh
    NOTE: the OBJ file must consist of triangles
    @param context the android context
    @param resourceId the id of the resource to load
    @param type the type of renderable this is to be
    @param the layer of the mesh
    @return the loaded mesh*/
    public static Mesh loadOBJ(final Context context, int resourceId,
        Renderable.Group type, int layer) {

         //open the file as a string
        String file = StringLoader.loadString(context, resourceId);

        //load the file into a scanner
        Scanner scanner = new Scanner(file);

        //a array list that contains the vertex coords
        ArrayList<Vector3> vertices = new ArrayList<Vector3>();
        //a list that contains the uv coords
        ArrayList<Vector2> uvCoords = new ArrayList<Vector2>();
        //a list that contains the vertex normals
        ArrayList<Vector3> normals = new ArrayList<Vector3>();
        //the array of the vertexs in order that make up the model
        ArrayList<Vector3> faceVertices = new ArrayList<Vector3>();
        //the array of texture coords in the model
        ArrayList<Vector2> faceUV = new ArrayList<Vector2>();
        //the array of normals in order
        ArrayList<Vector3> normalsFinal = new ArrayList<Vector3>();

        //read through the entire file
        while(scanner.hasNext()) {

            //get the next value
            String next  = scanner.next();

            //read the vertex coords
            if (next.equals("v")) {

                //add the vertex
                vertices.add(new Vector3(Float.parseFloat(scanner.next()),
                    Float.parseFloat(scanner.next()),
                    Float.parseFloat(scanner.next())));
            }
            //read the texture coords
            else if (next.equals("vt")) {

                //add the coord
                uvCoords.add(new Vector2(Float.parseFloat(scanner.next()),
                    -Float.parseFloat(scanner.next())));
            }
            //read the vertex normals
            else if (next.equals("vn")) {

                //add the normal
                normals.add(new Vector3(Float.parseFloat(scanner.next()),
                        Float.parseFloat(scanner.next()),
                        Float.parseFloat(scanner.next())));
            }
            //read face vertices
            else if (next.equals("f")) {

                for (int i = 0; i < 3; ++i) {

                    //get the index from the file and split
                    String indices[] = scanner.next().split("/");

                    //parse as ints
                    int vertexIndex = Integer.parseInt(indices[0]) - 1;
                    int textureIndex = Integer.parseInt(indices[1]) - 1;
                    int normalIndex = -1;
                    if (indices.length > 2) {

                        normalIndex = Integer.parseInt(indices[2]) - 1;
                    }

                    faceVertices.add(vertices.get(vertexIndex));
                    faceUV.add(uvCoords.get(textureIndex));
                    if (normalIndex > -1) {

                        normalsFinal.add(normals.get(normalIndex));
                    }
                }
            }
            else {

                //throw away the line
                scanner.nextLine();
            }
        }
        
        scanner.close();

        //get the triangle coords
        float coords[] = new float[faceVertices.size() * 3];
        float uv[] = new float[faceUV.size() * 2];
        float nrm[] = new float[normalsFinal.size() * 3];
        for (int i = 0; i < faceVertices.size(); ++i) {

            //get vertex coords
            coords[(i * 3)]     = faceVertices.get(i).x;
            coords[(i * 3) + 1] = faceVertices.get(i).y;
            coords[(i * 3) + 2] = faceVertices.get(i).z;

            //get texture coords
            uv[(i * 2)]     = faceUV.get(i).x;
            uv[(i * 2) + 1] = faceUV.get(i).y;

            //get normal coords
            if (normalsFinal.size() > i) {

                nrm[(i * 3)]     = normalsFinal.get(i).x;
                nrm[(i * 3) + 1] = normalsFinal.get(i).y;
                nrm[(i * 3) + 2] = normalsFinal.get(i).z;
            }
        }

        return new Mesh(type, layer, coords, uv, nrm);
    }
    
    /**Loads a new sprite from the given values
    @param type the type of renderable this is
    @param layer the layer of this renderable
    @param dim the dimensions of the sprite
    @param divisions the vertex divisions of the sprite
    @param faceDirection the direction of the sprite
    @return the loaded sprite*/
	public static Sprite loadSprite(Renderable.Group type,
        int layer, Vector2 dim, int divisions, FaceDirection faceDirection) {
        
        //vertices
        float lowX = -(dim.x / 2.0f);
        float highX = -lowX;
        float lowY = -(dim.y / 2.0f);
        float highY = -lowY;
        float z = 0.0f;
        float coords[] = new float[18];
        switch (faceDirection) {
            
            case FRONT: {
                
                coords[0] =  highX; coords[1] =  lowY;  coords[2] = z;
                coords[3] =  lowX;  coords[4] =  lowY;  coords[5] = z;
                coords[6] =  highX; coords[7] =  highY; coords[8] = z;
                coords[9] =  lowX;  coords[10] = lowY;  coords[11] = z;
                coords[12] = lowX;  coords[13] = highY; coords[14] = z;
                coords[15] = highX; coords[16] = highY; coords[17] = z;
                break;
            }
            case TOP: {
                
                coords[0] =  highX; coords[1] =  z; coords[2] =  lowY;
                coords[3] =  lowX;  coords[4] =  z; coords[5] =  lowY;
                coords[6] =  highX; coords[7] =  z; coords[8] =  highY;
                coords[9] =  lowX;  coords[10] = z; coords[11] = lowY;
                coords[12] = lowX;  coords[13] = z; coords[14] = highY;
                coords[15] = highX; coords[16] = z; coords[17] = highY;
                break;
            }
			case BACK: {
				
				//TODO:
				break;
			}
			case BOTTOM: {
				
				//TODO:
				break;
			}
			case LEFT: {
				
				//TODO:
				break;
			}
			case RIGHT: {
				
				//TODO:
				break;
			}
        }
        
        //uv
        float uv[] = {
                
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f
        };
        
        //normals
        float normals[] = new float[18];
        switch (faceDirection) {
            
            case FRONT: {
                
                normals[0] =  0.0f;  normals[1] =  0.0f;   normals[2] =  -1.0f;
                normals[3] =  0.0f;  normals[4] =  0.0f;   normals[5] =  -1.0f;
                normals[6] =  0.0f;  normals[7] =  0.0f;   normals[8] =  -1.0f;
                normals[9] =  0.0f;  normals[10] = 0.0f;   normals[11] = -1.0f;
                normals[12] = 0.0f;  normals[13] = 0.0f;   normals[14] = -1.0f;
                normals[15] = 0.0f;  normals[16] = 0.0f;   normals[17] = -1.0f;
                break;
            }
            case TOP: {
                
                normals[0] =  0.0f;  normals[1] =  1.0f;   normals[2] =  0.0f;
                normals[3] =  0.0f;  normals[4] =  1.0f;   normals[5] =  0.0f;
                normals[6] =  0.0f;  normals[7] =  1.0f;   normals[8] =  0.0f;
                normals[9] =  0.0f;  normals[10] = 1.0f;   normals[11] = 0.0f;
                normals[12] = 0.0f;  normals[13] = 1.0f;   normals[14] = 0.0f;
                normals[15] = 0.0f;  normals[16] = 1.0f;   normals[17] = 0.0f;
                break;
            }
			case BACK: {
				
				//TODO:
				break;
			}
			case BOTTOM: {
				
				//TODO:
				break;
			}
			case LEFT: {
				
				//TODO:
				break;
			}
			case RIGHT: {
				
				//TODO:
				break;
			}
        }
        
        return new Sprite(type, layer, coords, uv, normals);
    }
    
    /**Loads a new animation from the given values
    @param context the android context
    @param type the type of renderable this is
    @param layer the layer of this renderable
    @param dim the dimensions of the sprite
    @param divisions the vertex divisions of the sprite
    @param faceDirection the direction of the face
    @return the loaded sprite*/
    public static Animation loadAnimation(final Context context,
        int resourceId, Renderable.Group type,
        int layer, Vector2 dim, int divisions, FaceDirection faceDirection) {
        
        int length = 0;
        Vector2 uvDim = new Vector2();
        int rows = 0;
        float framerate = 30.0f;
        int playTimes = -1;
        
        //read the file
        String file = StringLoader.loadString(context, resourceId);
        Scanner scanner = new Scanner(file);
       
        while (scanner.hasNext()) {
           
            String next = scanner.next();
           
            if(next.equals("#LENGTH")) {
               
                length = Integer.parseInt(scanner.next());
            }
            else if(next.equals("#WIDTH")) {
               
                uvDim.x = Float.parseFloat(scanner.next());
            }
            else if(next.equals("#HEIGHT")) {
               
                uvDim.y = Float.parseFloat(scanner.next());
            }
            else if(next.equals("#ROWS")) {
               
                rows = Integer.parseInt(scanner.next());
            }
            else if(next.equals("#FPS")) {
               
                framerate = Float.parseFloat(scanner.next());
            }
            else if(next.equals("#STOP")) {
                   
                playTimes = Integer.parseInt(scanner.next());
            }
        }
        scanner.close();
        
        //vertices
        float lowX = -(dim.x / 2.0f);
        float highX = -lowX;
        float lowY = -(dim.y / 2.0f);
        float highY = -lowY;
        float z = 0.0f;
        float coords[] = new float[18];
        switch (faceDirection) {
            
            case FRONT: {
                
                coords[0] =  highX; coords[1] =  lowY;  coords[2] = z;
                coords[3] =  lowX;  coords[4] =  lowY;  coords[5] = z;
                coords[6] =  highX; coords[7] =  highY; coords[8] = z;
                coords[9] =  lowX;  coords[10] = lowY;  coords[11] = z;
                coords[12] = lowX;  coords[13] = highY; coords[14] = z;
                coords[15] = highX; coords[16] = highY; coords[17] = z;
                break;
            }
            case TOP: {
                
                coords[0] =  highX; coords[1] =  z; coords[2] =  lowY;
                coords[3] =  lowX;  coords[4] =  z; coords[5] =  lowY;
                coords[6] =  highX; coords[7] =  z; coords[8] =  highY;
                coords[9] =  lowX;  coords[10] = z; coords[11] = lowY;
                coords[12] = lowX;  coords[13] = z; coords[14] = highY;
                coords[15] = highX; coords[16] = z; coords[17] = highY;
                break;
            }
			case BACK: {
				
				//TODO:
				break;
			}
			case BOTTOM: {
				
				//TODO:
				break;
			}
			case LEFT: {
				
				//TODO:
				break;
			}
			case RIGHT: {
				
				//TODO:
				break;
			}
        }
        
        //uv
        float uv[] = {
                
            0.0f,   0.25f,
            0.125f, 0.25f,
            0.0f,   0.125f,
            0.125f, 0.25f,
            0.125f, 0.125f,
            0.0f,   0.125f
        };
        
        //normals
        float normals[] = new float[18];
        switch (faceDirection) {
            
            case FRONT: {
                
                normals[0] =  0.0f;  normals[1] =  0.0f;   normals[2] =  -1.0f;
                normals[3] =  0.0f;  normals[4] =  0.0f;   normals[5] =  -1.0f;
                normals[6] =  0.0f;  normals[7] =  0.0f;   normals[8] =  -1.0f;
                normals[9] =  0.0f;  normals[10] = 0.0f;   normals[11] = -1.0f;
                normals[12] = 0.0f;  normals[13] = 0.0f;   normals[14] = -1.0f;
                normals[15] = 0.0f;  normals[16] = 0.0f;   normals[17] = -1.0f;
                break;
            }
            case TOP: {
                
                normals[0] =  0.0f;  normals[1] =  1.0f;   normals[2] =  0.0f;
                normals[3] =  0.0f;  normals[4] =  1.0f;   normals[5] =  0.0f;
                normals[6] =  0.0f;  normals[7] =  1.0f;   normals[8] =  0.0f;
                normals[9] =  0.0f;  normals[10] = 1.0f;   normals[11] = 0.0f;
                normals[12] = 0.0f;  normals[13] = 1.0f;   normals[14] = 0.0f;
                normals[15] = 0.0f;  normals[16] = 1.0f;   normals[17] = 0.0f;
                break;
            }
			case BACK: {
				
				//TODO:
				break;
			}
			case BOTTOM: {
				
				//TODO:
				break;
			}
			case LEFT: {
				
				//TODO:
				break;
			}
			case RIGHT: {
				
				//TODO:
				break;
			}
        }
        
        return new Animation(type, layer, coords, uv, normals,
            length, uvDim, rows, framerate, playTimes);
    }
}
