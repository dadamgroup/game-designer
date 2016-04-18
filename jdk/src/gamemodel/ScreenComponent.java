package gamemodel;

import java.io.Serializable;
import java.awt.Point;

/**
 * Created by admin on 4/3/16.
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

    public Point getPosition() { return position; }

    public void setPosition(Point position) { this.position = position; }

    public PlayableScreen getParent() { return parent; }

    // returns whether other's position is compatible with this
    // can be overridden, but by default returns false if the positions are equal
    // change if other components contain information about their size
    // perhaps make abstract
    public boolean isCompatible(ScreenComponent other, int x, int y)
    {
        return (x < position.getX() || x >= position.getX() + width)
            || (y < position.getY() || y >= position.getY() + height);
    }

    // what to do in leaving a screen, nothing by default
    public void leavingScreen(GamePlayer player)
    {

    }

    public void playing() {}

    public abstract void accept(ComponentVisitor visitor);

    public abstract boolean valid();
}