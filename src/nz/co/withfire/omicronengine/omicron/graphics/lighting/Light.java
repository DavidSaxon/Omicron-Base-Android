/*************************************************************\
| Abstract class that lights (apart from ambient extend from. |
|															  |
| @author David Saxon										  |
\*************************************************************/

package nz.co.withfire.omicronengine.omicron.graphics.lighting;

import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

public abstract class Light {

	//VARIABLES
	//the colour of the light
	private Vector3 colour;
	//the strength of the light
	private float strength;
	//the position of the light
	private Vector3 position;
	
	//CONSTRUCTOR
	/**Creates a new light
	@param colour the colour of the light
	@param strenght the strength of the light
	@param position the position of the light*/
	public Light(Vector3 colour, float strength, Vector3 position) {
		
		//initialise the variables
		this.colour = colour;
		this.strength = strength;
		this.position = position;
	}
	
	//PUBLIC METHODS
	//GETTERS
	/**@return the value of the light (colour * strength)*/
	public Vector3 getValue() {
		
		return new Vector3(colour.x * strength,
			colour.y * strength, colour.z * strength);
	}
	
	/**@return the colour of the ambient light*/
	public Vector3 getColour() {
		
		return colour;
	}
	
	/**@return the strength of the light*/
	public float getStrength() {
		
		return strength;
	}
	
	/**@return the position of the light*/
	public Vector3 getPosition() {
		
		return position;
	}
	
	//SETTERS
	/**@param colour the new colour of the light*/
	public void setColour(Vector3 colour) {
		
		this.colour = colour;
	}
	
	/**@param strength the new colour of the strength*/
	public void setStrength(float strength) {
		
		this.strength = strength;
	}
	
	/**@param position the new position of the light*/
	public void setPosition(Vector3 position) {
		
		this.position = position;
	}
}
