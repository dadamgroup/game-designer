package gui;

import gamemodel.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Deniz Alkislar on 21.4.2016.
 */
public class EditScrollPaneLeft extends JPanel
{
    ScreenEditController parent;
    String selectedComponent;

    public EditScrollPaneLeft(ScreenEditController parent)
    {
        ButtonGroup group;
        JScrollPane scrollPane;
        JRadioButton iconButton;
        JRadioButton addButton;
        JRadioButton addTextBox;
        JRadioButton addLabel;
        JPanel scrollPanel;

        this.parent = parent;

        setLayout(new GridLayout(2, 1));
        setPreferredSize(new Dimension(150, 504));

        //Add buttons to group and panel

        group = new ButtonGroup();
        scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(ObjectIconView.values().length, 1));

        // add icon buttons
        for (ObjectIconView icon : ObjectIconView.values())
        {
            if (icon.movable)
            {
                iconButton = new JRadioButton(icon.toString(), icon.getImage());
                iconButton.setName(icon.toString());
                iconButton.addActionListener(new IconListener(icon.icon));
                group.add(iconButton);
                scrollPanel.add(iconButton);
            }
        }

        // add buttons for other objects
        addButton = new JRadioButton("Button");
        addTextBox = new JRadioButton("Text Box");
        addLabel = new JRadioButton("Label");

        addButton.addActionListener(new ButtonListener());
        addTextBox.addActionListener(new TextBoxListener());
        addLabel.addActionListener(new LabelListener());

        group.add(addButton);
        group.add(addTextBox);
        group.add(addLabel);
        scrollPanel.add(addButton);
        scrollPanel.add(addTextBox);
        scrollPanel.add(addLabel);

        // create scroll pane for the panel of buttons
        scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(150, 250));

        add(scrollPane);

        add(new OptionsList(parent.screen.getOptions()));

        // TODO button can't be deleted, textbox can't be added or deleted

    }

    public String getSelectedComponent()
    {
        return selectedComponent;
    }

    class IconListener implements ActionListener
    {
        ObjectIcon icon;

        public IconListener(ObjectIcon icon)
        {
            this.icon = icon;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            parent.screenView.setMovable(icon);

            parent.repaint();
        }
    }

    class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JPanel panel = new JPanel();

            JComboBox comboBox = new JComboBox<>(parent.screen.getOptions().toArray());
            JTextField textField = new JTextField();

            panel.setLayout(new GridLayout(2, 2));

            panel.add(new JLabel("Name: "));
            panel.add(textField);
            panel.add(new JLabel("Options: "));
            panel.add(comboBox);

            if (JOptionPane.showConfirmDialog(null, panel, "Create button", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION)
            {
                ScreenButton button = new ScreenButton(parent.screen, textField.getText());
                button.setOption(comboBox.getSelectedItem().toString());
                parent.setSelectedComponent(button);
            }
        }
    }

    class TextBoxListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JPanel panel = new JPanel();

            JComboBox comboBox = new JComboBox<>(parent.screen.getParent().getVariables().toArray());
            JTextField textField = new JTextField();

            panel.setLayout(new GridLayout(2, 2));

            panel.add(new JLabel("Name: "));
            panel.add(textField);
            panel.add(new JLabel("Variables: "));
            panel.add(comboBox);

            if (JOptionPane.showConfirmDialog(null, panel, "Create text box", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION)
            {
                ScreenTextBox textBox = new ScreenTextBox(parent.screen, textField.getText());
                textBox.setVariable(comboBox.getSelectedItem().toString());
                parent.setSelectedComponent(textBox);
            }
        }
    }

    class LabelListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JPanel panel = new JPanel();

            JTextField textField = new JTextField();
            JTextField textField2 = new JTextField();

            panel.setLayout(new GridLayout(2, 2));

            panel.add(new JLabel("Name: "));
            panel.add(textField);
            panel.add(new JLabel("Text: "));
            panel.add(textField2);

            if (JOptionPane.showConfirmDialog(null, panel, "Create label", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION)
            {
                ScreenLabel label = new ScreenLabel(parent.screen, textField.getText(), textField2.getText());
                if (label.valid())
                    parent.setSelectedComponent(label);
            }
        }
    }

    class OptionsList extends JPanel
    {
        AbstractTableModel model;
        JTable table;
        JTextField nameField;
        JComboBox<Screen> screenField;

        public OptionsList(ArrayList<Option> options)
        {
            setLayout(new BorderLayout());

            add(new JLabel("Options"), BorderLayout.NORTH);

            model = new MyTableModel();
            table = new JTable(model);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new Dimension(150, 200));

            add(scrollPane);

            JPanel panel = new JPanel(new GridLayout(3, 2));

            panel.setPreferredSize(new Dimension(150, 50));

            nameField = new JTextField();
            System.out.println(parent.screen.getParent().getScreens());
            screenField = new JComboBox<>(parent.screen.getParent().getScreens().toArray(new Screen[0]));

            panel.add(new JLabel("Name: "));
            panel.add(nameField);
            panel.add(new JLabel("Screen: "));
            panel.add(screenField);

            JButton button = new JButton("Add");
            button.addActionListener(new AddListener());
            panel.add(button);

            button = new JButton("Delete");
            button.addActionListener(new DeleteListener());
            panel.add(button);

            add(panel, BorderLayout.SOUTH);
        }

        // TODO table does not update

        class AddListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // add option to screen and update combo box
                parent.screen.addOption(nameField.getText(), (Screen) screenField.getSelectedItem());

                repaint();
            }
        }

        // delete selected screen
        class DeleteListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.screen.removeOption((String) model.getValueAt(table.getSelectedRow(), 0));

                repaint();
            }
        }

        class MyTableModel extends AbstractTableModel
        {
            @Override
            public int getRowCount()
            {
                return parent.screen.getOptions().size();
            }

            @Override
            public int getColumnCount()
            {
                return 2;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex)
            {
                Option option = parent.screen.getOptions().get(rowIndex);

                if (option == null)
                    return null;

                if (columnIndex == 0)
                    return option.getName();

                if (columnIndex == 1)
                    return option.getScreen();

                return null;
            }
        }
    }

}
