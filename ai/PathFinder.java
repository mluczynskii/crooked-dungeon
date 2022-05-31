package ai;

import java.util.ArrayList;

import main.GamePanel;

public class PathFinder { // A* pathfinding algorithm
    GamePanel gp;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> path = new ArrayList<>();
    Node start, goal, current;
    boolean reachedGoal = false;
    int step = 0;

    public PathFinder (GamePanel gp) {
        this.gp = gp;
        initNodes();
    }
    void initNodes () {
        nodes = new Node[GamePanel.rowNum][GamePanel.colNum];
        for (int row = 0; row < GamePanel.rowNum; row++) {
            for (int col = 0; col < GamePanel.colNum; col++) {
                nodes[row][col] = new Node(row, col);
            }
        }
    }
    void resetNodes () {
        for (int row = 0; row < GamePanel.rowNum; row++) {
            for (int col = 0; col < GamePanel.colNum; col++) {
                nodes[row][col].open = false;
                nodes[row][col].unpassable = false;
                nodes[row][col].checked = false;
            }
        }
        openList.clear();
        path.clear();
        reachedGoal = false;
        step = 0;
    }
    void setNodes (int startRow, int startCol, int goalRow, int goalCol) {
        resetNodes();
        start = nodes[startRow][startCol];
        goal = nodes[goalRow][goalCol];
        current = start;
        openList.add(current);
        for (int row = 0; row < GamePanel.rowNum; row++) {
            for (int col = 0; col < GamePanel.colNum; col++) {
                // TODO: check if location is reachable
                getCost(nodes[row][col]);
            }
        }
    }
    void getCost (Node node) {
        // G cost
        int dx = Math.abs(node.col - start.col);
        int dy = Math.abs(node.row - start.row);
        node.gCost = dx + dy;
        // H cost
        dx = Math.abs(node.col - goal.col);
        dy = Math.abs(node.row - goal.row);
        node.hCost = dx + dy;
        // F cost
        node.fCost = node.gCost + node.hCost;
    }
    public boolean search () {
        while (!reachedGoal && step < 500) {
            int row = current.row;
            int col = current.col;

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
        if (!node.open && !node.checked && !node.unpassable) {
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
