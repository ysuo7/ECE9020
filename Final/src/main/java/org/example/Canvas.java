package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Canvas extends JComponent implements Observer {
    private Model model;
    Point M = new Point(); // mouse point
    Point C = new Point();

    int countline = 0;
    int countcircle = 0;
    int countrectangel = 0;
    boolean gotnotify = false;


    Canvas(Model model){

        super();
        this.model = model;
        model.addObserver(this);
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                M.x = e.getX();
                M.y = e.getY();
                if (model.tool == "line"){
                    if (countline == 0){
                        C.x = e.getX();
                        C.y = e.getY();
                        countline +=1;
                    } else {
                        Point p1 =  new Point(C.x, C.y);
                        Point p2 = new Point(e.getX(), e.getY());
                         line item = new line(model.lastbutton.getName(), model.stickness, model.color, null, false, false, p1, p2);
                         model.addItem(item);
                         countline -=1;
                    }
                } else if (model.tool == "circle"){
                    if (countcircle == 0){
                        C.x = e.getX();
                        C.y = e.getY();
                        countcircle +=1;
                    } else {
                        Point p1 =  new Point(C.x, C.y);
                        Point p2 = new Point(e.getX(), e.getY());
                        circle item = new circle(model.lastbutton.getName(), model.stickness, model.color,null, false, false, p1, p2);
                        model.addItem(item);
                        countcircle -=1;
                    }
                } else if(model.tool == "rectangle"){
                    if (countrectangel == 0){
                        C.x = e.getX();
                        C.y = e.getY();
                        countrectangel +=1;
                    } else {
                        Point p1 =  new Point(C.x, C.y);
                        Point p2 = new Point(e.getX(), e.getY());
                        rectangle item = new rectangle(model.lastbutton.getName(), model.stickness, model.color, null, false, false, p1,p2);
                        model.addItem(item);
                        countrectangel -=1;
                    }
                } else if (model.tool == "eraser"){
                    Point p = new Point();
                    p.x = e.getX();
                    p.y = e.getY();
                    for (function item : model.Items){
                        if(item.closepoint(p)){
                            model.removeItem(item);
                            break;
                        }
                    }
                } else if (model.tool == "paint"){
                    Point p = new Point();
                    p.x = e.getX();
                    p.y = e.getY();
                    for (function item : model.Items){
                        if(item.closepoint(p)){
                            item.fillit(model.color);
                            break;
                        }
                    }
                } else if (model.tool == "mouse"){
                    Point p = new Point();
                    p.x = e.getX();
                    p.y = e.getY();
                    for (function item : model.Items){
                        item.beselect(false);
                        model.theItem=null;
                        model.someselect = false;
                    }
                    for (function item : model.Items){
                        if(item.closepoint(p)){
                            item.beselect(true);
                            model.someselect = true;
                            model.theItem = item;
                            model.notifyViewObserver();
                            break;
                        }
                    }
                }
                repaint();
            }
        });

        /*this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(model.someselect = true && model.theItem.closepoint(e.getPoint())){
                    System.out.println("Press");
                    M = e.getPoint();
                    repaint();
                }
            }
        });*/

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(model.someselect == true ) {
                    int dx = e.getX() - M.x;
                    int dy = e.getY() - M.y;
                    model.theItem.changeposition(dx, dy);
                    M = e.getPoint();
                    repaint();
                }
            }
        });


        this.addMouseMotionListener(new MouseAdapter(){
            public void mouseMoved(MouseEvent e){
                M.x = e.getX();
                M.y = e.getY();
                repaint();
            }
        });

    }
    // custom graphics drawing
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // cast to get 2D drawing methods
        Graphics2D g2 = (Graphics2D) g;

        if(this.gotnotify){
            rp(g2);
        }

        if (model.tool == "line"){
            if(countline == 1){
                g2.setColor(model.color);
                g2.setStroke(new BasicStroke(model.stickness));
                g2.drawLine(C.x, C.y, M.x, M.y);
            }
        } else if (model.tool == "circle"){
            if(countcircle == 1){
                g2.setColor(model.color);
                g2.setStroke(new BasicStroke(model.stickness));
                int x = Math.min(C.x, M.x);
                int y = Math.min(C.y, M.y);
                int width = Math.max(C.x, M.x) - Math.min(C.x, M.x);
                int length = Math.max(C.y, M.y) - Math.min(C.y, M.y);
                g2.drawOval(x,y,width,length);
            }
        } else if (model.tool == "rectangle"){
            if(countrectangel == 1){
                g2.setColor(model.color);
                g2.setStroke(new BasicStroke(model.stickness));
                int x = Math.min(C.x, M.x);
                int y = Math.min(C.y, M.y);
                int width = Math.max(C.x, M.x) - Math.min(C.x, M.x);
                int length = Math.max(C.y, M.y) - Math.min(C.y, M.y);
                g2.drawRect(x,y,width,length);
            }
        }

        for(function item: model.Items){
            item.paint(g2);
        }
        if(this.model.someselect){
            this.model.theItem.paint(g2);
        }

    }


    public void rp(Graphics2D g){
        /*System.out.println("repaint");
        g.setColor(this.model.color);
        float[] arr = {4.0f,2.0f};
        BasicStroke stroke = new BasicStroke(this.model.stickness, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 1.0f,arr,0);
        g.setStroke(stroke);*/
        this.model.theItem.reset(this.model.color, this.model.stickness);
        this.model.theItem.paint(g);
        this.gotnotify = false;

    }

    public void save(BufferedWriter bufw){
        try {
            bufw.write(model.Items.size());
            //System.out.println("Size :" + model.Items.size());
            for(function item:model.Items){
                String s = item.getname();
                if(s.equals("line")){
                    bufw.write(1);
                    //System.out.println("line");
                } else if(s.equals("circle")){
                    bufw.write(2);
                    //System.out.println("circle");
                } else if(s.equals("rectangle")){
                    bufw.write(3);
                    //System.out.println("rectangle");
                }

                bufw.write(item.getStickness());
                //System.out.println("Stickness :" + item.getStickness());


                if(item.getisfill()){
                    bufw.write(1);
                    //System.out.println("isfill :" + 1);
                } else {
                    bufw.write(0);
                    //System.out.println("isfill :" + 0);
                }

                if(item.getisselect()){
                    bufw.write(1);
                    ///System.out.println("isselect :" + 1);
                } else {
                    bufw.write(0);
                    //System.out.println("isselect :" + 0);
                }

                if(item.getisfill()){
                    bufw.write(item.getinner().getRGB());
                    //System.out.println("get inner RGB: " + item.getinner().getRGB());
                } else {
                    bufw.write(0);
                    //System.out.println("no inner Color" + 0);
                }

                bufw.write(item.getColor().getRed());
                //System.out.println("red :" + item.getColor().getRed());
                bufw.write(item.getColor().getBlue());
                //System.out.println("blue :" + item.getColor().getBlue());
                bufw.write(item.getColor().getGreen());
                //System.out.println("green :" + item.getColor().getGreen());


                bufw.write(item.getP1().x);
                //System.out.println("Get p1x");
                bufw.write(item.getP2().x);
                //System.out.println("Get p2x");
                bufw.write(item.getP1().y);
                //System.out.println("Get p1y");
                bufw.write(item.getP2().y);
                //System.out.println("Get p2y");
            }
        }
        catch (IOException ex) {

            throw new RuntimeException("Save File Failed");

        }

    }

    public void read(BufferedReader bufr){
        try{
            model.Items.clear();
            int size = bufr.read();
            System.out.println("Size :" + size);
            for(int i = 0; i < size; i++){

                int x = bufr.read();     //name
                System.out.println(x);
                int stickness = bufr.read(); // stickness
                //System.out.println("stixkness :" + stickness);

                int isfill = bufr.read();  // isfill
                //System.out.println("isfill :" + isfill);
                boolean isfillb;
                if(isfill == 1){
                    isfillb = true;
                } else {
                    isfillb = false;
                }

                int isselect = bufr.read();  //isselect
                //System.out.println("isselect :" + isselect);
                boolean isselectb;
                if(isselect == 1){
                    isselectb = true;
                } else {
                    isselectb = false;
                }

                int inner = bufr.read();    //inner color
                Color innercl;
                if(isfillb){
                    //System.out.println("get inner RGB: " + inner);
                    innercl = new Color(inner);
                } else {
                    innercl = null;
                    //System.out.println("get no inner RGB: " + inner);
                }

                int red = bufr.read(); //  color
                //System.out.println("red :" + red);
                int blue = bufr.read();
                //System.out.println("red :" + blue);
                int green = bufr.read();
                //System.out.println("red :" + green);
                Color color = new Color(red, blue, green);

                int p1x = bufr.read();  //p1.x
                //System.out.println("Get p1x");
                int p2x = bufr.read();  //p2.x
                //System.out.println("Get p2x");
                int p1y = bufr.read();  //p1.y
                //System.out.println("Get p1y");
                int p2y = bufr.read();  //p2.y
                //System.out.println("Get p2y");
                Point p1 = new Point(p1x, p1y);
                Point p2 = new Point(p2x, p2y);

                if(x == 1){
                    line item = new line("line", stickness, color, innercl, isfillb, isselectb, p1, p2);
                    model.Items.add(item);
                } else if (x == 2){
                    circle item = new circle("circle", stickness, color, innercl, isfillb, isselectb, p1, p2);
                    model.Items.add(item);
                } else if (x == 3){
                    rectangle item = new rectangle("rectangle", stickness, color, innercl, isfillb, isselectb, p1, p2);
                    model.Items.add(item);
                }

            }

        }


        catch (IOException ex) {

            throw new RuntimeException("Save File Failed");

        }

        repaint();
    }


    public void reset(){
        model.Items.clear();
    }

    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        this.gotnotify = true;
        repaint();
        //System.out.println("Canvas changed!");
    }
}
