
import org.newdawn.slick.Input;

public class Bike extends Sprite {
    private static final String ASSET_PATH = "assets/bike.png";
    private static final float SPEED = 0.2f;
    private static final int LEFT_BOUND = 24;
    private static final int RIGHT_BOUND = 1000;
    private int direction_internal = getDirection();





    @Override
    public void update(Input input, int delta) {

        move(SPEED * delta * direction_internal, 0);
        // reverse direction when bounds reached

        //Note: the +/-2 pixels are to avoid a bug where the bike gets trapped on the side of the screen
        if (getX() > RIGHT_BOUND){
            direction_internal = -direction_internal;
                setX(RIGHT_BOUND -2);
        }
        if (getX() < LEFT_BOUND){
            direction_internal = -direction_internal;
                setX(LEFT_BOUND + 2);
        }

    }


    public Bike(float x, float y, boolean direction, String tags) {
        super(ASSET_PATH, x, y, direction, tags);
        setSPEED(SPEED);
    }

}
