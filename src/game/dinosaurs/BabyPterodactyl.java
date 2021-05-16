package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.grounds.Lake;
import game.items.CarnivoreMealKit;
import game.items.Corpse;
import game.items.Fish;

import java.util.HashMap;

/**
 * A baby Pterodactyl, a herbivorous dinosaur
 */
public class BabyPterodactyl extends BabyDino{

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.PTERODACTYL;
    private static final int STARTING_HITPOINTS = 30;
    private static final int MAX_HITPOINTS = 60;
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 100;
    private static final int THIRSTY_THRESHOLD = 50;
    private static final String NAME = "Pterodactyl";
    private static final char DISPLAY_CHAR = 'p';
    public static final int HUNGRY_THRESHOLD = 50;
    private static final int PTERODACTYL_GROW_UP_TICK = 20;
    private static final Class<?>[] FOOD = {Corpse.class, CarnivoreMealKit.class, Fish.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>() {{
        put(Ground.class, new Class[]{Corpse.class});
    }};
    private boolean flying;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public BabyPterodactyl(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD, FOOD,FROM_THESE_EATS_THESE , PTERODACTYL_GROW_UP_TICK);
    }


    /**
     * Constructor. Sets initial hitPoints to 20 and randomises gender
     */
    public BabyPterodactyl() {
        super(NAME, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD, FOOD,FROM_THESE_EATS_THESE , PTERODACTYL_GROW_UP_TICK);
    }

    @Override
    public void growUp(GameMap map) {
        Location actorLocation = map.locationOf(this);
        map.removeActor(this);
        map.addActor(new Pterodactyl(), actorLocation);
    }

    @Override
    public void eat(Item food, int quantity) {
        final int CORPSE_HEAL = 10;
        final int CARNIVORE_MEAL_KIT_HEAL = maxHitpoints;
        final int FISH_HEAL = 5;
        if (food.getClass() == Corpse.class){
            for (int i=0;i<quantity;i++){
                ((Corpse) food).eat(CORPSE_HEAL);
            }
        } else if (food.getClass() == CarnivoreMealKit.class) {
            for (int i = 0; i < quantity; i++) {
                heal(CARNIVORE_MEAL_KIT_HEAL);
            }
        } else if (food.getClass() == Fish.class){
            for (int i = 0; i < quantity; i++) {
                heal(FISH_HEAL);
            }
        }
    }

    @Override
    public void drink(int sips) {
        adjustWaterLevel(30);
    }

    @Override
    protected Action determineBehaviour(GameMap map) {
        return null;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (map.locationOf(this).getGround() instanceof Lake && hitPoints<maxHitpoints){
            eat(new Fish(),((Lake) map.locationOf(this).getGround()).eatFish((int) (Math.round(Math.random()*3))));
        }
        return super.playTurn(actions, lastAction, map, display);
    }
}