package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import main.GamePanel;

import entity.Entity;

public class OBJ_InWall extends Entity {
    GamePanel gp;

    public OBJ_InWall(GamePanel gp) {
        super(gp);

        name = "InWall";
        collision = true;
    }

}
