package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

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




    public  List<function> Items;
    public void addItem(function item) {this.Items.add(item);}
    public void removeItem(function item){this.Items.remove(item);}

    /** The observers that are watching this model for changes. */
    private List<Observer> observers;

    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList<Observer>();
        this.Items = new ArrayList<function>();
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
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

        //System.out.println(button.getName());
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
        //System.out.println(button.getName());
    }


    public void ChangeSticknessButton(JButton button){

        lastbutton = button;
        lastbutton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));

        if(linebutton != null){
            linebutton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        }
        linebutton = button;
        linebutton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));

        //System.out.println(button.getName());
    }


    public void ChangeStickness(JButton button){
        String name = button.getName();
        if(name == "1"){
            stickness = 1;
        } else if (name == "2"){
            stickness = 2;
        } else if (name == "3"){
            stickness = 3;
        } else if(name == "4"){
            stickness = 4;
        }
    }
}
