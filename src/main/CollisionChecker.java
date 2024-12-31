package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.width;

        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.TILE_SIZE;
        int entityRightCol = entityRightWorldX / gamePanel.TILE_SIZE;

        int entityTopRow = entityTopWorldY / gamePanel.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / gamePanel.TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision == true || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision == true || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision == true || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision == true || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkGameObject(Entity entity, boolean isPlayer) {

        int index = 999;

        for (int i = 0; i < gamePanel.gameObjects.length; i++) {

            if (gamePanel.gameObjects[i] != null) {

                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                gamePanel.gameObjects[i].solidArea.x = gamePanel.gameObjects[i].worldX + gamePanel.gameObjects[i].solidArea.x;
                gamePanel.gameObjects[i].solidArea.y = gamePanel.gameObjects[i].worldY + gamePanel.gameObjects[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            if (gamePanel.gameObjects[i].collision == true) {
                                entity.collisionOn = true;
                            }

                            if (isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            if (gamePanel.gameObjects[i].collision == true) {
                                entity.collisionOn = true;
                            }

                            if (isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            if (gamePanel.gameObjects[i].collision == true) {
                                entity.collisionOn = true;
                            }

                            if (isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            if (gamePanel.gameObjects[i].collision == true) {
                                entity.collisionOn = true;
                            }

                            if (isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.gameObjects[i].solidArea.x = gamePanel.gameObjects[i].solidAreaDefaultX;
                gamePanel.gameObjects[i].solidArea.y = gamePanel.gameObjects[i].solidAreaDefaultY;
            }

        }

        return index;
    }
}
