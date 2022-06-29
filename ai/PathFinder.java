package ai;

import java.util.ArrayList;
import java.util.HashMap;
import world.Room;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import world.Prop;
import entity.Entity;
import main.GamePanel;

// Work in progress
public class PathFinder { // A* pathfinding algorithm
    public class Node {
        Node parent;
        int fCost, gCost, hCost;
        boolean open, blocked, checked;
        public int x, y;
        public Node (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    /*private class Info { // This class is used as a key for nodeDict
        Class<?> c;
        Room room;
        public Info (Class<?> c, Room room) {
            this.c = c;
            this.room = room;
        }
    }*/
    Room room; // Current room

    // Attempt at optimization...?
    Area map; // Map of all solid areas in current room
    static HashMap<Room, Area> mapDict = new HashMap<>(); // This is used so the area can get calculated only once

    Node[][] nodes; // Two dimensional Node grid
    static int width = (int)(GamePanel.screenWidth/10), height = (int)(GamePanel.screenHeight/10);
    //static HashMap<Info, Node[][]> nodeDict = new HashMap<>(); // Same as mapDict, calculating blocked nodes probably takes very long (guess)

    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> path = new ArrayList<>();
    Node start, goal, current;
    boolean reachedGoal = false;
    int step = 0;
    Entity entity;

    public PathFinder (Room room, Entity entity) {
        this.room = room;
        this.entity = entity;

        // Setup map solidAreas
        Area a = mapDict.get(room);
        if (a != null) {
            //System.out.println("Found map for: " + room); // Debug
            map = a;
        }
        else initMap();

        //Node[][] b = nodeDict.get(key)
        initNodes();
    }
    void initMap () {
        this.map = new Area();
        // First, get solidAreas of all tiles
        this.map.add(room.solidAreaMap);
        // Then, solidAreas of all props
        for (Prop p : room.propList) {
            AffineTransform matrix = new AffineTransform();
            matrix.translate(p.x, p.y);
            Area clone = new Area(p.params.solidArea);
            clone.transform(matrix);
            this.map.add(clone);
        }
        // Save the result
        mapDict.put(room, this.map);
    }
    void initNodes () {
        nodes = new Node[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                nodes[row][col] = new Node (col, row);
                checkBlocked(row, col);
            }
        }
    }
    void resetNodes () {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                nodes[row][col].open = false;
                nodes[row][col].checked = false;
            }
        }
        openList.clear();
        path.clear();
        reachedGoal = false;
        step = 0;
    }
    void checkBlocked (int row, int col) { // Check if node is reachable by an entity
        Area clone = new Area (entity.solidArea);
        AffineTransform matrix = new AffineTransform ();
        matrix.translate(entity.x, entity.y);
        clone.transform(matrix);
        clone.intersect(this.map);
        if (clone.isEmpty() == false)
            nodes[row][col].blocked = true;
    }
    public void setNodes (int startRow, int startCol, int goalRow, int goalCol) {
        resetNodes();
        start = nodes[startRow][startCol];
        goal = nodes[goalRow][goalCol];
        current = start;
        openList.add(current);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                getCost(nodes[row][col]);
            }
        }
    }
    void getCost (Node node) {
        // G cost
        int dx = Math.abs(node.x - start.x);
        int dy = Math.abs(node.y - start.y);
        node.gCost = dx + dy;
        // H cost
        dx = Math.abs(node.x - goal.x);
        dy = Math.abs(node.y - goal.y);
        node.hCost = dx + dy;
        // F cost
        node.fCost = node.gCost + node.hCost;
    }
    public boolean search () {
        while (!reachedGoal && step < 1000) {
            int row = current.y;
            int col = current.x;

            current.checked = true;
            openList.remove(current);

            if (row - 1 >= 0) { open(nodes[row-1][col]); }
            if (row + 1 >= 0) { open(nodes[row+1][col]); }
            if (col - 1 >= 0) { open(nodes[row][col-1]); }
            if (col + 1 >= 0) { open(nodes[row][col+1]); }

            Node currentBest = openList.get(0);
            Node node;

            for (int i = 0; i < openList.size(); i++) {
                node = openList.get(i);
                
                if (node.fCost < currentBest.fCost) {
                    currentBest = node;
                }
                else if (node.fCost == currentBest.fCost) {
                    if (node.gCost < currentBest.gCost)
                        currentBest = node;
                }
            }

            if (openList.size() == 0)
                break;

            current = currentBest;
            if (current == goal) {
                reachedGoal = true;
                trace();
            }
            step++;
        }
        return reachedGoal;
    }
    void open (Node node) {
        if (!node.open && !node.checked && !node.blocked) {
            node.open = true;
            node.parent = current;
            openList.add(node);
        }
    }
    void trace () {
        Node current = goal;
        while (current != start) {
            path.add(0, current);
            current = current.parent;
        }
    }
}
