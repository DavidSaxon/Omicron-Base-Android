package nz.co.withfire.omicronengine;

import nz.co.withfire.omicronengine.omicron.android.OmicronSurfaceView;
import nz.co.withfire.omicronengine.omicron.sound.MusicManager;
import nz.co.withfire.omicronengine.override.Values;
import nz.co.withfire.omicronengine.scenes.StartUpScene;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class OmicronActivity extends Activity {

    //VARIABLES
    //the context
    public static Context context;
    //the surface view
    private OmicronSurfaceView surfaceView;

    //METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //super call
        super.onCreate(savedInstanceState);

        //set the context
        context = this;

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
}
