import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Player extends Sprite {

	/** Performs initialisation logic **/
	private static final String ASSET_PATH = "assets/frog.png";
	public static int lives = 4;
	private int[] holeBounds = {96, 168, 264, 360, 456, 552, 648, 744, 840, 936};
	private int holeY = 48;
	public static ArrayList<Sprite> holesFilled = new ArrayList<>();
	public static boolean[] ifHoleFilled = {false, false, false, false, false};
	private float randomNum = ThreadLocalRandom.current().nextInt(25, 36000);;
	private final float extraLifeDuration = 14000;
	private float extraLifeTimer = 0;
	private float extraLifeTimer2 = 0;


	public Player(float x, float y, String tags) {

		super(ASSET_PATH, x, y, tags);

	}

	/** Handles the movmement of the frog
	 *
	 */

	@Override
	public void update(Input input, int delta) {
		int dx = 0,
				dy = 0;
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dx -= World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dx += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			dy += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			dy -= World.TILE_SIZE;
		}

		// make sure the frog stays on screen
		if (getX() + dx - World.TILE_SIZE / 2 < 0 || getX() + dx + World.TILE_SIZE / 2 > App.SCREEN_WIDTH) {
			dx = 0;
		}
		if (getY() + dy - World.TILE_SIZE / 2 < 0 || getY() + dy + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT) {
			dy = 0;
		}
		// make sure frog doesn't collide with solid objects

		move(dx, dy);
		for (Sprite t : World.trees) {
			if (collides(t)) {
				setX(getX() - dx);
				setY(getY() - dy);
			}
		}
		for (Sprite d : World.dozers) {
			if (collides(d)) {
				setX(getX() - dx);
				setY(getY() - dy);
			}
		}

		if (App.level && getLives() == -1) {
			System.exit(0);
		}
		else if (!App.level && getLives() == 0) {
			System.exit(0);
		}

		extraLifeTimer = (extraLifeTimer + delta);
		extraLifeTimer2 = (extraLifeTimer2 + delta);

		if (extraLifeTimer > randomNum + extraLifeDuration) {
			extraLifeTimer = 0;
			extraLifeTimer2 = 0;
			removeExtraLife();
			addExtraLife();
		}

		if (extraLifeTimer2 > randomNum){
			addExtraLife();
		}


	}
	/** Gets lives
	 *  @returns lives **/
	public int getLives() {
		return lives;
	}
	/** Sets lives
	 *  @params sets target lives **/
	public void setLives(int lives) {
		this.lives = lives;
	}
	/** Handles the filling of the holes **/
	private void holeOneFilled() {
		loseLifeReset();
		holesFilled.add(new Sprite(ASSET_PATH, holeBounds[1] - World.TILE_SIZE, holeY, Sprite.HAZARD));
		ifHoleFilled[0] = true;
	}

	private void holeTwoFilled() {
		loseLifeReset();
		holesFilled.add(new Sprite(ASSET_PATH, holeBounds[3] - World.TILE_SIZE, holeY, Sprite.HAZARD));
		ifHoleFilled[1] = true;
	}

	private void holeThreeFilled() {
		loseLifeReset();
		holesFilled.add(new Sprite(ASSET_PATH, holeBounds[5] - World.TILE_SIZE, holeY, Sprite.HAZARD));
		ifHoleFilled[2] = true;
	}

	private void holeFourFilled() {
		loseLifeReset();
		holesFilled.add(new Sprite(ASSET_PATH, holeBounds[7] - World.TILE_SIZE, holeY, Sprite.HAZARD));
		ifHoleFilled[3] = true;
	}

	private void holeFiveFilled() {
		loseLifeReset();
		holesFilled.add(new Sprite(ASSET_PATH, holeBounds[9] - World.TILE_SIZE, holeY, Sprite.HAZARD));
		ifHoleFilled[4] = true;
	}

	/** Assesses if all holes have been filled
	 *  @returns boolean **/
	public static boolean allHolesFilled() {
		if (ifHoleFilled[0] && ifHoleFilled[1] && ifHoleFilled[2] && ifHoleFilled[3] && ifHoleFilled[4]) {
			return true;
		} else {
			return false;
		}
	}
	/** Handles losing a life **/
	public void loseLife() {
		loseLifeReset();
		setLives(lives - 1);
		World.lifeList.remove(World.lifeList.size() - 1);
	}
	/** Handles adding a life **/
	public void addLife() {
		setLives(lives + 1);
		World.lifeList.add(new Sprite(Lives.ASSET_PATH, Lives.LIFESPACING*getLives(), Lives.LIFEY, Sprite.EXTRALIFE));
	}
	private Sprite randomLog(){
		World.numLogs = World.logs.size();
		World.randomLogSelection = ThreadLocalRandom.current().nextInt(0, World.numLogs);
		return World.randomLog = World.logs.get(World.randomLogSelection);
	}
	private void addExtraLife(){
		randomLog();
		World.extralives.add(new Extralife( randomLog().getX(), randomLog().getY(), Sprite.EXTRALIFE));
	}
	private void removeExtraLife(){
		World.extralives.remove(World.extralives.size() - 1);
	}
	public void moveExtraLife() {
			setX(randomLog().getX());
			setY(randomLog().getY());

	}


	/** Handles the collisions with different types of sprites **/
	@Override
	public void onCollision(Sprite other, int delta) {
			if (other.getTags().equals(Sprite.HAZARD)) {
				loseLife();
			} else if (other.getTags().equals(Sprite.BULLDOZER)) {
				setX(other.getX() + World.TILE_SIZE + 1);
				if (!onScreen(getX(), getY())) {
					loseLife();
				}
		    } else if (other.getTags().equals(Sprite.EXTRALIFE)) {
				addLife();
				removeExtraLife();
				addExtraLife();
				System.out.println("hello");
			} else if (other.getTags().equals(Sprite.WATER)) {
				loseLife();
			} else if (other.getTags().equals(Sprite.TURTLEUNDER)) {
				loseLife();
			} else if (other.getTags().equals(Sprite.SOLID)) {

			} else if (getY() == holeY && getX() > holeBounds[0] && getX() < holeBounds[1]) {
				holeOneFilled();
			} else if (getY() == holeY && getX() > holeBounds[2] && getX() < holeBounds[3]) {
				holeTwoFilled();
			} else if (getY() == holeY && getX() > holeBounds[4] && getX() < holeBounds[5]) {
				holeThreeFilled();
			} else if (getY() == holeY && getX() > holeBounds[6] && getX() < holeBounds[7]) {
				holeFourFilled();
			} else if (getY() == holeY && getX() > holeBounds[8] && getX() < holeBounds[9]) {
				holeFiveFilled();
			}
		}
	}
