package com.company;

import javax.swing.*;
//JFrame is a window
public class Snake extends JFrame {
    Snake(){
        Board bd = new Board(); //obj of board class  in snake constructor
        add(bd); //need to pass the obj of board to add at frame
        pack();  //set the window frame size
        setLocationRelativeTo(null); //set the location and position of window at centre
        setTitle("Snake Game Slither");
        setResizable(false);
    }

    public static void main(String[] args) {
        new Snake().setVisible(true);  //By default frame is false
    }
}
