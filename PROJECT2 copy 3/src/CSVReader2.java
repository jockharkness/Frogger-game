import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class CSVReader2 {

    public ArrayList<Sprite> spritesii = new ArrayList<>();

    public CSVReader2(String filename) {
        spritesii = readSpritesFromCSV(filename);
    }

    //scans every line of the csv file and adds the sprites to an arrayList
    public ArrayList<Sprite> readSpritesFromCSV(String fileName) {
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] line_split = line.split(",");
                Sprite sprite = createSprite(line_split);
                spritesii.add(sprite);
                line = br.readLine();
            }
            spritesii.add(new Player(App.SCREEN_WIDTH / 2, App.SCREEN_HEIGHT - World.TILE_SIZE,  Sprite.PLAYER));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return spritesii;


    }


    // creates a the itemised sprite objects from the CSV file
    public Sprite createSprite(String[] metadata) {
        String type;
        String tags;
        float x;
        float y;
        boolean direction;
        if (metadata.length == 4) {
            type = metadata[0];
            x = Integer.parseInt(metadata[1]);
            y = Integer.parseInt(metadata[2]);
            direction = Boolean.parseBoolean(metadata[3]);

            if (type.equals("bus")) {
                return new Bus(x, y, direction, Sprite.HAZARD);
            }
            else if (type.equals("racecar")) {
                return new Racecar(x, y, direction, Sprite.HAZARD);
            }
            else if (type.equals("bike")) {
                return new Bike(x, y, direction, Sprite.HAZARD);
            }
            else if (type.equals("bulldozer")) {
                return new Bulldozer(x, y, direction, Sprite.BULLDOZER);
            }
            else if (type.equals("log")) {
                return new Log(x, y, direction, Sprite.MOVE);
            }
            else if (type.equals("longLog")) {
                return new Longlog(x, y, direction, Sprite.MOVE);
            }
            else if (type.equals("turtle")) {
                return new Turtles(x, y, direction, Sprite.TURTLE);
            }
            else {
                return new Sprite("assets/" + type + ".png", x, y, direction, Sprite.NOTHAZARD);
            }

        }
        else {
            type = metadata[0];
            x = Integer.parseInt(metadata[1]);
            y = Integer.parseInt(metadata[2]);

            if (type.equals("grass")) {
                return new Tile(Tile.GRASS_PATH, x, y, Sprite.NOTHAZARD);
            }
            else if (type.equals("tree")) {
                return new Tile(Tile.TREE_PATH, x, y, Sprite.SOLID);
            }
            else {
                return new Tile(Tile.WATER_PATH, x, y, Sprite.WATER);
            }
        }


    }
}





