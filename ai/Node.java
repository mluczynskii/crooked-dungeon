package ai;

public class Node {
    Node parent;
    int fCost, gCost, hCost;
    boolean open, unpassable, checked;
    public int row, col;

    public Node (int row, int col) {
        this.row = row;
        this.col = col;
    }
}
