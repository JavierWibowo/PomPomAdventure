package main;

import javax.swing.JFrame;

public class rpgGame {
    
    public static void main(String args[]){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PoMPoM AdVeNtUrE");
        
        RpgPanel rpgPanel = new RpgPanel();
        window.add(rpgPanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        rpgPanel.setupGame();
        rpgPanel.startGameThread();
    }
}
