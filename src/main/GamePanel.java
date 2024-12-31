package main;

import entity.Player;
import game_object.GameObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int ORIGINAL_TILE_SIZE = 16; //16X16 single tile at design
    final int SCALE = 3;
    final static int FPS = 60;

    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //render 48*48 tile

    //viewport/camera settings
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = TILE_SIZE * maxScreenCol; //width 48*16 = 768
    public final int screenHeight = TILE_SIZE * maxScreenRow; //height 48*12 = 576

    //world settings
    public final int maxWorldCol = 49;
    public final int maxWorldRow = 49;

    public final int worldWidth = TILE_SIZE * maxWorldCol;
    public final int worldHeight = TILE_SIZE * maxWorldRow;

    public GameObject[] gameObjects = new GameObject[10];


    KeyHandler kh = new KeyHandler();
    public Player player = new Player(kh, this);
    TileManager tileManager = new TileManager(this);
    Sound gameMusic = new Sound();
    Sound gameSoundEffects = new Sound();

    Thread gameThread;

    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.addKeyListener(kh);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setDefaultGameObject();
        playMusic(0);
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void playMusic(int musicFileIndex) {
        gameMusic.setFile(musicFileIndex);
        gameMusic.play();
        gameMusic.loop();
    }

    public void stopMusic() {
        gameMusic.stop();
    }

    public void playSoundEffect(int soundFileIndex) {
        gameSoundEffects.setFile(soundFileIndex);
        gameSoundEffects.play();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //tiles
        tileManager.draw(g2d);

        //game objects
        for (int i = 0; i < gameObjects.length; i++) {
            if (gameObjects[i] != null) {
                gameObjects[i].draw(g2d, this);
            }
        }

        //player
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
