package org.example;

import java.awt.*;
import java.awt.geom.Line2D;

public class line extends Item implements function {
    private Model model;

    public line(Model model, String name, int stickness, Color color, Color inner, boolean isfill, boolean isselect, Point p1, Point p2) {
        this.model = model;
        this.name = name;
        this.color = color;
        this.inner = inner;
        this.stickness = stickness;
        this.isfill = isfill;
        this.isselect = isselect;
        this.p1 = new Point();
        this.p2 = new Point();
        this.p1 = p1;
        this.p2 = p2;
    }

    public void paint(Graphics2D g2) {
        if (this.isselect) {
            float[] arr = {4.0f, 2.0f};
            BasicStroke stroke = new BasicStroke(this.stickness, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 1.0f, arr, 0);
            g2.setStroke(stroke);
        } else {
            g2.setStroke(new BasicStroke(this.stickness));
        }
        g2.setColor(this.color);
        g2.drawLine(this.p1.x, this.p1.y, this.p2.x, this.p2.y);
    }

    public void fill(Graphics2D g2) {
        // No fill for line
    }

    public void beselect(Boolean b) {
        model.saveState(); // Save state before modifying
        this.isselect = b;
    }

    @Override
    public boolean getselect() {
        return this.isselect;
    }

    public void reset(Color color, int stickness) {
        model.saveState(); // Save state before modifying
        this.color = color;
        this.stickness = stickness;
    }

    public void fillit(Color color) {
        // No fill for line
    }

    @Override
    public void changeposition(int dx, int dy) {
        model.saveState(); // Save state before modifying
        this.p1.x += dx;
        this.p2.x += dx;
        this.p1.y += dy;
        this.p2.y += dy;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public int getStickness() {
        return this.stickness;
    }

    public boolean closepoint(Point p) {
        Line2D line = new Line2D.Double(this.p1, this.p2);
        return line.ptLineDist(p) < 2;
    }

    @Override
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
