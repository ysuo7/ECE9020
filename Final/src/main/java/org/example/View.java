package org.example;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class View extends JPanel implements Observer {

    private Model model;
    public ArrayList<JButton> Buttons;

    /**
     * Create a new View.
     */
    public View(Model model) {
        // Set up the window.
        this.Buttons = new ArrayList<JButton>();
        JPanel tool = new JPanel();
        tool.setLayout(new FlowLayout());
        tool.setBackground(Color.white);
        tool.setPreferredSize(new Dimension(130, 150));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        for (String s : new String[]{"mouse", "eraser", "pen", "line", "circle", "rectangle", "paint"}) {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + s + ".png"));
            JButton b = new JButton(imageIcon);
            b.setName(s);
            b.setPreferredSize(new Dimension(40, 40));
            b.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            b.addActionListener(new ToolActionListener());
            Buttons.add(b);
            tool.add(b);
        }

        // Add undo and redo buttons
        JButton undoButton = new JButton("Undo");
        undoButton.setPreferredSize(new Dimension(100, 30));
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.undo();
                repaint();
            }
        });
        
        JButton redoButton = new JButton("Redo");
        redoButton.setPreferredSize(new Dimension(100, 30));
        redoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.redo();
                repaint();
            }
        });

        tool.add(undoButton);
        tool.add(redoButton);

        JPanel setcolor = new JPanel();
        setcolor.setLayout(new FlowLayout());
        setcolor.setBackground(Color.white);
        setcolor.setPreferredSize(new Dimension(130, 220));
        for (String s : new String[]{"blue", "orange", "red", "yellow", "green", "pink"}) {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + s + ".png"));
            JButton b = new JButton(imageIcon);
            b.setName(s);
            b.setPreferredSize(new Dimension(50, 40));
            b.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            b.addActionListener(new ColorActionListener());
            Buttons.add(b);
            setcolor.add(b);
        }
        JButton choose = new JButton("Choose");
        choose.setName("Choose");
        choose.setPreferredSize(new Dimension(100, 30));
        choose.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        choose.addActionListener(new ChooseActionListener());
        JButton cl = new JButton("Color");
        cl.setName("color");
        choose.setPreferredSize(new Dimension(100, 30));
        choose.setBorder(BorderFactory.createLineBorder(Color.white, 4));
        model.curcolor = cl;
        Buttons.add(choose);
        setcolor.add(cl);
        setcolor.add(choose);

        JPanel select_line = new JPanel();
        select_line.setLayout(new FlowLayout());
        select_line.setBackground(Color.white);
        select_line.setPreferredSize(new Dimension(130, 130));
        for (String s : new String[]{"1", "2", "3", "4"}) {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + s + ".png"));
            JButton b = new JButton(imageIcon);
            b.setName(s);
            b.setPreferredSize(new Dimension(100, 20));
            b.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            b.addActionListener(new SticknessActionListener());
            Buttons.add(b);
            select_line.add(b);
        }

        this.add(tool);
        this.add(setcolor);
        this.add(select_line);
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        model.addObserver(this);
    }

    public class ToolActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton bt = (JButton) e.getSource();
            model.ChangeToolButton(bt);
            model.tool = bt.getName();
        }
    }

    public class ColorActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton bt = (JButton) e.getSource();
            Color c = Color.black;
            switch (bt.getName()) {
                case "blue":
                    c = Color.blue;
                    break;
                case "red":
                    c = Color.red;
                    break;
                case "yellow":
                    c = Color.yellow;
                    break;
                case "orange":
                    c = Color.orange;
                    break;
                case "green":
                    c = Color.green;
                    break;
                case "pink":
                    c = Color.pink;
                    break;
            }
            model.color = c;
            model.ChangeColorButton(bt);
            if (model.someselect) {
                model.notifyCanvasObserver();
            }
        }
    }

    public class SticknessActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton bt = (JButton) e.getSource();
            model.ChangeSticknessButton(bt);
            model.ChangeStickness(bt);
            if (model.someselect) {
                model.notifyCanvasObserver();
            }
        }
    }

    public class ChooseActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton bt = (JButton) e.getSource();
            Color c = JColorChooser.showDialog(null, "Color Dialog", Color.BLACK);
            model.color = c;
            model.ChangeColorButton(bt);
            if (model.someselect) {
                model.notifyCanvasObserver();
            }
        }
    }

    /**
     * Update with data from the model.
     */
    @Override
    public void update(Object observable) {
        if (model.theItem != null) {
            model.color = model.theItem.getColor();
            model.stickness = model.theItem.getStickness();
        }

        for (JButton b : Buttons) {
            String s = b.getName();
            if (s.equals("red") && model.color.equals(Color.red)) {
                model.ChangeColorButton(b);
            } else if (s.equals("blue") && model.color.equals(Color.blue)) {
                model.ChangeColorButton(b);
            } else if (s.equals("orange") && model.color.equals(Color.orange)) {
                model.ChangeColorButton(b);
            } else if (s.equals("yellow") && model.color.equals(Color.yellow)) {
                model.ChangeColorButton(b);
            } else if (s.equals("pink") && model.color.equals(Color.pink)) {
                model.ChangeColorButton(b);
            } else if (s.equals("green") && model.color.equals(Color.green)) {
                model.ChangeColorButton(b);
            } else if (s.equals("choose")) {
                model.ChangeColorButton(b);
            }

            if (s.equals("1") && model.stickness == 1) {
                model.ChangeSticknessButton(b);
            } else if (s.equals("2") && model.stickness == 2) {
                model.ChangeSticknessButton(b);
            } else if (s.equals("3") && model.stickness == 3) {
                model.ChangeSticknessButton(b);
            } else if (s.equals("4") && model.stickness == 4) {
                model.ChangeSticknessButton(b);
            }
        }
    }
}
