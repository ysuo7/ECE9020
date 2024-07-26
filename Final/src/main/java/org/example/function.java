package org.example;

import java.awt.*;

public interface function {
    void paint(Graphics2D g2);
    void fill(Graphics2D g2);
    boolean closepoint(Point p1);
    void fillit(Color color);
    void beselect(Boolean b);
    boolean getselect();
    void reset(Color color, int stickness);
    void changeposition(int dx, int dy);
    String getname();
    boolean getisfill();
    boolean getisselect();
    Color getinner();
    Color getColor();
    int getStickness();
    Point getP1();
    Point getP2();
}
