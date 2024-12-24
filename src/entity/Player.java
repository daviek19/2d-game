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

    public Player(KeyHandler keyHandler, GamePanel gamePanel) {
        this.kh = keyHandler;
        this.gamePanel = gamePanel;

        setDefaults();
        getPlayerImage();
    }

    public void setDefaults() {
        x = 100;
        y = 100;
        speed = 4; //4px
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
                y -= speed;
            } else if (kh.downPressed == true) {
                direction = "down";
                y += speed;
            } else if (kh.leftPressed == true) {
                direction = "left";
                x -= speed;
            } else if (kh.rightPressed == true) {
                direction = "right";
                x += speed;
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

        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }


}
