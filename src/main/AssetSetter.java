package main;

import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Chest;
import object.OBJ_InWall;
import entity.NPC_Dracula;
import object.OBJ_Boots;
import object.OBJ_Noppal;
import monster.MON_GreenSlime;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 8 * gp.tileSize;
        gp.obj[0].worldY = 21 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 24 * gp.tileSize;
        gp.obj[1].worldY = 43 * gp.tileSize;

        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = 33 * gp.tileSize;
        gp.obj[2].worldY = 12 * gp.tileSize;

        gp.obj[3] = new OBJ_Chest(gp);
        gp.obj[3].worldX = 33 * gp.tileSize;
        gp.obj[3].worldY = 8 * gp.tileSize;

        gp.obj[4] = new OBJ_InWall(gp);
        gp.obj[4].worldX = 22 * gp.tileSize;
        gp.obj[4].worldY = 9 * gp.tileSize;

        gp.obj[5] = new OBJ_Boots(gp);
        gp.obj[5].worldX = 17 * gp.tileSize;
        gp.obj[5].worldY = 31 * gp.tileSize;

        gp.obj[6] = new OBJ_Noppal(gp);
        gp.obj[6].worldX = 26 * gp.tileSize;
        gp.obj[6].worldY = 36 * gp.tileSize;

    }

    public void setNPC() {
        gp.npc[0] = new NPC_Dracula(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }
    public void setMonster() {
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize * 16;
        gp.monster[0].worldY = gp.tileSize * 40;
    }
}
