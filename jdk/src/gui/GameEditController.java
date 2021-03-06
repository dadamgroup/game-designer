package gui;

import gamemodel.*;
import javax.swing.*;
import java.awt.*;

/**
 * Panel allowing the user to edit a game.
 * @author  Ata Deniz Aydin
 * @author  Demir Topaktas
 * @version 28/04/16
 */
public class GameEditController extends JPanel
{
    Game game;
    GameView gameView;
    GameEditTabbedPane pane;
    VariableList vars;
    boolean deleting = false;

    public GameEditController(Game game)
    {
        this.game = game;

        gameView = new GameView(this);

        pane = new GameEditTabbedPane(this);
        vars = new VariableList(game);

        // add components to panel
        setPreferredSize(new Dimension(1000, 650));
        setLayout(new BorderLayout());
        add(pane);

        JPanel leftPanel = new JPanel();

        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(new ScreenAddPanel(this), BorderLayout.NORTH);
        leftPanel.add(vars, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);
    }

    // methods

    // create new assignment screen
    public void createAssignScreen(String name, String varName, String newValue, String newScreen)
    {
        if (name == null || varName == null || newValue == null || newScreen == null || game.getScreen(name) != null)
            return;

        AssignScreen assign = new AssignScreen(game, name);

        assign.setVariable(varName);
        assign.setNewValue(newValue);
        assign.addOption("default", game.getScreen(newScreen));

        game.addScreen(assign);
        gameView.addScreen(assign);

        repaint();
    }

    public void createCondScreen(String name, String pred, String trueScreen, String falseScreen)
    {
        if (name == null || pred == null || trueScreen == null || falseScreen == null || game.getScreen(name) != null)
            return;

        CondScreen cond = new CondScreen(game, name);

        cond.setPred(pred);
        cond.addOption("true", game.getScreen(trueScreen));
        cond.addOption("false", game.getScreen(falseScreen));

        game.addScreen(cond);
        gameView.addScreen(cond);

        repaint();

    }
    public void createPlayableScreen(String name)
    {
        if (name == null || game.getScreen(name) != null)
            return;

        PlayableScreen screen = new PlayableScreen(game, name);

        game.addScreen(screen);
        gameView.addScreen(screen);

        repaint();
    }
}
