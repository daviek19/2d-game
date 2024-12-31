package game_object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {

    public BufferedImage image;

    public String name;

    public boolean collision = false;

    public int worldX;

    public int worldY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;


    public void setImage(String URL) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(URL));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        //world -camera + viewport
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        //optimize the world around view port
        if (worldX + gamePanel.TILE_SIZE > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.TILE_SIZE < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.TILE_SIZE > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.TILE_SIZE < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            graphics2D.drawImage(image, screenX, screenY, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
        }

    }
}
