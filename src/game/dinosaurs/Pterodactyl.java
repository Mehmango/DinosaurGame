package game.dinosaurs;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class that represents an adult Pterodactyl
 */
public class Pterodactyl extends AdultDino{



    @Override
    public void resetPregnantTick() {

    }

    @Override
    public void eat(Item food, int quantity) {

    }

    @Override
    public void drink(int sips) {

    }

    @Override
    protected Action determineBehaviour(GameMap map) {
        return null;
    }
}
