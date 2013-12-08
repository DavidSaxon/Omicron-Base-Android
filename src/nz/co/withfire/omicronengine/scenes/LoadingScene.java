/*********************\
| Loads a scene.      |
|                     |
| @author David Saxon |
\*********************/

package nz.co.withfire.omicronengine.scenes;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import nz.co.withfire.omicronengine.entities.gui.Fader;
import nz.co.withfire.omicronengine.entities.gui.Fader.FadeDirection;
import nz.co.withfire.omicronengine.entities.loading.LoadingBar;
import nz.co.withfire.omicronengine.entities.loading.LoadingCog;
import nz.co.withfire.omicronengine.entities.start_up.Splash;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.renderable.Mesh;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.utilities.MathUtil;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.Values;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class LoadingScene extends Scene {

    //VARIABLES
    //the camera
    private Camera camera = new PerspectiveCamera(60.0f, 0.1f, 100.0f);
    
    //the fader
    private Fader fader;
    //the loading cog
    private LoadingCog cog;
    //the loading bar
    private LoadingBar loadingBar;
    
    //the resource groups to load
    private static List<ResourceGroup> loadGroups =
        new ArrayList<ResourceGroup>();
    //the next scene
    private static Scene nextScene;
    
    //is true once we have begin loading
    private boolean loadingBegun = false;
    //is true once we are fading out
    private boolean fadingOut = false;
    
    //PUBLIC METHODS
    @Override
    public void init() {
        
        //set the camera
        OmicronRenderer.setCamera(camera);
        
        //load the start up resources
        ResourceManager.load(ResourceGroup.LOADING);
        
        //create and add the cog
        cog = new LoadingCog();
        entities.add(cog);
        
        //create and add the bar
        loadingBar = new LoadingBar();
        entities.add(loadingBar);
        
        //create and add the fader
        fader = new Fader(FadeDirection.FADE_IN, 0.02f,
            new Vector4(0.0f, 0.0f, 0.0f, 1.0f), true);
        entities.add(fader);
    }
    
    @Override
    public boolean execute() {
        
        //super call
        super.execute();
        
        //begin loading
        if (fader.complete() && !loadingBegun) {
            
            loadingBegun = true;
            
            for (ResourceGroup r : loadGroups) {
            
                ResourceManager.concurrentLoad(r);
            }
        }
        
        //update the loading bar
        if (loadingBegun) {
            
            //get the percent
            float percent = ResourceManager.concurrentLoadPercent();
            
            loadingBar.setPercent(percent);
            
            if (MathUtil.floatEqualsOrGreater(percent, 1.0f) && !fadingOut) {
                
                fadingOut = true;
                
                //create fade out
                fader = new Fader(FadeDirection.FADE_OUT, 0.02f,
                    new Vector4(0.0f, 0.0f, 0.0f, 1.0f), false);
                entities.add(fader);
            }
        }
        
        return loadingBegun && fadingOut && fader.complete();
    }
    
    /**@return the next scene*/
    public Scene nextScene() {
        
        //super call
        super.nextScene();
        
        //release loading resources
        ResourceManager.destroy(ResourceGroup.LOADING);
        
        return nextScene;
    }
    
    @Override
    public boolean backPressed() {
        
        return true;
    }
    
    //SETTERS
    /**@param the new list of resource groups to load*/
    public static void setLoadGroups(List<ResourceGroup> loadGroups) {
        
        LoadingScene.loadGroups = loadGroups;
    }
    
    /**@param nextScene the new next scene*/
    public static void setNextScene(Scene nextScene) {
        
        LoadingScene.nextScene = nextScene;
    }
}
