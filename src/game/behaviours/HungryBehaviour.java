package game.behaviours;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

public class HungryBehaviour implements Behaviour {
    private Class<?> foodClass;
//    private int hungryHitPoints;

    public HungryBehaviour(Class<?> foodClass) {
        this.foodClass = foodClass;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {

        Location here = map.locationOf(actor);
        Location there = getFoodLocation(here, map, foodClass);

        if (there == null){
            return null;
        }

        int currentDistance = getDistance(here, there);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = getDistance(destination, there);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }

        return null;


    }

    public Location getFoodLocation(Location here, GameMap map, Class<?> foodClass) {

        List<Location> locationsWithFood = new ArrayList<>();

        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                List<Item> items = map.at(x, y).getItems();
                if (items != null) {
                    for (Item item : items) {
                        //TODO test if below if statement works
                        if (item.getClass() == foodClass){
                            locationsWithFood.add(map.at(x,y));
                        }
                    }
                }
            }
        }

        //compare distance of each location to actor
        int distance = 0;
        Location closestFoodLocation = null;
        for (Location location : locationsWithFood) {
            int newDistance = getDistance(location, here);
            if (distance > newDistance) {
                distance = newDistance;
                closestFoodLocation = location;
            }
        }

        return closestFoodLocation;
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int getDistance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}