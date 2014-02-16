package nz.co.withfire.omicron_engine;

import nz.co.withfire.omicron_engine.R;
import nz.co.withfire.omicron_engine.omicron.android.OmicronSurfaceView;
import nz.co.withfire.omicron_engine.omicron.logic.scene.Scene;
import nz.co.withfire.omicron_engine.omicron.sound.MusicManager;
import nz.co.withfire.omicron_engine.omicron.utilities.ValuesUtil;
import nz.co.withfire.omicron_engine.scenes.StartUpScene;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class OmicronActivity extends Activity implements SensorEventListener {
    
    //VARIABLES
    //the context
    public static Context context;
    //The Activity
    public static OmicronActivity activity;
    //the surface view
    private OmicronSurfaceView surfaceView;
    
    //the sensor manager
    private SensorManager sensorManager;
    //the accelerometer sensor
    private Sensor accelerometer;
    
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

        //set up sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer =
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        //get the dpi
        ValuesUtil.dpi = getResources().getDisplayMetrics().density;
        
        //create an Omicron surface view
        surfaceView =
            new OmicronSurfaceView(this, new StartUpScene());

        frame.addView(surfaceView);
        
        //get fonts
        //TODO:
        
        //super call
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onResume() {
        
        //super call
        super.onResume();
        
        //register the sensors
        sensorManager.registerListener(this, accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL);
        
        //notify the scene
        Scene.notifyResume();
        
        //resume the music player
        MusicManager.resume();
    }
    
    @Override
    protected void onPause() {

        //super call
        super.onPause();

        //unregister sensors
        sensorManager.unregisterListener(this);
        
        //stop the music player
        MusicManager.pause();
    }

    @Override
    protected void onDestroy() {
        
        //super call
        super.onDestroy();
        
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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
        //DO NOTHING
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //pass on
        if (surfaceView != null) {
        
            surfaceView.onSensorChanged(event);
        }
    }
}
