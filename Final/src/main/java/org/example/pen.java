package org.example;

import java.awt.*;
import java.util.List;

public class pen extends Item implements function {
    private Model model;
    private List<Point> points;

    public pen(Model model, String name, int stickness, Color color, List<Point> points, boolean isfill, boolean isselect, Point p1) {
        this.model = model;
        this.name = name;
        this.color = color;
        this.stickness = stickness;
        this.isfill = isfill;
        this.isselect = isselect;
        this.points = points;
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
        Point prev = points.get(0);
        for (Point point : points) {
            g2.drawLine(prev.x, prev.y, point.x, point.y);
            prev = point;
        }
    }

    public void fill(Graphics2D g2) {
        // No fill for pen
    }

    public void fillit(Color color) {
        // No fill for pen
    }

    public void beselect(Boolean b) {
        model.saveState(); // Save state before modifying
        this.isselect = b;
    }

    public boolean getselect() {
        return this.isselect;
    }

    public void changeposition(int dx, int dy) {
        model.saveState(); // Save state before modifying
        for (Point point : points) {
            point.x += dx;
            point.y += dy;
        }
    }

    public void reset(Color color, int stickness) {
        model.saveState(); // Save state before modifying
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

    public boolean closepoint(Point p) {
        for (Point point : points) {
            if (point.distance(p) < 3) {
                return true;
            }
        }
        return false;
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
        return null; // No inner color for pen
    }

    @Override
    public Point getP1() {
        return points.get(0);
    }

    @Override
    public Point getP2() {
        return points.get(points.size() - 1);
    }

    @Override
    public String getname() {
        return this.name;
    }
}
