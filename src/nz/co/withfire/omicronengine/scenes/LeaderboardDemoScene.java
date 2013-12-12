/*****************************************************************************\
| Scene that demos Omicron's leaderboards which use Google Game Leaderboards. |
|                                                                             |
| @author David Saxon                                                         |
\*****************************************************************************/

package nz.co.withfire.omicronengine.scenes;

import nz.co.withfire.omicronengine.entities.gui.Fader;
import nz.co.withfire.omicronengine.entities.gui.Fader.FadeDirection;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class LeaderboardDemoScene extends Scene {

    //VARIRBALES
    //the next scene
    private Scene nextScene = null;
    
    //the camera
    private Camera camera = new PerspectiveCamera(60.0f, 0.01f, 1000.0f);
    
    //fade out
    private Fader fadeOut = null;
    
    //PUBLIC METHODS
    @Override
    public void init() {
        
        //set the camera
        OmicronRenderer.setCamera(camera);
    }
    
    @Override
    public boolean execute() {
        
        //super call
        super.execute();
     
        return fadeOut != null && fadeOut.complete();
    }
    
    @Override
    public Scene nextScene() {
        
        //super call
        super.nextScene();
        
        //ResourceManager.destroy(ResourceGroup.TWOD_DEMO);
        ResourceManager.load(ResourceGroup.MAIN_MENU);
        
        return nextScene;
    }
    
    @Override
    public boolean backPressed() {
        
        if (fadeOut == null) {
            
            nextScene = new MainMenuScene();
            fadeOut = new Fader(FadeDirection.FADE_OUT, 0.02f,
                new Vector4(0.0f, 0.0f, 0.0f, 1.0f), false);
            entities.add(fadeOut);
        }
        
        return true;
    }
}
