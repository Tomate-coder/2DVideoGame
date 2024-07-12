package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
public class MON_GreenSlime extends Entity{

    public MON_GreenSlime(GamePanel gp) {
        super (gp);
        name = "Green Slime";
        speed = 1;
        maxLife = 4;

        type = 2;
        solidArea.x= 3;
        solidArea.y= 18;
        solidArea.width= 42;
        solidArea.height= 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage() {
        move1 = setup("./src/monsters/sprite_0");
        move2 = setup("./src/monsters/sprite_1");
        move3 = setup("./src/monsters/sprite_2");
        move4 = setup("./src/monsters/sprite_3");
        move5 = setup("./src/monsters/sprite_4");
        move6 = setup("./src/monsters/sprite_5");
        move7 = setup("./src/monsters/sprite_6");
        move8 = setup("./src/monsters/sprite_7");

        up1 = setup("./src/monsters/sprite_0");
        up2 = setup("./src/monsters/sprite_1");
        up3 = setup("./src/monsters/sprite_2");
        down1 = setup("./src/monsters/sprite_3");
        down2 = setup("./src/monsters/sprite_4");
        down3 = setup("./src/monsters/sprite_5");
        left1 = setup("./src/monsters/sprite_6");
        left2 = setup("./src/monsters/sprite_7");
        left3 = setup("./src/monsters/sprite_0");
        right1 = setup("./src/monsters/sprite_1");
        right2 = setup("./src/monsters/sprite_2");
        right3 = setup("./src/monsters/sprite_3");
        
        
    }
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // es para elegir un número entre 1 y 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }


    
}
