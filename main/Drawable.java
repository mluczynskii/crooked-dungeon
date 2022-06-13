package main;

import java.awt.Graphics2D;

public interface Drawable extends Comparable<Drawable> {
    public int height ();
    public void draw (Graphics2D g);
}
