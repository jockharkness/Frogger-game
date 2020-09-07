
import org.newdawn.slick.Input;

public class Bulldozer extends Sprite {
    private static final String ASSET_PATH = "assets/bulldozer.png";
    private static final float SPEED = 0.05f;




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


    public Bulldozer(float x, float y, boolean direction, String tags) {
        super(ASSET_PATH, x, y, direction, tags);
        setSPEED(SPEED);
    }

}
