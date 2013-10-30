/*********************************************************\
| A 3 dimensional mesh. Current must consist of triangles |
|					    								  |
| @author David Saxon   								  |
\*********************************************************/

package nz.co.withfire.omicronengine.omicron.graphics.renderable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;

public class Mesh extends Renderable {

	//VARIABLES
	//the number of dimensions of vertices
	private static final int VERTEX_DIM = 3;
	//the number of dimensions of uv co-ordinates
	private static final int UV_DIM = 2;
	//the number of dimensions of normals
	private static final int NORMAL_DIM = 3;
	
	//the stride of a vertex
	private static final int VERTEX_STRIDE =
		VERTEX_DIM * ValuesUtil.FLOAT_SIZE;
	//the stide of uv co-ordinates
	private static final int UV_STRIDE =
		UV_DIM * ValuesUtil.FLOAT_SIZE;
	//the stride of a normal
	private static final int NORMAL_STRIDE =
		NORMAL_DIM * ValuesUtil.FLOAT_SIZE;

	//the number of vertices the mesh has
	private int vertexCount;
	
	//the vertices
	protected float vertices[];
	//the uv co-ordinates
	protected float uvCoords[];
	//the normals of the shape
	protected float normals[];
	
	//the vertex buffer
	protected FloatBuffer vertexBuffer;
	//the uv buffer
	protected FloatBuffer uvBuffer;
	//the normal buffer
	protected FloatBuffer normalBuffer;
	
	//CONSTRUCTORS
	/**Creates a new mesh
	@param group the group of renderable this is in
	@param layer the layer of this
	@param vertices the vertex array
	@param uvCoords the uv co-ordinates of the mesh
	@param normals the normals array*/
	public Mesh(Group group, int layer,
		float vertices[], float uvCoords[], float normals[]) {
		
		//super call
		super(group, layer);
		
		//initialise variables
		this.vertices = vertices;
		this.uvCoords = uvCoords;
		this.normals = normals;
		
		//build
		init();
	}
	
	//PUBLIC METHODS
	@Override
	public void render(float viewMatrix[], float projectionMatrix[]) {
		
		//apply transformations
		applyTransformations(viewMatrix, projectionMatrix);
		
		//set the blending function
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        
        //Set the OpenGL program to use
        int program = material.getShader().getProgram();
        GLES20.glUseProgram(program);
        
        //VERTEX CO-ORDINATES
        //get a handle the vertex positions and enable them
        int positionHandle = GLES20.glGetAttribLocation(program, "a_Position");
        GLES20.glEnableVertexAttribArray(positionHandle);
        //prepare the triangle coordinate data
    	GLES20.glVertexAttribPointer(positionHandle, VERTEX_DIM,
             GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer);
    	
    	//COLOUR
    	//get a handle to the colour
        int colourHandle = GLES20.glGetUniformLocation(program, "u_Colour");
        //set the colour for drawing
        GLES20.glUniform4fv(colourHandle, 1, material.getColour().toArray(), 0);
        
        //TEXTURE
        //get a handle to the has texture value
        int hasTextureHandle =
            GLES20.glGetUniformLocation(program, "u_HasTexture");
        
        if (material.getTexture() != null) {
        	
        	GLES20.glUniform1f(hasTextureHandle, 1);
        	
            int texId = material.getTexture().getId();
            //set the active texture unit to texture unit 0
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            //bind this texture to this unit
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
            
            //pass the texture to the shader
            GLES20.glUniform1i(texId, 0);
        }
        else {
        	
        	//pass in that there is no texture
        	GLES20.glUniform1f(hasTextureHandle, 0);
        }
        
        //UV
    	//get a handle to the uv co-ordinates and enable them
    	int uvHandle =
			GLES20.glGetAttribLocation(program, "a_UVCoord");
		GLES20.glEnableVertexAttribArray(uvHandle);
		 
		//pass in texture information
        uvBuffer.position(0);
        GLES20.glVertexAttribPointer(uvHandle, UV_DIM,
        		GLES20.GL_FLOAT, false, UV_STRIDE, uvBuffer);
        
        //MVP MATRIX
        //get handle to the model view projection matrix
        int mvpMatrixHandle =
    		GLES20.glGetUniformLocation(program, "u_MVPMatrix");
        //pass in the model view projection matrix
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
        
        //DRAW
        //wireframe
        if (material.getWireframe()) {
        	
        	GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vertexCount);
        }
        //normal render
        else {
        	
        	GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        }
	}
	
	@Override
	public Renderable deepCopy() {
		
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
		
		Mesh copy = new Mesh(group, layer,
			verticesCopy, uvCopy, normalsCopy);
		
		//copy over the common renderable elements
		copyCommonElements(copy);
		
		return copy;
	}
	
	//PRIVATE METHODS
	/**Performs initialisation of the mesh data*/
	private void init() {
		
		//calculate the number of vertices
		vertexCount = this.vertices.length / VERTEX_DIM;
        
        //build the vertex buffer
        buildVertexBuffer();
        //build the uv buffer
        buildUVBuffer();
        //build the normal buffer
        buildNormalBuffer();
	}
	
	/**Builds the vertex buffer*/
	private void buildVertexBuffer() {
		
        //initialise the byte buffer for the vertex buffer
        ByteBuffer vb = ByteBuffer.allocateDirect(
            vertices.length * ValuesUtil.FLOAT_SIZE);
        vb.order(ByteOrder.nativeOrder());
        
        //initialise the vertex buffer and insert the vertices
        vertexBuffer = vb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
	}
	
	/**Builds the UV buffer*/
	private void buildUVBuffer() {
		
        //Initialise the byte buffer for the texture coords
        ByteBuffer uvb = ByteBuffer.allocateDirect(
            uvCoords.length * ValuesUtil.FLOAT_SIZE);
        uvb.order(ByteOrder.nativeOrder());
        
        //initialise the texture buffer and insert the co-ordinates
        uvBuffer = uvb.asFloatBuffer();
        uvBuffer.put(uvCoords);
        uvBuffer.position(0);
	}
	
	/**Builds the normal buffer*/
	private void buildNormalBuffer() {
		
        //initialise the byte buffer for the normal buffer
        ByteBuffer nb = ByteBuffer.allocateDirect(
            normals.length * ValuesUtil.FLOAT_SIZE);
        nb.order(ByteOrder.nativeOrder());
        
        //initialise the normal buffer and insert the normal
        normalBuffer = nb.asFloatBuffer();
        normalBuffer.put(normals);
        normalBuffer.position(0);
	}
}
