package main;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Graphics2D;

import java.awt.Color;
import java.awt.Font;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    // int damagetimecounter = 0;
    // Rectangle eventRect;
    // int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        // Podría utilizar la propiedad de evento para hacer que cuando el personaje se
        // aproxime al borde del mapa éste se detenga y quie avance a hora se Ion.

        // ANOTACIONES PARA LA HISTORIA
        // Que existan remanetes de memorias de ciertos personajes, e tre ellos una
        // conversación de Viktor y Griffin en el que el primero intenta convencerlo de
        // colaborar con el humano para conseguir la invisibilidad, para esto apela a
        // que el invisible ser era antes un humano, y que ellos dos son mejores que el
        // resto.

        // GRIFFIN
        // Viktor utiliza un vampiro para consumir su sangre, una vez que psas esto la
        // monocaína se empieza a genear dntro del chupasangre, por lo que la siguiente
        // vez que muerde al hombre invisible le inyecta de nuevo la droga, creando así
        // un ciclo provocnado que Griffin comience a ser adicto otra vez.

        // VIKTOR
        // Viktor comienza a experimentar con otros monstruos para intentar revivirlos,
        // estos vuelven en sí, pero cada vez que lo intenta con Arthur el procedimieno
        // no funciona lo que lo lleva a la desesperación. Estos mosntruos que revive
        // los suelta y son los enemigos contra los que nos enfrentamos, harto de ser
        // icapaz de reivir a su hermano decide hacer la prueba con alguien vivo, en
        // este caso Ion, revelándonos que el es el verdadero monstruo de Frankenstein.

        // ION
        // Al inicio del juegp IOn muere rescatando a la muerte, al final nos enteremos
        // que realmentte ella lo mató para evitaar que Viktor loo hicieraa primero y no
        // jugara a ser Diios.
        // (Idea) Viktor al final se suicida para revivirse a spi mismo, aunque tal vez
        // no quede por su miedo a la muerte-}, y por su parecido a Edgar

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 16;
            eventRect[col][row].y = 16;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent() {

        // Check if player is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX); // Este método de matemáticas te devuelve al número
                                                                     // a positivo aunque sea negativo (allegedly)
        int YDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, YDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;

        }
        if (canTouchEvent == true) {
            if (hit(26, 36, "right") == true) {
                damageNoppal(26, 36, gp.dialogueState);
            }
            if (hit(20, 19, "up") == true) {
                healingPool(20, 19, gp.dialogueState);

            }
        }

    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        return hit;
    }

    public void damageNoppal(int col, int row, int gameState) {
        //
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Chocaste cotra un noppal";
        gp.player.life -= 1;
        canTouchEvent = false;
        // eventRect[col][row].eventDone = true;
        // gp.player.worldX -= gp.player.speed;
        // gp.player.direction = "damageright";

        // gp.player.worldX -= gp.tileSize - 2;

        // for (int i = 0; i < 120; i++) {
        // gp.player.collisionOn = true;
        // // gp.player.speed = 0;
        // }

        // damagetimecounter++;
        // if (damagetimecounter < 120) {
        // gp.player.collisionOn = true;
        // }
        // damagetimecounter = 0;
    }

    Graphics2D g2;

    // public void draw(Graphics2D g2) {

    // g2.setFont(new Font("Arial", Font.PLAIN, 26));
    // g2.setColor(Color.white);
    // g2.drawString("Curarse", gp.player.screenX, gp.player.screenY);
    // }

    public void healingPool(int col, int row, int gameState) {

        if (gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "Has bebido de la fuente de la vida.\n Salud Total recuperada";
            gp.player.life = gp.player.maxLife;
        }
    }
}
