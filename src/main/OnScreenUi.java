package main;

import game_object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class OnScreenUi {

    GamePanel gamePanel;
    Font arial40 = new Font("Arial", Font.PLAIN, 40);
    Font arial80b = new Font("Arial", Font.BOLD, 80);
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean isGameFinished = false;

    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public OnScreenUi(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.keyImage = new Key().image;
    }

    public void showMessage(String txt) {
        message = txt;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {
        if (isGameFinished) {
            int x;
            int y;

            graphics2D.setFont(arial40);
            graphics2D.setColor(Color.WHITE);

            String text = "You found the treasure!";
            int textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.screenWidth / 2 - textLength / 2;
            y = gamePanel.screenHeight / 2 - (gamePanel.TILE_SIZE * 3);
            graphics2D.drawString(text, x, y);

            text = "Your time is : " + decimalFormat.format(playTime) + "!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.screenWidth / 2 - textLength / 2;
            y = gamePanel.screenHeight / 2 + (gamePanel.TILE_SIZE * 4);
            graphics2D.drawString(text, x, y);

            graphics2D.setFont(arial80b);
            graphics2D.setColor(Color.YELLOW);

            text = "Congratulations!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.screenWidth / 2 - textLength / 2;
            y = gamePanel.screenHeight / 2 + (gamePanel.TILE_SIZE * 2);
            graphics2D.drawString(text, x, y);


            //kill the game
            gamePanel.gameThread = null;

        } else {
            graphics2D.setFont(arial40);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawImage(keyImage, gamePanel.TILE_SIZE / 2, gamePanel.TILE_SIZE / 2, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
            graphics2D.drawString("x " + gamePanel.player.keyCount, 74, 65);

            //play time
            playTime += (double) 1 / 60;
            graphics2D.drawString("Time:" + decimalFormat.format(playTime), gamePanel.TILE_SIZE * 11, 65);

            if (messageOn) {
                graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
                graphics2D.drawString(message, gamePanel.TILE_SIZE / 2, gamePanel.TILE_SIZE * 5);

                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
