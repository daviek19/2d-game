package main;

import game_object.*;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setDefaultGameObject() {
        gamePanel.gameObjects[0] = new Key();
        gamePanel.gameObjects[0].worldX = 23 * gamePanel.TILE_SIZE;
        gamePanel.gameObjects[0].worldY = 7 * gamePanel.TILE_SIZE;

        gamePanel.gameObjects[1] = new Key();
        gamePanel.gameObjects[1].worldX = 23 * gamePanel.TILE_SIZE;
        gamePanel.gameObjects[1].worldY = 40 * gamePanel.TILE_SIZE;


        gamePanel.gameObjects[2] = new Key();
        gamePanel.gameObjects[2].worldX = 38 * gamePanel.TILE_SIZE;
        gamePanel.gameObjects[2].worldY = 7 * gamePanel.TILE_SIZE;

        gamePanel.gameObjects[3] = new Door();
        gamePanel.gameObjects[3].worldX = 10 * gamePanel.TILE_SIZE;
        gamePanel.gameObjects[3].worldY = 11 * gamePanel.TILE_SIZE;

        gamePanel.gameObjects[4] = new Door();
        gamePanel.gameObjects[4].worldX = 8 * gamePanel.TILE_SIZE;
        gamePanel.gameObjects[4].worldY = 28 * gamePanel.TILE_SIZE;

        gamePanel.gameObjects[5] = new Door();
        gamePanel.gameObjects[5].worldX = 12 * gamePanel.TILE_SIZE;
        gamePanel.gameObjects[5].worldY = 22 * gamePanel.TILE_SIZE;

        gamePanel.gameObjects[6] = new Chest();
        gamePanel.gameObjects[6].worldX = 10 * gamePanel.TILE_SIZE;
        gamePanel.gameObjects[6].worldY = 7 * gamePanel.TILE_SIZE;

        gamePanel.gameObjects[7] = new Boots();
        gamePanel.gameObjects[7].worldX = 37 * gamePanel.TILE_SIZE;
        gamePanel.gameObjects[7].worldY = 42 * gamePanel.TILE_SIZE;
    }
}
