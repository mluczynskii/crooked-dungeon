package main;

import entity.Entity;
import world.Tile;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp=gp;
    }
    private void detectCollision (Entity entity, Tile tile1, Tile tile2) {
        if (tile1.solidArea.intersects(entity.solidArea) || tile2.solidArea.intersects(entity.solidArea)) {
            entity.collisionOn = true;
        }
    }
    public void checkTile(Entity entity){
        int entityLeftX = entity.x + entity.solidArea.x;
        int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.y + entity.solidArea.y;
        int entityBotY = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = Math.max(entityLeftX/gp.tileSize, 0);
        int entityRightCol = Math.min(entityRightX/gp.tileSize, gp.colNum-1);
        int entityTopRow = Math.max(entityTopY/gp.tileSize, 0);
        int entityBotRow = Math.min(entityBotY/gp.tileSize, gp.rowNum);

        int tileNum1, tileNum2;

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tM.mapTileNum[entityTopRow][entityRightCol];
                detectCollision(entity, gp.tM.tiles[tileNum1], gp.tM.tiles[tileNum2]);
                break;

            case "down":
                entityBotRow = (entityBotY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tM.mapTileNum[entityBotRow][entityLeftCol];
                tileNum2 = gp.tM.mapTileNum[entityBotRow][entityRightCol];
                detectCollision(entity, gp.tM.tiles[tileNum1], gp.tM.tiles[tileNum2]);
                break;

            case "left":
                entityLeftCol = (entityLeftX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tM.mapTileNum[entityBotRow][entityLeftCol];
                detectCollision(entity, gp.tM.tiles[tileNum1], gp.tM.tiles[tileNum2]);
                break;

            case "right":
                entityRightCol = (entityRightX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tM.mapTileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tM.mapTileNum[entityBotRow][entityRightCol];
                detectCollision(entity, gp.tM.tiles[tileNum1], gp.tM.tiles[tileNum2]);
                break;
        }
    }
}
