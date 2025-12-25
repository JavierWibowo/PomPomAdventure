package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import main.RpgPanel;
import entity.Entity;

public class SetTiles extends Entity {

    RpgPanel gp;
    public Tile[] tile;
    public int mapTile[][];

    public SetTiles(RpgPanel gp) {
        this.gp = gp;
        tile = new Tile[10];

        mapTile = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImg();
        generateRandomMap();
    }

    public void generateRandomMap() {
        // 1. Initialize
        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                mapTile[col][row] = 4;
            }
        }
        carveMaze(1, 1);

        for (int i = 0; i < gp.maxWorldCol; i++) {
            mapTile[i][0] = 4;
            mapTile[i][gp.maxWorldRow - 1] = 4;
        }
        for (int i = 0; i < gp.maxWorldRow; i++) {
            mapTile[0][i] = 4;
            mapTile[gp.maxWorldCol - 1][i] = 4;
        }

        ensureSpawnClearance(10, 10);
    }

    private void ensureSpawnClearance(int centerX, int centerY) {
        for (int i = centerX - 1; i <= centerX + 1; i++) {
            for (int j = centerY - 1; j <= centerY + 1; j++) {
                mapTile[i][j] = 0;
            }
        }
    }

    private void carveMaze(int cx, int cy) {
        mapTile[cx][cy] = 0;

        Integer[] dirs = { 0, 1, 2, 3 };
        java.util.Collections.shuffle(java.util.Arrays.asList(dirs));

        for (int dir : dirs) {
            int nx = cx, ny = cy;
            int px = cx, py = cy;

            if (dir == 0) {
                ny = cy - 2;
                py = cy - 1;
            } // Up
            else if (dir == 1) {
                ny = cy + 2;
                py = cy + 1;
            } // Down
            else if (dir == 2) {
                nx = cx - 2;
                px = cx - 1;
            } // Left
            else if (dir == 3) {
                nx = cx + 2;
                px = cx + 1;
            } // Right

            if (nx > 0 && nx < gp.maxWorldCol - 1 && ny > 0 && ny < gp.maxWorldRow - 1) {
                if (mapTile[nx][ny] == 4) {
                    mapTile[px][py] = 0; // Remove wall between
                    carveMaze(nx, ny);
                }
            }
        }
    }

    public void getTileImg() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/floor2.jpg"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/waterNew.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/floor3.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/techWall.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/floor.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/floor01.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updates() {
        // CHECKING OBJECT COLLISION
        int objIndex = gp.checker.checkObject(this, true);
        pickUpObject(objIndex);
    }

    public void pickUpObject(int i) {

        if (i != 999) {

            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Flashed":
                    gp.screenHeight = 6;
                    gp.screenWidth = 5;
                    gp.obj[i] = null;
            }
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTile[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTile[worldCol][worldRow];

            int worldX = worldCol * gp.TileSize;
            int worldY = worldRow * gp.TileSize;
            int screenX = worldX - gp.player.defaultX + gp.player.screenX;
            int screenY = worldY - gp.player.defaultY + gp.player.screenY;

            if (worldX + gp.TileSize > gp.player.defaultX - gp.player.screenX &&
                    worldX - gp.TileSize < gp.player.defaultX + gp.player.screenX &&
                    worldY + gp.TileSize > gp.player.defaultY - gp.player.screenY &&
                    worldY - gp.TileSize < gp.player.defaultY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.TileSize, gp.TileSize, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
