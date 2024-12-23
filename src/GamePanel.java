import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    final int originalTileSize = 16; //16X16 single tile at design
    final int scale = 3;
    final int tileSize = originalTileSize * scale; //render 48*48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //width 48*16 = 768
    final int screenHeight = tileSize * maxScreenRow; //height 48*12 = 576

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }
}
