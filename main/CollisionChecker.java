package main;

import entity.Entity;
import world.Tile;
import java.awt.Rectangle;

public class CollisionChecker {
    GamePanel gp;
    class TestArea {
        public Rectangle one, two;
    }
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
        TestArea area = new TestArea();
        area.one = tile.solidArea;
        area.two = new Rectangle(dx + entity.solidArea.x, dy + entity.solidArea.y, entity.solidArea.width, entity.solidArea.height);
        if (area.one.intersects(area.two)) {
            entity.collisionOn = true;
        }
    }
    public void checkTile(Entity entity){
        int entityLeftX = entity.x + entity.solidArea.x;
        int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.y + entity.solidArea.y;
        int entityBotY = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftX/gp.tileSize;
        int entityRightCol = entityRightX/gp.tileSize;
        int entityTopRow = entityTopY/gp.tileSize;
        int entityBotRow = entityBotY/gp.tileSize;

        Tile tile1, tile2;
        int dx, dy;

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopY - entity.speed)/gp.tileSize;
                tile1 = gp.tM.tiles[gp.tM.mapTileNum[entityTopRow][entityLeftCol]];
                tile2 = gp.tM.tiles[gp.tM.mapTileNum[entityTopRow][entityRightCol]];

                dx = entity.x - (entityLeftCol * gp.tileSize);
                dy = (entity.y - entity.speed) - (entityTopRow * gp.tileSize);
                detectCollision (entity, tile1, dx, dy);
                dx = entity.x - (entityRightCol * gp.tileSize);
                detectCollision (entity, tile2, dx, dy);
                break;

            case "down":
                entityBotRow = (entityBotY + entity.speed)/gp.tileSize;
                tile1 = gp.tM.tiles[gp.tM.mapTileNum[entityBotRow][entityLeftCol]];
                tile2 = gp.tM.tiles[gp.tM.mapTileNum[entityBotRow][entityRightCol]];

                dx = entity.x - (entityLeftCol * gp.tileSize);
                dy = (entity.y + entity.speed) - (entityBotRow * gp.tileSize);
                detectCollision (entity, tile1, dx, dy);
                dx = entity.x - (entityRightCol * gp.tileSize);
                detectCollision (entity, tile2, dx, dy);
                break;

            case "left":
                entityLeftCol = (entityLeftX - entity.speed)/gp.tileSize;
                tile1 = gp.tM.tiles[gp.tM.mapTileNum[entityTopRow][entityLeftCol]];
                tile2 = gp.tM.tiles[gp.tM.mapTileNum[entityBotRow][entityLeftCol]];

                dx = (entity.x - entity.speed) - (entityLeftCol * gp.tileSize);
                dy = entity.y - (entityTopRow * gp.tileSize);
                detectCollision (entity, tile1, dx, dy);
                dy = entity.y - (entityBotRow * gp.tileSize);
                detectCollision (entity, tile2, dx, dy);
                break;

            case "right":
                entityRightCol = (entityRightX + entity.speed)/gp.tileSize;
                tile1 = gp.tM.tiles[gp.tM.mapTileNum[entityTopRow][entityRightCol]];
                tile2 = gp.tM.tiles[gp.tM.mapTileNum[entityBotRow][entityRightCol]];

                dx = (entity.x + entity.speed) - (entityRightCol * gp.tileSize);
                dy = entity.y - (entityTopRow * gp.tileSize);
                detectCollision (entity, tile1, dx, dy);
                dy = entity.y - (entityBotRow * gp.tileSize);
                detectCollision (entity, tile2, dx, dy);
                break;
        }
    }
}
