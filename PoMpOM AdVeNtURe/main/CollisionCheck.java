package main;

import entity.Entity;

public class CollisionCheck {

    RpgPanel gp;

    public CollisionCheck(RpgPanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftDefaultX = entity.defaultX + entity.solidArea.x;
        int entityRightDefaultX = entity.defaultX + entity.solidArea.x + entity.solidArea.width;
        int entityTopDefaultY = entity.defaultY + entity.solidArea.y;
        int entityBottomDefaultY = entity.defaultY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftDefaultX / gp.TileSize;
        int entityRightCol = entityRightDefaultX / gp.TileSize;
        int entityTopRow = entityTopDefaultY / gp.TileSize;
        int entityBottomRow = entityBottomDefaultY / gp.TileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopDefaultY - entity.speed) / gp.TileSize;
                tileNum1 = gp.tiles.mapTile[entityLeftCol][entityTopRow];
                tileNum2 = gp.tiles.mapTile[entityRightCol][entityTopRow];
                check(entity, tileNum1, tileNum2);
                break;
            case "down":
                entityBottomRow = (entityBottomDefaultY + entity.speed) / gp.TileSize;
                tileNum1 = gp.tiles.mapTile[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tiles.mapTile[entityRightCol][entityBottomRow];
                check(entity, tileNum1, tileNum2);
                break;
            case "left":
                entityLeftCol = (entityLeftDefaultX - entity.speed) / gp.TileSize;
                tileNum1 = gp.tiles.mapTile[entityLeftCol][entityTopRow];
                tileNum2 = gp.tiles.mapTile[entityLeftCol][entityBottomRow];
                check(entity, tileNum1, tileNum2);
                break;
            case "right":
                entityRightCol = (entityRightDefaultX + entity.speed) / gp.TileSize;
                tileNum1 = gp.tiles.mapTile[entityRightCol][entityTopRow];
                tileNum2 = gp.tiles.mapTile[entityRightCol][entityBottomRow];
                check(entity, tileNum1, tileNum2);
                break;
        }
    }

    private void check(Entity entity, int tile1, int tile2) {
        if (gp.tiles.tile[tile1].collision || gp.tiles.tile[tile2].collision) {
            entity.collisionOn = true;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        // Calculate the entity's future solid area position ONCE
        int originalX = entity.solidArea.x;
        int originalY = entity.solidArea.y;

        entity.solidArea.x += entity.defaultX;
        entity.solidArea.y += entity.defaultY;

        // Adjust for movement direction
        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Update object's solid area world position
                gp.obj[i].solidArea.x += gp.obj[i].worldX;
                gp.obj[i].solidArea.y += gp.obj[i].worldY;

                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                // Reset object's solid area to default relative coordinates
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        // Reset entity's solid area to default relative coordinates
        entity.solidArea.x = originalX;
        entity.solidArea.y = originalY;

        return index;
    }
}
