/*************************\
| Represents a 4d vector. |
|                         |
| @author David Saxon     |
\*************************/

package nz.co.withfire.omicronengine.omicron.utilities.vector;

import nz.co.withfire.omicronengine.override.Values;
import android.util.Log;

public class Vector4 {

    //VARIABLES
    //static zero vector
    static public final Vector4 zero4d = new Vector4();
    
    //values
    public float x;
    public float y;
    public float z;
    public float w;
    
    //CONSTRUCTORS
    /**Creates a new zero 4d vector*/
    public Vector4() {
        
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 0.0f;
    }
    
    /**Creates a new 4d vector from the given values
    @param x the x value of the vector
    @param y the y value of the vector
    @param z the z value of the vector
    @param w the w value of the vector*/
    public Vector4(float x, float y, float z, float w) {
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    /**Creates a new 4d vector by copying the values from the other vector
    @param other the other vector to copy from*/
    public Vector4(final Vector4 other) {
        
        x = other.x;
        y = other.y;
        z = other.z;
        w = other.w;
    }
    
    /**Create a new 4d vector by copying the x and y values from
    the 2d vector and sets the z and w values to 0
    @param other the 2d vector to copy from*/
    public Vector4(final Vector2 other) {
    	
    	x = other.x;
    	y = other.y;
    	z = 0;
    	w = 0;
    }
    
    /**Create a new 4d vector by copying the x, y, and z values from
    the 3d vector and sets the w value to 0
    @param other the 3d vector to copy from*/
    public Vector4(final Vector3 other) {
    	
    	x = other.x;
    	y = other.y;
    	z = other.z;
    	w = 0;
    }
    
    /**Create a new 4d vector from the 2d vector and the two scalars
    @param v2 the 2d vector for the x and y values
    @param z the scalar for the z value
    @param w the scalar for the w value*/
    public Vector4(final Vector2 v2, float z, float w) {
        
        x = v2.x;
        y = v2.y;
        this.z = z;
        this.w = w;
    }
    
    /**Create a new 4d vector from a scalar, a 2d vector, and another scalar
    @param x the scalar for the x value
    @param v2 the 2d vector for the y and z values
    @param w the scalar for the w value*/
    public Vector4(float x, final Vector2 v2, float w) {
        
        this.x = x;
        y = v2.x;
        z = v2.y;
        this.w = w;
    }
    
    /**Create a new 4d vector from two scalars and a 2d vector
    @param x the scalar for the x value
    @param y the scalar for the y value
    @param v2 the 2d vector for the z and w values*/
    public Vector4(float x, float y, final Vector2 v2) {
        
        this.x = x;
        this.y = y;
        z = v2.x;
        w = v2.y;
    }
    
    /**Create a new 4d vector from two 2d vector
    @param firstV2 the 2d vector for the x and y values
    @param secondV2 the 2d vector for the z and w values*/
    public Vector4(final Vector2 firstV2, final Vector2 secondV2) {
        
        x = firstV2.x;
        y = firstV2.y;
        z = secondV2.x;
        w = secondV2.y;
    }
    
    /**Create a new 4d vector from the 3d vector and the scalar
    @param v3 the 3d vector for the x, y, and z values
    @param w the scalar for the w value*/
    public Vector4(final Vector3 v3, float w) {
        
        x = v3.x;
        y = v3.y;
        z = v3.z;
        this.w = w;
    }
    
    /**Create a new 4d vector from the scalar and the 3d vector
    @param x the scalar for the x value
    @param v3 the 3d vector for the y, z, and w values*/
    public Vector4(float x, final Vector3 v3) {
        
        this.x = x;
        y = v3.x;
        z = v3.y;
        w = v3.z;
    }
    
    /**Create a new 4d vector from the values of a length 4 array
    @param array the array to create the vector from*/
    public Vector4(float array[]) {
    	
    	//check the size of the array
    	if (array.length < 4) {
    		
    		Log.v(Values.TAG, "ERROR: cannot create 4D vector from array " +
				"of length " + array.length);
    		throw new RuntimeException(
				"ERROR: cannot create 4D vector from array " +
				"of length " + array.length);
    	}
    	
    	x = array[0];
    	y = array[1];
    	z = array[2];
    	w = array[3];
    }
    
    //PUBLIC METHODS
    /**Set the values of the vector to be the new given values
    @param x the new x value
    @param y the new y value
    @param z the new z value
    @param w the new w value*/
    public void set(float x, float y, float z, float w) {
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    /**Copy the values from the other vector to this vector
    @param other the other vector to copy from*/
    public void copy(final Vector4 other) {
        
        x = other.x;
        y = other.y;
        z = other.z;
        w = other.w;
    }
    
    /**Adds the values of the scalar to this vector
    @param scalar the scalar to add*/
    public void add(float scalar) {
        
        x += scalar;
        y += scalar;
        z += scalar;
        w += scalar;
    }
    
    /**Adds the values of the other vector to this vector
    @param other the other vector to add to this vector*/
    public void add(final Vector4 other) {
        
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
    }
    
    /**Subtracts the values of the scalar from this vector
    @param scalar the scalar to subtract with*/
    public void sub(float scalar) {
        
        x -= scalar;
        y -= scalar;
        z -= scalar;
        w -= scalar;
    }
    
    /**Subtracts the values of the other vector from this vector
    @param other the other vector to subtract by*/
    public void sub(final Vector4 other) {
        
        x -= other.x;
        y -= other.y;
        z -= other.z;
        w -= other.w;
    }

    /**Multiplies the values of this vector by the values of the scalar
    @param scalar the scalar to multiply by*/
    public void mul(float scalar) {
        
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
    }
    
    /**Divides the values of this vector by the scalar
    @param scalar the scalar to divide with*/
    public void div(float scalar) {
        
        x /= scalar;
        y /= scalar;
        z /= scalar;
        w /= scalar;
    }
    
    /**@return the magnitude of this vector*/
    final public float magnitude() {
        
    	return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }
    
    /**Inverses this vector*/
    public void inverse() {
        
        x = -x;
        y = -y;
        z = -z;
        w = -w;
    }
    
    /**Normalises this vector*/
    public void normalise() {
    	
    	//get the magnitude
    	float mag = magnitude();
    	
    	//normalise the components
    	x /= mag;
    	y /= mag;
    	z /= mag;
    	w /= mag;
    }
    
    /**Calculates the dot product of this vector and the other vector
    @param other the other vector to calculate with
    @return the dot product*/
    final public float dot(final Vector4 other) {
        
        return (this.x * other.x) + (this.y * other.y) +
            (this.z * other.z) + (this.w * other.w);
    }
    
    /**Calculates this distance between this vector and the other vector
    @param other the other vector to find the distance between
    @return the distance between the two vectors*/
    final public float distance(final Vector4 other) {
        
        return (float) Math.sqrt(
            Math.pow(this.x - other.x, 2.0f) +
            Math.pow(this.y - other.y, 2.0f) +
            Math.pow(this.z - other.z, 2.0f) +
            Math.pow(this.w - other.w, 2.0f));
    }
    
    /**@return the values of the vector as an array*/
    final public float[] toArray() {
        
        float array[] = {x, y, z, w};
        
        return array;
    }
    
    /**@return a copy of this vector*/
    public Vector4 clone() {
        
        return new Vector4(x, y, z, w);
    }
    
    /**@return the vector as a string*/
    public String toString() {
        
        return "(" + x + ", " + y + ", " + z + "," + w + ")";
    }
}