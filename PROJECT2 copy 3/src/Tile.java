public class Tile extends Sprite {
	//Performs initialisation logic
	public static final String GRASS_PATH = "assets/grass.png";
	public static final String WATER_PATH = "assets/water.png";
	public static final String TREE_PATH = "assets/tree.png";
	
	//constructor
	public Tile(String imageSrc, float x, float y, String tags) {

		super(imageSrc, x, y,  tags);
	}
}

