package object;

import javax.imageio.ImageIO;

public class SpeedImg extends SuperObject{
    
    public SpeedImg(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Tiles/boots.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
