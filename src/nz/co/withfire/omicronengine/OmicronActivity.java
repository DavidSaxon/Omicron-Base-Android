package nz.co.withfire.omicronengine;

import com.google.example.games.basegameutils.BaseGameActivity;

import nz.co.withfire.omicronengine.omicron.android.OmicronSurfaceView;
import nz.co.withfire.omicronengine.omicron.sound.MusicManager;
import nz.co.withfire.omicronengine.override.Values;
import nz.co.withfire.omicronengine.scenes.StartUpScene;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class OmicronActivity extends BaseGameActivity {
    
    //VARIABLES
    //the context
    public static Context context;
    //The Activity
    public static OmicronActivity activity;
    //the surface view
    private OmicronSurfaceView surfaceView;
    
    //METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set the context
        context = this;
        activity = this;

        //set to full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.setContentView(R.layout.omicron);

        FrameLayout frame =
            (FrameLayout)findViewById(R.id.omicron_frame_layout);

        //create an Omicron surface view
        surfaceView =
            new OmicronSurfaceView(this, new StartUpScene());

        frame.addView(surfaceView);
        
        //super call
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onPause() {

        //super call
        super.onPause();

        //stop the music player
        MusicManager.stop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (surfaceView.backPressed()) {

                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSignInFailed() {
        
        Log.v(Values.TAG, "failed");
    }

    public void gamesSignIn() {
        
        beginUserInitiatedSignIn();
    }
    
    @Override
    public void onSignInSucceeded() {
        
        Log.v(Values.TAG, "signed in");
    }
}
