
import org.newdawn.slick.Input;

public class Turtles extends Sprite {
    private static final String ASSET_PATH = "assets/turtles.png";
    private static final float TURTLES_SIZE = 144;
    private static final float SPEED = 0.085f;




    @Override
    public void update(Input input, int delta) {

        move(getSPEED() * delta * getDirection(), 0);

        // check if the vehicle has moved off the screen
        if (getDirection() == 1 && getX() > App.SCREEN_WIDTH + TURTLES_SIZE / 2 ) {
            setX(-TURTLES_SIZE / 2);
        }
        if (getDirection() == -1 && getX() < -TURTLES_SIZE / 2){
            setX(App.SCREEN_WIDTH + TURTLES_SIZE / 2);
        }
    }


    public Turtles(float x, float y, boolean direction, String tags) {
        super(ASSET_PATH, x, y, direction, tags);
        setSPEED(SPEED);
    }

}
