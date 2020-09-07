
import org.newdawn.slick.Input;

public class Racecar extends Sprite {

    /**Performs initialisation logic*/
    private static final String ASSET_PATH = "assets/racecar.png";
    private static final float SPEED = 0.5f;
    private int direction;


    /** Updates the Sprite and is responsible for moving
     * it and keeping it on the screen
     * @param input
     * @param delta
     */
    @Override
    public void update(Input input, int delta) {
        move(getSPEED() * delta * getDirection(), 0);

        // check if the vehicle has moved off the screen
        if (getDirection() == 1 && getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 ) {
            setX(-World.TILE_SIZE  / 2);
        }
        if (getDirection() == -1 && getX() < -World.TILE_SIZE  / 2){
            setX(App.SCREEN_WIDTH + World.TILE_SIZE  / 2);
        }
    }



    public Racecar(float x, float y, boolean direction, String tags) {
        super(ASSET_PATH, x, y, direction, tags);
        setSPEED(SPEED);
    }

}
