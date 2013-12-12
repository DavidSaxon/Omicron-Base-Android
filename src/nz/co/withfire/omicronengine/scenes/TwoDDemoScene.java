/********************************\
| Scene that demos Omicron's 2d. |
|                                |
| @author David Saxon            |
\********************************/

package nz.co.withfire.omicronengine.scenes;

import java.util.HashMap;
import java.util.Map;

import nz.co.withfire.omicronengine.OmicronActivity;
import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.entities.gui.Fader;
import nz.co.withfire.omicronengine.entities.gui.Fader.FadeDirection;
import nz.co.withfire.omicronengine.entities.twod_demo.Explosion;
import nz.co.withfire.omicronengine.entities.twod_demo.GlowFish;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.physics.collision.CollisionCallBack;
import nz.co.withfire.omicronengine.omicron.physics.collision.CollisionGroups;
import nz.co.withfire.omicronengine.omicron.physics.collision.CollisionProcess;
import nz.co.withfire.omicronengine.omicron.physics.types.Collidable;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.sound.FxManager;
import nz.co.withfire.omicronengine.omicron.sound.MusicManager;
import nz.co.withfire.omicronengine.omicron.utilities.ColourUtil;
import nz.co.withfire.omicronengine.omicron.utilities.TransformationsUtil;
import nz.co.withfire.omicronengine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.DebugValues;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class TwoDDemoScene extends Scene {

    //VARIABLES
    //the next scene
    private Scene nextScene = null;
    
    //the camera
    private Camera camera = new PerspectiveCamera(60.0f, 0.01f, 1000.0f);
    
    //fade out
    private Fader fadeOut = null;
    
    //the collision call back for fish
    private CollisionCallBack fishCollisionCallBack =
        new FishCollisionCallBack();
    //map of fish collisions this frame
    private Map<GlowFish,GlowFish> fishCollisions =
        new HashMap<GlowFish, GlowFish>();
    
    //TODO: FIX ME
    int sound = 0;
    
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
        MusicManager.fadeIn(R.raw.music_twod_demo_swarming, 0.6f, 0.02f);
        
        //load the sound
        //TODO move!
        sound = FxManager.load(OmicronActivity.context, R.raw.sound_fish_explosion);
    }
    
    @Override
    public boolean execute() {
        
        //super call
        super.execute();
        
        //process collisions
        CollisionProcess.betweenGroup("fish", fishCollisionCallBack);
        createFishExplosions();
        fishCollisions.clear();
        
        return fadeOut != null && fadeOut.complete() &&
            (DebugValues.DISABLE_MUSIC || !MusicManager.isFading());
    }
    
    @Override
    public Scene nextScene() {
        
        //super call
        super.nextScene();
        
        //stop music
        MusicManager.stop();
        
        //remove the collision group
        CollisionGroups.removeGroup("fish");
        
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
            MusicManager.fadeOut(0.02f);
        }
        
        return true;
    }
    
    //PRIVATE METHODS
    /**Creates explosions from collisions between two fish*/
    private void createFishExplosions() {
        
        for (Map.Entry<GlowFish, GlowFish> e : fishCollisions.entrySet()) {
            
            //combine the colours
            Vector4 combine = ColourUtil.combine(
                e.getKey().getColour(), e.getValue().getColour());
            entities.add(new Explosion(e.getKey().getPos().clone(), combine));
            e.getKey().remove();
            e.getValue().remove();
            
            //play sound
            FxManager.play3d(sound, e.getKey().getPos(), 1.0f);
        }
    }
    
    
    /**Adds the initial entities*/
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
        
        //fader
        entities.add(new Fader(FadeDirection.FADE_IN, 0.02f,
            new Vector4(0.0f, 0.0f, 0.0f, 1.0f), true));
    }
    
    //PRIVARE CLASSES
    private class FishCollisionCallBack implements CollisionCallBack {

        //PUBLIC METHODS
        @Override
        public void collision(Collidable c1, Collidable c2) {

            fishCollisions.put((GlowFish) c1, (GlowFish) c2);
        }
    }
}
