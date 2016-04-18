package gamemodel;

/**
 * Created by admin on 4/3/16.
 */
public class ScreenButton extends ScreenComponent
{
    String option;
    String text;
    boolean visible; // if invisible, an object will call it from its keyboard listener
    boolean playing = false;

    // possibly rearrange arguments
    public ScreenButton(PlayableScreen par, String nam)
    {
        super(par, nam);
        option  = "";
        text    = nam;
        visible = true;
        width = 3;
    }

    // getters, setters

    public String getOption() { return option; }
    public void setOption(String option) { this.option = option; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean getVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public void playing() { playing = true; }

    // call parent screen if clicked
    // called by game's action listeners
    public void clicked(GamePlayer player)
    {
        if (playing)
        {
            playing = false;
            parent.toPlayer(player, parent.getOption(option));
        }
    }

    // return true if not visible, call super.isCompatible() otherwise
    @Override
    public boolean isCompatible(ScreenComponent other, int x, int y)
    {
        return !visible || super.isCompatible(other, x, y);
    }

    @Override
    public void accept(ComponentVisitor visitor)
    {
        visitor.visit(this);
    }

    public boolean valid()
    {
        return parent.hasOption(option);
    }
}