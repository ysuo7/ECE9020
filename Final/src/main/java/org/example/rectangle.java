package org.example;

import java.awt.*;
import java.awt.geom.Line2D;

public class rectangle extends Item implements function {


    Line2D top;
    Line2D buttom;
    Line2D left;
    Line2D right;


    public rectangle(String name, int stickness, Color color, Color inner, boolean isfill, boolean isselect, Point p1, Point p2){
        this.name = name;
        this.color = color;
        this.stickness = stickness;
        this.isfill = isfill;
        this.inner = inner;
        this.isselect = isselect;
        Point buttomleft = new Point();
        Point buttomright = new Point();
        Point topleft = new Point();
        Point topright = new Point();
        this.p1 = new Point();
        this.p2 = new Point();
        this.p1 = p1;
        this.p2 = p2;
        topright.x = Math.min(this.p1.x, this.p2.x);
        topright.y = Math.min(this.p1.y, this.p2.y);
        topleft.x = Math.max(this.p1.x, this.p2.x);
        topleft.y = topright.y;
        buttomright.x = topright.x;
        buttomright.y = Math.max(this.p1.y, this.p2.y);
        buttomleft.x = topleft.x;
        buttomleft.y = buttomright.y;
        this.top = new Line2D.Double(topright, topleft);
        this.left = new Line2D.Double(buttomleft, topleft);
        this.right = new Line2D.Double(topright, buttomright);
        this.buttom = new Line2D.Double(buttomleft, buttomright);
    }

    public void paint(Graphics2D g2) {
        if(this.isselect){
            float[] arr = {4.0f,2.0f};
            BasicStroke stroke = new BasicStroke(this.stickness, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 1.0f,arr,0);
            g2.setStroke(stroke);
        } else {
            g2.setStroke(new BasicStroke(this.stickness));
        }
        g2.setColor(this.color);
        int x = Math.min(this.p1.x, this.p2.x);
        int y = Math.min(this.p1.y, this.p2.y);
        int width = Math.max(this.p1.x,this.p2.x) - Math.min(this.p1.x, this.p2.x);
        int length = Math.max(this.p1.y, this.p2.y) - Math.min(this.p1.y, this.p2.y);
        g2.drawRect(x, y, width, length);
        if(this.isfill){
            this.fill(g2);
        }
    }

    public void fill(Graphics2D g2) {
        g2.setColor(this.inner);
        int x = Math.min(this.p1.x, this.p2.x);
        int y = Math.min(this.p1.y, this.p2.y);
        int width = Math.max(this.p1.x,this.p2.x) - Math.min(this.p1.x, this.p2.x);
        int length = Math.max(this.p1.y, this.p2.y) - Math.min(this.p1.y, this.p2.y);
        g2.fillRect(x, y, width, length);
    }


    public void beselect(Boolean b) {
        this.isselect = b;
    }


    public void changeposition(int dx, int dy) {
        this.p1.x += dx;
        this.p2.x += dx;
        this.p1.y += dy;
        this.p2.y += dy;
        Point buttomleft = new Point();
        Point buttomright = new Point();
        Point topleft = new Point();
        Point topright = new Point();
        topright.x = Math.min(this.p1.x, this.p2.x);
        topright.y = Math.min(this.p1.y, this.p2.y);
        topleft.x = Math.max(this.p1.x, this.p2.x);
        topleft.y = topright.y;
        buttomright.x = topright.x;
        buttomright.y = Math.max(this.p1.y, this.p2.y);
        buttomleft.x = topleft.x;
        buttomleft.y = buttomright.y;
        this.top = new Line2D.Double(topright, topleft);
        this.left = new Line2D.Double(buttomleft, topleft);
        this.right = new Line2D.Double(topright, buttomright);
        this.buttom = new Line2D.Double(buttomleft, buttomright);
    }


    public boolean getselect() {
        return this.isselect;
    }

    public void reset(Color color, int stickness){
        this.color = color;
        this.stickness = stickness;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public int getStickness() {
        return this.stickness;
    }


    public void fillit(Color color) {
        this.isfill = true;
        this.inner = color;
    }

    public boolean closepoint(Point p){
        if((this.top.ptLineDist(p) < 3) || (this.buttom.ptLineDist(p) < 3) || (this.right.ptLineDist(p) < 3) || (this.left.ptLineDist(p) < 3)){
            return true;
        } else {
            return false;
        }
    }



    public boolean getisfill() {
        return this.isfill;
    }

    @Override
    public boolean getisselect() {
        return this.isselect;
    }

    @Override
    public Color getinner() {
        return this.inner;
    }

    @Override
    public Point getP1() {
        return this.p1;
    }

    @Override
    public Point getP2() {
        return this.p2;
    }

    @Override
    public String getname() {
        return this.name;
    }
}