/************************\
| Represent a 3d vector. |
|                        |
| @author David Saxon    |
\************************/

package nz.co.withfire.omicronengine.omicron.utilities.vector;

import nz.co.withfire.omicronengine.override.Values;
import android.util.Log;

public class Vector3 {
   
    //VARIABLES
    //static zero vector
    static public final Vector3 zero3d = new Vector3();
    
    //the values of the variables
    public float x;
    public float y;
    public float z;
    
    //CONSTRUCTORS
    /**Creates a new zero 3d vector*/
    public Vector3() {
        
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }
    
    /**Creates a new vector from the given values
    @param x the x value of the vector
    @param y the y value of the vector
    @param z the z value of the vector*/
    public Vector3(float x, float y, float z) {
        
        this.x  = x;
        this.y = y;
        this.z = z;
    }
    
    /**Creates a new vector by copying the values from the other vector
    @param other the other vector to copy from*/
    public Vector3(final Vector3 other) {
        
        x = other.x;
        y = other.y;
        z = other.z;
    }
    
    /**Create a new 3d vector by copying the x and y values from
    the 2d vector and sets the z value to 0
    @param other the 2d vector to copy from*/
    public Vector3(final Vector2 other) {
    	
    	x = other.x;
    	y = other.y;
    	z = 0;
    }
    
    /**Create a new 3d vector by copying the x, y, and z values from
    the 4d vector and truncates the w value
    @param other the 4d vector to copy from*/
    public Vector3(final Vector4 other) {
    	
    	x = other.x;
    	y = other.y;
    	z = other.z;
    }
    
    /**Create a new 3d vector from the values of a length 3 array
    @param array the array to create the vector from*/
    public Vector3(float array[]) {
    	
    	//check the size of the array
    	if (array.length < 3) {
    		
    		Log.v(Values.TAG, "ERROR: cannot create 3D vector from array " +
				"of length " + array.length);
    		throw new RuntimeException(
				"ERROR: cannot create 3D vector from array " +
				"of length " + array.length);
    	}
    	
    	x = array[0];
    	y = array[1];
    	z = array[2];
    }
    
    //PUBLIC METHODS
    /**Set the values of the vector to be the new given values
    @param x the new x value
    @param y the new y value
    @param z the new z value*/
    public void set(float x, float y, float z) {
        
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**Copy the values from the other vector to this vector
    @param other the other vector to copy from*/
    public void copy(final Vector3 other) {
        
        x = other.x;
        y = other.y;
        z = other.z;
    }
    
    /**Adds the values of the scalar to this vector
    @param scalar the scalar to add*/
    public void add(float scalar) {
        
        x += scalar;
        y += scalar;
        z += scalar;
    }
    
    /**Adds the values of the other vector to this vector
    @param other the other vector to add to this vector*/
    public void add(final Vector3 other) {
        
        x += other.x;
        y += other.y;
        z += other.z;
    }
    
    /**Subtracts the values of the scalar from this vector
    @param scalar the scalar to subtract with*/
    public void sub(float scalar) {
        
        x -= scalar;
        y -= scalar;
        z -= scalar;
    }
    
    /**Subtracts the values of the other vector from this vector
    @param other the other vector to subtract by*/
    public void sub(final Vector3 other) {
        
        x -= other.x;
        y -= other.y;
        z -= other.z;
    }
    
    /**Multiplies the values of this vector by the values of the scalar
    @param scalar the scalar to multiply by*/
    public void mul(float scalar) {
        
        x *= scalar;
        y *= scalar;
        z *= scalar;
    }
    
    /**Divides the values of this vector by the scalar
    @param scalar the scalar to divide with*/
    public void div(float scalar) {
        
        x /= scalar;
        y /= scalar;
        z /= scalar;
    }
    
    /**@return the magnitude of this vector*/
    final public float magnitude() {
        
    	return (float) Math.sqrt(x * x + y * y + z * z);
    }
    
    /**Inverses this vector*/
    public void inverse() {
        
        x = -x;
        y = -y;
        z = -z;
    }
    
    /**Normalises this vector*/
    public void normalise() {
    	
    	//get the magnitude
    	float mag = magnitude();
    	
    	//normalise the components
    	x /= mag;
    	y /= mag;
    	z /= mag;
    }
    
    /**Calculates the dot product of this vector and the other vector
    @param other the other vector to calculate with
    @return the dot product*/
    final public float dot(final Vector3 other) {
        
        return (this.x * other.x) + (this.y * other.y) +
            (this.z * other.z);
    }
    
    /**Calculates this distance between this vector and the other vector
    @param other the other vector to find the distance between
    @return the distance between the two vectors*/
    final public float distance(final Vector3 other) {
        
        return (float) Math.sqrt(Math.pow(this.x - other.x,  2.0f) +
            Math.pow(this.y - other.y, 2.0f) +
            Math.pow(this.z - other.z, 2.0f));
    }
    
    /**@return the values of the vector as an array*/
    final public float[] toArray() {
        
        float array[] = {x, y, z};
        
        return array;
    }

    /**@return the vector as a string*/
    public String toString() {
        
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
