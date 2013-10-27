/*************************\
| Represents a 2d vector. |
|                         |
| @author David Saxon     |
\*************************/

package nz.co.withfire.omicronengine.omicron.utilities.vector;

public class Vector2 {
    
    //VARIABLES
    //static zero vector
    static public final Vector2 zero2d = new Vector2();
    
    //the values of the vector
    private float x;
    private float y;
    
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
    	
    	this.x = other.getX();
    	this.y = other.getY();
    }
    
    /**Create a new 2d vector by copying the x and y values from
    the 4d vector and truncates the z and w values
    @param other the 4d vector to copy from*/
    public Vector2(final Vector4 other) {
    	
    	this.x = other.getX();
    	this.y = other.getY();
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
    
    /**@param value the value to add to the x value of this vector*/
    public void addX(float value) {
    	
    	this.x += value;
    }
    
    /**@param value the value to add to the y value of this vector*/
    public void addY(float value) {
    	
    	this.y += value;
    }
    
    /**@param value the value to subtract from the x value of this vector*/
    public void subX(float value) {
    	
    	this.x -= value;
    }
    
    /**@param value the value to subtract from the y value of this vector*/
    public void subY(float value) {
    	
    	this.y -= value;
    }
    
    /**@param value the value to multiply the x value of this vector by*/
    public void mulX(float value) {
    	
    	this.x *= value;
    }
    
    /**@param value the value to multiply the y value of this vector by*/
    public void mulY(float value) {
    	
    	this.y *= value;
    }

    /**@param value the value to divide the x value of this vector by*/
    public void divX(float value) {
    	
    	this.x *= value;
    }
    
    /**@param value the value to divide the y value of this vector by*/
    public void divY(float value) {
    	
    	this.y *= value;
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
    
    //GETTERS
    /**@return the x value of the vector*/
    final public float getX() {
        
        return x;
    }
    
    /**@return the y value of the vector*/
    final public float getY() {
        
        return y;
    }
    
    //SETTERS
    /**Sets the new x value
    @param x the new x value*/
    public void setX(float x) {
        
        this.x = x;
    }
    
    /**Sets the new y value
    @param y the new y value*/
    public void setY(float y) {
        
        this.y = y;
    }
    
    /**@return the vector as a string*/
    public String toString() {
        
        return "(" + x + ", " + y + ")";
    }
}
