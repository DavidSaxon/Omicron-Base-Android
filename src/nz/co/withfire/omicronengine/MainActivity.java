package nz.co.withfire.omicronengine;

import nz.co.withfire.omicronengine.omicron.android.OmicronSurfaceView;
import nz.co.withfire.omicronengine.override.Values;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	//VARIABLES
	//the omicron surface view
	OmicronSurfaceView surfaceView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//super call
		super.onCreate(savedInstanceState);
		
		//set to full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //create an Omicron surface view
        surfaceView = new OmicronSurfaceView(this);
        setContentView(surfaceView);
	}
}
