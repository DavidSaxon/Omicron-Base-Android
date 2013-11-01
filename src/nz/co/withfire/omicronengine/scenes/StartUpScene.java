/***********************************************************\
| The start up scene which is default to all Omicron games. |
|														    |
| @author David Saxon									    |
\***********************************************************/

package nz.co.withfire.omicronengine.scenes;

import nz.co.withfire.omicronengine.entities.start_up.Splash;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class StartUpScene extends Scene {

	//VARIABLES
	//true once loading has been completed
	private boolean loaded = false;
	
	//the splash entity
	private Splash splash;
	
	//PUBLIC METHODS
	@Override
	public void init() {
		
		//load the start up resources
		ResourceManager.load(ResourceGroup.ALL);
		ResourceManager.load(ResourceGroup.GUI);
		ResourceManager.load(ResourceGroup.START_UP);
		
		//create and add the splash
		splash = new Splash(
				(Mesh) ResourceManager.getRenderable("omicron_splash"));
		entities.add(splash);
	}
	
	@Override
	public boolean execute() {
		
		//super call
		super.execute();
		
		//once the splash has faded in load the next scene resources
		if (!loaded && splash.fadedIn()) {
			
			ResourceManager.load(ResourceGroup.MATERIAL_DEMO);
			loaded = true;
		}
		
		//complete once the splash has faded out
		return splash.fadedOut();
	}
	
	/**@return the next scene*/
	public Scene nextScene() {
		
		//super call
		super.nextScene();
		
		//release start up resources
		ResourceManager.destroy(ResourceGroup.START_UP);
		
		return new MaterialDemoScene();
	}
}
