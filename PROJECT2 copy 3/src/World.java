import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class World {
    public static final float TILE_SIZE = 48;
    public ArrayList<Sprite> sprites = new ArrayList<>();
    public ArrayList<Sprite> waters = new ArrayList<>();
    public static ArrayList<Sprite> trees = new ArrayList<>();
    public ArrayList<Sprite> movers = new ArrayList<>();
    public static ArrayList<Sprite> lifeList = new ArrayList<>();
    public static ArrayList<Sprite> dozers = new ArrayList<>();
    public static ArrayList<Sprite> logs = new ArrayList<>();
    public static  ArrayList<Sprite> extralives = new ArrayList<>();
    private Sprite player;
    private Sprite extralife;
    private int direction;
    private float speed;
    private boolean onLog = false;
    private float turtleTimer = 0;
    private final float  turtleTimerReappear = 9000;
    private final float turtleTimerDisappear = 7000;
    public static int numLogs;
    public static int randomLogSelection;
    public static Sprite randomLog;



    public World() {


        // create vehicles


        if (Player.allHolesFilled()) {
            CSVReader2 csvReader = new CSVReader2("assets/levels/1.lvl");
            this.sprites = csvReader.spritesii;
            movers.clear();
            logs.clear();
            player = sprites.get(sprites.size() - 1);
            for (Sprite sprite : sprites) {
                if (sprite.getTags().equals(Sprite.MOVE)) {
                    logs.add(sprite);
                }
                 else if (sprite.getTags().equals(Sprite.MOVE) || sprite.getTags().equals(Sprite.TURTLE)) {
                    movers.add(sprite);
                } else if (sprite.getTags().equals(Sprite.WATER)) {
                    waters.add(sprite);
                } else if (sprite.getTags().equals(Sprite.SOLID)) {
                    trees.add(sprite);
                } else if (sprite.getTags().equals(Sprite.BULLDOZER)) {
                    dozers.add(sprite);
                }

            }
            extralives.clear();
            Player.holesFilled.clear();

        } else {
            CSVReader2 csvReader = new CSVReader2("assets/levels/0.lvl");
            this.sprites = csvReader.spritesii;
            player = sprites.get(sprites.size() - 1);

            for (Sprite sprite : sprites) {
                if (sprite.getTags().equals(Sprite.MOVE)) {
                    movers.add(sprite);
                    logs.add(sprite);
                } else if (sprite.getTags().equals(Sprite.WATER)) {
                    waters.add(sprite);
                } else if (sprite.getTags().equals(Sprite.SOLID)) {
                    trees.add(sprite);
                } else if (sprite.getTags().equals(Sprite.BULLDOZER)) {
                    dozers.add(sprite);
                }


            }
            numLogs = World.logs.size();
            randomLogSelection = ThreadLocalRandom.current().nextInt(0, numLogs);
            randomLog = World.logs.get(randomLogSelection);
            System.out.println(logs.size());
        }



        Sprite life1 = new Lives(Lives.LIFEX, Lives.LIFEY, Sprite.NOTHAZARD);
        Sprite life2 = new Lives(Lives.LIFEX + Lives.LIFESPACING, Lives.LIFEY, Sprite.NOTHAZARD);
        Sprite life3 = new Lives(Lives.LIFEX + Lives.LIFESPACING + Lives.LIFESPACING, Lives.LIFEY, Sprite.NOTHAZARD);
        lifeList.clear();
        lifeList.add(life1);
        lifeList.add(life2);
        lifeList.add(life3);



    }


    public void update(Input input, int delta) {

        //setup turtle timer
        turtleTimer = (turtleTimer + delta);
        if (turtleTimer > turtleTimerReappear) {
            turtleTimer = 0;


        }



        //setup extralife timer


        // end extralife

        //start extra life


        //update all the lists
        for (Sprite sprite : sprites) {
            sprite.update(input, delta);
        }
        for (Sprite mover1 : movers) {
            mover1.update(input, delta);
        }
        for (Sprite water1 : waters) {
            water1.update(input, delta);
        }
        for (Sprite life2 : lifeList) {
            life2.update(input, delta);
        }
        player.update(input, delta);



        // exit game once completed
        if (App.level && Player.holesFilled.size() == 5) {
            System.exit(0);
        }


        // fetch attributes of the turtle/log the frog is on
        for (Sprite mover : movers) {

            if (mover.collides(player)) {
                speed = 2 * mover.getSPEED();
                direction = mover.getDirection();
                onLog = true;
                break;
            } else {
                onLog = false;
            }
        }


        // move the frog on the log
        if (onLog) {
            player.move((speed * delta * direction), 0);
            if (player.getX() < World.TILE_SIZE / 2) {
                player.setX(World.TILE_SIZE / 2);
            }
            if (player.getX() > App.SCREEN_WIDTH - World.TILE_SIZE / 2) {
                player.setX(App.SCREEN_WIDTH - World.TILE_SIZE / 2);
            }
            for (Sprite sprite4 : sprites) {
                if (player.collides(sprite4) && sprite4.getTags().equals(Sprite.TURTLEUNDER)) {
                    player.onCollision(sprite4, delta);

                }
            }
        }
        for (Sprite hole : Player.holesFilled) {
            if (player.collides(hole)) {
                player.onCollision(hole, delta);
            }

        }

        if (App.level == true && turtleTimer < turtleTimerDisappear) {

            for (Sprite spriteN : sprites) {

                if (spriteN.getTags().equals("turtleunder")) {
                    spriteN.setTags(Sprite.TURTLE);
                }
            }
        } else if (App.level == true && turtleTimer >= turtleTimerDisappear) {
            for (Sprite spritem : sprites) {
                if (spritem.getTags().equals("turtle")) {
                    spritem.setTags(Sprite.TURTLEUNDER);
                }

            }
        }


        for (Sprite life : extralives) {
            if (player.collides(life)){
                player.onCollision(life, delta);
                break;
            }
        }

        if (extralives.size() > 0) {
            //extralives.get(extralives.size() - 1).moveExtraLife();
        }
        if (!onLog) {
            for (Sprite water : waters) {
                if (water.collides(player)) {


                }
            }

            for (Sprite sprite2 : sprites) {
                if (player.collides(sprite2)) {
                    player.onCollision(sprite2, delta);

                }
            }
            for (Sprite sprite3 : sprites) {
                if (player.collides(sprite3) && sprite3.getTags().equals(Sprite.TURTLEUNDER)) {
                    player.onCollision(sprite3, delta);

                }
            }


            for (Sprite d : dozers) {
                if (player.collides(d)) {
                    player.onCollision(d, delta);

                }
            }
        }


    }


    public void render(Graphics g) {



        for (Sprite sprite : sprites) {
            if (!sprite.getTags().equals(Sprite.TURTLEUNDER)) {
                sprite.render();
            }


            for (Sprite life : lifeList) {
                life.render();
            }
            for (Sprite hole : Player.holesFilled) {
                hole.render();
            }


        }
        if (extralives.size() > 0){
            extralives.get(extralives.size() - 1).render();
        }

    }
}