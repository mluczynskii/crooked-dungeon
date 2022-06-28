package main;

import entity.Entity;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
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
    public static boolean checkPropSpawn (Prop prop, Room room) {
        if (detectCollision(prop.params.solidArea, room.solidAreaMap, new Distance(prop.x, prop.y)))
            return false;
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
        // Check collision with tiles
        if (detectCollision(entity.solidArea, room.solidAreaMap, calculateDistance(entity, 0, 0)))
            entity.collisionOn = true;
        // Check collision with other entities
        for (Entity e : room.entityList) {
            if (entity.equals(e))
                continue;
            if (detectCollision(entity.solidArea, e.solidArea, calculateDistance(entity, e.x, e.y))) 
                entity.collisionOn = true;                  
        }
        // Check collision with props
        for (Prop prop : room.propList) {
            if (detectCollision(entity.solidArea, prop.params.solidArea, calculateDistance(entity, prop.x, prop.y)))
                entity.collisionOn = true;
        }
    }
}
