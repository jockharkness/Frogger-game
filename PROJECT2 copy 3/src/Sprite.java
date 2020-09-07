import utilities.BoundingBox;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Sprite {
	// these are defined constants to avoid typos
	public final static String HAZARD = "hazard";
	public final static String NOTHAZARD = "notHazard";
	public final static String SOLID = "solid";
	public final static String MOVE = "move";
	public final static String BULLDOZER = "bulldozer";
	public final static String WATER = "water";
	public final static String PLAYER = "player";
	public final static String TURTLEUNDER = "turtleunder";
	public final static String TURTLE = "turtle";
	public final static String EXTRALIFE = "extralife";

	//Performs initialisation logic
	private BoundingBox bounds;
	private Image image;
	private float x;
	private float y;
	private boolean direction;
	private float SPEED;
	private int direction_int;
	private Sprite sprite;

	private String tags;

	//Sprite constructor for tiles
	public Sprite(String imageSrc, float x, float y, String tags) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;

		bounds = new BoundingBox(image, (int)x, (int)y);
		this.tags = tags;
	}

	//Sprite constructor for moving sprites
	public Sprite(String imageSrc, float x, float y, boolean direction, String tags) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		this.tags = tags;
		this.direction = direction;
		this.tags = tags;

		bounds = new BoundingBox(image, (int)x, (int)y);

	}




	/**
	 * Sets the x position of the sprite.
	 * @param x	 the target x position
	 */
	public final void setX(float x) {
		this.x = x;
		bounds.setX((int)x); }
	/**
	 * Sets the y position of the sprite.
	 * @param y	 the target y position
	 */
	public final void setY(float y) {
		this.y = y;
		bounds.setY((int)y); }
	/**
	 * Accesses the x position of the sprite.
	 * @return	the x position of the sprite
	 */
	public final float getX() {
		return x; }
	/**
	 * Accesses the y position of the sprite.
	 * @return	the y position of the sprite
	 */
	public final float getY() {
		return y; }


	/**
	 * Gets the speed pf the sprite.
	 * @return SPEED the speed of sprite
	 */
	public final float getSPEED(){
		return SPEED;

	/**
	 * Sets the SPEED of the sprite.
	 * @param SPEED	 the target speed of the sprite
	 */
	}
	public final void setSPEED(float SPEED){
		this.SPEED = SPEED;
	}

	/**
	 * Gets the direction of the sprite.
	 * @return direction in an integer form
	 */
	public final int getDirection() {
		if (direction) {
			direction_int = 1;
		} else {
			direction_int = -1;
		}
		return direction_int;
	}

	/**
	 * Gets the the sprite.
	 * @return sprite, the sprite object
	 */
	public final Sprite getSprite() {
		return sprite;
	}

	//moves the sprite
	public final void move(float dx, float dy) {
		setX(x + dx);
		setY(y + dy);
	}
	//handles the resetting of the frog when a life is lost
	public void loseLifeReset(){
		setX(App.SCREEN_WIDTH / 2);
		setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
	}

	// returns whether or not a sprite is on the screen
	public final boolean onScreen(float x, float y) {
		return !(x + World.TILE_SIZE / 2 > App.SCREEN_WIDTH || x - World.TILE_SIZE / 2 < 0
			 || y + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT || y - World.TILE_SIZE / 2 < 0);
	}
	

	//detects when two sprites collide
	public final boolean collides(Sprite other) {
		return bounds.intersects(other.bounds);
	}

	//updates the sprite
	public void update(Input input, int delta) { }

	//directs to the series of outcomes when a collision takes place
	public void onCollision(Sprite other, int delta) { }

	//draws the sprite on the screen
	public void render() {
		image.drawCentered(this.getX(), this.getY());

	}

	/**
	 * Gets the sprites tags.
	 * @return tags
	 */
	public String getTags(){
	    return tags;
    }

	/**
	 * Sets the tags of the sprite.
	 * @param tags	 the target tags of the sprite
	 */
    public void setTags(String tags){
		this.tags = tags;
	}



}
