package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {

    Clip clip;
    URL musicULR[] = new URL[30];

    public Music(){

        musicULR[0] = getClass().getResource("/Music/BlueBoyAdventure.wav");
        musicULR[1] = getClass().getResource("/Music/laguTurunIQ.wav");
        musicULR[2] = getClass().getResource("/Music/teleport.wav");
        musicULR[3] = getClass().getResource("/Music/electricZap.wav");
        musicULR[4] = getClass().getResource("/Music/bottleOpen.wav");
        musicULR[5] = getClass().getResource("/Music/slowSfx.wav");
        musicULR[6] = getClass().getResource("/Music/gameWin.wav");
        musicULR[7] = getClass().getResource("/Music/gameLose.wav");
        musicULR[8] = getClass().getResource("/Music/collideSfx.wav");
        musicULR[9] = getClass().getResource("/Music/walkSfx.wav");
    }

    public void setFile(int i){

        try {
            
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicULR[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
        }
    }

    public void play(){
        clip.start();
    } 

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
