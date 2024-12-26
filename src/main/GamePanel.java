package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16; //16X16 single tile at design
    final int scale = 3;
    final static int FPS = 60;

    public final int tileSize = originalTileSize * scale; //render 48*48 tile

    //viewport/camera settings
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //width 48*16 = 768
    public final int screenHeight = tileSize * maxScreenRow; //height 48*12 = 576

    //world settings
    public final int maxWorldCol = 49;
    public final int maxWorldRow = 49;

    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    KeyHandler kh = new KeyHandler();
    public Player player = new Player(kh, this);
    TileManager tileManager = new TileManager(this);
    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.addKeyListener(kh);
        this.setFocusable(true);

    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        tileManager.draw(g2d);
        player.draw(g2d);

        g2d.dispose();
    }

    @Override
    public void run() {

        double timePerSingleFrame = 1000000000 / FPS; //0.01666 Secs
        double delta = 0;

        long lastUpdateTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastUpdateTime) / timePerSingleFrame; //has a frame passed?

            timer += (currentTime - lastUpdateTime);

            lastUpdateTime = currentTime;

            if (delta >= 1) { //one frame’s worth of time has passed.
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }


}
