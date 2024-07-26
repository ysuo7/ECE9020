package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {



    private static FileDialog openDia,saveDia;
    private static File file;

    public static void main(String[] args) {

        Model model = new Model();
        View view = new View(model);
        view.setBackground(Color.white);
        view.setPreferredSize(new Dimension(150, 600));


        Canvas canvas = new Canvas(model);
        canvas.setBackground(Color.white);
        canvas.setBorder(BorderFactory.createLineBorder(Color.blue,3));



        JFrame frame = new JFrame("frame");
        frame.setLayout(new BorderLayout());
        frame.setTitle("Good Luck Have Fun :)");
        frame.setMinimumSize(new Dimension(700, 550));
        frame.setSize(800, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas,BorderLayout.CENTER);
        frame.add(view,BorderLayout.LINE_START);
        /*frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("lalala");
            }
        });*/


        openDia = new FileDialog(frame,"我的打开",FileDialog.LOAD);
        saveDia = new FileDialog(frame,"我的保存",FileDialog.SAVE);

        JMenuBar menubar= new JMenuBar();
        JMenu menu = new JMenu("File");
        for (String s: new String[] {"New", "Load", "Save"}){
            JMenuItem mi = new JMenuItem(s);
            if(s.equals("Save")){
                mi.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(file == null){
                            saveDia.setVisible(true);
                            String dirPath = saveDia.getDirectory();
                            String fileName = saveDia.getFile();
                            if(dirPath == null || fileName == null)

                                return ;

                            file = new File(dirPath,fileName);

                        }

                        try{
                            BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
                            canvas.save(bufw);
                            bufw.close();
                        }

                        catch (IOException ex) {

                            throw new RuntimeException("Save File Failed");

                        }


                    }

                });
            } else if (s.equals("Load")){
                mi.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openDia.setVisible(true);
                        String dirPath = openDia.getDirectory();
                        String fileName = openDia.getFile();
                        if(dirPath == null || fileName == null){
                            return;

                        }

                        file = new File(dirPath, fileName);


                        try{
                            BufferedReader bufr = new BufferedReader(new FileReader(file));
                            canvas.read(bufr);
                            bufr.close();


                        }

                        catch (IOException ex) {

                            throw new RuntimeException("Open File Failed");

                        }
                    }
                });
            } else if (s.equals("New")){
                mi.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        canvas.reset();
                    }
                });
            }
            menu.add(mi);
        }
        menubar.add(menu);

        frame.add(menubar,BorderLayout.NORTH);
        frame.setVisible(true);
    }



}

