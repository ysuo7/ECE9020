package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Model {

    JButton lastbutton = null;
    JButton toolbutton = null;
    JButton colorbutton = null;
    JButton curcolor = null;
    JButton linebutton = null;
    String tool = null;
    int stickness = 1;
    Color color = Color.BLACK;
    Boolean someselect = false;
    function theItem = null;

    public List<function> Items;
    public void addItem(function item) {this.Items.add(item);}
    public void removeItem(function item){this.Items.remove(item);}

    private List<Observer> observers;

    public Model() {
        this.observers = new ArrayList<>();
        this.Items = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public void notifyViewObserver(){
        Observer observer = this.observers.get(0);
        observer.update(this);
    }

    public void notifyCanvasObserver(){
        Observer observer = this.observers.get(1);
        observer.update(this);
    }

    public void ChangeToolButton(JButton button){
        lastbutton = button;
        lastbutton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
        if(toolbutton != null){
            toolbutton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        }
        toolbutton = button;
        toolbutton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
    }

    public void ChangeColorButton(JButton button){
        lastbutton = button;
        lastbutton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
        if(colorbutton != null){
            colorbutton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        }
        colorbutton = button;
        colorbutton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
        curcolor.setForeground(color);
    }

    public void ChangeSticknessButton(JButton button){
        lastbutton = button;
        lastbutton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
        if(linebutton != null){
            linebutton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        }
        linebutton = button;
        linebutton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
    }

    public void ChangeStickness(JButton button){
        String name = button.getName();
        if(name.equals("1")){
            stickness = 1;
        } else if (name.equals("2")){
            stickness = 2;
        } else if (name.equals("3")){
            stickness = 3;
        } else if(name.equals("4")){
            stickness = 4;
        }
    }
}
