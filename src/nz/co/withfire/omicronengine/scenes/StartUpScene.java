/***********************************************************\
| The start up scene which is default to all Omicron games. |
|														    |
| @author David Saxon									    |
\***********************************************************/

package nz.co.withfire.omicronengine.scenes;

import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;

public class StartUpScene extends Scene {

	//PUBLIC METHODS
	@Override
	public boolean execute() {
		
		return true;
	}
	
	/**@return the next scene*/
	public Scene nextScene() {
		
		return new MaterialDemoScene();
	}
}
