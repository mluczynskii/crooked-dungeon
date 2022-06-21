package main;

import entity.Entity;
import world.Tile;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import world.TileManager;
import entity.*;
import pickup.*;

public class CollisionChecker {
    private static boolean detectCollision (Area one, Area two, int dx, int dy) {
        Area a = new Area(one);

        // We need to translate one of the solidAreas because they dont have the same point of reference
        AffineTransform matrix = new AffineTransform();
        matrix.translate(dx, dy);
        Area b = two.createTransformedArea(matrix);

        a.intersect(b); // Sets [a] to intersection of [a] and [b]
        return a.isEmpty() ? false : true; // If intersection is empty then that means there is no collision
    }
    static int[] calculateDistance (Entity origin, Entity target) {
        int[] d = {0, 0};
        switch (origin.direction) {
            case "up":
                d[0] = target.x - origin.x;
                d[1] = target.y - (origin.y - origin.speed);
                break;
            case "down":
                d[0] = target.x - origin.x;
                d[1] = target.y - (origin.y + origin.speed);
                break;
            case "left":
                d[0] = target.x - (origin.x - origin.speed);
                d[1] = target.y - origin.y;
                break;
            case "right":
                d[0] = target.x - (origin.x + origin.speed);
                d[1] = target.y - origin.y;
                break;
        }
        return d;
    }
    public static void checkPickup (Player player) {
        int dx, dy;
        for (Pickup pickup : TileManager.currentRoom.pickupList) {
            dx = pickup.x - player.x; dy = pickup.y - player.y;
            if (detectCollision(player.solidArea, pickup.solidArea, dx, dy))
                pickup.getPickedUp(player);
        }
    }
    public static void checkMonster (Monster monster) {
        checkEntity(monster);
        Player player = TileManager.currentRoom.player;
        int[] d = calculateDistance(monster, player);
        int dx = d[0]; int dy = d[1];
        if (detectCollision(player.solidArea, monster.solidArea, dx, dy) && player.invulnerable == false)
            player.takeDamage(monster);
    }
    public static void checkPlayer (Player player) {
        checkEntity (player);
        int dx, dy;
        for (Monster monster : TileManager.currentRoom.monsterList) {
            int[] d = calculateDistance(player, monster);
            dx = d[0]; dy = d[1];
            if (detectCollision(player.solidArea, monster.solidArea, dx, dy) && player.invulnerable == false)
                player.takeDamage(monster);
        }
    }
    public static void findInteraction (Player player) {
        int dx, dy;
        for (NPC npc : TileManager.currentRoom.npcList) {
            int[] d = calculateDistance(player, npc);
            dx = d[0]; dy = d[1];
            if (detectCollision(player.solidArea, npc.solidArea, dx, dy))
                player.interactionNPC = npc;
        }
    }
    public static void checkEntity (Entity entity) {
        Rectangle bounds = entity.solidArea.getBounds();

        // Positions of each side of the bounding rectangle
        int entityLeftX = entity.x + bounds.x;
        int entityRightX = entity.x + bounds.x + bounds.width;
        int entityTopY = entity.y + bounds.y;
        int entityBotY = entity.y + bounds.y + bounds.height;

        // Indexes used to get adjacent tiles
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
                if (detectCollision (entity.solidArea, tileNW.solidArea, dx, dy)) entity.collisionOn = true;

                // North-east tile
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileNE.solidArea, dx, dy)) entity.collisionOn = true;

                // South-west tile
                dx = entity.x - (entityLeftCol * GamePanel.tileSize);
                dy = (entity.y - entity.speed) - (entityBotRow * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileSW.solidArea, dx, dy)) entity.collisionOn = true;

                // South-east tile
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileSE.solidArea, dx, dy)) entity.collisionOn = true;
                
                for (Entity e : TileManager.currentRoom.entityList) {
                    if (entity.equals(e))
                        continue;
                    int[] d = calculateDistance(entity, e);
                    dx = d[0]; dy = d[1];

                    if (detectCollision(entity.solidArea, e.solidArea, dx, dy)) entity.collisionOn = true;                  
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
                if (detectCollision (entity.solidArea, tileNW.solidArea, dx, dy)) entity.collisionOn = true;

                // North-east tile
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileNE.solidArea, dx, dy)) entity.collisionOn = true;

                // South-west tile
                dx = entity.x - (entityLeftCol * GamePanel.tileSize);
                dy = (entity.y + entity.speed) - (entityBotRow * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileSW.solidArea, dx, dy)) entity.collisionOn = true;

                // South-east tile
                dx = entity.x - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileSE.solidArea, dx, dy)) entity.collisionOn = true;

                for (Entity e : TileManager.currentRoom.entityList) {
                    if (entity.equals(e))
                        continue;
                    int[] d = calculateDistance(entity, e);
                    dx = d[0]; dy = d[1];

                    if (detectCollision(entity.solidArea, e.solidArea, dx, dy)) entity.collisionOn = true;                  
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
                if (detectCollision (entity.solidArea, tileNW.solidArea, dx, dy)) entity.collisionOn = true;

                // North-east tile
                dx = (entity.x - entity.speed) - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileNE.solidArea, dx, dy)) entity.collisionOn = true;

                // South-west tile
                dx = (entity.x - entity.speed) - (entityLeftCol * GamePanel.tileSize);
                dy = entity.y - (entityBotRow * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileSW.solidArea, dx, dy)) entity.collisionOn = true;

                // South-east tile
                dx = (entity.x - entity.speed) - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileSE.solidArea, dx, dy)) entity.collisionOn = true;  
                
                for (Entity e : TileManager.currentRoom.entityList) {
                    if (entity.equals(e))
                        continue;
                    int[] d = calculateDistance(entity, e);
                    dx = d[0]; dy = d[1];

                    if (detectCollision(entity.solidArea, e.solidArea, dx, dy)) entity.collisionOn = true;                  
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
                if (detectCollision (entity.solidArea, tileNW.solidArea, dx, dy)) entity.collisionOn = true;

                // North-east tile
                dx = (entity.x + entity.speed) - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileNE.solidArea, dx, dy)) entity.collisionOn = true;

                // South-west tile
                dx = (entity.x + entity.speed) - (entityLeftCol * GamePanel.tileSize);
                dy = entity.y - (entityBotRow * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileSW.solidArea, dx, dy)) entity.collisionOn = true;

                // South-east tile
                dx = (entity.x + entity.speed) - (entityRightCol * GamePanel.tileSize);
                if (detectCollision (entity.solidArea, tileSE.solidArea, dx, dy)) entity.collisionOn = true;    
                
                for (Entity e : TileManager.currentRoom.entityList) {
                    if (entity.equals(e))
                        continue;
                    int[] d = calculateDistance(entity, e);
                    dx = d[0]; dy = d[1];

                    if (detectCollision(entity.solidArea, e.solidArea, dx, dy)) entity.collisionOn = true;                  
                }
                break;
        }
    }
}
