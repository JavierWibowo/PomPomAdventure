package object;

import javax.imageio.ImageIO;

public class StuckTrap extends SuperObject{

    public StuckTrap(){

        name = "Stuck";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Tiles/electricOrb.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
