package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.EndScreen;
import object.SpeedImg;
import object.WinScreen;

public class UI {
    
    RpgPanel gp;
    Font arial_40;
    BufferedImage speedImg, endImage, winImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTimer = 120;
    DecimalFormat dFormat = new DecimalFormat("#0");
    
    public UI(RpgPanel gp){
        this.gp = gp;

        arial_40 = new Font(Font.MONOSPACED, Font.BOLD , 40);
        SpeedImg spdImg = new SpeedImg();
        speedImg = spdImg.image;
        EndScreen endImg = new EndScreen();
        endImage = endImg.image;
        WinScreen winImg = new WinScreen();
        winImage = winImg.image;
    }

    public void showMessage(String text){
        
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){

        if(gameFinished == true){
            g2.drawImage(winImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
            gp.stopMusic();
            gp.playSE(6);
            gp.gameThread = null;
        }else if(playTimer <= 0){
            g2.drawImage(endImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
            gp.stopMusic();
            gp.playSE(7);
            gp.gameThread = null;
        }else{
            g2.setFont(arial_40);
            g2.setColor(Color.red);
            g2.drawImage(speedImg, gp.TileSize/2, gp.TileSize/2, gp.TileSize, gp.TileSize, null);
            g2.drawString(": " + gp.player.speed, 79, 63);

            //TIME
            playTimer -= (double)1/60;
            g2.drawString("Time Left: " + dFormat.format(playTimer), gp.TileSize*9, 65);

            //MESSAGE
            if(messageOn == true){

                g2.setFont(g2.getFont().deriveFont(31F));
                g2.drawString(message, gp.TileSize/2, gp.TileSize*9);

                messageCounter++;
                if(messageCounter > 90){
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
