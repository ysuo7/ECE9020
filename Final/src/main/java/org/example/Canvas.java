package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JComponent implements Observer {
    private Model model;
    Point M = new Point(); // current mouse point
    Point C = new Point(); // starting point
    int countline = 0;
    int countcircle = 0;
    int countrectangle = 0;
    boolean gotnotify = false;
    private List<Point> freehandPoints;

    Canvas(Model model) {
        super();
        this.model = model;
        this.freehandPoints = new ArrayList<>();
        model.addObserver(this);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                M.x = e.getX();
                M.y = e.getY();
                if (model.tool != null) {
                    switch (model.tool) {
                        case "pen":
                            freehandPoints.clear();
                            freehandPoints.add(new Point(M));
                            break;
                        case "line":
                            if (countline == 0) {
                                C.x = e.getX();
                                C.y = e.getY();
                                countline = 1;
                            }
                            break;
                        case "circle":
                            if (countcircle == 0) {
                                C.x = e.getX();
                                C.y = e.getY();
                                countcircle = 1;
                            }
                            break;
                        case "rectangle":
                            if (countrectangle == 0) {
                                C.x = e.getX();
                                C.y = e.getY();
                                countrectangle = 1;
                            }
                            break;
                        case "eraser":
                            eraseItemAt(e.getPoint());
                            break;
                        case "paint":
                            paintEdgeAt(e.getPoint());
                            break;
                        case "mouse":
                            selectItemAt(e.getPoint());
                            break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (model.tool != null) {
                    switch (model.tool) {
                        case "pen":
                            if (!freehandPoints.isEmpty()) {
                                pen item = new pen(model.lastbutton.getName(), model.stickness, model.color, new ArrayList<>(freehandPoints), false, false, null);
                                model.addItem(item);
                                freehandPoints.clear();
                            }
                            break;
                        case "line":
                            if (countline == 1) {
                                Point p1 = new Point(C.x, C.y);
                                Point p2 = new Point(e.getX(), e.getY());
                                line item = new line(model.lastbutton.getName(), model.stickness, model.color, null, false, false, p1, p2);
                                model.addItem(item);
                                countline = 0;
                            }
                            break;
                        case "circle":
                            if (countcircle == 1) {
                                Point p1 = new Point(C.x, C.y);
                                Point p2 = new Point(e.getX(), e.getY());
                                circle item = new circle(model.lastbutton.getName(), model.stickness, model.color, null, false, false, p1, p2);
                                model.addItem(item);
                                countcircle = 0;
                            }
                            break;
                        case "rectangle":
                            if (countrectangle == 1) {
                                Point p1 = new Point(C.x, C.y);
                                Point p2 = new Point(e.getX(), e.getY());
                                rectangle item = new rectangle(model.lastbutton.getName(), model.stickness, model.color, null, false, false, p1, p2);
                                model.addItem(item);
                                countrectangle = 0;
                            }
                            break;
                        case "mouse":
                            model.theItem = null;
                            model.someselect = false;
                            break;
                    }
                }
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (model.tool != null) {
                    switch (model.tool) {
                        case "pen":
                            freehandPoints.add(new Point(e.getX(), e.getY()));
                            repaint();
                            break;
                        case "line":
                            M.x = e.getX();
                            M.y = e.getY();
                            repaint();
                            break;
                        case "circle":
                            M.x = e.getX();
                            M.y = e.getY();
                            repaint();
                            break;
                        case "rectangle":
                            M.x = e.getX();
                            M.y = e.getY();
                            repaint();
                            break;
                        case "eraser":
                            eraseItemAt(e.getPoint());
                            repaint();
                            break;
                        case "paint":
                            paintEdgeAt(e.getPoint());
                            repaint();
                            break;
                        case "mouse":
                            if (model.someselect && model.theItem != null) {
                                int dx = e.getX() - M.x;
                                int dy = e.getY() - M.y;
                                model.theItem.changeposition(dx, dy);
                                M = e.getPoint();
                                repaint();
                            }
                            break;
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                M.x = e.getX();
                M.y = e.getY();
                repaint();
            }
        });
    }

    private void eraseItemAt(Point point) {
        for (function item : model.Items) {
            if (item.closepoint(point)) {
                model.removeItem(item);
                break;
            }
        }
    }

    private void paintEdgeAt(Point point) {
        for (function item : model.Items) {
            if (item.closepoint(point)) {
                item.reset(model.color, item.getStickness());
                break;
            }
        }
    }

    private void selectItemAt(Point point) {
        for (function item : model.Items) {
            item.beselect(false); // Deselect all items first
        }
        model.someselect = false;
        for (function item : model.Items) {
            if (item.closepoint(point)) {
                item.beselect(true);
                model.theItem = item;
                model.someselect = true;
                break;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (this.gotnotify) {
            rp(g2);
        }

        if (model.tool != null) {
            switch (model.tool) {
                case "line":
                    if (countline == 1) {
                        g2.setColor(model.color);
                        g2.setStroke(new BasicStroke(model.stickness));
                        g2.drawLine(C.x, C.y, M.x, M.y);
                    }
                    break;
                case "circle":
                    if (countcircle == 1) {
                        g2.setColor(model.color);
                        g2.setStroke(new BasicStroke(model.stickness));
                        int x = Math.min(C.x, M.x);
                        int y = Math.min(C.y, M.y);
                        int width = Math.max(C.x, M.x) - Math.min(C.x, M.x);
                        int length = Math.max(C.y, M.y) - Math.min(C.y, M.y);
                        g2.drawOval(x, y, width, length);
                    }
                    break;
                case "rectangle":
                    if (countrectangle == 1) {
                        g2.setColor(model.color);
                        g2.setStroke(new BasicStroke(model.stickness));
                        int x = Math.min(C.x, M.x);
                        int y = Math.min(C.y, M.y);
                        int width = Math.max(C.x, M.x) - Math.min(C.x, M.x);
                        int length = Math.max(C.y, M.y) - Math.min(C.y, M.y);
                        g2.drawRect(x, y, width, length);
                    }
                    break;
                case "pen":
                    if (!freehandPoints.isEmpty()) {
                        g2.setColor(model.color);
                        g2.setStroke(new BasicStroke(model.stickness));
                        Point prev = freehandPoints.get(0);
                        for (Point point : freehandPoints) {
                            g2.drawLine(prev.x, prev.y, point.x, point.y);
                            prev = point;
                        }
                    }
                    break;
            }
        }

        for (function item : model.Items) {
            item.paint(g2);
        }

        if (this.model.someselect && this.model.theItem != null) {
            this.model.theItem.paint(g2);
        }
    }

    public void rp(Graphics2D g) {
        if (this.model.theItem != null) {
            this.model.theItem.reset(this.model.color, this.model.stickness);
            this.model.theItem.paint(g);
        }
        this.gotnotify = false;
    }

    public void save(BufferedWriter bufw) {
        try {
            bufw.write(model.Items.size());
            for (function item : model.Items) {
                String s = item.getname();
                if (s.equals("line")) {
                    bufw.write(1);
                } else if (s.equals("circle")) {
                    bufw.write(2);
                } else if (s.equals("rectangle")) {
                    bufw.write(3);
                }

                bufw.write(item.getStickness());

                if (item.getisfill()) {
                    bufw.write(1);
                } else {
                    bufw.write(0);
                }

                if (item.getisselect()) {
                    bufw.write(1);
                } else {
                    bufw.write(0);
                }

                if (item.getisfill()) {
                    bufw.write(item.getinner().getRGB());
                } else {
                    bufw.write(0);
                }

                bufw.write(item.getColor().getRed());
                bufw.write(item.getColor().getBlue());
                bufw.write(item.getColor().getGreen());

                bufw.write(item.getP1().x);
                bufw.write(item.getP2().x);
                bufw.write(item.getP1().y);
                bufw.write(item.getP2().y);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Save File Failed", ex);
        }
    }

    public void read(BufferedReader bufr) {
        try {
            model.Items.clear();
            int size = bufr.read();
            for (int i = 0; i < size; i++) {
                int x = bufr.read();
                int stickness = bufr.read();
                int isfill = bufr.read();
                boolean isfillb = (isfill == 1);

                int isselect = bufr.read();
                boolean isselectb = (isselect == 1);

                int inner = bufr.read();
                Color innercl = isfillb ? new Color(inner) : null;

                int red = bufr.read();
                int blue = bufr.read();
                int green = bufr.read();
                Color color = new Color(red, blue, green);

                int p1x = bufr.read();
                int p2x = bufr.read();
                int p1y = bufr.read();
                int p2y = bufr.read();
                Point p1 = new Point(p1x, p1y);
                Point p2 = new Point(p2x, p2y);

                switch (x) {
                    case 1:
                        line item1 = new line("line", stickness, color, innercl, isfillb, isselectb, p1, p2);
                        model.Items.add(item1);
                        break;
                    case 2:
                        circle item2 = new circle("circle", stickness, color, innercl, isfillb, isselectb, p1, p2);
                        model.Items.add(item2);
                        break;
                    case 3:
                        rectangle item3 = new rectangle("rectangle", stickness, color, innercl, isfillb, isselectb, p1, p2);
                        model.Items.add(item3);
                        break;
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Read File Failed", ex);
        }

        repaint();
    }

    public void reset() {
        model.Items.clear();
    }

    public void update(Object observable) {
        this.gotnotify = true;
        repaint();
    }
}
