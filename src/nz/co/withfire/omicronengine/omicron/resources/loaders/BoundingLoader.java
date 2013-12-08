/********************************\
| Methods for loading boundings. |
|                                |
| @author David Saxon            |
\********************************/

package nz.co.withfire.omicronengine.omicron.resources.loaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nz.co.withfire.omicronengine.omicron.physics.bounding.Bounding;
import nz.co.withfire.omicronengine.omicron.physics.bounding.BoundingRect;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector2;
import nz.co.withfire.omicronengine.omicron.utilities.vector.Vector3;

import android.content.Context;

public class BoundingLoader {

    /**Loads a bounding from a bd file
    @param context the android context
    @param resourceId the resource ID of the bd file*/
    static public List<Bounding> loadBounding(
        final Context context, int resourceId) {
        
        //the bounding box to return
        Bounding bounding = null;
        
        //open the file as a string
        String file = StringLoader.loadString(context, resourceId);
        
        //load the file into a scanner
        Scanner scanner = new Scanner(file);
        
        //read the first line to get the type of bounding box
        String type = scanner.nextLine();
        
        //load a rectangle
        if (type.equals("#RECT")) {
            
            //get the x and y dimensions of the rectangle
            float dimX = Float.parseFloat(scanner.next());
            float dimY = Float.parseFloat(scanner.next());
            float offX =  Float.parseFloat(scanner.next());
            float offY =  Float.parseFloat(scanner.next());
            float offZ =  Float.parseFloat(scanner.next());
            
            //create the rectangle
            bounding = new BoundingRect(new Vector2(dimX, dimY),
                new Vector3(offX, offY, offZ));
        }
        
        //TODO: fix
        //create list
        List<Bounding> boundingsList = new ArrayList<Bounding>();
        boundingsList.add(bounding);
        
        return boundingsList;
    }
}
