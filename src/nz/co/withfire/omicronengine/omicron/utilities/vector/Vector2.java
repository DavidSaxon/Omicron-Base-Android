/*************************\
| Represents a 2d vector. |
|                         |
| @author David Saxon     |
\*************************/

package nz.co.withfire.omicronengine.omicron.utilities.vector;

import android.util.Log;
import nz.co.withfire.omicronengine.override.Values;

public class Vector2 {
    
    //VARIABLES
    //static zero vector
    static public final Vector2 zero2d = new Vector2();
    
    //the values of the vector
    public float x;
    public float y;
    
    //CONSTRUCTORS
    /**Creates a new zero 2d vector*/
    public Vector2() {
        
        x = 0.0f;
        y = 0.0f;
    }
    
    /**Creates a new 2d vector using the given the values
    @param x the x value of the vector
    @param y the y value of the vector*/
    public Vector2(float x, float y) {
        
        this.x = x;
        this.y = y;
    }
    
    /**Creates a new 2d vector by copying the values from the other vector
    @param other the other vector to copy from*/
    public Vector2(final Vector2 other) {
        
        this.x = other.x;
        this.y = other.y;
    }
    
    /**Create a new 2d vector by copying the x and y values from
    the 3d vector and truncates the z value
    @param other the 3d vector to copy from*/
    public Vector2(final Vector3 other) {
    	
    	this.x = other.x;
    	this.y = other.y;
    }
    
    /**Create a new 2d vector by copying the x and y values from
    the 4d vector and truncates the z and w values
    @param other the 4d vector to copy from*/
    public Vector2(final Vector4 other) {
    	
    	this.x = other.x;
    	this.y = other.y;
    }
    
    /**Create a new 2d vector from the values of a length 2 array
    @param array the array to create the vector from*/
    public Vector2(float array[]) {
    	
    	//check the size of the array
    	if (array.length < 2) {
    		
    		Log.v(Values.TAG, "ERROR: cannot create 2D vector from array " +
				"of length " + array.length);
    		throw new RuntimeException(
				"ERROR: cannot create 2D vector from array " +
				"of length " + array.length);
    	}
    	
    	this.x = array[0];
    	this.y = array[1];
    }
    
    //PUBLIC METHODS
    /**Set the values of the vector to be the new given values
    @param x the new x value
    @param y the new y value*/
    public void set(float x, float y) {
        
        this.x = x;
        this.y = y;
    }
    
    /**Copy the values from the other vector to this vector
    @param other the other vector to copy from*/
    public void copy(final Vector2 other) {
        
        this.x = other.x;
        this.y = other.y;
    }
    
    /**Adds the values of the other vector to this vector
    @param other the other vector to add to this vector*/
    public void add(final Vector2 other) {
        
        this.x += other.x;
        this.y += other.y;
    }
    
    /**Subtracts the values of the other vector from this vector
    @param other the other vector to subtract by*/
    public void sub(final Vector2 other) {
        
        this.x -= other.x;
        this.y -= other.y;
    }
    
    /**Multiplies the values of this vector by the values of the other vector
    @param other the other vector to multiply by*/
    public void mul(final Vector2 other) {
        
        this.x *= other.x;
        this.y *= other.y;
    }
    
    /**Divides the values of this vector by the values of the other vector
    @param other the other vector to divide by*/
    public void div(final Vector2 other) {
        
        this.x /= other.x;
        this.y /= other.y;
    }
    
    /**@return the magnitude of this vector*/
    final public float magnitude() {
        
        return (float) Math.sqrt(x * x + y * y);
    }
    
    /**@return the inverse of the vector*/
    public Vector2 inverse() {
    	
    	return new Vector2(-x, -y);
    }
    
    /**Normalises this vector*/
    public void normalise() {
    	
    	//get the magnitude
    	float mag = magnitude();
    	
    	//normalise the components
    	x /= mag;
    	y /= mag;
    }
    
    /**Calculates the dot product of this vector and the other vector
    @param other the other vector to calculate with
    @return the dot product*/
    final public float dot(final Vector2 other) {
        
        return (this.x * other.x) + (this.y * other.y);
    }
    
    /**Calculates this distance between this vector and the other vector
    @param other the other vector to find the distance between
    @return the distance between the two vectors*/
    final public float distance(final Vector2 other) {
        
        return (float) Math.sqrt(Math.pow(this.x - other.x, 2.0f) +
            Math.pow(this.y - other.y, 2.0f));
    }
    
    /**Calculate the angle between this vector and the other vector
    @param other the other vector to calculate the angle between
    @return the angle between the vectors*/
    final public float angleBetween(final Vector2 other) {
        
        return (float) (-1.0*(Math.atan2(y-other.y, x-other.x)));
    }
    
    /**@return the values of the vector as an array*/
    final public float[] toArray() {
        
        float array[] = {x, y};
        
        return array;
    }
    
    /**@return the vector as a string*/
    public String toString() {
        
        return "(" + x + ", " + y + ")";
    }
}
