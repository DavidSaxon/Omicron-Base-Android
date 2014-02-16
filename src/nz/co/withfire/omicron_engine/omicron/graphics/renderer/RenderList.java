/*****************************************\
| Contains all components to be rendered. |
|                                          |
| @author David Saxon                      |
\*****************************************/

package nz.co.withfire.omicron_engine.omicron.graphics.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import nz.co.withfire.omicron_engine.omicron.graphics.lighting.DirectionalLight;
import nz.co.withfire.omicron_engine.omicron.graphics.lighting.Light;
import nz.co.withfire.omicron_engine.omicron.graphics.lighting.PointLight;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Batch;
import nz.co.withfire.omicron_engine.omicron.graphics.renderable.Renderable;
import nz.co.withfire.omicron_engine.override.Values;

public class RenderList {

    //VARIABLES    
    //the list of renderable layers
    private TreeMap<Integer, ArrayList<Renderable>> renderables =
        new TreeMap<Integer, ArrayList<Renderable>>();
    //the list of GUI layers
    private TreeMap<Integer, ArrayList<Renderable>> GUI =
        new TreeMap<Integer, ArrayList<Renderable>>();
    //the batches
    private TreeMap<Integer, ArrayList<Batch>> batches =
        new TreeMap<Integer, ArrayList<Batch>>();

    //the number of point lights currently being rendered
    private int pointCount = 0;
    //the number of directional lights currently being rendered
    private int dirCount = 0;
    //the list of point lights
    private List<PointLight> pointLights = new ArrayList<PointLight>();
    //the list of directional lights
    private List<DirectionalLight> dirLights =
        new ArrayList<DirectionalLight>();

    //CONSTRUCTOR
    /**Creates a new render list*/
    public RenderList() {
    }

    //PUBLIC METHODS
    /**Renders the non gui elements of the list
    @param viewMatrix the view matrix
    @param projectionMatrix the projection matrix*/
    public void renderStd(float viewMatrix[], float projectionMatrix[]) {

        //iterate over all the renderables and render
        for (int i = 0; i < 15; ++i) {
            
            if (renderables.containsKey(i)) {
                
                if (!renderables.get(i).isEmpty()) {
                
                    //sort
                    Collections.sort(renderables.get(i));
                    
                    //render
                    for (Renderable r : renderables.get(i)) {
        
                        r.render(viewMatrix, projectionMatrix);
                    }
                }
            }
            if (batches.containsKey(i)) {
                
                for (Batch b : batches.get(i)) {
                    
                    b.render(viewMatrix, projectionMatrix);
                }
            }
        }
        
//        for (List<Renderable> l : renderables.entrySet()) {
//            
//            for (Renderable r : l) {
//
//                r.render(viewMatrix, projectionMatrix);
//            }
//        }
//        
//        //render the batch
//        for (Batch b : batches) {
//            
//            b.render(viewMatrix, projectionMatrix);
//        }
    }

    /**Renders the gui elements of the list
    @param viewMatrix the view matrix
    @param projectionMatrix the projection matrix*/
    public void renderGUI(float viewMatrix[], float projectionMatrix[]) {

        //iterate over all the renderables and render
        for (List<Renderable> l : GUI.values()) {
            for (Renderable r : l) {

                r.render(viewMatrix, projectionMatrix);
            }
        }
    }

    /**Adds a new renderable to the list
    @param renderable the new renderable to add*/
    public void add(Renderable renderable) {

        //find the type
        switch (renderable.getGroup()) {

            //add to standard
            case STD: {

                add(renderable, renderables);
                break;
            }
            //add to gui
            case GUI: {

                add(renderable, GUI);
                break;
            }
        }
    }

    public void add(int layer, Batch batch) {
        
        if (!batches.containsKey(layer)) {
            
            batches.put(layer, new ArrayList<Batch>());
        }
        
        batches.get(layer).add(batch);
    }
    
    /**Adds a new light
    #WARNING: the light will be ignored if it exceeds the maximum
    amount for its light type
    @param light the new light*/
    public void add(Light light) {

        //check type of light
        if (light instanceof PointLight) {

            //if there are already maximum point lights then ignore
            if (pointCount >= Values.MAX_POINT_LIGHTS) {

                return;
            }

            //increase count and add
            ++pointCount;
            pointLights.add((PointLight) light);
        }
        else if (light instanceof DirectionalLight) {
            
            //if there are already maximum directional lights then ignore
            if (dirCount >= Values.MAX_DIR_LIGHTS) {

                return;
            }
            
            //increase count and add
            ++dirCount;
            dirLights.add((DirectionalLight) light);
        }
    }

    /**Removes a renderable from the list
    @param renderable the renderable to remove*/
    public void remove(Renderable renderable) {

        //find the type
        switch (renderable.getGroup()) {

            //remove from standard
            case STD: {

                remove(renderable, renderables);
                break;
            }
            //remove from gui
            case GUI: {

                remove(renderable, GUI);
                break;
            }
        }
    }

   public void remove(int layer, Batch batch) {
        
       batches.get(layer).remove(batch);
   }
    
    /**Removes the light from the list
    @param light the light to remove*/
    public void remove(Light light) {

        //check type of light
        if (light instanceof PointLight) {

            //first make sure the light is in the render list
            if (!pointLights.contains((PointLight) light)) {

                return;
            }

            //decrease count and remove
            --pointCount;
            pointLights.remove((PointLight) light);
        }
        else if (light instanceof DirectionalLight) {
            
            //first make sure the light is in the render list
            if (!dirLights.contains((DirectionalLight) light)) {

                return;
            }
            
            //decrease count and remove
            --dirCount;
            dirLights.remove((DirectionalLight) light);
        }
    }

    /**Clears the lights from the renderer*/
    public void clearLights() {
        
        pointLights.clear();
        dirLights.clear();
        pointCount = 0;
        dirCount = 0;
    }
    
    //GETTERS
    /**@return the number of point lights currently in the lists*/
    public int getPointCount() {

        return pointCount;
    }

    /**@return the list of point lights*/
    public List<PointLight> getPointLights() {

        return pointLights;
    }

    /**@return the list of directional lights*/
    public List<DirectionalLight> getDirLights() {
        
        return dirLights;
    }
    
    //PRIVATE METHODS
    /**Adds the renderable to the given list
    @param renderable the renderable to add
    @param list the list to add to*/
    private void add(Renderable renderable,
        TreeMap<Integer, ArrayList<Renderable>> list) {

        //check if the layer needs adding
        Integer layer = renderable.getLayer();
        if (!list.containsKey(layer)) {

            list.put(layer, new ArrayList<Renderable>());
        }

        //add
        list.get(layer).add(renderable);
    }

    /**Removes the renderable from the given list
    @param renderable the renderable to remove
    @param list the list to remove from*/
    private void remove(Renderable renderable,
        TreeMap<Integer, ArrayList<Renderable>> list) {

        //get the layer to remove from
        Integer layer = renderable.getLayer();
        list.get(layer).remove(renderable);

        //remove the list if it's now empty
        if (list.get(layer).isEmpty()) {

            list.remove(layer);
        }
    }
}
