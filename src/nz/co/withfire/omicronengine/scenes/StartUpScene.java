/***********************************************************\
| The start up scene which is default to all Omicron games. |
|														    |
| @author David Saxon									    |
\***********************************************************/

package nz.co.withfire.omicronengine.scenes;

import nz.co.withfire.omicronengine.entities.start_up.Splash;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class StartUpScene extends Scene {

	//VARIABLES
	//the camera
  	private Camera camera = new PerspectiveCamera(60.0f, 0.1f, 100.0f);
	
	//true once loading has been completed
	private boolean loaded = false;
	
	//the splash entity
	private Splash splash;
	
	//PUBLIC METHODS
	@Override
	public void init() {
		
		//set the camera
  		OmicronRenderer.setCamera(camera);
		
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
			
			//ResourceManager.load(ResourceGroup.MATERIAL_DEMO);
			ResourceManager.load(ResourceGroup.MAIN_MENU);
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
		
		//return new MaterialDemoScene();
		return new MainMenuScene();
	}
}
