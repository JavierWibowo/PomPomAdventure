package object;

import javax.imageio.ImageIO;

public class SlowTrap extends SuperObject{

    public SlowTrap(){

        name = "Slow";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Tiles/slowOrb.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
