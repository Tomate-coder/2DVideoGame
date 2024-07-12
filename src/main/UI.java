package main;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.io.InputStream;

import entity.Entity;
// import object.OBJ_Key;
import object.OBJ_Skull;
// import object.SuperObject;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font alagard;
    BufferedImage full_skull, dead_skull;

    public boolean messageOn = false;
    public int messageCounter = 0;
    public String message = "";
    public boolean gameFinished = false;
    public String currentDialogue = "";
    double playTime;
    public int commandNum = 0;

    public boolean textOn = false;
    public int textCounter = 0;
    String text;

    public UI(GamePanel gp) {
        this.gp = gp;
        InputStream is = getClass().getResourceAsStream("/font/alagard.ttf");
        try {
            alagard = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
            // TODO: handle exception
        } catch (IOException e) {
            e.printStackTrace();
        }

        Entity skull = new OBJ_Skull(gp);
        full_skull = skull.image;
        dead_skull = skull.image2;

    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
        textOn = true;
    }

    public void showText(String texto) {
        // text = texto;
        textOn = true;
    }

    public void draw(Graphics2D g2) {
        // g2.setFont(new Font("Arial", Font.PLAIN, 40)); Esto es un mal ejemplo porque
        // aparentemente el métdo loop va a llamar esto 60 veces por segundo y se
        // chingará la memoria RAM

        // if (gameFinished == true) {

        // g2.setFont(arial_80);
        // g2.setColor(Color.white);

        // int textLenght;
        // int x;
        // int y;
        // if (textOn == true) {

        // text = "Has conseguido esencia de monstruo";
        // textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        // x = gp.screenHeight / 2 - textLenght / 2;
        // y = gp.screenWidth / 2 - (gp.tileSize * 3);
        // // g2.drawString(text, x, y);

        // g2.drawString(text, gp.tileSize / 2, gp.tileSize * 5);

        // g2.setFont(arial_40);
        // g2.setColor(Color.white);

        // text = "Ahora podrás atravesar suelo impuro";
        // textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        // x = gp.screenHeight / 2 - textLenght / 2;
        // y = gp.screenWidth / 2 + (gp.tileSize * 2);

        // g2.drawString(text, x, y);

        // }
        // textCounter++;
        // if (textCounter > 120) {
        // textCounter = 0;
        // textOn = false;
        // gameFinished = false;
        // }
        // } else {
        // g2.setFont(arial_40);
        // g2.setColor(Color.white);
        // g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize,
        // gp.tileSize, null);
        // g2.drawString("x " + gp.player.hasKey, 74, 65);

        // // Los drawStrings funcionan distinto que los drawImages, ya que indican
        // // diferentes puntos, pues los drawStrings muestran la línea base del texto

        // // playTime +=(double)1/60;

        // if (messageOn == true) {

        // g2.setFont(g2.getFont().deriveFont(30F));
        // g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

        // messageCounter++;

        // if (messageCounter > 80) {
        // messageCounter = 0;
        // messageOn = false;
        // }
        // }
        // }

        this.g2 = g2;

        g2.setFont(alagard);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        // Estado de Título
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // ESTADO CONTINUO
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        }
        // ESTADO DDE PAUSA
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerLife();

        }
        // ESTADO DE DIÁLOGOS
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
            // drawPlayerLife();

        }

    }

    public void drawPlayerLife() {

        // gp.player.life = 4;

        // VIDAS PERDIDAS
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // DIBUJAR VIDAS MÁXIMAS
        while (i < gp.player.maxLife) {
            g2.drawImage(dead_skull, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // RESETEAR
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // VIDAS ACTUALES
        while (i < gp.player.life) {
            g2.drawImage(full_skull, x, y, null);
            i++;
            x += gp.tileSize;

        }
    }

    public void drawTitleScreen() {

        g2.setColor(new Color(75, 2, 89));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // TÍTULO
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "Punto Muerto";
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 3;

        // SHADOW
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        // Main COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // ION IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down2, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NUEVO JUEGO";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "CONTINUAR JUEGO";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "SALIR";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);

        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDialogue.split("/n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
        // g2.drawString(currentDialogue, x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 120);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenHeight / 2 - length / 2;
        return x;
    }
}
