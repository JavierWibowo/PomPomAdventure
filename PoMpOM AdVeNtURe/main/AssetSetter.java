package main;

import java.util.Random;
import object.EndTile;
import object.FastPotion;
import object.SlowTrap;
import object.StuckTrap;
import object.TeleportTrap;

public class AssetSetter {
    RpgPanel gp;
    Random random = new Random();

    public AssetSetter(RpgPanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        placeRandomObjects(new FastPotion(), 5);
        placeRandomObjects(new SlowTrap(), 4);
        placeRandomObjects(new StuckTrap(), 4);
        placeRandomObjects(new TeleportTrap(), 4);
        placeBorderExits();
    }

    private void placeRandomObjects(object.SuperObject prototype, int count) {
        int placed = 0;
        int index = 0;

        while (index < gp.obj.length && gp.obj[index] != null) {
            index++;
        }

        while (placed < count && index < gp.obj.length) {
            int worldX = random.nextInt(gp.maxWorldCol);
            int worldY = random.nextInt(gp.maxWorldRow);

            int tileNum = gp.tiles.mapTile[worldX][worldY];
            if (tileNum != 1 && tileNum != 2 && tileNum != 4) {

                try {
                    gp.obj[index] = prototype.getClass().getDeclaredConstructor().newInstance();
                    gp.obj[index].worldX = worldX * gp.TileSize;
                    gp.obj[index].worldY = worldY * gp.TileSize;

                    index++;
                    placed++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void placeBorderExits() {
        for (int side = 0; side < 4; side++) {
            int col = 0, row = 0;
            int adjCol = 0, adjRow = 0;

            switch (side) {
                case 0:
                    col = random.nextInt(gp.maxWorldCol - 2) + 1;
                    row = 0;
                    adjCol = col;
                    adjRow = 1;
                    break;
                case 1:
                    col = random.nextInt(gp.maxWorldCol - 2) + 1;
                    row = gp.maxWorldRow - 1;
                    adjCol = col;
                    adjRow = row - 1;
                    break;
                case 2:
                    col = 0;
                    row = random.nextInt(gp.maxWorldRow - 2) + 1;
                    adjCol = 1;
                    adjRow = row;
                    break;
                case 3:
                    col = gp.maxWorldCol - 1;
                    row = random.nextInt(gp.maxWorldRow - 2) + 1;
                    adjCol = col - 1;
                    adjRow = row;
                    break;
            }
            gp.tiles.mapTile[col][row] = 0;
            gp.tiles.mapTile[adjCol][adjRow] = 0;

            int index = getFreeIndex();
            if (index != -1) {
                gp.obj[index] = new EndTile();
                gp.obj[index].worldX = col * gp.TileSize;
                gp.obj[index].worldY = row * gp.TileSize;
            }
        }
    }

    private int getFreeIndex() {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == null)
                return i;
        }
        return -1;
    }
}