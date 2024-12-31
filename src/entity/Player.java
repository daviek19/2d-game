package entity;

import main.KeyHandler;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler kh;
    GamePanel gamePanel;

    public final int screenX;
    public final int screenY;

    public int keyCount = 0;

    public Player(KeyHandler keyHandler, GamePanel gamePanel) {
        this.kh = keyHandler;
        this.gamePanel = gamePanel;

        screenX = (gamePanel.screenWidth / 2) - (gamePanel.TILE_SIZE / 2);
        screenY = (gamePanel.screenHeight / 2) - (gamePanel.TILE_SIZE / 2);

        solidArea = new Rectangle(8, 16, 16, 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaults();
        getPlayerImage();
    }

    public void setDefaults() {
        //set a starting point in the game for the player
        worldX = gamePanel.TILE_SIZE * 23;
        worldY = gamePanel.TILE_SIZE * 21;

        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/boy/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/boy/boy_up_2.png"));

            down1 = ImageIO.read(getClass().getResourceAsStream("/boy/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/boy/boy_down_2.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/boy/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/boy/boy_left_2.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/boy/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/boy/boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        //only change the player sprite if a key is pressed
        if (kh.upPressed == true || kh.downPressed == true || kh.rightPressed == true || kh.leftPressed == true) {

            if (kh.upPressed == true) {
                direction = "up";
            } else if (kh.downPressed == true) {
                direction = "down";
            } else if (kh.leftPressed == true) {
                direction = "left";
            } else if (kh.rightPressed == true) {
                direction = "right";
            }

            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            //check objects collision
            int gameObjectIndex = gamePanel.collisionChecker.checkGameObject(this, true);
            pickupObject(gameObjectIndex);


            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickupObject(int gameObjectIndex) {
        if (gameObjectIndex != 999) {
            String objectName = gamePanel.gameObjects[gameObjectIndex].name;

            switch (objectName) {
                case "Key":
                    keyCount++;
                    gamePanel.playSoundEffect(1);
                    gamePanel.gameObjects[gameObjectIndex] = null;
                    gamePanel.onScreenUi.showMessage("You got a key!");
                    break;
                case "Door":
                    if (keyCount > 0) {
                        gamePanel.playSoundEffect(3);
                        gamePanel.gameObjects[gameObjectIndex] = null;
                        keyCount--;
                        gamePanel.onScreenUi.showMessage("You opened a door!");
                    } else {
                        gamePanel.onScreenUi.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    speed += 2;
                    gamePanel.playSoundEffect(2);
                    gamePanel.gameObjects[gameObjectIndex] = null;
                    gamePanel.onScreenUi.showMessage("Speed up!");
                    break;
                case "Chest":
                    gamePanel.onScreenUi.isGameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSoundEffect(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }

                if (spriteNum == 2) {
                    image = up2;
                }
                break;

            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }

                if (spriteNum == 2) {
                    image = down2;
                }
                break;

            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }

                if (spriteNum == 2) {
                    image = left2;
                }
                break;

            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }

                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        graphics2D.drawImage(image, screenX, screenY, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
    }


}
