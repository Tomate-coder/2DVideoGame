package entity;

import java.util.Random;

// import java.io.File;
// import java.io.IOException;

// import javax.imageio.ImageIO;

import main.GamePanel;
// import main.UtilityTool;

public class NPC_Dracula extends Entity {
    public NPC_Dracula(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        type = 1;
        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("./src/npc/drac_B_1");
        up2 = setup("./src/npc/drac_B_2");
        up3 = setup("./src/npc/drac_B_3");
        down1 = setup("./src/npc/drac_F_2");
        down2 = setup("./src/npc/drac_F_1");
        down3 = setup("./src/npc/drac_F_4");
        left1 = setup("./src/npc/drac_L_1");
        left2 = setup("./src/npc/drac_L_0");
        left3 = setup("./src/npc/drac_L_3");
        right1 = setup("./src/npc/drac_r1");
        right2 = setup("./src/npc/drac_r0");
        right3 = setup("./src/npc/drac_r3");

    }

    public void setDialogue() {
        dialogues[0] = "Hello, little freak.";
        dialogues[1] = "Don't be afraid, I won't hurt you.";
        dialogues[2] = "After all, I still appreciate you.";
        dialogues[3] = "But it looks like you can't remeber.";
        dialogues[4] = "Maybe it is better for all of us.";
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

    public void speak() {
        // Aquí puedo personailzar cuando un npc habla
        super.speak();
    }

}
