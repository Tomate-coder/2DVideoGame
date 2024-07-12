package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.text.Utilities;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[99];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap();

    }

    public void getTileImage() {

        setup(0, "grass", false);
        setup(1, "grass", false);
        setup(2, "grass", false);
        setup(3, "grass", false);
        setup(4, "water", false);
        setup(5, "grass", false);
        setup(6, "grass", false);
        setup(7, "grass", false);
        setup(8, "grass", false);
        setup(9, "grass", false);

        setup(10, "water", true);
        setup(11, "watern", true);
        setup(12, "wateru", true);
        setup(13, "waterd", true);
        setup(14, "waterr", true);
        setup(15, "waterl", true);
        setup(16, "waterul", true);
        setup(17, "waterur", true);
        setup(18, "waterdr", true);
        setup(19, "waterdl", true);

        setup(20, "roadv", false);
        setup(21, "roadh", false);
        setup(22, "roadup", false);
        setup(23, "roaddown", false);
        setup(24, "roadleft", false);
        setup(25, "roadright", false);
        setup(26, "roadcornerul", false);
        setup(27, "roadcornerur", false);
        setup(28, "roadcornerdr", false);
        setup(29, "roadcornerdl", false);

        setup(30, "/woods/tree_c", true);
        setup(31, "/woods/tree_ul", true);
        setup(32, "/woods/tree_u", true);
        setup(33, "/woods/tree_ur", true);
        setup(34, "/woods/tree_dl", true);
        setup(35, "/woods/tree_d", true);
        setup(36, "/woods/tree_dr", true);
        setup(37, "/woods/tree_l", true);
        setup(38, "/woods/tree_r", true);

        setup(50, "wall", true);
        setup(51, "wall2", true);
        setup(52, "floor", false);
        setup(53, "walllu", true);
        setup(54, "wallleft", true);
        setup(55, "wallright", true);
        setup(56, "wallru", true);
        setup(40, "grass", false);

    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            File t1 = new File("./src/tiles/" + imageName + ".png");
            tile[index].image = ImageIO.read(t1);
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            // up1 = ImageIO.read(getClass().getResourceAsStream(".src/player/I_D_1.png"));
            // File f1 = new File("./src/player/I_U_1.png");
            // up1 = ImageIO.read(f1);

            // File map01 = new File("./src/maps/map01.txt");
            File mapWorld = new File("./src/maps/mapWorld.txt");

            // File map0 = new File("./src/maps/map01.txt");

            // InputStream is = getClass().getResourceAsStream(filePath); ESta madre no
            // sirvió porque no sirve la esa chingadera de getResourceAsStream, así que como
            // en las imágenes se convierte la ruta en un archivo y luego en el InputStream
            // para que lo pueda leer
            InputStream is = new FileInputStream(mapWorld);

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    // System.out.println(numbers);
                    int num = Integer.parseInt(numbers[col]);
                    // System.out.println(num);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D g2) {
        // g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
        // g2.drawImage(tile[1].image, 96, 0, gp.tileSize, gp.tileSize, null);
        // g2.drawImage(tile[2].image, 48, 0, gp.tileSize, gp.tileSize, null);

        int worldCol = 0;
        int worldRow = 0;
        // int x = 0;
        // int y = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Detener la cámara al borde de la pantalla
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if (rightOffset > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if (bottomOffset > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            // System.out.println(tileNum);
            // System.out.println(row);
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

            }
            else if (gp.player.screenX > gp.player.worldX || 
                     gp.player.screenY > gp.player.worldY ||
                     rightOffset > gp.worldWidth - gp.player.worldX ||
                     bottomOffset > gp.worldHeight - gp.player.worldY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                
            }

            worldCol++;
            // x += gp.tileSize;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                // x = 0;
                worldRow++;
                // y += gp.tileSize;
            }

        }

    }
}
