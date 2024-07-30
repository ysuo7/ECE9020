package org.example;

import java.awt.*;
import java.io.Serializable;

public class Item implements Serializable {
    String name;
    int stickness;
    Boolean isfill;
    Boolean isselect;
    Color inner;
    Color color;
    Point p1;
    Point p2;

    public Item(){
        this.name = null;
        this.color = Color.BLACK;
        this.stickness = 1;
        this.isfill = false;
        this.inner = null;
        this.isselect = false;
        this.p1 = new Point();
        this.p2 = new Point();
        this.p1.x = 0;
        this.p1.y = 0;
        this.p2.x = 0;
        this.p2.y = 0;
    }

}