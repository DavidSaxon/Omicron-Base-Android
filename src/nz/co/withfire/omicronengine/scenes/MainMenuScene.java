/*******************************************\
| The main menu were demos can be selected. |
|                                           |
| @author David Saxon                       |
\*******************************************/

package nz.co.withfire.omicronengine.scenes;

import java.util.ArrayList;
import java.util.List;

import com.google.example.games.basegameutils.BaseGameActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import nz.co.withfire.omicronengine.OmicronActivity;
import nz.co.withfire.omicronengine.R;
import nz.co.withfire.omicronengine.entities.gui.Fader;
import nz.co.withfire.omicronengine.entities.gui.Fader.FadeDirection;
import nz.co.withfire.omicronengine.entities.main_menu.MainMenuBackground;
import nz.co.withfire.omicronengine.omicron.graphics.camera.Camera;
import nz.co.withfire.omicronengine.omicron.graphics.camera.PerspectiveCamera;
import nz.co.withfire.omicronengine.omicron.graphics.renderer.OmicronRenderer;
import nz.co.withfire.omicronengine.omicron.logic.scene.Scene;
import nz.co.withfire.omicronengine.omicron.resources.manager.ResourceManager;
import nz.co.withfire.omicronengine.omicron.sound.MusicManager;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector4;
import nz.co.withfire.omicronengine.override.Values;
import nz.co.withfire.omicronengine.override.ResourceGroups.ResourceGroup;

public class MainMenuScene extends Scene {

    //VARIABLES
    //is true once we are complete
    private boolean complete = false;
    //the next scene
    private Scene nextScene = null;
    
    //the camera
    private Camera camera = new PerspectiveCamera(60.0f, 0.01f, 1000.0f);
    
    //the table of buttons
    private TableLayout tableLayout;
    
    //PUBLIC METHODS
    @Override
    public void init() {
        
        //set the camera
        OmicronRenderer.setCamera(camera);
        
        //add the background
        entities.add(new MainMenuBackground());
        entities.add(new Fader(FadeDirection.FADE_IN, 0.02f,
            new Vector4(0.0f, 0.0f, 0.0f, 1.0f), true));
        
        //set up the buttons
        initButtons();
        
        //start playing music
        MusicManager.play(R.raw.music_mainmenu_drone, 1.0f);
    }
    
    @Override
    public boolean execute() {
        
        super.execute();
        
        return complete;
    }
    
    @Override
    public Scene nextScene() {
        
        //super call
        super.nextScene();
        
        //remove the buttons
        removeButtons();
        
        //stop music
        MusicManager.stop();
        
        ResourceManager.destroy(ResourceGroup.MAIN_MENU);
        
        return nextScene;
    }
    
    //PRIVATE METHODS
    /**Initialises the menu's buttons*/
    private void initButtons() {
        
        ((Activity) OmicronActivity.context).runOnUiThread(
            new Runnable() {
                    
                public void run() {
                    
                    //create a table layout and add to the layout
                    RelativeLayout layout =
                        (RelativeLayout) ((Activity) OmicronActivity.context)
                        .findViewById(R.id.omicron_layout);
                    
                    tableLayout =
                        new TableLayout(OmicronActivity.context);
                    tableLayout.setLayoutParams(
                        new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT));
                    tableLayout.setGravity(Gravity.CENTER);
                    
                    //create the first row of buttons
                    {
                        
                        TableRow tableRow = new TableRow(OmicronActivity.context);
                        tableRow.setGravity(Gravity.CENTER);
                        
                        createButton(R.string.material_demo,
                            new MaterialDemoListener(), tableRow);
                        createButton(R.string.twod_demo,
                            new TwoDDemoListener(), tableRow);
                        createButton(R.string.leaderboard_demo,
                            new LeaderboardDemoListener(), tableRow);
                        createButton(R.string.exit,
                            new ExitListener(), tableRow);
                        
                        tableLayout.addView(tableRow);
                    }
                    
                    layout.addView(tableLayout);
                }
            }
         );
    }
    
    /**Creates a new button for the table
    @param text the button text resource id
    @param listener the on click listener of the button
    @param tableRow the table row to add it to*/
    private void createButton(int text,
        OnClickListener listener, TableRow tableRow) {
        
        Button b = new Button(OmicronActivity.context);
        b.getBackground().setColorFilter(
            0xAA000000, PorterDuff.Mode.MULTIPLY);
        b.setText(text);
        b.setTextSize(18.0f);
        b.setTextColor(Color.rgb(255, 255, 255));
        b.setOnClickListener(listener);
        b.setGravity(Gravity.CENTER);
        tableRow.addView(b, 250, 125);
    }
    
    /**Removes the buttons*/
    private void removeButtons() {
        
        ((Activity) OmicronActivity.context).runOnUiThread(
            new Runnable() {
                        
                public void run() {
                    
                    RelativeLayout layout =
                        (RelativeLayout) ((Activity) OmicronActivity.context)
                        .findViewById(R.id.omicron_layout);
                    
                    layout.removeView(tableLayout);
                }
            }
        );
    }
    
    /**Fades out the scene*/
    private void fadeOut() {
        
        //add the fade out
        Fader fadeOut = new Fader(FadeDirection.FADE_OUT, 0.02f,
            new Vector4(0.0f, 0.0f, 0.0f, 1.0f), false);
        entities.add(fadeOut);
        
        //wait for it to complete
        while (!fadeOut.complete());
    }
    
    //CLICK LISTENERS
    private class MaterialDemoListener implements OnClickListener {

        //PUBLIC METHODS
        @Override
        public void onClick(View view) {
            
            //set load values
            List<ResourceGroup> loadList = new ArrayList<ResourceGroup>();
            loadList.add(ResourceGroup.MATERIAL_DEMO);
            LoadingScene.setLoadGroups(loadList);
            LoadingScene.setNextScene(new MaterialDemoScene());
            
            //set the next scene
            nextScene = new LoadingScene();
            
            //fade out
            fadeOut();
            
            complete = true;
        }
    }
    
    private class TwoDDemoListener implements OnClickListener {

        //PUBLIC METHODS
        @Override
        public void onClick(View view) {
            
            //set load values
            List<ResourceGroup> loadList = new ArrayList<ResourceGroup>();
            loadList.add(ResourceGroup.TWOD_DEMO);
            LoadingScene.setLoadGroups(loadList);
            LoadingScene.setNextScene(new TwoDDemoScene());
            
            //set the next scene
            nextScene = new LoadingScene();
            
            //fade out
            fadeOut();
            
            complete = true;
        }
    }
    
    private class LeaderboardDemoListener implements OnClickListener {
        
        //PUBLIC METHODS
        @Override
        public void onClick(View view) {
            
//            //set load values
//            List<ResourceGroup> loadList = new ArrayList<ResourceGroup>();
//            //loadList.add(ResourceGroup.TWOD_DEMO);
//            LoadingScene.setLoadGroups(loadList);
//            LoadingScene.setNextScene(new LeaderboardDemoScene());
            
            //set the next scene
//            nextScene = new LeaderboardDemoScene();
//            
//            //fade out
//            fadeOut();
//            
//            complete = true;
            
            OmicronActivity.activity.gamesSignIn();
        }
    }
    
    private class ExitListener implements OnClickListener {

        //PUBLIC METHODS
        @Override
        public void onClick(View view) {

            //fade out
            fadeOut();
            
            System.exit(0);
        }
    }
}
