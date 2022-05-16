package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp=gp;
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

        int tileNum1, tileNum2;
        switch(entity.direction){
            case "up":
            entityTopRow = (entityTopY - entity.speed)/gp.tileSize;
            tileNum1 = gp.tM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tM.mapTileNum[entityRightCol][entityTopRow];
            if(gp.tM.tiles[tileNum1].collision || gp.tM.tiles[tileNum2].collision){
                entity.collisionOn=true;
            }
            break;

            case "down":
            entityBotRow = (entityBotY + entity.speed)/gp.tileSize;
            tileNum1 = gp.tM.mapTileNum[entityLeftCol][entityBotRow];
            tileNum2 = gp.tM.mapTileNum[entityRightCol][entityBotRow];
            if(gp.tM.tiles[tileNum1].collision || gp.tM.tiles[tileNum2].collision){
                entity.collisionOn=true;
            }
            break;

            case "left":
            entityLeftCol = (entityLeftX - entity.speed)/gp.tileSize;
            tileNum1 = gp.tM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tM.mapTileNum[entityLeftCol][entityBotRow];
            if(gp.tM.tiles[tileNum1].collision || gp.tM.tiles[tileNum2].collision){
                entity.collisionOn=true;
            }
            break;

            case "right":
            entityRightCol = (entityRightX + entity.speed)/gp.tileSize;
            tileNum1 = gp.tM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tM.mapTileNum[entityRightCol][entityBotRow];
            if(gp.tM.tiles[tileNum1].collision || gp.tM.tiles[tileNum2].collision){
                entity.collisionOn=true;
            }
            break;
        }
    }
}
