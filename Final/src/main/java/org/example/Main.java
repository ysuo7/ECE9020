package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main {

    private static FileDialog openDia, saveDia;
    private static File file;

    public static void main(String[] args) {

        Model model = new Model();
        View view = new View(model);
        view.setBackground(Color.white);
        view.setPreferredSize(new Dimension(150, 600));

        Canvas canvas = new Canvas(model);
        canvas.setBackground(Color.white);
        canvas.setBorder(BorderFactory.createLineBorder(Color.blue, 3));

        JFrame frame = new JFrame("frame");
        frame.setLayout(new BorderLayout());
        frame.setTitle("Good Luck Have Fun :)");
        frame.setMinimumSize(new Dimension(700, 550));
        frame.setSize(800, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(view, BorderLayout.LINE_START);

        openDia = new FileDialog(frame, "Open", FileDialog.LOAD);
        saveDia = new FileDialog(frame, "Save", FileDialog.SAVE);

        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        for (String s : new String[]{"New", "Load", "Save"}) {
            JMenuItem mi = new JMenuItem(s);
            if (s.equals("Save")) {
                mi.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (file == null) {
                            saveDia.setVisible(true);
                            String dirPath = saveDia.getDirectory();
                            String fileName = saveDia.getFile();
                            if (dirPath == null || fileName == null)
                                return;

                            file = new File(dirPath, fileName);
                        }

                        try {
                            BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
                            canvas.save(bufw);
                            bufw.close();
                        } catch (IOException ex) {
                            throw new RuntimeException("Save File Failed", ex);
                        }
                    }
                });
            } else if (s.equals("Load")) {
                mi.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openDia.setVisible(true);
                        String dirPath = openDia.getDirectory();
                        String fileName = openDia.getFile();
                        if (dirPath == null || fileName == null) {
                            return;
                        }

                        file = new File(dirPath, fileName);

                        try {
                            BufferedReader bufr = new BufferedReader(new FileReader(file));
                            canvas.read(bufr);
                            bufr.close();
                        } catch (IOException ex) {
                            throw new RuntimeException("Open File Failed", ex);
                        }
                    }
                });
            } else if (s.equals("New")) {
                mi.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        canvas.reset();
                    }
                });
            }
            menu.add(mi);
        }

        // Add Undo and Redo menu items
        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.undo();
                canvas.repaint();
            }
        });
        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.redo();
                canvas.repaint();
            }
        });

        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        menubar.add(menu);
        menubar.add(editMenu);

        frame.add(menubar, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}
