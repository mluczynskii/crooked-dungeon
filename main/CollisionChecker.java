package main;

import entity.Entity;
import world.Tile;
import java.awt.Rectangle;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp=gp;
    }
    private void detectCollision (Entity entity, Tile tile, int dx, int dy) {
        // t1B - Tile solidArea, entB - entity solidArea
        // dx, dy - distance to upper-left corner of the tile (@)
        // @-----------------#   /\
        // |                 |   || dy+entB.y
        // |  dx+entB.x      |   \/ 
        // | <=========> #-------#   
        // |             |  entB | 
        // |             #-------#
        // |    #-----#      |
        // |    | t1B |      |
        // |    |     |      |
        // #----#-----#------#
        Rectangle one = tile.solidArea;
        Rectangle two = new Rectangle(dx + entity.solidArea.x, dy + entity.solidArea.y, entity.solidArea.width, entity.solidArea.height);
        if (one.intersects(two)) {
            entity.collisionOn = true;
        }
    }
    public void checkTile(Entity entity){
        int entityLeftX = entity.x + entity.solidArea.x;
        int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.y + entity.solidArea.y;
        int entityBotY = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = Math.max(entityLeftX/GamePanel.tileSize, 0);
        int entityRightCol = Math.min(entityRightX/GamePanel.tileSize, GamePanel.colNum-1);
        int entityTopRow = Math.max(entityTopY/GamePanel.tileSize, 0);
        int entityBotRow = Math.min(entityBotY/GamePanel.tileSize, GamePanel.rowNum-1);

        Tile tile1, tile2;
        int dx, dy;

        switch(entity.direction){
            case "up":
                entityTopRow = Math.max((entityTopY - entity.speed)/GamePanel.tileSize, 0);
                tile1 = gp.tM.tiles[gp.tM.currentRoom.roomTileNum[entityTopRow][entityLeftCol]];
                tile2 = gp.tM.tiles[gp.tM.currentRoom.roomTileNum[entityTopRow][entityRightCol]];

                dx = entity.x - (entityLeftCol * GamePanel.tileSize);
                dy = (entity.y - entity.speed) - (entityTopRow * GamePanel.tileSize);
                detectCollision (entity, tile1, dx, dy);
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                detectCollision (entity, tile2, dx, dy);
                break;

            case "down":
                entityBotRow = Math.min((entityBotY + entity.speed)/GamePanel.tileSize, GamePanel.rowNum-1);
                tile1 = gp.tM.tiles[gp.tM.currentRoom.roomTileNum[entityBotRow][entityLeftCol]];
                tile2 = gp.tM.tiles[gp.tM.currentRoom.roomTileNum[entityBotRow][entityRightCol]];

                dx = entity.x - (entityLeftCol * GamePanel.tileSize);
                dy = (entity.y + entity.speed) - (entityBotRow * GamePanel.tileSize);
                detectCollision (entity, tile1, dx, dy);
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                detectCollision (entity, tile2, dx, dy);
                break;

            case "left":
                entityLeftCol = Math.max((entityLeftX - entity.speed)/GamePanel.tileSize, 0);
                tile1 = gp.tM.tiles[gp.tM.currentRoom.roomTileNum[entityTopRow][entityLeftCol]];
                tile2 = gp.tM.tiles[gp.tM.currentRoom.roomTileNum[entityBotRow][entityLeftCol]];

                dx = (entity.x - entity.speed) - (entityLeftCol * GamePanel.tileSize);
                dy = entity.y - (entityTopRow * GamePanel.tileSize);
                detectCollision (entity, tile1, dx, dy);
                dy = entity.y - (entityBotRow * GamePanel.tileSize);
                detectCollision (entity, tile2, dx, dy);
                break;

            case "right":
                entityRightCol = Math.min((entityRightX + entity.speed)/GamePanel.tileSize, GamePanel.colNum-1);
                tile1 = gp.tM.tiles[gp.tM.currentRoom.roomTileNum[entityTopRow][entityRightCol]];
                tile2 = gp.tM.tiles[gp.tM.currentRoom.roomTileNum[entityBotRow][entityRightCol]];

                dx = (entity.x + entity.speed) - (entityRightCol * GamePanel.tileSize);
                dy = entity.y - (entityTopRow * GamePanel.tileSize);
                detectCollision (entity, tile1, dx, dy);
                dy = entity.y - (entityBotRow * GamePanel.tileSize);
                detectCollision (entity, tile2, dx, dy);
                break;
        }
    }
}
