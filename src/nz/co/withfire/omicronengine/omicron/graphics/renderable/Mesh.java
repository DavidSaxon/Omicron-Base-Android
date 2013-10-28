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
import android.util.Log;
import nz.co.withfire.omicronengine.omicron.graphics.shader.Shader;
import nz.co.withfire.omicronengine.omicron.resources.ShaderLoader;
import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;

public class Mesh extends Renderable {

	//VARIABLES
	
	//the number of dimensions of vertices
	private static final int VERTEX_DIM = 3;
	//the number of dimensions of normals
	private static final int NORMAL_DIM = 3;
	
	//the stride of a vertex
	private static final int VERTEX_STRIDE =
		VERTEX_DIM * ValuesUtil.FLOAT_SIZE;
	//the stride of a normal
	private static final int NORMAL_STRIDE =
		NORMAL_DIM * ValuesUtil.FLOAT_SIZE;
	
	//the opengl program //TODO: in the shader
	//private int program;
	
	//TESTING
	//the shaderS
	private Shader shader;
	
	//TODO: remove
    private final String vertexShaderCode =
        // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 u_MVPMatrix;" +
        "attribute vec4 a_Position;" +
        "void main() {" +
        // the matrix must be included as a modifier of gl_Position
        "  gl_Position = u_MVPMatrix * a_Position;" +
        "}";

    private final String fragmentShaderCode =
        "precision mediump float;" +
        "uniform vec4 v_Colour;" +
        "void main() {" +
        "  gl_FragColor = v_Colour;" +
        "}";

	//TODO: in material in shader class
    //the vertex shader of the program
//	protected int vertexShader;
//    //the fragment shader of the program
//	protected int fragmentShader;
	
	//the number of vertices the mesh has
	private int vertexCount;
	
	//the vertices
	protected float vertices[];
	//the normals of the shape
	protected float normals[] = new float[0];
	
	//the vertex buffer
	protected FloatBuffer vertexBuffer;
	//the texture buffer
	protected FloatBuffer texBuffer;
	//the normal buffer
	protected FloatBuffer normalBuffer;
	
	//the colour REMOVE ME
	Vector4 colour = new Vector4(1.0f, 0.0f, 0.0f, 1.0f);
	
	//CONSTRUCTORS
	/**Creates a new mesh
	@param type the type of renderable this is
	@param layer the layer of this
	@param vertices the vertex array
	@param normals the normals array*/
	public Mesh(Type type, int layer, float vertices[], float normals[]) {
		
		//super call
		super(type, layer);
		
		//initialise variables
		this.vertices = vertices;
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
        int program = shader.getProgram();
        GLES20.glUseProgram(program);
        
        //get a handle the vertex positions and enable them
        int positionHandle = GLES20.glGetAttribLocation(program, "a_Position");
        GLES20.glEnableVertexAttribArray(positionHandle);
        //prepare the triangle coordinate data
    	GLES20.glVertexAttribPointer(positionHandle, VERTEX_DIM,
             GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer);
    	
    	//get a handle to the colour
        int colourHandle = GLES20.glGetUniformLocation(program, "v_Colour");
        //set the colour for drawing
        GLES20.glUniform4fv(colourHandle, 1, colour.toArray(), 0);
        
        //get handle to the model view projection matrix
        int mvpMatrixHandle =
    		GLES20.glGetUniformLocation(program, "u_MVPMatrix");
        //pass in the model view projection matrix
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
        
        //draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
	}
	
	//PRIVATE METHODS
	/**Performs initialisation of the mesh data*/
	private void init() {
		
		//calculate the number of vertices
		vertexCount = this.vertices.length / VERTEX_DIM;
		
		//------------------------------------------------
		
		//TODO: remove
		//compile the shaders
		int vertexShader = ShaderLoader.compileShader(
			GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader = ShaderLoader.compileShader(
			GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
		
		//TODO: in the shader
		//create the openGL program
        int program = GLES20.glCreateProgram();
        //attach the vertex shader to the program
        GLES20.glAttachShader(program, vertexShader);
        //attach the fragment shader to the program
        GLES20.glAttachShader(program, fragmentShader);
        
        //create openGL program executables
        GLES20.glLinkProgram(program);
        
        //create the shader
        shader = new Shader(vertexShader, fragmentShader, program);
        
        //------------------------------------------------
        
        //build the vertex buffer
        buildVertexBuffer();
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
