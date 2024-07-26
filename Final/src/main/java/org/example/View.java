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
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
        for(String s: new String[] {"mouse", "eraser", "line", "circle", "rectangle", "paint"}){
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + s + ".png"));
            JButton b = new JButton(imageIcon);
            b.setName(s);
            b.setPreferredSize(new Dimension(40,40));
            b.setBorder(BorderFactory.createLineBorder(Color.white,2));
            b.addActionListener(new ToolActionListener());
            Buttons.add(b);
            tool.add(b);
        }



        JPanel setcolor = new JPanel();
        setcolor.setLayout(new FlowLayout());
        setcolor.setBackground(Color.white);
        setcolor.setPreferredSize(new Dimension(130, 220));
        for(String s: new String[]{"blue", "orange", "red", "yellow", "green", "pink"}){
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + s + ".png"));
            JButton b = new JButton(imageIcon);
            b.setName(s);
            b.setPreferredSize(new Dimension(50, 40));
            b.setBorder(BorderFactory.createLineBorder(Color.white,2));
            b.addActionListener(new ColorActionListener());
            Buttons.add(b);
            setcolor.add(b);

        }
        JButton choose = new JButton("Choose");
        choose.setName("Choose");
        choose.setPreferredSize(new Dimension(100, 30));
        choose.setBorder(BorderFactory.createLineBorder(Color.white,2));
        choose.addActionListener(new ChooseActionListener());
        JButton cl = new JButton("Color");
        cl.setName("color");
        choose.setPreferredSize(new Dimension(100, 30));
        choose.setBorder(BorderFactory.createLineBorder(Color.white,4));
        model.curcolor = cl;
        Buttons.add(choose);
        setcolor.add(cl);
        setcolor.add(choose);





        JPanel select_line = new JPanel();
        select_line.setLayout(new FlowLayout());
        select_line.setBackground(Color.white);
        select_line.setPreferredSize(new Dimension(130, 130));
        for(String s: new String[]{"1", "2", "3", "4"}){
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + s + ".png"));
            JButton b = new JButton(imageIcon);
            b.setName(s);
            b.setPreferredSize(new Dimension(100,20));
            b.setBorder(BorderFactory.createLineBorder(Color.white,2));
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


    public class ToolActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton bt = (JButton)e.getSource();
            model.ChangeToolButton(bt);
            model.tool = bt.getName();
        }
    }


    public class ColorActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton bt = (JButton)e.getSource();
            Color c = Color.black;
            if(bt.getName() == "blue"){
                c = Color.blue;
            } else if (bt.getName() == "red"){
                c = Color.red;
            } else if (bt.getName() == "yellow"){
                c = Color.yellow;
            } else if (bt.getName() == "orange"){
                c = Color.orange;
            } else if (bt.getName() == "green"){
                c = Color.green;
            } else if (bt.getName() == "pink"){
                c = Color.pink;
            }
            model.color = c;
            model.ChangeColorButton(bt);
            if(model.someselect){
                model.notifyCanvasObserver();
            }

        }
    }


    public class SticknessActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton bt = (JButton)e.getSource();
            model.ChangeSticknessButton(bt);
            model.ChangeStickness(bt);
            if(model.someselect){
                model.notifyCanvasObserver();
            }
        }
    }


    public class ChooseActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton bt = (JButton)e.getSource();
            Color c = JColorChooser.showDialog(null,"Color Dialog",Color.BLACK);
            model.color = c;
            model.ChangeColorButton(bt);
            if(model.someselect){
                model.notifyCanvasObserver();
            }
        }
    }


    /**
     * Update with data from the model.
     */
    @Override
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        model.color = model.theItem.getColor();
        model.stickness = model.theItem.getStickness();
        for(JButton b : Buttons){
            String s = b.getName();
            if(s == "red" && model.color == Color.red){
                model.ChangeColorButton(b);
            } else if (s == "blue" && model.color == Color.blue){
                model.ChangeColorButton(b);
            } else if (s == "orange" && model.color == Color.orange){
                model.ChangeColorButton(b);
            } else if (s == "yellow" && model.color == Color.yellow){
                model.ChangeColorButton(b);
            } else if (s == "pink" && model.color == Color.pink){
                model.ChangeColorButton(b);
            } else if (s == "green" && model.color == Color.green){
                model.ChangeColorButton(b);
            } else if (s == "choose"){
                model.ChangeColorButton(b);
            }

            if (s == "1" && model.stickness == 1){
                model.ChangeSticknessButton(b);
            } else if (s == "2" && model.stickness == 2){
                model.ChangeSticknessButton(b);
            } else if (s == "3" && model.stickness == 3){
                model.ChangeSticknessButton(b);
            } else if (s == "4" && model.stickness == 4){
                model.ChangeSticknessButton(b);
            }
        }
    }
}
