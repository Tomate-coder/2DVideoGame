package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Skull extends Entity {
    GamePanel gp;

    public OBJ_Skull(GamePanel gp) {
        // this.gp = gp;
        super(gp);

        name = "Skul";
        image = setup("./src/objects/full_skull");
        image2 = setup("./src/objects/dead_skull");

    }
}