package entity;

// import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOError;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;

public class Player extends Entity {

    // GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    public boolean hasOpen = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        // this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(worldX, worldY, spriteCounter, speed);
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23; // No son variables ya predeterminadas, indican donde se posicionará Player
                                   // cuando se inicie el juego
        worldY = gp.tileSize * 21; // Aparentemente estos son ya variables predeterminadas
        speed = 3;
        direction = "down";
        maxLife = 5;
        life = maxLife;
    }

    public void getPlayerImage() {

        up1 = setup("./src/player/I_U_1_2");
        up2 = setup("./src/player/I_U_2_2");
        up3 = setup("./src/player/I_U_3_2");
        down1 = setup("./src/player/I_D_1_4");
        down2 = setup("./src/player/I_D_2_4");
        down3 = setup("./src/player/I_D_3_4");
        left1 = setup("./src/player/I_L_1_2");
        left2 = setup("./src/player/I_L_2_2");
        left3 = setup("./src/player/I_L_3_2");
        right1 = setup("./src/player/I_R_1_2");
        right2 = setup("./src/player/I_R_2_2");
        right3 = setup("./src/player/I_R_3_2");

        upl1 = setup("./src/player/I_U_L_1");
        upl2 = setup("./src/player/I_U_L_2");
        upl3 = setup("./src/player/I_U_L_3");
        upr1 = setup("./src/player/I_U_R_1");
        upr2 = setup("./src/player/I_U_R_2");
        upr3 = setup("./src/player/I_U_R_3");
        downl1 = setup("./src/player/I_D_L_1");
        downl2 = setup("./src/player/I_D_L_2");
        downl3 = setup("./src/player/I_D_L_3");
        downr1 = setup("./src/player/I_D_R_1");
        downr2 = setup("./src/player/I_D_R_2");
        downr3 = setup("./src/player/I_D_R_3");

    }

    // public BufferedImage setup(String imageName) {
    // UtilityTool uTool = new UtilityTool();
    // BufferedImage image = null;

    // try {
    // File f1 = new File("./src/player/" + imageName + ".png");
    // image = ImageIO.read(f1);
    // image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

    // } catch (IOException e) {
    // e.printStackTrace();
    // // TODO: handle exception
    // }
    // return image;
    // }

    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true
                || keyH.leftPressed == true
                || keyH.rightPressed == true) {

            if (keyH.upPressed == true && keyH.leftPressed == true) {
                // En Java la esquina superior izquierda es X:0 Y:0
                // El valor de X incrementa a la derecha y el valor de Y incrementa a hacia
                // abajo
                direction = "upLeft";
                // playerY = playerY - playerSpeed;

            } else if (keyH.upPressed == true && keyH.rightPressed == true) {
                direction = "upRight";

            } else if (keyH.downPressed == true && keyH.rightPressed == true) {
                direction = "downRight";

            } else if (keyH.downPressed == true && keyH.leftPressed == true) {
                direction = "downLeft";

            } else if (keyH.upPressed == true) {
                // En Java la esquina superior izquierda es X:0 Y:0
                // El valor de X incrementa a la derecha y el valor de Y incrementa a hacia
                // abajo
                direction = "up";
                // playerY = playerY - playerSpeed;

            } else if (keyH.downPressed == true) {
                direction = "down";

            } else if (keyH.leftPressed == true) {
                direction = "left";

            } else if (keyH.rightPressed == true) {
                direction = "right";

            }

            // Checar colisión del tile
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Checamos la colisión del objeto
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CChecamos la colisión del npc
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK EVENT
            gp.eHandler.checkEvent();
            gp.keyH.spacePressed = false;
            gp.keyH.enterPressed = false;

            // si la colisión es false el jugador se puede mover
            if (collisionOn == false) {
                switch (direction) {
                    case "upLeft":
                        // significa que es igual a a la posición en y menos la velocidad; o sea que si
                        // se mueve a la izquierda la posición en y va a disminuir en 2 (bueno arriba)
                        worldY -= speed;
                        worldX -= speed;
                        break;

                    case "downRight":
                        worldY += speed;
                        worldX += speed;
                        break;

                    case "downLeft":
                        worldX -= speed;
                        worldY += speed;
                        break;

                    case "upRight":
                        worldX += speed;
                        worldY -= speed;
                        break;

                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "damageright":
                        worldX -= gp.tileSize - 2;
                        // worldX -= speed * 17;
                        // for (int i = 0; i < 120; i++) {
                        // gp.player.collisionOn = true;
                        // }

                        break;

                }
            }

            spriteCounter++;

            // ESte contador hace que cambie de animación cada 10 fotogramas
            // Aquí es dónde se establece la velocidad de la animación
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            BufferedImage image = down1;
            switch (direction) {

                case "down":

                    image = down3;

                    // System.out.println(direction);
                    break;

                // System.out.println(direction);

            }

        }
        // Se escribe fuera de la función de las teclas para que el contador funcione
        // aún cuando Player no se mueve
        if (invincible == true) {
            invincibleCounter++;

            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }

        }

    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Conseguiste una llave");

                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("Has utilizado una llave simple");

                    } else {
                        gp.ui.showMessage("Necesitas una llave");

                    }
                    break;
                case "Chest":
                    if (hasKey > 0) {
                        try {
                            File f1 = new File("./src/objects/openchest.png");

                            gp.obj[i].image = ImageIO.read(f1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        hasKey--;
                        if (hasOpen == false) {
                            gp.ui.textOn = true;
                            gp.ui.gameFinished = true;

                        }
                        hasOpen = true;
                        gp.ui.showMessage("Has utilizado una llave simple");

                        if (hasOpen == false) {
                            if (hasKey < 1) {
                                gp.ui.showMessage("Necesitas una llave");

                            }
                        }

                    }
                    break;
                case "InWall":
                    if (hasOpen == true) {
                        gp.obj[i] = null;
                        // hasKey--;

                    }
                    break;
                case "Boots":
                    speed += 1;
                    gp.obj[i] = null;
                    // hasKey--;
                    gp.ui.showMessage("Has conseguido amuleto de velocidad");

                    break;
            }
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            // System.out.println("yeah it'r working");

            // Método para detener a un noc cuando lo tocas que hasta el momento no ha
            // funcionado |v
            // gp.npc[i].stop_character();

            if (gp.keyH.spacePressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
                // Esto es para que cuando hablemos con Dracula, nos volteé a ver.
                if (direction == "down") {
                    gp.npc[i].direction = "up";
                } else if (direction == "up") {
                    gp.npc[i].direction = "down";
                } else if (direction == "left") {
                    gp.npc[i].direction = "right";
                } else if (direction == "right") {
                    gp.npc[i].direction = "left";
                }
            }
            // Aquí podría hacer una función para que cuando Player toque a un npc que se
            // meuva, éste se dretenga un momento para que podamos hablar con él.

        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false) {
                life -= 1;
                invincible = true;

            }

        }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);

        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = down3;

        switch (direction) {
            case "upLeft":
                if (collisionOn == false) {

                    if (spriteNum == 1) {
                        image = upl1;
                    }
                    if (spriteNum == 2) {
                        image = upl2;
                    }
                    if (spriteNum == 3) {
                        image = upl3;
                    }
                    if (spriteNum == 4) {
                        image = upl2;
                    }

                    // System.out.println(direction);
                } else {
                    image = upl2;

                }
                break;

            case "upRight":
                if (collisionOn == false) {

                    if (spriteNum == 1) {
                        image = upr1;
                    }
                    if (spriteNum == 2) {
                        image = upr2;
                    }
                    if (spriteNum == 3) {
                        image = upr3;
                    }
                    if (spriteNum == 4) {
                        image = upr2;
                    }
                } else {
                    image = upr2;

                }

                // System.out.println(direction);
                break;

            case "downLeft":
                if (collisionOn == false) {

                    if (spriteNum == 1) {
                        image = downl1;
                    }
                    if (spriteNum == 2) {
                        image = downl2;
                    }
                    if (spriteNum == 3) {
                        image = downl3;
                    }
                    if (spriteNum == 4) {
                        image = downl2;
                    }
                } else {
                    image = downl2;

                }
                // System.out.println(direction);
                break;
            case "downRight":
                if (collisionOn == false) {

                    if (spriteNum == 1) {
                        image = downr2;
                    }
                    if (spriteNum == 2) {
                        image = downr1;
                    }
                    if (spriteNum == 3) {
                        image = downr3;
                    }
                    if (spriteNum == 4) {
                        image = downr1;
                    }
                } else {
                    image = downr1;

                }
                // System.out.println(direction);
                break;

            case "up":
                if (collisionOn == false) {

                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (spriteNum == 3) {
                        image = up3;
                    }
                    if (spriteNum == 4) {
                        image = up2;
                    }
                } else {
                    image = up2;

                }
                // interactNPC(actionLockCounter);

                // System.out.println(direction);
                break;

            case "down":
                if (collisionOn == false) {

                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    if (spriteNum == 3) {
                        image = down3;
                    }
                    if (spriteNum == 4) {
                        image = down2;
                    }
                } else {
                    image = down2;

                }
                // System.out.println(direction);
                break;

            case "left":
                if (collisionOn == false) {

                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (spriteNum == 3) {
                        image = left3;
                    }
                    if (spriteNum == 4) {
                        image = left2;
                    }
                    // System.out.println(direction);
                } else {
                    image = left2;

                }
                break;

            case "right":
                if (collisionOn == false) {

                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    if (spriteNum == 3) {
                        image = right3;
                    }
                    if (spriteNum == 4) {
                        image = right2;
                    }
                    // System.out.println(direction);
                } else {
                    image = right2;

                }
                break;
            case "damageright":
                image = right2;
                break;
        }

        int x = screenX;
        int y = screenY;
        // Para futuras referencias no entiendo por qué chingados no deja seguior
        // avanzando luego del punto en que la cámara deja de movere, pero empieza a
        // partir de aquí.
        if (screenX > worldX) {
            x = worldX;
        }
        if (screenY > worldY) {
            y = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if (rightOffset > gp.worldWidth - worldX) {
            x = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if (bottomOffset > gp.worldHeight - worldY) {
            y = gp.screenHeight - (gp.worldHeight - worldY);
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
