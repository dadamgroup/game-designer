package gui;

import javax.swing.*;
import gamemodel.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel containing ScreenView that allows to edit an individual screen.
 * @author  Ata Deniz Aydin
 * @author  Deniz Alkislar
 * @author  Akant Atilgan
 * @version 18/04/16
 */
public class ScreenEditController extends JPanel implements ScreenController
{
    Game game;
    PlayableScreen screen;
    ScreenView screenView;
    EditScrollPaneRight scrollPaneRight;
    EditScrollPaneLeft scrollPaneLeft;
    EditScreenOptions screenOptions;
    ScreenComponent comp;

    public ScreenEditController(PlayableScreen screen)
    {
        this.screen = screen;
        game = screen.getParent();

        screenView = new ScreenView(this, screen);
        add(screenView);

        scrollPaneRight = new EditScrollPaneRight(this);
        scrollPaneLeft = new EditScrollPaneLeft(this);
        screenOptions = new EditScreenOptions(this);

        setLayout(new BorderLayout());

        add(screenView, BorderLayout.CENTER);
        add(scrollPaneRight, BorderLayout.EAST);
        add(scrollPaneLeft, BorderLayout.WEST);
        add(screenOptions, BorderLayout.SOUTH);

        //setPreferredSize (new Dimension (504, 264));

        // have screenView at the center of the panel,
        // and a pane of components to add to the left
        screenView.addMouseListener(new ComponentListener());
        // screenView.setFocusable(true);
    }

    public GamePlayer getPlayer()
    {
        return null;
    }

    // update whether to show grid
    public void updateShowGrid(boolean shouldShowGrid)
    {
        screenView.setShowGrid(shouldShowGrid);
    }

    // receive
    public void setSelectedComponent(ScreenComponent comp)
    {
        if (comp instanceof ScreenObject && ((ScreenObject) comp).isBackground())
        {
            comp.accept(screenView);
            repaint();
        }
        else
        {
            this.comp = comp.copy();
        }
    }

    private class ComponentListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            Point objectAddDeletePos = e.getPoint();

            screenView.setFocusable(true);
            screenView.requestFocusInWindow();

            int rX = (int) objectAddDeletePos.getX() / screenView.IMAGE_HEIGHT;
            int rY = (int) objectAddDeletePos.getY() / screenView.IMAGE_HEIGHT;

            if (screenOptions.shouldDelete())
            {
                ScreenComponent temp = screen.findFirstComponentAt(rX, rY);
                if (temp != null)
                    screenView.removeComponent(temp);

            }
            else if (comp != null && screen.contains(comp, rX, rY)) // add component to screen // && screen.canPlaceComponent(comp, rX, rY))
            {
                if (!(comp instanceof ScreenObject && ((ScreenObject) comp).isBackground()))
                {
                    comp = comp.copy();
                    comp.setPosition(new Point(rX, rY));
                }
                comp.accept(screenView);
            }

            repaint();
        }
    }

}
