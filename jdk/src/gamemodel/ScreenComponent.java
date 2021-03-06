package gamemodel;

import java.io.Serializable;
import java.awt.Point;

/**
 * Abstract class to represent components on a playable screen.
 * @author  Ata Deniz Aydin
 * @version 17/04/16
 */
public abstract class ScreenComponent implements Serializable
{
    String name;
    Point  position;
    PlayableScreen parent;
    int height = 1, width = 1; // by default, can be changed for other components

    // null everything
    public ScreenComponent()
    {

    }

    // point initially null, set by editor
    public ScreenComponent(PlayableScreen par, String nam)
    {
        parent = par;
        name = nam;
        position = new Point(0, 0);
    }

    // copy constructor
    public ScreenComponent(ScreenComponent other)
    {
        this.name = other.name;
        this.position = new Point(other.position); // copy for movement
        this.parent = other.parent;
        this.height = other.height;
        this.width  = other.width;
    }

    public abstract ScreenComponent copy();

    public ScreenComponent copy(PlayableScreen parent)
    {
        ScreenComponent comp = copy();
        comp.parent = parent;
        return comp;
    }

    public boolean equals(Object other)
    {
        return other != null
            && other instanceof ScreenComponent
            && ((ScreenComponent) other).name.equals(name);
            // && ((ScreenComponent) other).position.equals(position);
    }

    public String getName() { return name; }

    public int getHeight() { return height; }

    public int getWidth() { return width; }

    public Point getPosition() { return position; }

    public void setPosition(Point position)
    {
        this.position = position;
    }

    public PlayableScreen getParent() { return parent; }

    // returns whether other's position is compatible with this
    // can be overridden, but by default returns false if the positions are equal
    // change if other components contain information about their size
    // perhaps make abstract
    public boolean isCompatible(ScreenComponent other, int x, int y)
    {
        return !contains(x, y);
    }

    // return whether component contains (x,y)
    public boolean contains(int x, int y)
    {
        return x >= position.getX()
            && x < position.getX() + width
            && y >= position.getY()
            && y < position.getY() + height;
    }

    // what to do in leaving a screen, nothing by default
    public void leavingScreen(GamePlayer player)
    {

    }

    public abstract void accept(ComponentVisitor visitor);

    // return whether component is set up correctly
    public abstract boolean valid();
}