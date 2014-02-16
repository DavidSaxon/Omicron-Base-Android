package nz.co.withfire.omicron_engine.omicron.graphics.renderable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nz.co.withfire.omicron_engine.omicron.graphics.lighting.AmbientLight;
import nz.co.withfire.omicron_engine.omicron.graphics.lighting.DirectionalLight;
import nz.co.withfire.omicron_engine.omicron.graphics.lighting.PointLight;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Renderable.CustomShaderInputMode;
import nz.co.withfire.omicron_engine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicron_engine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicron_engine.omicron.utilities.vector.Vector3;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Batch {

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
    
    //the vertex buffer
    protected FloatBuffer vertexBuffer;
    //the uv buffer
    protected FloatBuffer uvBuffer;
    //the normal buffer
    protected FloatBuffer normalBuffer;
    
    //Matrix
    //the model view matrix
    protected float[] mvMatrix = new float[16];
    //the model view projection matrix
    protected float[] mvpMatrix = new float[16];
    //the model matrix
    protected float[] modelMatrix = new float[16];
    
    private List<Renderable> renderables =
        new ArrayList<Renderable>();

    //PUBLIC METHODS
    public void add(Renderable r) {
        
        renderables.add(r);
    }
    
    public void remove(Renderable r) {
        
        renderables.remove(r);
    }
    
    public void clear() {
        
        renderables.clear();
    }
    
    public void render(float viewMatrix[], float projectionMatrix[]) {
        
        if (renderables.size() < 1) {
            
            return;
        }
        
        //update if they are animations
        for (Renderable r : renderables) {
            
            if (r instanceof Animation) {
                
                ((Animation) r).update(viewMatrix, projectionMatrix);
            }
        }
        
        Renderable base = renderables.get(0);
        
        applyTransformations(viewMatrix, projectionMatrix);
        
        //sort out data
        build();
        
        //set the blending function
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        
        //shader stuff
        //Set the OpenGL program to use
        int program = base.getMaterial().getShader().getProgram();
        GLES20.glUseProgram(program);

        //if there is a custom shader input function
        if (base.customShaderInputFunction != null) {

            //call the shader input code
            base.customShaderInputFunction.shaderInput(program);

            //if it replace draw the arrays now and return
            if (base.customShaderInputMode == CustomShaderInputMode.REPLACE) {

                drawArrays(base);
                return;
            }
        }

        //VERTEX CO-ORDINATES
        //get a handle the vertex positions and enable them
        int positionHandle = GLES20.glGetAttribLocation(program, "a_Position");
        GLES20.glEnableVertexAttribArray(positionHandle);
        //pass in the vertices
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(positionHandle, VERTEX_DIM,
             GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer);

        //COLOUR
        //set the colour for drawing
        GLES20.glUniform4fv(GLES20.glGetUniformLocation(program, "u_Colour"),
            1, base.getMaterial().getColour().toArray(), 0);

        //TEXTURE
        if (base.getMaterial().getTexture() != null) {

            int texId = base.getMaterial().getTexture().getId();
            //set the active texture unit to texture unit 0
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            //bind this texture to this unit
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);

            //pass the texture to the shader
            GLES20.glUniform1i(texId, 0);
        }

        //UV
        //get a handle to the uv co-ordinates and enable them
        int uvHandle =
            GLES20.glGetAttribLocation(program, "a_UVCoord");
        GLES20.glEnableVertexAttribArray(uvHandle);
        //pass in uvs
        uvBuffer.position(0);
        GLES20.glVertexAttribPointer(uvHandle, UV_DIM,
                GLES20.GL_FLOAT, false, UV_STRIDE, uvBuffer);

        //NORMALS
        //get a handle to the normal data and enable it
        int normalHandle = GLES20.glGetAttribLocation(program, "a_Normal");
        GLES20.glEnableVertexAttribArray(normalHandle);
        //pass in the normals
        normalBuffer.position(0);
        GLES20.glVertexAttribPointer(normalHandle, NORMAL_DIM,
            GLES20.GL_FLOAT, false, NORMAL_STRIDE, normalBuffer);

        //LIGHTING
        if (!base.getMaterial().getShadeless()) {

            //Ambient
            //pass in the ambient light value
            GLES20.glUniform4fv(
                GLES20.glGetUniformLocation(program, "u_Ambient"),
                1, AmbientLight.getValue().toArray(), 0);


            //Point
            //get the point lights
            List<PointLight> pointLights = OmicronRenderer.getPointLights();
            //pass in the number of point lights
            GLES20.glUniform1i(
                GLES20.glGetUniformLocation(program, "u_PointCount"),
                pointLights.size());

            //create the colours array
            float pointColours[] = new float[pointLights.size() * 3];
            for (int i = 0; i < pointColours.length; i += 3) {

                Vector3 pCol = pointLights.get(i / 3).getColour();
                float pStr = pointLights.get(i / 3).getStrength();
                pointColours[i]     = pCol.x * pStr;
                pointColours[i + 1] = pCol.y * pStr;
                pointColours[i + 2] = pCol.z * pStr;
            }
            //pass in the colours
            GLES20.glUniform3fv(
                GLES20.glGetUniformLocation(program, "u_PointColour"),
                pointLights.size(), pointColours, 0);

            //create the distances array
            float pointDistances[] = new float[pointLights.size()];
            for (int i = 0; i < pointDistances.length; ++i) {

                pointDistances[i] = pointLights.get(i).getDistance();
            }
            //pass in distances
            GLES20.glUniform1fv(
                GLES20.glGetUniformLocation(program, "u_PointDistances"),
                pointLights.size(), pointDistances, 0);

            //create the positions array
            float pointPositions[] = new float[pointLights.size() * 3];
            for (int i = 0; i < pointPositions.length; i += 3) {

                Vector3 pPos = pointLights.get(i / 3).getPosition();
                pointPositions[i]     = pPos.x;
                pointPositions[i + 1] = pPos.y;
                pointPositions[i + 2] = pPos.z;
            }
            //pass in positions
            GLES20.glUniform3fv(
                GLES20.glGetUniformLocation(program, "u_PointPos"),
                pointLights.size(), pointPositions, 0);
            
            //Directional
            //get the directional lights
            List<DirectionalLight> dirLights = OmicronRenderer.getDirLights();
            //pass in the number of point lights
            GLES20.glUniform1i(
                GLES20.glGetUniformLocation(program, "u_DirCount"),
                dirLights.size());

            //create the colours array
            float dirColours[] = new float[dirLights.size() * 3];
            for (int i = 0; i < dirColours.length; i += 3) {

                Vector3 dCol = dirLights.get(i / 3).getColour();
                float dStr = dirLights.get(i / 3).getStrength();
                dirColours[i]     = dCol.x * dStr;
                dirColours[i + 1] = dCol.y * dStr;
                dirColours[i + 2] = dCol.z * dStr;
            }
            //pass in the colours
            GLES20.glUniform3fv(
                GLES20.glGetUniformLocation(program, "u_DirColour"),
                dirLights.size(), dirColours, 0);

            //create the positons array
            float dirPositions[] = new float[dirLights.size() * 3];
            for (int i = 0; i < dirPositions.length; i += 3) {

                Vector3 dPos = dirLights.get(i / 3).getPosition();
                dirPositions[i]     = dPos.x;
                dirPositions[i + 1] = dPos.y;
                dirPositions[i + 2] = dPos.z;
            }
            //pass in positions
            GLES20.glUniform3fv(
                GLES20.glGetUniformLocation(program, "u_DirPos"),
                dirLights.size(), dirPositions, 0);

        }

        //MATRIX
        //pass in the model matrix
        GLES20.glUniformMatrix4fv(
            GLES20.glGetUniformLocation(program, "u_MMatrix"),
            1, false, modelMatrix, 0);
        //pass in the model view projection matrix
        GLES20.glUniformMatrix4fv(
            GLES20.glGetUniformLocation(program, "u_MVPMatrix"),
            1, false, mvpMatrix, 0);
        
        //DRAW
        drawArrays(base);
    }
    
    //PRIVATE METHODS
    /**The final stage or rendering the shape passing the arrays to OpenGL
    @param base the base renderable*/
    private void drawArrays(Renderable base) {

        //wireframe
        if (base.getMaterial().getWireframe()) {

            GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vertexCount);
        }
        //normal render
        else {

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        }
    }
    
    /**Apply the transformations to the renderables*/
    private void applyTransformations(
        float viewMatrix[], float projectionMatrix[]) {
        
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(mvMatrix, 0);
        
        Matrix.multiplyMM(mvMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvMatrix, 0);
    }
    
    /**Build the batch*/
    private void build() {
        
        Mesh base = (Mesh) renderables.get(0);
        float vertices[] =
            new float[base.vertices.length * renderables.size()];
        float uvCoords[] =
            new float[base.uvCoords.length * renderables.size()];
        float normals[] =
            new float[base.normals.length * renderables.size()];
        
        Collections.sort(renderables);
        
        //copy
        for (int i = 0; i < renderables.size(); ++i) {
            
            //cast to a mesh
            Mesh m = (Mesh) renderables.get(i);
            
            Vector3 trans = m.getTranslation();
            
            for (int j = 0; j < m.vertices.length; ++j) {
                
                float value = m.vertices[j];
                
                if (j % 3 == 0) {
                    
                    value += trans.x;
                }
                else if (j % 3 == 1) {
                    
                    value += trans.y;
                }
                else if (j % 3 == 2) {
                    
                    value += trans.z;
                }
                
                vertices[(i * base.vertices.length) + j] = value;
            }
            for (int j = 0; j < m.uvCoords.length; ++j) {
                
                uvCoords[(i * base.uvCoords.length) + j] = m.uvCoords[j];
            }
            for (int j = 0; j < m.normals.length; ++j) {
                
                normals[(i * base.normals.length) + j] = m.normals[j];
            }
        }

        vertexCount = vertices.length / VERTEX_DIM;
        
        //build
        //initialise the byte buffer for the vertex buffer
        ByteBuffer vb = ByteBuffer.allocateDirect(
            vertices.length * ValuesUtil.FLOAT_SIZE);
        vb.order(ByteOrder.nativeOrder());

        //initialise the vertex buffer and insert the vertices
        vertexBuffer = vb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        
        //Initialise the byte buffer for the texture coords
        ByteBuffer uvb = ByteBuffer.allocateDirect(
            uvCoords.length * ValuesUtil.FLOAT_SIZE);
        uvb.order(ByteOrder.nativeOrder());

        //initialise the texture buffer and insert the co-ordinates
        uvBuffer = uvb.asFloatBuffer();
        uvBuffer.put(uvCoords);
        uvBuffer.position(0);
        
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
