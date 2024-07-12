package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

    GamePanel gp;

    public OBJ_Chest(GamePanel gp) {
        super(gp);

        name = "Chest";
        down1 = setup("./src/objects/chest");

        // try {
        // File f1 = new File("./src/objects/chest.png");
        // image = ImageIO.read(f1);

        // uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        collision = true;
    }
}
