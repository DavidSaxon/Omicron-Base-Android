/*****************************************\
| Contains all components to be rendered. |
|								          |
| @author David Saxon					  |
\*****************************************/

package nz.co.withfire.omicronengine.omicron.graphics.renderer;

import java.util.ArrayList;
import java.util.List;

import nz.co.withfire.omicronengine.omicron.graphics.renderable.Renderable;

public class RenderList {

	//VARIABLES
	//the list of renderable layers
	private List<List<Renderable>> renderables =
		new ArrayList<List<Renderable>>();
	//the list of GUI layers
	private List<List<Renderable>> GUI =
		new ArrayList<List<Renderable>>();
	
	
}
