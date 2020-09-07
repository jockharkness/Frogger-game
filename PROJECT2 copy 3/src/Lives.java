
import org.newdawn.slick.Input;

import java.util.ArrayList;

public class Lives extends Sprite {
    /**Performs initialisation logic*/
    public static final String ASSET_PATH = "assets/lives.png";
    public static final int LIFEX = 24;
    public static final int LIFEY = 744;
    public static final int LIFESPACING = 32;



    public Lives(float x, float y, String tags) {
        super(ASSET_PATH, x, y, tags);

    }


}

