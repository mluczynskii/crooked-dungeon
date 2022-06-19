package main;

import entity.Entity;
import world.Tile;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import world.TileManager;
import entity.*;

public class CollisionChecker {
    private static boolean detectCollision (Entity entity, Area solidArea, int dx, int dy) {
        // t1B - entity/tile solidArea, entB - entity solidArea
        // dx, dy - distance to upper-left corner of the entity/tile (@)
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
        Area one = new Area(solidArea);
        AffineTransform matrix = new AffineTransform();
        matrix.translate(dx, dy);
        Area two = entity.solidArea.createTransformedArea(matrix);
        one.intersect(two);
        if (!(one.isEmpty())) {
            return true;
        }
        return false;
    }
    public static void check (Entity entity) {
        Rectangle bounds = entity.solidArea.getBounds();
        int entityLeftX = entity.x + bounds.x;
        int entityRightX = entity.x + bounds.x + bounds.width;
        int entityTopY = entity.y + bounds.y;
        int entityBotY = entity.y + bounds.y + bounds.height;

        int entityLeftCol = Math.max(entityLeftX/GamePanel.tileSize, 0);
        int entityRightCol = Math.min(entityRightX/GamePanel.tileSize, GamePanel.colNum-1);
        int entityTopRow = Math.max(entityTopY/GamePanel.tileSize, 0);
        int entityBotRow = Math.min(entityBotY/GamePanel.tileSize, GamePanel.rowNum-1);

        Tile tileNW, tileNE, tileSW, tileSE;
        int dx, dy;

        switch(entity.direction){
            case "up":
                entityTopRow = Math.max((entityTopY - entity.speed)/GamePanel.tileSize, 0);

                tileNW = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityTopRow][entityLeftCol]];
                tileNE = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityTopRow][entityRightCol]];
                tileSW = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityBotRow][entityLeftCol]];
                tileSE = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityBotRow][entityRightCol]];
                
                // North-west tile
                dx = entity.x - (entityLeftCol * GamePanel.tileSize);
                dy = (entity.y - entity.speed) - (entityTopRow * GamePanel.tileSize);
                if (detectCollision (entity, tileNW.solidArea, dx, dy)) entity.collisionOn = true;

                // North-east tile
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity, tileNE.solidArea, dx, dy)) entity.collisionOn = true;

                // South-west tile
                dx = entity.x - (entityLeftCol * GamePanel.tileSize);
                dy = (entity.y - entity.speed) - (entityBotRow * GamePanel.tileSize);
                if (detectCollision (entity, tileSW.solidArea, dx, dy)) entity.collisionOn = true;

                // South-east tile
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity, tileSE.solidArea, dx, dy)) entity.collisionOn = true;
                
                for (Entity e : TileManager.currentRoom.entityList) {
                    if (entity.equals(e))
                        continue;
                    dx = entity.x - e.x;
                    dy = (entity.y - entity.speed) - e.y;

                    if (detectCollision(entity, e.solidArea, dx, dy)) {
                        entity.collisionOn = true;
                        if (entity instanceof Player && e instanceof Monster && !entity.invulnerable) {
                            entity.currentHealth -= e.dmg;
                            entity.invulnerable = true;
                            entity.invulnerable_tick = 0;
                        }
                    }                    
                }
                break;

            case "down":
                entityBotRow = Math.min((entityBotY + entity.speed)/GamePanel.tileSize, GamePanel.rowNum-1);
                
                tileNW = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityTopRow][entityLeftCol]];
                tileNE = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityTopRow][entityRightCol]];
                tileSW = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityBotRow][entityLeftCol]];
                tileSE = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityBotRow][entityRightCol]];
                
                // North-west tile
                dx = entity.x - (entityLeftCol * GamePanel.tileSize);
                dy = (entity.y + entity.speed) - (entityTopRow * GamePanel.tileSize);
                if (detectCollision (entity, tileNW.solidArea, dx, dy)) entity.collisionOn = true;

                // North-east tile
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity, tileNE.solidArea, dx, dy)) entity.collisionOn = true;

                // South-west tile
                dx = entity.x - (entityLeftCol * GamePanel.tileSize);
                dy = (entity.y + entity.speed) - (entityBotRow * GamePanel.tileSize);
                if (detectCollision (entity, tileSW.solidArea, dx, dy)) entity.collisionOn = true;

                // South-east tile
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity, tileSE.solidArea, dx, dy)) entity.collisionOn = true;

                for (Entity e : TileManager.currentRoom.entityList) {
                    if (e.equals(entity))
                        continue;
                    dx = entity.x - e.x;
                    dy = (entity.y + entity.speed) - e.y;
                    if (detectCollision(entity, e.solidArea, dx, dy)) {
                        entity.collisionOn = true;
                        if (entity instanceof Player && e instanceof Monster && !entity.invulnerable) {
                            entity.currentHealth -= e.dmg;
                            entity.invulnerable = true;
                            entity.invulnerable_tick = 0;
                        }
                    }    
                }      
                break;

            case "left":
                entityLeftCol = Math.max((entityLeftX - entity.speed)/GamePanel.tileSize, 0);
                
                tileNW = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityTopRow][entityLeftCol]];
                tileNE = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityTopRow][entityRightCol]];
                tileSW = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityBotRow][entityLeftCol]];
                tileSE = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityBotRow][entityRightCol]];
                
                // North-west tile
                dx = (entity.x - entity.speed) - (entityLeftCol * GamePanel.tileSize);
                dy = entity.y - (entityTopRow * GamePanel.tileSize);
                if (detectCollision (entity, tileNW.solidArea, dx, dy)) entity.collisionOn = true;

                // North-east tile
                dx = (entity.x - entity.speed) - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity, tileNE.solidArea, dx, dy)) entity.collisionOn = true;

                // South-west tile
                dx = (entity.x - entity.speed) - (entityLeftCol * GamePanel.tileSize);
                dy = entity.y - (entityBotRow * GamePanel.tileSize);
                if (detectCollision (entity, tileSW.solidArea, dx, dy)) entity.collisionOn = true;

                // South-east tile
                dx = (entity.x - entity.speed) - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity, tileSE.solidArea, dx, dy)) entity.collisionOn = true;  
                
                for (Entity e : TileManager.currentRoom.entityList) {
                    if (e.equals(entity))
                        continue;
                    dx = (entity.x - entity.speed) - e.x;
                    dy = entity.y - e.y;
                    if (detectCollision(entity, e.solidArea, dx, dy)) {
                        entity.collisionOn = true;
                        if (entity instanceof Player && e instanceof Monster && !entity.invulnerable) {
                            entity.currentHealth -= e.dmg;
                            entity.invulnerable = true;
                            entity.invulnerable_tick = 0;
                        }
                    }     
                }
                break;

            case "right":
                entityRightCol = Math.min((entityRightX + entity.speed)/GamePanel.tileSize, GamePanel.colNum-1);
                
                tileNW = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityTopRow][entityLeftCol]];
                tileNE = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityTopRow][entityRightCol]];
                tileSW = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityBotRow][entityLeftCol]];
                tileSE = TileManager.tiles[TileManager.currentRoom.roomTileNum[entityBotRow][entityRightCol]];
                
                // North-west tile
                dx = (entity.x + entity.speed) - (entityLeftCol * GamePanel.tileSize);
                dy = entity.y - (entityTopRow * GamePanel.tileSize);
                if (detectCollision (entity, tileNW.solidArea, dx, dy)) entity.collisionOn = true;

                // North-east tile
                dx = (entity.x + entity.speed) - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity, tileNE.solidArea, dx, dy)) entity.collisionOn = true;

                // South-west tile
                dx = (entity.x + entity.speed) - (entityLeftCol * GamePanel.tileSize);
                dy = entity.y - (entityBotRow * GamePanel.tileSize);
                if (detectCollision (entity, tileSW.solidArea, dx, dy)) entity.collisionOn = true;

                // South-east tile
                dx = (entity.x + entity.speed) - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity, tileSE.solidArea, dx, dy)) entity.collisionOn = true;    
                
                for (Entity e : TileManager.currentRoom.entityList) {
                    if (e.equals(entity))
                        continue;
                    dx = (entity.x + entity.speed) - e.x;
                    dy = entity.y - e.y;
                    if (detectCollision(entity, e.solidArea, dx, dy)) {
                        entity.collisionOn = true;
                        if (entity instanceof Player && e instanceof Monster && !entity.invulnerable) {
                            entity.currentHealth -= e.dmg;
                            entity.invulnerable = true;
                            entity.invulnerable_tick = 0;
                        }
                    }    
                }
                break;
        }
    }
}
