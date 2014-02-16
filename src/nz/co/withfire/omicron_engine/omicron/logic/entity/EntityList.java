/******************************************\
| Stores and manages all current entities. |
|                                           |
| @author David Saxon                       |
\******************************************/

package nz.co.withfire.omicron_engine.omicron.logic.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityList {

    //VARIABLES
    //the list of all entities
    private List<Entity> entities = new ArrayList<Entity>();
    //the list of entities to add
    private List<Entity> addEntities =
        Collections.synchronizedList(new ArrayList<Entity>());
    //the list of entities to remove
    private List<Entity> removeEntities =
        Collections.synchronizedList(new ArrayList<Entity>());

    //the number of usable processors
        private int usableProcs;

    //CONSTRUCTOR
    /**Creates a new entity list*/
    public EntityList() {

        //get the number of usable processors
        usableProcs = Runtime.getRuntime().availableProcessors();
    }

    //PUBLIC METHODS
    /**Adds a new entity to the list
    @parm entity the entity to add to the list*/
    public void add(Entity entity) {

        entities.add(entity);
    }

    /**Removes the entity from the list
    @param entity the entity to remove from the list*/
    public void remove(Entity entity) {

        entity.cleanUp();
        entities.remove(entity);
    }

    /**@return the number of elements in the entity list*/
    public int size() {
        
        return entities.size();
    }
    
    /**Updates the entity list*/
    public void update() {

        //concurrently update the entities
        if (usableProcs > 1 && entities.size() > usableProcs) {

            //the list of threads we will be using
            List<Thread> threads = new ArrayList<Thread>();

            //iterate over the usable processors
            for (int i = 0; i < usableProcs; ++i) {

                //calculate the start and end indices for this thread
                int start = i * (entities.size() / usableProcs);
                int end = entities.size();

                if (i < usableProcs - 1) {

                    end = start + (entities.size() / usableProcs);
                }

                //spawn a new thread and start it
                Thread t = new UpdateThread(start, end);
                t.start();
                threads.add(t);
            }

            //wait for threads to join
            for (Thread t : threads) {

                try {

                    t.join();
                }
                catch (InterruptedException e) {

                    //this is bad mmk!
                    e.printStackTrace();
                }
            }
        }
        //sequentially update the entities
        else {

            for (Entity e : entities) {

                e.update();

                //check if we need to add any entities
                List<Entity> add = e.getAdd();
                if (add != null) {

                    for (Entity a : add) {

                        addEntities.add(a);
                    }
                }

                //check if we need to remove it
                if (e.shouldRemove()) {

                    removeEntities.add(e);
                }
            }
        }

        //remove entities
        for (Entity e : removeEntities) {

            e.cleanUp();
            entities.remove(e);
        }
        removeEntities.clear();

        //add entities
        for (Entity e : addEntities) {

            entities.add(e);
        }
        addEntities.clear();
    }

    /**Clears the entire entity list*/
    public void clear() {

        for (Entity e : entities) {

            e.cleanUp();
        }
        entities.clear();
    }

    //PRIVATE INNER CLASS
    private class UpdateThread extends Thread {

        //VARIABLES
        //the update start index
        private int start;
        //the update end index
        private int end;

        //CONSTRUCTOR
        /**Creates a new update thread
        @param start the start index
        @param end the end index*/
        public UpdateThread(int start, int end) {

            this.start = start;
            this.end = end;
        }

        //PUBLIC METHODS
        @Override
        public void run() {

            update();
        }

        /**Updates the entities between the given indices*/
        public void update() {

            for (int i = start; i < end; ++i) {

                entities.get(i).update();

                //check if we need to add any entities
                List<Entity> add = entities.get(i).getAdd();
                if (add != null) {

                    for (Entity a : add) {

                        addEntities.add(a);
                    }
                }

                //check if we need to remove it
                if (entities.get(i).shouldRemove()) {

                    removeEntities.add(entities.get(i));
                }
            }
        }
    }
}
