
import org.newdawn.slick.Input;

public class Longlog extends Sprite {
    /**Performs initialisation logic*/
    private static final String ASSET_PATH = "assets/longlog.png";
    private static final float SPEED = 0.07f;
    private static final float LOG_SIZE = 228;



    /** Updates the Sprite and is responsible for moving
     * it and keeping it on the screen
     * @param input
     * @param delta
     */
    @Override
    public void update(Input input, int delta) {

        move(getSPEED() * delta * getDirection(), 0);

        // check if the vehicle has moved off the screen
        if (getDirection() == 1 && getX() > App.SCREEN_WIDTH + LOG_SIZE / 2 ) {
            setX(-LOG_SIZE / 2);
        }
        if (getDirection() == -1 && getX() < -LOG_SIZE / 2){
            setX(App.SCREEN_WIDTH + LOG_SIZE / 2);
        }
    }



    public Longlog(float x, float y, boolean direction, String tags) {
        super(ASSET_PATH, x, y, direction, tags);
        setSPEED(SPEED);
    }

}
