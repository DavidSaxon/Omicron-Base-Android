/********************************\
| Scene that demos Omicron's 2d. |
|                                |
| @author David Saxon            |
\********************************/

package nz.co.withfire.omicronengine.scenes;

import java.util.Vector;

import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.entities.gui.Fader;
import nz.co.withfire.omicronengine.entities.gui.Fader.FadeDirection;
import nz.co.withfire.omicronengine.entities.twod_demo.GlowFish;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.physics.collision.CollisionGroups;
import nz.co.withfire.omicronengine.omicron.physics.collision.CollisionProcess;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.sound.MusicManager;
import nz.co.withfire.omicronengine.omicron.utilities.TransformationsUtil;
import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class TwoDDemoScene extends Scene {

    //VARIABLES
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
        
        //create collision groups
        CollisionGroups.newGroup("fish");
        
        //create entities
        initEntities();
        
        //start playing music
        MusicManager.play(R.raw.music_twod_demo_swarming, 1.0f);
    }
    
    @Override
    public boolean execute() {
        
        super.execute();
        
        //process collisions
        CollisionProcess.betweenGroups("fish", "fish");
        
        return fadeOut != null && fadeOut.complete();
    }
    
    @Override
    public Scene nextScene() {
        
        //super call
        super.nextScene();
        
        //stop music
        MusicManager.stop();
        
        ResourceManager.destroy(ResourceGroup.TWOD_DEMO);
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
    
    //PRIVATE METHODS
    private void initEntities() {
        
        for (int i = 0; i < 15; ++i) {
        
            //generate random position and direction
            Vector3 pos = new Vector3(
                ((ValuesUtil.rand.nextFloat() * 2.0f) - 1.0f) *
                TransformationsUtil.getOpenGLDim().x,
                ((ValuesUtil.rand.nextFloat() * 2.0f) - 1.0f) *
                TransformationsUtil.getOpenGLDim().y,
                0.0f);
            float rot = ValuesUtil.rand.nextFloat() * 360.0f;
            
            entities.add(new GlowFish(pos, new Vector3(0.0f, 0.0f, rot)));
        }
    }
}
