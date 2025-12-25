package object;

import javax.imageio.ImageIO;

public class FastPotion extends SuperObject{
    
    public FastPotion(){

        name = "Fast";
        try {
            image = ImageIO.read(
                getClass().getResourceAsStream("/Tiles/speedPotion.png")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
