package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Noppal extends Entity {
    GamePanel gp;

    public OBJ_Noppal(GamePanel gp) {

        super(gp);

        name = "Noppal";
        down1 = setup("./src/objects/noppal");

    }
}
