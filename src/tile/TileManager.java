package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;

    Tile[] tiles;

    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow]; //[16][12]

        getTileImage();
        loadMap("/maps/maps_01.txt");
    }

    private void getTileImage() {
        try {

            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D graphics2D) {
        int col = 0;
        int row = 0;

        int x = 0;
        int y = 0;

        while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {

            int tileNum = mapTileNum[col][row];

            graphics2D.drawImage(tiles[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);

            col++;
            x += gamePanel.tileSize;

            if (col == gamePanel.maxScreenCol) {
                x = 0;
                y += gamePanel.tileSize;
                col = 0;
                row++;
            }

        }
    }

    public void loadMap(String mapURI) {
        try (InputStream inputStream = getClass().getResourceAsStream(mapURI); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            for (int row = 0; row < gamePanel.maxScreenRow; row++) {
                String line = bufferedReader.readLine();

                if (line == null || line.length() < gamePanel.maxScreenCol) {
                    throw new IOException("Invalid map file: insufficient rows or columns");
                }

                for (int col = 0; col < gamePanel.maxScreenCol; col++) {
                    // Convert character to integer
                    int num = Character.getNumericValue(line.charAt(col));

                    if (num < 0 || num > 9) {
                        throw new IOException("Invalid character in map file: " + line.charAt(col));
                    }

                    mapTileNum[col][row] = num;
                }
            }

        } catch (Exception e) {
            System.err.println("Error loading map: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
