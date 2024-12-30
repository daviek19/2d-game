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

    public Tile[] tiles;

    public int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow]; //[50][50]

        getTileImage();
        loadMap("/maps/world_01.txt");
    }

    private void getTileImage() {
        try {

            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tiles[4].collision = true;

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D graphics2D) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.TILE_SIZE;
            int worldY = worldRow * gamePanel.TILE_SIZE;

            //world -camera + viewport
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            //optimize the world around view port
            if (worldX + gamePanel.TILE_SIZE > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.TILE_SIZE < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.TILE_SIZE > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.TILE_SIZE < gamePanel.player.worldY + gamePanel.player.screenY
            ) {
                graphics2D.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
            }

            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }
    }

    public void loadMap(String mapURI) {
        try (
                InputStream inputStream = getClass().getResourceAsStream(mapURI);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ) {

            for (int row = 0; row < gamePanel.maxWorldCol; row++) {
                String line = bufferedReader.readLine();

                if (line == null || line.length() < gamePanel.maxWorldCol) {
                    throw new IOException("Invalid map file: insufficient rows or columns");
                }

                for (int col = 0; col < gamePanel.maxWorldCol; col++) {
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
