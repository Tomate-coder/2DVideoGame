package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;
import entity.Player;
import entity.Entity;
// import object.SuperObject;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Ajustes de la pantalla
    // Graphics2D g2;

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // Tamaño final del personaje 48 x 48 tile
    public final int maxScreenCol = 16; // Tamaño de la pantalla vertical
    public final int maxScreenRow = 12; // Tamaño de la pantalla horizontal
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixeles
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixeles

    // AJUSTES DEL MUNDO
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound se = new Sound();
    Sound music = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread; // Sirve para crear un proceso que no se detiene y se actualiza, útil para las
                       // animaciones.

    // ENTIDADES Y OBJETOS
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];// <--- Ese 10 es porque sólo va a crear 10 objetos al mismo tiempo,
                                         // pero eso no significa que sólo se creen 10 objetos en el juego. Se
                                         // puede incrementar.
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // ESTADO DEL JUEGO
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        // Este es el que debo descomentar
        // playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this); // El this hace referencia al GamePanel, así que básicamente pasamos el
                                       // GamePanel al constructor de la clase Thread, así es como iniciamos un Thread
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 9;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                // System.out.println("FPS" + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update() {
        if (gameState == playState) {
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            // playMusic(0);
            
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].update();
                }
            }
        }
        if (gameState == pauseState) {
            stopMusic();

        }
    }

    public void paintComponent(Graphics g) { // Graphics es una clase que tinene muchas funciones para dibujar objetos
                                             // en la pantalla

        super.paintComponent(g); // El super significa la clase padre de esta clase, que en este caso es JPanel
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
            ui.draw(g2);

        } else {
            // TILE
            tileM.draw(g2); // Pos aquí namás dibujamos primero el fondo y luego el personaje para que no lo
            // tape ._.

            // AÑADIMOS ENTIDADES A LA LISTA
            entityList.add(player);

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }

            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            // EMPTY ENTITY LIST
            for (int i = 0; i < entityList.size(); i++) {
                entityList.remove(i);
            }
            ui.draw(g2);
        }

        // DEBUG
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time:" + passed, 10, 400);
            System.out.println("Draw Time:" + passed);
        }
        g2.dispose();

    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
        // music.pause();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
