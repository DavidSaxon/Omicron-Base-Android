package nz.co.withfire.omicronengine;

import nz.co.withfire.omicronengine.omicron.android.OmicronSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//super call
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onResume() {
		
		//super call
		super.onResume();
		
        //set to full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        //set the display
        OmicronSurfaceView surfaceView = new OmicronSurfaceView(this);
        
        //set the content view to the display
        setContentView(surfaceView);
	}
}
