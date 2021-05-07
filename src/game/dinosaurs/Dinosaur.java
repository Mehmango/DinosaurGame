package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Player;
import game.actions.FeedAction;
import game.actions.LayEggAction;
import game.behaviours.Behaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Bush;
import game.items.Corpse;
import game.items.Egg;
import game.items.Fruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An abstract class to represent a dinosaur
 */
public abstract class Dinosaur extends Actor {


    private static final int STARTING_HITPOINTS = 10;
    private static final int MAX_HITPOINTS = 100;
    protected static final int WANDER_BEHAVIOUR = 0;
    protected static final Class<?>[] FOOD = {};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>();

    /**
     * gender
     */
    protected List<Behaviour> actionFactories = new ArrayList<Behaviour>();

    protected DinosaurEnumType dinoType;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.maxHitPoints = MAX_HITPOINTS;
        setBehaviours();
    }

    /**
     * Gets an array of classes of food the dinosaur eats
     * @return  An array of classes of food the dinosaur eats
     */
    public static Class<?>[] getFOOD() {
        return FOOD;
    }

    /**
     * Gets a HashMap of Grounds the dinosaur eats from together with the foods they eat from them
     * @return
     */
    public static HashMap<Class<?>, Class<?>[]> getFROM_THESE_EATS_THESE() {
        return FROM_THESE_EATS_THESE;
    }

    /**
     * sets dinosaur behaviours
     */
    protected void setBehaviours() {
        actionFactories.add(new WanderBehaviour());
    }


    /**
     * Checks if the Dinosaur is dead, and places a dinosaur corpse on of the right type at its location in its place if it is
     *
     * @param map the GameMap the dinosaur is in
     * @see Corpse
     */
    public void checkDead(GameMap map) {
        if (hitPoints <= 0 && dinoType != null) {
            map.locationOf(this).addItem(new Corpse(dinoType));
            map.removeActor(this);
        }
    }

    /**
     * Used to let the dinosaur eat a quantity of a food Item. Adjusts hitpoints according to the food provided
     *
     * @param food     The Item eaten
     * @param quantity The quantity of the food eaten
     */
    public abstract void eat(Item food, int quantity);

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        if (otherActor instanceof Player) {
            for (Item item : otherActor.getInventory()) {
                for (Class<?> food : FOOD) {
                    if (item.getClass() == food) {
                        return (new Actions(new FeedAction(this, item, 1)));
                    }
                }
            }
        }
        return super.getAllowableActions(otherActor, direction, map);
    }

    /**
     * abstract method to be implemented
     * determines the highest priority behaviour based on probability
     *
     * @param map gamemap the actor is on
     * @return action to be performed this playturn
     */
    protected abstract Action determineBehaviour(GameMap map);

    /**
     * gets the required behaviour from actionfactories and returns it's getAction
     *
     * @param clazz class of the Behaviour
     * @param map gamemap the actor is on
     * @return Action performed by the behaviour
     */
    protected Action getBehaviourAction(Class<?> clazz, GameMap map) {
        Action action = null;
        for (Behaviour behaviour : actionFactories) {
            if (behaviour.getClass() == clazz) {
                action = actionFactories.get(actionFactories.indexOf(behaviour)).getAction(this, map);
                break;
            }
        }
        return action;
    }

    /**
     * Inform a Dino the passage of time.
     * This method is called once per turn
     *
     * @param map the map the actor is in
     * @return an action if applicable
     */
    protected Action tick(GameMap map) {
        hitPoints -= 1;
        return null;
    }


    /**
     * Select and return an action to perform on the current turn.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        checkDead(map);
        Action action = tick(map);
        if (action != null) {
            return action;
        } else {
            return null;
        }
    }

}
