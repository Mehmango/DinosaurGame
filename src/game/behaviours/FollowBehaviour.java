package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.actions.BreedAction;
import game.actions.FollowAction;
import game.dinosaurs.AdultDino;

import static game.Util.distance;

/**
 * A class that figures out a MoveAction that will move the actor one step
 * closer to a target Actor.
 */
public class FollowBehaviour implements Behaviour {

    /**
     * target actor to be followed
     */
    private Actor target;

    /**
     * Constructor.
     *
     * @param subject the Actor to follow
     */
    public FollowBehaviour(Actor subject) {
        this.target = subject;
    }

    /**
     * gets action for current behaviour
     *
     * @param actor the Actor following
     * @param map   the GameMap containing the Actor
     * @return returns the action for current behaviour
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(target) || !map.contains(actor))
            return null;

        Location here = map.locationOf(actor);
        Location there = map.locationOf(target);

        int currentDistance = distance(here, there);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, there);
                if (newDistance < currentDistance) {
                    Action breedAction = new BreedAction((AdultDino) target);
                    return new FollowAction(destination, exit.getName(), actor, target, breedAction, map);
                }
            }
        }

        return null;
    }


}