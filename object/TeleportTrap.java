package object;

import javax.imageio.ImageIO;

public class TeleportTrap extends SuperObject {
    public TeleportTrap() {

        name = "Teleport";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Tiles/warpHole.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
