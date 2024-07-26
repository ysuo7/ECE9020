package org.example;

import java.awt.*;

public interface  function{
    public void paint(Graphics2D g2);
    public void fill(Graphics2D g2);
    public boolean closepoint(Point p1);
    public void fillit(Color color);
    public void beselect(Boolean b);
    public boolean getselect();
    public void reset(Color color, int stickness);
    public void changeposition(int dx, int dy);
    public String getname();
    public boolean getisfill();
    public boolean getisselect();
    public Color getinner();
    public Color getColor();
    public int getStickness();
    public Point getP1();
    public Point getP2();
}
