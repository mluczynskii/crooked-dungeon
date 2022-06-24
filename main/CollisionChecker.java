package main;

import entity.Entity;
import world.Tile;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import world.TileManager;
import entity.*;
import pickup.*;
import world.Room;
import world.Prop;

public class CollisionChecker {
    private static class Distance {
        public int x, y;
        public Distance (int dx, int dy) {
            this.x = dx;
            this.y = dy;
        }
    }
    private static boolean detectCollision (Area one, Area two, Distance d) {
        Area a = new Area(one);

        // We need to translate one of the solidAreas because they dont have the same point of reference

        // Distance is calculated from A to B
        // A ################## B ##################
        // #                  # #   xxxxxxx        #  
        // #                  # #   x two x        #
        // #     xxxxxxx      # #   xxxxxxx        #
        // #     x one x      # #                  #
        // #     xxxxxxx      # #                  #
        // #                  # #                  #
        // #################### ####################
        //
        // AB ################# C ######################################
        // #     xxxxxxx      # #                       xxxxxxx        #
        // #     x two x      # #                       x two x        #
        // #   xx=====xx      # #     xxxxxxx           xxxxxxx        #
        // #   x one x        # #     x one x                          #
        // #   xxxxxxx        # #     xxxxxxx                          #
        // #                  # #                                      #
        // #################### ########################################
        // WITHOUT  TRANSLATION             WITH TRANSLATION

        AffineTransform matrix = new AffineTransform();
        matrix.translate(d.x, d.y);
        Area b = two.createTransformedArea(matrix);

        a.intersect(b); // Sets [a] to intersection of [a] and [b]
        return a.isEmpty() ? false : true; // If intersection is empty then that means there is no collision
    }
    public static boolean checkSpawn (Monster monster, Room room) {
        checkEntity(monster, room);
        if (monster.collisionOn == true)
            return false;
        return true;
    }
    public static boolean checkSpawn (Prop prop, Room room) {
        // TODO: Implement logic
        return true;
    }
    static Distance calculateDistance (Entity origin, int x, int y) {
        int dx = 0, dy = 0;
        switch (origin.direction) {
            case "up":
                dx = x - origin.x;
                dy = y - (origin.y - origin.speed);
                break;
            case "down":
                dx = x - origin.x;
                dy = y - (origin.y + origin.speed);
                break;
            case "left":
                dx = x - (origin.x - origin.speed);
                dy = y - origin.y;
                break;
            case "right":
                dx = x - (origin.x + origin.speed);
                dy = y - origin.y;
                break;
            default:
                System.out.println("Unknown direction: " + origin.direction);
        }
        return new Distance (dx, dy);
    }
    public static void checkPickup (Player player, Room room) {
        for (Pickup pickup : room.pickupList) {
            if (detectCollision(player.solidArea, pickup.solidArea, new Distance (player.x - pickup.x, player.y - pickup.y)))
                pickup.getPickedUp(player);
        }
    }
    public static void checkMonster (Monster monster, Room room) {
        checkEntity(monster, room);
        Player player = room.player;
        boolean flag;
        if (player.attackArea != null) {
            flag = detectCollision(player.attackArea, monster.solidArea, new Distance (monster.x - player.x, monster.y - player.y));
            if (flag && player.attacking == true)
                monster.takeDamage(player);
        }
        flag = detectCollision(player.solidArea, monster.solidArea, calculateDistance(monster, player.x, player.y));
        if (flag && player.invulnerable == false)
            player.takeDamage(monster);
    }
    public static void checkPlayer (Player player, Room room) {
        checkEntity (player, room);
        checkPickup(player, room);
        for (Monster monster : room.monsterList) {
            boolean flag;
            if (player.attackArea != null) {
                flag = detectCollision(player.attackArea, monster.solidArea, new Distance (player.x - monster.x, player.y - monster.y));
                if (flag && player.attacking == true)
                    monster.takeDamage(player);
            }
            flag = detectCollision(player.solidArea, monster.solidArea, calculateDistance(player, monster.x, monster.y));
            if (flag && player.invulnerable == false)
                player.takeDamage(monster);
        }
    }
    public static void findInteraction (Player player, Room room) {
        for (NPC npc : room.npcList) {
            if (detectCollision(player.solidArea, npc.solidArea, calculateDistance(player, npc.x, npc.y)))
                player.interactionNPC = npc;
        }
    }
    static void checkEntity (Entity entity, Room room) {
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

        // Calculate possibly changed indexes after shifting the hitbox according to current direction
        switch (entity.direction) {
            case "up":
                entityTopRow = Math.max((entityTopY - entity.speed)/GamePanel.tileSize, 0);
                entityBotRow = Math.max((entityBotY - entity.speed)/GamePanel.tileSize, 0);
                break;
            case "down":
                entityTopRow = Math.min((entityTopY + entity.speed)/GamePanel.tileSize, GamePanel.rowNum-1);
                entityBotRow = Math.min((entityBotY + entity.speed)/GamePanel.tileSize, GamePanel.rowNum-1);
                break;
            case "left":
                entityLeftCol = Math.max((entityLeftX - entity.speed)/GamePanel.tileSize, 0);
                entityRightCol = Math.max((entityRightX - entity.speed)/GamePanel.tileSize, 0);
                break;
            case "right":
                entityLeftCol = Math.min((entityLeftX + entity.speed)/GamePanel.tileSize, GamePanel.colNum-1);
                entityRightCol = Math.min((entityRightX + entity.speed)/GamePanel.tileSize, GamePanel.colNum-1);
                break;
            default:
                System.out.println("Unknown direction: " + entity.direction);
                break;
        }

        // Save adjacent tiles
        Tile NW = TileManager.tiles[room.roomTileNum[entityTopRow][entityLeftCol]];
        Tile NE = TileManager.tiles[room.roomTileNum[entityTopRow][entityRightCol]];
        Tile SW = TileManager.tiles[room.roomTileNum[entityBotRow][entityLeftCol]];
        Tile SE = TileManager.tiles[room.roomTileNum[entityBotRow][entityRightCol]];

        // Get adjacent tiles (x, y) positions
        int top = entityTopRow * GamePanel.tileSize;
        int bot = entityBotRow * GamePanel.tileSize;
        int left = entityLeftCol * GamePanel.tileSize;
        int right = entityRightCol * GamePanel.tileSize;

        if (detectCollision(entity.solidArea, NW.solidArea, calculateDistance(entity, left, top)) || // Check north-west tile
            detectCollision(entity.solidArea, NE.solidArea, calculateDistance(entity, right, top)) || // Check north-east tile
            detectCollision(entity.solidArea, SW.solidArea, calculateDistance(entity, left, bot)) || // Check south-west tile
            detectCollision(entity.solidArea, SE.solidArea, calculateDistance(entity, right, bot))) { // Check south-east tile
                entity.collisionOn = true;
        }

        // Check collision with other entities
        for (Entity e : room.entityList) {
            if (entity.equals(e))
                continue;
            if (detectCollision(entity.solidArea, e.solidArea, calculateDistance(entity, e.x, e.y))) 
                entity.collisionOn = true;                  
        }
    }
}
